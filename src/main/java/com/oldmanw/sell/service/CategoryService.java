package com.oldmanw.sell.service;

import com.oldmanw.sell.dataobject.ProductCategory;

import java.util.List;

/**
 * 类目
 */

public interface CategoryService {

    ProductCategory findById(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
