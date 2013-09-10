package com.bestplat.framework.util;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;

/**
 * 包扫描器
 * 
 * @author lujijiang
 * 
 */
public class PackageScanner {
	/**
	 * 文件后缀名
	 */
	private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

	/**
	 * 扫描所有包含API注解的类
	 * 
	 * @param scanPackages
	 * @return
	 */
	public static Set<Class<?>> scan(String scanPackages) {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(
				resourcePatternResolver);
		Set<Class<?>> classSet = new LinkedHashSet<Class<?>>();
		Iterator<String> scanPackageIterator = Strings.splitIgnoreBlank(
				scanPackages, ",");
		while (scanPackageIterator.hasNext()) {
			String scanPackage = scanPackageIterator.next();
			scanPackage = '/' + scanPackage.replace('.', '/');
			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
					+ scanPackage + "/" + DEFAULT_RESOURCE_PATTERN;
			try {
				Resource[] resources = resourcePatternResolver
						.getResources(packageSearchPath);
				for (Resource resource : resources) {
					String className = metadataReaderFactory
							.getMetadataReader(resource).getClassMetadata()
							.getClassName();
					Class<?> cls = Class.forName(className);
					classSet.add(cls);
				}
			} catch (Exception ex) {
				throw new IllegalStateException(String.format(
						"scan packages(%s)  has exception:", scanPackages), ex);
			}
		}
		return classSet;
	}

	public static void main(String[] args) {
		System.out.println(scan("com.**.startup"));
	}
}
