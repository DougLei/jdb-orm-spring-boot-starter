package com.douglei.orm.spring.boot.starter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.douglei.orm.spring.TransactionComponentRegister2Spring;

/**
 * 
 * @author DougLei
 */
public class TransactionComponentAutoRegistry extends TransactionComponentRegister2Spring implements BeanFactoryAware, ImportBeanDefinitionRegistrar{
	private static final Logger logger = LoggerFactory.getLogger(TransactionComponentAutoRegistry.class);
	
	private BeanFactory beanFactory;
	
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		if(!AutoConfigurationPackages.has(this.beanFactory)) {
			logger.debug("自动扫描事物组件的packages值为空");
			return;
		}
		
		List<String> transactionComponentPackages = AutoConfigurationPackages.get(this.beanFactory);
		if(logger.isDebugEnabled()) {
			transactionComponentPackages.forEach(transactionComponentPackage -> logger.debug("AutoConfigurationPackages.get(this.beanFactory), 获取的事物组件package为: {}", transactionComponentPackage));
		}
		
		String[] transactionComponentPackageArray = filterPackages(transactionComponentPackages);
		register2Spring(registry, true, transactionComponentPackageArray);
	}

	/**
	 * 过滤包, 如果重复的筛选掉, 防止重复扫描和加载同一个bean
	 * @param transactionComponentPackages
	 * @return
	 */
	private String[] filterPackages(List<String> transactionComponentPackages) {
		List<String> finalTransactionComponentPackages = null;
		if(transactionComponentPackages.size() == 1) {
			finalTransactionComponentPackages =  transactionComponentPackages;
		}else {
			int length = transactionComponentPackages.size();
			PackageFilterWrapper[] filter = new PackageFilterWrapper[length];
			byte index = 0;
			for (String package_ : transactionComponentPackages) {
				filter[index] = new PackageFilterWrapper(index, package_);
				index++;
			}
			Arrays.sort(filter);
			
			finalTransactionComponentPackages = new ArrayList<String>(length);
			String package_ = null;
			for (int i = 0; i < length; i++) {
				if(filter[i] == null) {
					continue;
				}
				
				package_ = filter[i].package_;
				finalTransactionComponentPackages.add(package_);
				if(i == length-1) {
					break;
				}
				
				for (int j = i+1; j < length; j++) {
					if(filter[j] != null && filter[j].package_.startsWith(package_)) {
						filter[j] = null;
					}
				}
			}
		}
		return finalTransactionComponentPackages.toArray(new String[finalTransactionComponentPackages.size()]);
	}
	
	/**
	 * 
	 * @author DougLei
	 */
	private class PackageFilterWrapper implements Comparable<PackageFilterWrapper>{
		byte index;
		byte length;
		String package_;
		PackageFilterWrapper(byte index, String package_) {
			this.index = index;
			this.package_ = package_;
			this.length = (byte) package_.split(".").length;
		}
		
		@Override
		public String toString() {
			return "\nPackageFilterWrapper [index=" + index + ", length=" + length + ", package_=" + package_ + "]";
		}

		@Override
		public int compareTo(PackageFilterWrapper o) {
			if(this.length < o.length) {
				return -1;
			}else if(this.length == o.length) {
				return 0;
			}else {
				return 1;
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + index;
			result = prime * result + length;
			result = prime * result + package_.hashCode();
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			PackageFilterWrapper other = (PackageFilterWrapper) obj;
			return index == other.index && length == other.length && package_.equals(other.package_);
		}
	}
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
}
