package com.lei.address.addressapplication;

import java.util.List;

/**
 * Created by Vincent.Lei on 2018/12/27.
 * Title：
 * Note：
 */
public class Address {
    public static final String DB_NAME = "baza_address.db";
    public static final String TABLE_NAME = "address";
    public static final int DB_VERSION = 1;
    public static final String COL_CODE  ="code";
    public static final String COL_NAME  ="name";
    public static final String COL_EN_NAME  ="enName";
    public static final String COL_SHORT_NAME  ="shortName";
    public static final String COL_LEVEL  ="level";
    public static final String COL_PARENT_CODE  ="parentCode";

    public int code;
    public String name;
    public String enName;
    public String shortName;
    public int level;
    public int parentCode;
    public List<Address> children;

    public Address(int code, String name, String enName, int level, int parentCode) {
        this.code = code;
        this.name = name;
        this.enName = enName;
        this.level = level;
        this.parentCode = parentCode;
    }

    @Override
    public String toString() {
        return "code = " + code + ";name = " + name + ";enName = " + enName+ ";shortName = " + shortName + ";level = " + level + ";parentCode = " + parentCode;
    }
}
