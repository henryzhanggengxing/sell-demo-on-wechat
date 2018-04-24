package com.oldmanw.sell.dto;

import lombok.Data;

/**
 * 购物车，只有商品Id和商品数量
 */

@Data
public class CartDTO {

    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
