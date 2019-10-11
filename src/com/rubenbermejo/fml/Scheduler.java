package com.rubenbermejo.fml;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Scheduler {

    private final ScheduledExecutorService scheduler;
    /**
     * Starts the scheduler.
     */
    Scheduler() {
        scheduler = Executors.newScheduledThreadPool(2);

        scheduler.scheduleAtFixedRate(new ScheduledCommand("save-all"), 0, getNextAutoSave().getTime(), TimeUnit.MILLISECONDS);
        scheduler.schedule(new ScheduledServerUpdate(), getNextUpdate().getTime(), TimeUnit.MILLISECONDS);
    }

    /**
     * Calculates the date when a server folder has to be updated.
     * @return The date when the update happens.
     */
    private Date getNextUpdate() {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.WEEK_OF_YEAR, 2);

        return Date.from(cal.toInstant());
    }

    /**
     * Calculates the date when an autosave has to happen.
     * @return The date when the autosave happens.
     */
    private Date getNextAutoSave () {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.HOUR_OF_DAY, 2);

        return Date.from(cal.toInstant());
    }

    /**
     * Task that runs a command when scheduled.
     */
    static class ScheduledCommand implements Runnable {
        private String command;

        ScheduledCommand(String command) {
            this.command = command;
        }

        @Override
        public void run() {
            ThreadManager.sendCommand(command);
        }
    }

    /**
     * Task that performs a server update.
     */
    class ScheduledServerUpdate implements Runnable {
        @Override
        public void run() {
            ThreadManager.sendCommand("say [MSE] Updating to next version...");
            ThreadManager.skipToNextServer();
            scheduler.schedule(new ScheduledServerUpdate(), getNextUpdate().getTime(), TimeUnit.MILLISECONDS);

        }
    }
}


