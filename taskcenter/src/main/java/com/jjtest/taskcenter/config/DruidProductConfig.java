package com.jjtest.taskcenter.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.jjtest.tool.exception.ServiceException;
import lombok.Data;
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
 * 商品数据源配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "druid.product")
@MapperScan(basePackages = "com.jjtest.taskcenter.dao.product", sqlSessionTemplateRef = "productSqlSessionTemplate")
public class DruidProductConfig {
    private String url;
    private String username;
    private String password;
    private int initialSize;
    private int minIdle;
    private int maxIdle;
    private int maxActive;
    private Long maxWait;

    @Bean(name = "productDataSource")
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

    @Bean(name = "productSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("productDataSource")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/product/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "productDataSourceTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("productDataSource")DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "productSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("productSqlSessionFactory")SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
