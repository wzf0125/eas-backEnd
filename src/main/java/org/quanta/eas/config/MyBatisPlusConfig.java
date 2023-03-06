package org.quanta.eas.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description: mybatis配置
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/9
 */
@MapperScan("com.baomidou.cloud.service.*.mapper*")
@EnableTransactionManagement
@Configuration // 注册为配置类
public class MyBatisPlusConfig {

    // 分页插件配置(新版配置)
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
