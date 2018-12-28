package com.lei.address.addressapplication;

import android.content.Context;
import android.os.Environment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Vincent.Lei on 2018/12/28.
 * Title：
 * Note：
 */
public class JsonOuter {
    private static final String JSON_FILE_NAME = "/address.json";

    public static String objectToJson(Object object) {

        return JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
    }

    public static void writeJsonToLocal(Context context, List<Address> treeNodes) {
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + context.getPackageName();
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        file = new File(path + JSON_FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String json = objectToJson(treeNodes);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(json.getBytes("utf-8"));
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
}
