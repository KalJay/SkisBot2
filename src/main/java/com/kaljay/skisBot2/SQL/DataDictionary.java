package com.kaljay.skisBot2.SQL;

import java.util.ArrayList;

/**
 * Project: SkisBot2
 * Created by KalJay on 26/11/2017 at 8:57 PM.
 */
public class DataDictionary {

    private ArrayList<DataColumn> Columns = new ArrayList<>();


    public void addColumn(String name, SQL type, boolean pk, boolean fk, boolean unique, boolean check, boolean notnull, boolean collate) {
        Columns.add(new DataColumn(name, type, pk, fk, unique, check, notnull, collate));
    }

    public void removeColumnsByName(String name) {
        ArrayList<DataColumn> temp = new ArrayList<>();
        for(DataColumn column: Columns) {
            if(column.checkName(name)) {
                temp.add(column);
            }
        }
        Columns.removeAll(temp);
    }
}
