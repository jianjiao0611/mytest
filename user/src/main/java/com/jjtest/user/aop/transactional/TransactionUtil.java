package com.jjtest.user.aop.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

/**
 * 事务工具类
 */
@Component
@Scope("prototype")
public class TransactionUtil {
    // 全局接受事务状态
    private TransactionStatus transactionStatus;

    // 获取事务原
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    // 开启事务
    public TransactionStatus begin() {
        System.out.println("开启事务");
        transactionStatus = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
        return transactionStatus;
    }

    // 提交事务
    public void commit(TransactionStatus transaction) {
        System.out.println("提交事务");
        if (dataSourceTransactionManager != null) {
            dataSourceTransactionManager.commit(transaction);
        }
    }

    public void rollback(TransactionStatus transaction) {
        System.out.println("回滚事务");
        if (dataSourceTransactionManager != null) {
            dataSourceTransactionManager.rollback(transaction);
        }
    }

}
