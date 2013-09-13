package com.bestplat.framework.persistence.dao;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.dao.support.DaoSupport;

/**
 * 抽象Dao
 * 
 * @author lujijiang
 * 
 */
public abstract class Dao extends DaoSupport {
	/**
	 * 默认获取尺寸（25）
	 */
	private int defaultFetchSize = 25;

	public int getDefaultCacheSize() {
		return defaultFetchSize;
	}

	public void setDefaultCacheSize(int defaultCacheSize) {
		this.defaultFetchSize = defaultCacheSize;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static List asList(Object... a) {
		List list = new LinkedList();
		if (a == null) {
			list.add(a);
			return list;
		}
		for (Object o : a) {
			if (o instanceof Collection) {
				list.addAll(asList(((Collection) o).toArray()));
			} else if (o.getClass().isArray()) {
				for (int i = 0; i < Array.getLength(o); i++) {
					list.add(Array.get(o, i));
				}
			} else {
				list.add(o);
			}
		}
		return list;
	}

	/**
	 * 统计数量
	 * 
	 * @param ql
	 * @return
	 */
	public abstract Long count(QL ql);

	/**
	 * 查询所有数据
	 * 
	 * @param type
	 *            指定返回类型
	 * @param ql
	 *            查询构造器
	 * @return
	 */
	public <T> List<T> find(Class<? extends T> type, QL ql) {
		return find(type, ql, Integer.MAX_VALUE);
	}

	/**
	 * 查询所有数据
	 * 
	 * @param type
	 *            指定返回类型
	 * @param ql
	 *            查询构造器
	 * @return
	 */
	public <T> List<T> find(Class<? extends T> type, QL ql, int size) {
		return find(type, ql, 0, size);
	}

	/**
	 * 查询指定范围内的数据
	 * 
	 * @param type
	 *            指定返回类型
	 * @param ql
	 *            查询构造器
	 * @param start
	 *            数据开始范围
	 * @param size
	 *            数据量
	 * @return
	 */
	public abstract <T> List<T> find(Class<? extends T> type, QL ql, int start,
			int size);

	/**
	 * 查询所有数据
	 * 
	 * @param ql
	 *            查询构造器
	 * @return
	 */
	public List<?> find(QL ql) {
		return find(ql, Integer.MAX_VALUE);
	}

	/**
	 * 查询所有数据
	 * 
	 * @param ql
	 *            查询构造器
	 * @param size
	 *            数据量
	 * @return
	 */
	public List<?> find(QL ql, int size) {
		return find(ql, 0, size);
	}

	/**
	 * 查询指定范围内的数据
	 * 
	 * @param ql
	 *            查询构造器
	 * @param start
	 *            数据开始范围
	 * @param size
	 *            数据量
	 * @return
	 */
	public abstract List<?> find(QL ql, int start, int size);

	/**
	 * 查询符合条件的唯一记录
	 * 
	 * @param ql
	 * @return
	 */
	public abstract <T> T unique(Class<? extends T> type, QL ql);

	/**
	 * 查询符合条件的唯一记录
	 * 
	 * @param ql
	 * @return
	 */
	public abstract Object unique(QL ql);

	/**
	 * 获取迭代能力
	 * 
	 * @param finder
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Iterable<?> iterable(final QL ql) {
		return new Iterable() {
			public Iterator<?> iterator() {
				return new Iterator() {
					int start = 0;

					final int size = defaultFetchSize;
					int count = -1;
					Iterator source;

					public boolean hasNext() {
						if (source == null || !source.hasNext()) {
							if (count != -1) {
								return false;
							}
							List list = find(ql, start, size);
							if (list.size() < size) {
								count = start + list.size();
							}
							start += size;
							source = list.iterator();
						}
						return source.hasNext();
					}

					public Object next() {
						return source.next();
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}

	/**
	 * 根据ID获取一个实体对象
	 * 
	 * @param entityClass
	 * @param primaryKey
	 * @return
	 */
	public abstract <T> T get(Class<T> entityClass, Serializable primaryKey);

	/**
	 * 保存一个实体对象
	 * 
	 * @param entity
	 */
	public abstract void persist(Object entity);

	/**
	 * 全部操作
	 * 
	 * @param objs
	 */
	@SuppressWarnings("rawtypes")
	public void persistAll(Object... objs) {
		List entitys = asList(objs);
		for (Object entity : entitys) {
			persist(entity);
		}
	}

	/**
	 * 保存或者更新一个实体对象
	 * 
	 * @param entity
	 */
	public void save(Object entity) {
		Serializable id = (Serializable) getIdentifier(entity);
		if (id != null) {
			if (get(getEntityType(entity.getClass()), id) != null) {
				merge(entity);// 合并实体
			} else {
				replicate(entity);// 强制写入指定ID的实体
			}
		} else {
			persist(entity);// 持久化实体
		}
	}

	/**
	 * 根据传入类型获取实体类型
	 * 
	 * @param type
	 * @return
	 */
	protected abstract <T> Class<T> getEntityType(Class<? extends T> type);

	/**
	 * 全部操作
	 * 
	 * @param objs
	 */
	@SuppressWarnings("rawtypes")
	public void saveAll(Object... objs) {
		List entitys = asList(objs);
		for (Object entity : entitys) {
			save(entity);
		}
	}

	/**
	 * 更新一个实体对象（该实体对象必须已经存在于数据库中）
	 * 
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T update(T entity) {
		Serializable id = (Serializable) getIdentifier(entity);
		T managedEntity = null;
		if (id != null) {
			managedEntity = (T) get(getEntityType(entity.getClass()), id);
		}
		if (managedEntity == null) {
			throw new IllegalArgumentException(String.format(
					"The entity %s is not exists",
					ToStringBuilder.reflectionToString(entity)));
		}
		merge(entity);
		return managedEntity;
	}

	/**
	 * 全部操作
	 * 
	 * @param objs
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object[] updateAll(Object... objs) {
		List newEntitys = new ArrayList();
		List entitys = asList(objs);
		for (Object entity : entitys) {
			newEntitys.add(update(entity));
		}
		return newEntitys.toArray();
	}

	/**
	 * 合并对象
	 * 
	 * @param entity
	 * @return
	 */
	public abstract <T> T merge(T entity);

	/**
	 * 全部操作
	 * 
	 * @param objs
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object[] mergeAll(Object... objs) {
		List newEntitys = new ArrayList();
		List entitys = asList(objs);
		for (Object entity : entitys) {
			newEntitys.add(merge(entity));
		}
		return newEntitys.toArray();
	}

	/**
	 * 完整的保存或者更新对象，Hibernate将不会自动生成任何数据，需要自行指定ID、版本和创建更新日期等
	 * 
	 * @param entity
	 */
	public abstract void replicate(Object entity);

	/**
	 * 全部操作
	 * 
	 * @param objs
	 */
	@SuppressWarnings("rawtypes")
	public void replicateAll(Object... objs) {
		List entitys = asList(objs);
		for (Object entity : entitys) {
			replicate(entity);
		}
	}

	/**
	 * 删除一个实体对象
	 * 
	 * @param entity
	 */
	public abstract void remove(Object entity);

	/**
	 * 全部操作
	 * 
	 * @param objs
	 */
	@SuppressWarnings("rawtypes")
	public void deleteAll(Object... objs) {
		List entitys = asList(objs);
		for (Object entity : entitys) {
			remove(entity);
		}
	}

	/**
	 * 根据类型和主键删除实体
	 * 
	 * @param entityClass
	 * @param primaryKeys
	 *            主键列表
	 */
	public void delete(Class<?> entityClass, Serializable... primaryKeys) {
		for (Serializable primaryKey : primaryKeys) {
			Object entity = get(entityClass, primaryKey);
			if (entity != null) {
				remove(entity);
			}
		}
	}

	/**
	 * 刷新一个实体对象，使其状态和数据库状态一致
	 * 
	 * @param entity
	 */
	public abstract void refresh(Object entity);

	/**
	 * 判断一个实体对象是否存在于当前一级缓存上下文中并且状态有效。 注意：不是判断是否存在于数据库中
	 * 
	 * @param entity
	 * @return
	 */
	public abstract boolean contains(Object entity);

	/**
	 * 把一个对象从托管态变成非托管态
	 * 
	 * @param entity
	 */
	public abstract void detach(Object entity);

	/**
	 * 获取一个实体对象的主键
	 * 
	 * @param entity
	 * @return
	 */
	public abstract Object getIdentifier(Object entity);

	/**
	 * 提交缓冲区
	 */
	public abstract void flush();

	/**
	 * 清空一级缓存并提交缓冲区
	 */
	public abstract void clear();
}
