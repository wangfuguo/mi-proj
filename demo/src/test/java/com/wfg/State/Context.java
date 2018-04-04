package com.wfg.State;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-11-01 10:28
 * @since V1.0.0
 */
public class Context {
    private State state;
    {
        state = null;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
