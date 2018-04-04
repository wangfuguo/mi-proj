package com.wfg.State;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-11-01 10:35
 * @since V1.0.0
 */
public class StateDemo {
    public static void main(String[] args){

        Context context = new Context();
        StartState startState = new StartState();
        startState.doAction(context);
        System.out.println(context.getState().toString());

    }

}
