package com.jjtest.taskcenter.service;

import com.jjtest.taskcenter.dao.product.ProductDao;
import com.jjtest.taskcenter.po.ProductPO;
import com.jjtest.tool.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品service
 */
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Transactional(rollbackFor = Exception.class, value = "productDataSourceTransactionManager")
    public void editProduct(ProductPO productPO) {
        ProductPO oldProduct = productDao.selectById(productPO.getId());
        oldProduct.setProductName(productPO.getProductName());
        productDao.updateProduct(oldProduct);
        if (true) {
           throw new ServiceException("ces");
        }
        oldProduct.setPrice(productPO.getPrice());
        productDao.updateProduct(oldProduct);
    }

}
