package com.oldmanw.sell.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductForm {

    private String productId;

    /** 名称 */
    private String productName;

    /** 单价 */
    private BigDecimal productPrice;

    /** 库存 */
    private Integer productStock;

    /** 描述 */
    private String productDescription;

    /** 小图链接地址 */
    private String productIcon;

//    /** 状态 */
//    private Integer productStatus;

    /** 类目编号 */
    private Integer categoryType;

}
