package com.kaljay.skisBot2.modules;

import com.kaljay.skisBot2.SQL.Database;

/**
 * Project: SkisBot2
 * Created by KalJay on 1/01/2018 at 3:02 AM.
 */
public class ModuleManager {

    public static void initialiseModules() {
        Database.connect();
        CalendarEvents.setTimers();

    }
}
