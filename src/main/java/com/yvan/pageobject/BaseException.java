package com.yvan.pageobject;

/**
 * Function：异常基类
 * Created by YangWang on 2018-01-04 21:15.
 */

public class BaseException extends InterruptedException {

    public BaseException(String msg) {
        super(msg);
    }

}
