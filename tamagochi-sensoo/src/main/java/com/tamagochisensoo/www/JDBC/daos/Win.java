package com.tamagochisensoo.www.JDBC.daos;

import java.sql.Date;

public class Win {
    // Represents Win DB table
    public double id;
    public double creature_id;
    public Date date_win;

    @Override
    public String toString() {
        return "Creature " + creature_id + " : " + date_win;
    }
}
