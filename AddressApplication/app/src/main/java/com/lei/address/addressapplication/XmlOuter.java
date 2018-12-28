package com.lei.address.addressapplication;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Vincent.Lei on 2018/12/28.
 * Title：
 * Note：
 */
public class XmlOuter {
    private static final String XML_FILE_NAME = "/address.xml";


    private static final int LEVEL_COUNTRY = 1;
    private static final int LEVEL_PROVINCE = 2;
    private static final int LEVEL_CITY = 3;
    private static final int LEVEL_DISTRICT = 4;
    private static final String COUNTRY = "country";
    private static final String PROVINCE = "province";
    private static final String CITY = "city";
    private static final String DISTRICT = "district";


    public static void writeXMLToLocal(Context context, List<Address> treeNodes) {
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + context.getPackageName();
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        file = new File(path + XML_FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<root>\n".getBytes("utf-8"));
            writeBody(treeNodes, fileOutputStream, LEVEL_COUNTRY);
            fileOutputStream.write("</root>".getBytes("utf-8"));
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static void writeBody(List<Address> list, FileOutputStream fileOutputStream, int level) throws Exception {
        if (list == null || list.isEmpty())
            return;
        Address address;
        String temp;
        for (int i = 0, size = list.size(); i < size; i++) {
            address = list.get(i);
            if (level == LEVEL_COUNTRY)
                fileOutputStream.write("<country ".getBytes("utf-8"));
            else if (level == LEVEL_PROVINCE)
                fileOutputStream.write("<province ".getBytes("utf-8"));
            else if (level == LEVEL_CITY)
                fileOutputStream.write("<city ".getBytes("utf-8"));
            else if (level == LEVEL_DISTRICT)
                fileOutputStream.write("<district ".getBytes("utf-8"));
            temp = "code=\"" + address.code + "\" name=\"" + address.name + "\" enName=\"" + address.enName + "\" shortName=\"" + address.shortName + "\" parentCode=\"" + address.parentCode + "\" level=\"" + address.level + "\"";
            if (address.children == null || address.children.isEmpty())
                temp += "/>\n";
            else
                temp += ">\n";
            fileOutputStream.write(temp.getBytes("utf-8"));
            if (address.children != null) {
                writeBody(address.children, fileOutputStream, level + 1);
            }
            if (address.children != null && !address.children.isEmpty()) {
                if (level == LEVEL_COUNTRY)
                    fileOutputStream.write("</country>\n".getBytes("utf-8"));
                else if (level == LEVEL_PROVINCE)
                    fileOutputStream.write("</province>\n".getBytes("utf-8"));
                else if (level == LEVEL_CITY)
                    fileOutputStream.write("</city>\n".getBytes("utf-8"));
            }

//            else if (level == LEVEL_DISTRICT)
//                fileOutputStream.write("</district>\n".getBytes("utf-8"));
        }
    }
}
