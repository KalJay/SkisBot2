package com.kaljay.skisBot2.SQL;

/**
 * Project: SkisBot2
 * Created by KalJay on 26/11/2017 at 10:28 PM.
 */
public enum SQL {
    INT("INT"),
    INTEGER("INTEGER"),
    TINYINT("TINYINT"),
    SMALLINT("SMALLINT"),
    MEDIUMINT("MEDIUMINT"),
    BIGINT("BIGINT"),
    UNSIGNED_BIG_INT("UNSIGNED BIG INT"),
    INT2("INT2"),
    INT8("INT8"),
    CHARACTER("CHARACTER(20)"),
    VARCHAR("VARCHAR(255)"),
    VARYING_CHARACTER("VARYING CHARACTER(255)"),
    NCHAR("NCHAR(55)"),
    NATIVE_CHARACTER("NATIVE CHARACTER(70)"),
    NVARCHAR("NVCHAR(100)"),
    TEXT("TEXT"),
    CLOB("CLOB"),
    BLOB("BLOB"),
    REAL("REAL"),
    DOUBLE("DOUBLE"),
    DOUBLE_PRECISION("DOUBLE PRECISION"),
    FLOAT("FLOAT"),
    NUMERIC("NUMERIC"),
    DECIMAL("DECIMAL(10,5)"),
    DATE("DATE"),
    DATETIME("DATETIME"),
    NONE("");

    private String displayTag;

    SQL(String displayTag) {
        this.displayTag = displayTag;
    }


    public String displayTag() { return displayTag; }


}
