package com.wfg.State;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-11-01 10:34
 * @since V1.0.0
 */
public class StopState implements State {
    @Override
    public void doAction(Context context) {
        System.out.println("Player is in stop state");
        context.setState(this);
    }

    @Override
    public String toString() {
        return "Stop State";
    }
}
