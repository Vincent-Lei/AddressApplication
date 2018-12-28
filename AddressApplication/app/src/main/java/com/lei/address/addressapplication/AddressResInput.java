package com.lei.address.addressapplication;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent.Lei on 2018/12/27.
 * Title：
 * Note：
 */
public class AddressResInput {
    private static final String ASSET_NAME = "area.csv";
    private InputStream inputStream;

    public void open(Context context) {
        try {
            inputStream = context.getAssets().open(ASSET_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Address> readAllAddress() {
        List<Address> list = new ArrayList<>();
        if (inputStream == null)
            throw new RuntimeException("open Assets first");
        String lineResult;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String[] split;
            while ((lineResult = bufferedReader.readLine()) != null) {
                split = lineResult.split("\",\"");
                list.add(new Address(Integer.parseInt(split[0].replace("\"","")), split[1].replace("\"",""), split[2].replace("\"",""), Integer.parseInt(split[3].replace("\"","")), Integer.parseInt(split[4].replace("\"",""))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


}
