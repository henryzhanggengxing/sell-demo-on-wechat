package com.oldmanw.sell.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * http请求返回的最外层对象
 */

@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 1828966361913730056L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回的具体内容
     */
    private T data;


}
