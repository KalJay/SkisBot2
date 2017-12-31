package com.kaljay.skisBot2.modules;

import com.kaljay.skisBot2.comms.Text;


import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Project: SkisBot2
 * Created by KalJay on 31/12/2017 at 6:12 PM.
 */
public class CalendarEvents {
    private static Timer timer = new Timer();
    private static long newyears_milli_epoch = new Long("1514725200000");//1514725200;
    private static TimerTask new_years_day = new TimerTask() {
        @Override
        public void run() {
            Text.sendToAllGuilds("Happy New Year!");
        }
    };

    public static void setTimers() {
        timer.schedule(new_years_day, new Date(newyears_milli_epoch));
    }

}
