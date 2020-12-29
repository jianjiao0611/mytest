package com.jjtest.user.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.jjtest.tool.exception.ServiceException;
import lombok.Data;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 默认数据源配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.druid")
@MapperScan(basePackages = "com.jjtest.user.dao", sqlSessionTemplateRef = "masterSqlSessionTemplate")
public class DruidMasterConfig {
    private String url;
    private String username;
    private String password;
    private int initialSize;
    private int minIdle;
    private int maxIdle;
    private int maxActive;
    private Long maxWait;

    @Bean(name = "masterDataSource")
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMaxWait(maxWait);
        try {
            druidDataSource.init();
        } catch (SQLException e) {
            throw new ServiceException();
        }
        return druidDataSource;
    }

    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("masterDataSource")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));

        MyBatisInterceptor myBatisInterceptor = new MyBatisInterceptor();
        bean.setPlugins(new Interceptor[]{myBatisInterceptor});
        return bean.getObject();
    }

    @Bean(name = "masterDataSourceTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("masterDataSource")DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "masterSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("masterSqlSessionFactory")SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
