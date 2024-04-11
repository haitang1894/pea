package com.pea.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.pea.common.mybatis.config.MybatisPlusMetaObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *  MybatisPlusConfig
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.pea.business.sys.mapper")
public class MybatisPlusConfig {

	/**
	 * 新的分页插件
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return interceptor;
	}

	/**
	 * 审计字段自动填充
	 * @return {@link MetaObjectHandler}
	 */
	@Bean
	public MybatisPlusMetaObjectHandler dbFieldFillHandler() {
		return new MybatisPlusMetaObjectHandler();
	}

}