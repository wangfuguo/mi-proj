package com.wfg.strategy;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-11-01 8:55
 * @since V1.0.0
 */
public class StrategyDemo {
    public static void main(String[] args){

        Context context = new Context(new OperationMultiply());

        System.out.println(context.executeStrategy(1, 2));

    }
}
