package ru.cft.shift.task3.controller.timer;

import lombok.extern.slf4j.Slf4j;
import ru.cft.shift.task3.controller.updateview.ViewUpdateListener;

@Slf4j
public class GameTimer extends TimerUpdateListener {
    private ViewUpdateListener updateListener;
    private boolean flag = false;
    private int timer;

    @Override
    public void run() {
        log.trace("timer run: {}", this);
        reset();
        flag = true;
        new Thread(this::completeTask).start();
        // TODO set daemon
    }

    private void completeTask() {
        log.trace("timer completeTask: {}", timer);
        try {
            while (flag) {
                updateListener.updateGameTimer(timer++);
                Thread.sleep(1000);  // TODO предупреждение идеи, как исправить?
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        flag = false;
    }

    @Override
    public void reset() {
        flag = false;
        timer = 0;
        updateListener.updateGameTimer(timer);
    }

    @Override
    public void setUpdateListener(ViewUpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    @Override
    public int getTimer() {
        return timer == 0 ? 0 : --timer;
    }
}
