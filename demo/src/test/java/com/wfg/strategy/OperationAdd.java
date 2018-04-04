package com.wfg.strategy;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-11-01 8:48
 * @since V1.0.0
 */
public class OperationAdd implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 + num2;
    }
}
