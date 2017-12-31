package com.kaljay.skisBot2.SQL;

/**
 * Project: SkisBot2
 * Created by KalJay on 26/11/2017 at 8:56 PM.
 */

public class DataTable {

    String name;
    DataDictionary dictionary;

    public String getName() {
        return name;
    }

    public DataDictionary getDictionary() {
        return dictionary;
    }

    public DataTable(String name, DataDictionary dictionary) {
        this.name = name;
        this.dictionary = dictionary;
    }


}
