package com.bestplat.framework.persistence.startup;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bestplat.framework.Lang;
import com.bestplat.framework.log.Logs;
import com.bestplat.framework.startup.Startup;

/**
 * 数据库初始化启动器
 * 
 * @author lujijiang
 * 
 */
public class DatabaseInitializer {
	/**
	 * 连接池
	 */
	private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();
	/**
	 * 数据源
	 */
	@Autowired
	private DataSource dataSource;

	/**
	 * SQL文件路径
	 */
	private List<String> sqlFiles;

	/**
	 * 是否忽略失败
	 */
	private boolean ignoreFailure;

	/**
	 * 文件字符编码
	 */
	private String encoding = "UTF-8";

	public void setSqlFiles(List<String> sqlFiles) {
		this.sqlFiles = sqlFiles;
	}

	public void setIgnoreFailure(boolean ignoreFailure) {
		this.ignoreFailure = ignoreFailure;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 初始化方法（最先执行）
	 * 
	 * @throws SQLException
	 */
	@Startup(Integer.MAX_VALUE)
	public void initialize() throws SQLException {
		Logs.info("数据库初始化开始");
		if (sqlFiles != null) {
			Connection connection = dataSource.getConnection();
			// 开启事务，确保整个初始化过程是一个事务，要么全成功，要么全失败
			connection.setAutoCommit(false);
			connectionThreadLocal.set(connection);
			try {
				for (String sqlFile : sqlFiles) {
					String sqlText = readSqlText(sqlFile);
					executeSqlText(sqlFile, sqlText);
				}
				connection.commit();
			} catch (Exception e) {
				connection.rollback();
				Logs.error(e, "数据库初始化失败，事务已回滚");
				if (!ignoreFailure) {
					throw Lang.wrapCause(e);
				}
			} finally {
				connection.close();
				connectionThreadLocal.remove();
			}
		}
		Logs.info("数据库初始化结束");
	}

	/**
	 * 执行SQL脚本
	 * 
	 * @param sqlFile
	 * @param sqlText
	 */
	private void executeSqlText(String sqlFile, String sqlText) {
		Iterator<String> sqlIterator = toSqls(sqlText).iterator();
		while (sqlIterator.hasNext()) {
			final String sql = sqlIterator.next();
			Logs.info("Begin execute sql(%s) which from sql file(%s)", sql,
					sqlFile);
			int count = executeSql(sql);
			Logs.info(
					"Execute sql(%s) which from sql file(%s) done,the number of rows affected is %d",
					sql, sqlFile, count);
		}
	}

	/**
	 * 执行SQL
	 * 
	 * @param sql
	 * @return
	 */
	private int executeSql(String sql) {
		try {
			Connection connection = connectionThreadLocal.get();
			int effect = 0;
			try {
				Statement statement = connection.createStatement();
				try {
					effect = statement.executeUpdate(sql);
				} finally {
					statement.close();
				}
			} catch (Exception e) {
				throw Lang.wrapCause(e);
			}
			return effect;
		} catch (Exception e) {
			throw Lang.wrapCause(e);
		}
	}

	/**
	 * 读取SQL脚本
	 * 
	 * @param sqlFile
	 * @return
	 */
	private String readSqlText(String sqlFile) {
		if (sqlFile == null) {
			throw new IllegalStateException("Sql file path should be null");
		}
		sqlFile = sqlFile.trim();
		if (sqlFile.length() == 0) {
			throw new IllegalStateException("Sql file path should be empty");
		}
		if (sqlFile.toLowerCase().startsWith("classpath")) {
			int p = sqlFile.indexOf(":");
			if (p == -1 || p == sqlFile.length() - 1) {
				throw new IllegalStateException(String.format(
						"Sql file path(%s) not a valid path", sqlFile));
			}
			String realClasspath = sqlFile.substring(p + 1);
			try {
				InputStream is = DatabaseInitializer.class
						.getResourceAsStream(realClasspath);
				if (is == null) {
					throw new IllegalStateException(
							String.format(
									"Sql file path(%s) not a exists file path",
									sqlFile));
				}
				String sqlText = IOUtils.toString(is, encoding);
				return sqlText;
			} catch (Exception e) {
				throw Lang.wrapCause(e);
			}
		}
		if (sqlFile.toLowerCase().startsWith("file")) {
			int p = sqlFile.indexOf(":");
			if (p == -1 || p == sqlFile.length() - 1) {
				throw new IllegalStateException(String.format(
						"Sql file path(%s) not a valid path", sqlFile));
			}
			String realFilePath = sqlFile.substring(p + 1);
			try {
				InputStream is = new FileInputStream(realFilePath);
				String sqlText = IOUtils.toString(is, encoding);
				return sqlText;
			} catch (Exception e) {
				throw Lang.wrapCause(e);
			}
		}
		throw Lang.newCause(UnsupportedOperationException.class,
				"Sql file path(%s) not a valid path", sqlFile);
	}

	/**
	 * 将SQL脚本转换为SQL语句，并去除其中的注释部分
	 * 
	 * @param sqlText
	 * @return
	 */
	private static List<String> toSqls(String sqlText) {
		List<String> sqls = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		boolean isNote = false;
		for (int i = 0; i < sqlText.length(); i++) {
			char c = sqlText.charAt(i);
			if (!isNote) {
				if (c == '-') {
					if (i + 1 < sqlText.length()
							&& sqlText.charAt(i + 1) == '-') {// 是SQL注释，略过
						isNote = true;
						i++;
						continue;
					}
				}
				if (c == ';') {
					String sql = sb.toString().trim();
					if (sql.length() > 0) {
						sqls.add(sql);
					}
					sb.setLength(0);
					continue;
				}
				sb.append(c);
			} else {
				if (c == '\n') {
					isNote = false;
					continue;
				}
			}
		}
		String sql = sb.toString().trim();
		if (sql.length() > 0) {
			sqls.add(sql);
		}
		return sqls;
	}
}