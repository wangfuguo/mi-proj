package com.wfg.strategy;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-11-01 8:52
 * @since V1.0.0
 */
public class Context {
    private Strategy strategy;
    public Context (Strategy strategy) {
        this.strategy = strategy;
    }

    public int executeStrategy(int num1, int num2) {
        return strategy.doOperation(num1, num2);
    }
}
