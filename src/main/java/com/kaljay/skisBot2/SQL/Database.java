package com.kaljay.skisBot2.SQL;

import com.kaljay.skisBot2.skisBot2;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
            initialiseTables();
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

    private static void addDataTable(DataTable table) {
        if(!TableList.contains(table)) {
            TableList.add(table);
        }

        String statement = "CREATE TABLE IF NOT EXISTS " + table.name + " (";
        ArrayList<DataColumn> primaryKeys = new ArrayList<>();
        ArrayList<DataColumn> foreignKeys = new ArrayList<>();
        for(DataColumn column :table.getDictionary().getColumns()) {
            statement += " " + column.getName();
            statement += " " + column.getType();
            if(column.isPrimaryKey()) {
                primaryKeys.add(column);
            }
            if(column.isForeignKey()) {
                foreignKeys.add(column);
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
            statement += ", ";
        }


        if(!primaryKeys.isEmpty()) {
            statement += "PRIMARY KEY (";
            for(DataColumn column : primaryKeys) {
                statement += column.getName() + ", ";
            }
            statement = statement.substring(0, statement.length()-2);
            statement += ")";
        }
        if(!foreignKeys.isEmpty()) {
            //Do nothing cause foreign key syntax is not used in this program, and is complicated (THIS KEY REFERENCES THIS KEY etc even if the key may not exist yet in the foreign table >_>)
        }
        statement += ")";

        //System.out.println(statement);
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

    public static void initialiseTables() {
        ArrayList<DataTable> tables = new ArrayList<>();

        DataDictionary guildsDict = new DataDictionary();
        guildsDict.addColumn("Guild_ID", SQL.VARCHAR, true, false, true, false, true, false);
        guildsDict.addColumn("Guild_Name", SQL.VARCHAR, false, false, false, false, false, false);
        guildsDict.addColumn("Bot_Channel", SQL.VARCHAR, false, false, false, false, false, false);
        tables.add(new DataTable("Guilds", guildsDict));

        DataDictionary playersDict = new DataDictionary();
        playersDict.addColumn("Player_ID", SQL.VARCHAR, true, false, true, false, true, false);
        playersDict.addColumn("Player_Name", SQL.VARCHAR, false, false, false, false, false, false);
        playersDict.addColumn("Summoner_ID", SQL.VARCHAR, false, false, false, false, false, false);
        tables.add(new DataTable("Players", playersDict));

        DataDictionary guildPlayersDict = new DataDictionary();
        guildPlayersDict.addColumn("Guild_ID", SQL.VARCHAR, true, false, false, false, true, false);
        guildPlayersDict.addColumn("Player_ID", SQL.VARCHAR, true, true, false, false, true, false);
        guildPlayersDict.addColumn("Player_Nickname", SQL.VARCHAR, false, false, false, false, false, false);
        tables.add(new DataTable("GuildPlayers", guildPlayersDict));

        for(DataTable table : tables) {
            addDataTable(table);
        }
    }

    public static void addInitialiseTable(DataTable table) {
        TableList.add(table);
    }

    public static void initialiseModuleTables() {
        for(DataTable table : TableList) {
            addDataTable(table);
        }
    }

    public static void UpdateOrInsertDefaultTables() {
        ArrayList<IUser> uniqueUsers = new ArrayList<>();

        for (IGuild guild : skisBot2.getGuilds()) {

            for  (IUser user: guild.getUsers()) {
                String statement1 = "";
                statement1 += "UPDATE GuildPlayers SET Player_ID = " + user.getLongID() + ", Player_Nickname = '" + user.getDisplayName(guild) + "' WHERE Player_ID = " + user.getLongID() + ";";
                //System.out.println(statement);
                SQLUpdate(statement1);
                statement1 = "INSERT OR IGNORE INTO GuildPlayers (Guild_ID, Player_ID, Player_Nickname) VALUES (" + guild.getLongID() + ", " + user.getLongID() + ", '" + user.getDisplayName(guild) + "')";
                //System.out.println(statement);
                SQLUpdate(statement1);
                if(!uniqueUsers.contains(user)) {
                    uniqueUsers.add(user);
                }
            }

            for (IUser user : uniqueUsers) {
                String statement1 = "";
                statement1 += "UPDATE Players SET Player_ID = " + user.getLongID() + ", Player_Name = '" + user.getName() + "' WHERE Player_ID = " + user.getLongID() + ";";
                //System.out.println(statement);
                SQLUpdate(statement1);
                statement1 = "INSERT OR IGNORE INTO Players (Player_ID, Player_Name) VALUES (" + user.getLongID() + ", '" + user.getName() + "')";
                //System.out.println(statement);
                SQLUpdate(statement1);
            }

            String statement = "";
            statement += "UPDATE Guilds SET Guild_ID = " + guild.getLongID() + ", Guild_Name = '" + guild.getName() + "' WHERE Guild_ID = " + guild.getLongID() + ";";
            //System.out.println(statement);
            SQLUpdate(statement);
            statement = "INSERT OR IGNORE INTO Guilds (Guild_ID, Guild_Name) VALUES (" + guild.getLongID() + ", '" + guild.getName() + "')";
            //System.out.println(statement);
            SQLUpdate(statement);
        }



    }

}
