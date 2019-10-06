package com.rubenbermejo.fml;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {

    private static Timer scheduleTimer = new Timer();

    public void startScheduler() {

        // Do scheduling.

        scheduleTimer.scheduleAtFixedRate(new ScheduleCommand("save-all"), new Date(), 3600000);
        scheduleTimer.schedule(new JumpToNextServer(), new Date());
    }

    // TODO: ADD DATE MANAGING FUNCTIONS TO CALCULATE DATES

    class ScheduleCommand extends TimerTask {

        private String command;

        ScheduleCommand(String command) {
            this.command = command;
        }

        @Override
        public void run() {
            ThreadManager.sendCommand(command);
        }
    }

    class JumpToNextServer extends TimerTask {

        @Override
        public void run() {
            ThreadManager.sendCommand("Updating to next version...");
            ThreadManager.skipToNextServer();
            scheduleTimer.schedule(new JumpToNextServer(), new Date());
        }
    }

}

