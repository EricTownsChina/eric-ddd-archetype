package io.github.erictowns.infrastructure.dal.config.mysql.mysql01;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import io.github.erictowns.infrastructure.dal.interceptor.MybatisPlusAllSqlLog;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


/**
 * Description: mysql01 configuration
 * 配置datasource.mysql.mysql01.enable: true时进行装配
 *
 * @author EricTowns
 * @date 2023/10/6 21:53
 */
@Configuration
@ConditionalOnProperty(name = "datasource.mysql.mysql01.enable", havingValue = "true")
@MapperScan(basePackages = "io.github.erictowns.infrastructure.dal.mapper.mysql.mysql01",
        sqlSessionFactoryRef = "mysql01SqlSessionFactory")
public class Mysql01Config {

    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * 数据源连接配置
     *
     * @return {@link DataSourceProperties}
     */
    @Bean("mysql01DataSourceProperties")
    @ConfigurationProperties(prefix = "datasource.mysql.mysql01")
    public DataSourceProperties mysql01DataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 增加hikari连接池配置, 创建mysql01数据源
     *
     * @return {@link HikariDataSource}
     */
    @Bean("mysql01DataSource")
    @Qualifier(value = "mysql01DataSourceProperties")
    @ConfigurationProperties(prefix = "datasource.mysql.mysql01.hikari")
    public HikariDataSource mysql01DataSource() {
        return mysql01DataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "mysql01TransactionManager")
    public DataSourceTransactionManager mysql01TransactionManager() {
        return new DataSourceTransactionManager(mysql01DataSource());
    }

    @Bean("mysql01Interceptor")
    public MybatisPlusInterceptor mysql01Interceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 阻断插件 如果是对全表的删除或更新操作，就会终止该操作
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 日志打印插件(dev环境时使用)
        if ("dev".equals(profile)) {
            interceptor.addInnerInterceptor(new MybatisPlusAllSqlLog());
        }
        return interceptor;
    }

    @Bean(name = "mysql01SqlSessionFactory")
    public SqlSessionFactory mysql01SqlSessionFactory() throws Exception {
        final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/mysql/mysql01/*.xml"));
        sessionFactory.setDataSource(mysql01DataSource());
        sessionFactory.setPlugins(mysql01Interceptor());
        return sessionFactory.getObject();
    }

}
