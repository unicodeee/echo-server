package simstation;

import mvc.Utilities;

import java.io.Serializable;

public abstract class Agent implements Runnable, Serializable {
    private int xc;
    private int yc;
    private boolean paused = false;
    private boolean stopped = false;
    private String agentName;
    transient protected Thread myThread;
    protected World world;
    private int SIZE = 10;

    public int getAgentSize(){
        return SIZE;
    }
    public int getXc() {
        return xc;
    }

    public int getYc() {
        return yc;
    }

    public void setXc(int xc) {
        this.xc = xc;
    }

    public void setYc(int yc) {
        this.yc = yc;
    }

    public void start() {
        myThread = new Thread(this);
        myThread.start();
    }
    public void reset() {
        stopped = false;
        paused = false;
    }
    public void stop() {
        stopped = true;
    }

    public void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notify();
    }

    public abstract void update();

    // in progress
    @Override
    public void run() {
        //myThread = Thread.currentThread();
        //checkPaused();
        onStart();
        while (!isStopped()) {
            try {
                update();
                Thread.sleep(20);
                checkPaused();
            } catch(InterruptedException e) {
                onInterrupted();
                Utilities.error(e);
            }
        }
        onExit();
    }

    synchronized void onExit() {
    }

    synchronized void onStart() {
    }

    synchronized void onInterrupted() {
    }

    private boolean isStopped() {
        return stopped;
    }

    private synchronized void checkPaused() {
        try {
            while(!stopped && paused) {
                wait();
                paused = false;
            }
        } catch (InterruptedException e) {
            Utilities.error(e.getMessage());
        }
    }
}
