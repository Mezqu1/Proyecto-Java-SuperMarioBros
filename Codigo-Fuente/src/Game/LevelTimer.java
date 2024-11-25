package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LevelTimer {
    private int remainingTime;
    private javax.swing.Timer swingTimer;
    private ActionListener listener;

    public LevelTimer(int minutes, Level level) {
        this.remainingTime = minutes * 60;

        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingTime > 0) {
                	remainingTime--;
                } else {
                    swingTimer.stop();
                    level.levelTimerStopped();
                }
            }
        };

        swingTimer = new javax.swing.Timer(1000, listener);
    }

    public void start() {
        swingTimer.start();
    }

    public void stop() {
        swingTimer.stop();
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public boolean timeHasFinished() {
        return remainingTime <= 0;
    }
}