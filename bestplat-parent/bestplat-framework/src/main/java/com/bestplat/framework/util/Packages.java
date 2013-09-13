package com.bestplat.framework.util;

import java.lang.annotation.Annotation;
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
public class Packages {
	interface ClassFilter {
		boolean accept(Class<?> type);
	}

	/**
	 * 文件后缀名
	 */
	private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

	/**
	 * 从指定包中扫描符合条件的类型
	 * 
	 * @param scanPackages
	 *            要扫描的包
	 * @param classFilter
	 *            类型过滤器
	 * @return
	 */
	public static Set<Class<?>> scanClasses(String scanPackages,
			ClassFilter classFilter) {
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
					if (classFilter.accept(cls)) {
						classSet.add(cls);
					}
				}
			} catch (Exception ex) {
				throw new IllegalStateException(String.format(
						"scan packages(%s)  has exception:", scanPackages), ex);
			}
		}
		return classSet;
	}

	/**
	 * 从指定包中扫描符合条件的类型
	 * 
	 * @param scanPackages
	 *            要扫描的包
	 * @param classFilter
	 *            类型过滤器
	 * @return
	 */
	public static Set<Class<?>> scanClasses(String scanPackages) {
		return scanClasses(scanPackages, new ClassFilter() {
			public boolean accept(Class<?> type) {
				return true;
			}
		});
	}

	/**
	 * 根据指定的注解从指定的包中扫描合适的类型
	 * 
	 * @param scanPackages
	 * @param annotationClass
	 * @return
	 */
	public static Set<Class<?>> scanClassesByAnnotation(String scanPackages,
			final Class<? extends Annotation> annotationClass) {
		return scanClasses(scanPackages, new ClassFilter() {
			public boolean accept(Class<?> type) {
				return type.getAnnotation(annotationClass) != null;
			}
		});
	}
}
