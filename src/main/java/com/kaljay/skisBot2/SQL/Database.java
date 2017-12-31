package com.kaljay.skisBot2.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Project: SkisBot2
 * Created by KalJay on 26/11/2017 at 8:56 PM.
 */
public class Database {

    final static String url = "jdbc:sqlite:skisbot2";

    private static ArrayList<DataTable> TableList =  new ArrayList<>();

    public static void connect() {
        System.out.println("SKIS: Connecting to SQLite Database...");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("SKIS: Successfully Connected to SQLite Database");
        } catch (SQLException e) {
            System.out.println("SKIS: Error connecting to SQLite Database :" + e.getMessage());
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

    public static void addDataTable(DataTable table) {
        if(!TableList.contains(table)) {
            TableList.add(table);
        }

        String statement = "CREATE TABLE IF NOT EXISTS " + table.name + " (";
        for(DataColumn column :table.getDictionary().getColumns()) {
            statement += " " + column.getName();
            statement += " " + column.getType();
            if(column.isPrimaryKey()) {
                statement += " PRIMARY KEY";
            }
            if(column.isForeignKey()) {
                statement += " FOREIGN KEY";
            }
            if(column.isUnique()) {
                statement += " UNIQUE";
            }
            if(column.isCheck()) {
                statement += " CHECK";
            }
            if(column.isNotNull()) {
                statement += " NOT NULL";
            }
            if(column.isCollate()) {
                statement += " COLLATE";
            }
            statement += ",";
        }
        SQLUpdate(statement);


    }

    private static void SQLUpdate(String sql) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);

            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        } finally {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
