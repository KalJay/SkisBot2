package com.kaljay.skisBot2.SQL;

/**
 * Project: SkisBot2
 * Created by KalJay on 26/11/2017 at 11:13 PM.
 */
public class DataColumn {

    private String Name;
    private SQL Type;
    private boolean PrimaryKey = false;
    private boolean ForeignKey = false;
    private boolean Unique = false;
    private boolean Check = false;
    private boolean NotNull = false;
    private boolean Collate = false;

    public DataColumn(String name, SQL type, boolean primaryKey, boolean foreignKey, boolean unique, boolean check, boolean notNull, boolean collate) {
        Name = name;
        Type = type;
        PrimaryKey = primaryKey;
        ForeignKey = foreignKey;
        Unique = unique;
        Check = check;
        NotNull = notNull;
        Collate = collate;
    }

    public boolean checkName(String testName) {
        return testName == Name;
    }

    public String getName() {
        return Name;
    }

    public SQL getType() {
        return Type;
    }

    public boolean isPrimaryKey() {
        return PrimaryKey;
    }

    public boolean isForeignKey() {
        return ForeignKey;
    }

    public boolean isUnique() {
        return Unique;
    }

    public boolean isCheck() {
        return Check;
    }

    public boolean isNotNull() {
        return NotNull;
    }

    public boolean isCollate() {
        return Collate;
    }



}
