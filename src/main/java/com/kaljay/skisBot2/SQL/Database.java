package com.kaljay.skisBot2.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Project: SkisBot2
 * Created by KalJay on 26/11/2017 at 8:56 PM.
 */
public class Database {

    ArrayList TableList =  new ArrayList();

    public void connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:skisbot2";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void addDataTable(DataTable table) {
        TableList.add(table);
    }
}
