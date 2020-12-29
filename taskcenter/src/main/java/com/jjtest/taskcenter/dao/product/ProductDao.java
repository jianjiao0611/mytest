package com.jjtest.taskcenter.dao.product;

import com.jjtest.taskcenter.po.ProductPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDao {

    ProductPO selectById(Long id);

    int updateProduct(ProductPO po);
}
