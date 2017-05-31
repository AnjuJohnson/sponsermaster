package com.cutesys.sponsermasterlibrary.ScrollSwipe.Swipe;

public abstract class SwipeRunnable implements Runnable {
    int mDirection;

    protected SwipeRunnable(int direction) {
        mDirection = direction;
    }

    @Override
    public abstract void run();

    protected int getDirection() {
        return mDirection;
    }
}