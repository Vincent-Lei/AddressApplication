package com.lei.address.addressapplication;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.d(getApplicationInfo().dataDir);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                makeAddress();
            }
        }).start();
    }


    private void makeAddress() {
        LogUtil.d("start read address source");
        AddressResInput addressResInput = new AddressResInput();
        addressResInput.open(this);
        List<Address> listSource = addressResInput.readAllAddress();
        addressResInput.close();
        LogUtil.d("start format address");
        AddressFormatter addressFormatter = new AddressFormatter();
        addressFormatter.formatAddress(this, listSource);
        LogUtil.d("start save address to db");
        saveToDb(this, listSource);
        List<Address> treeNodes = addressFormatter.getTree(listSource);
        LogUtil.d("start save address json file");
        JsonOuter.writeJsonToLocal(this, treeNodes);
        LogUtil.d("start save address xml file");
        XmlOuter.writeXMLToLocal(this, treeNodes);
        LogUtil.d("finish");

    }

    private void printNodes(List<Address> treeNodes) {
        if (treeNodes != null) {
            Address address;
            for (int i = 0, size = treeNodes.size(); i < size; i++) {
                address = treeNodes.get(i);
                LogUtil.d(address.toString());
                if (address.children != null)
                    printNodes(address.children);
            }
        }
    }

    private void saveToDb(Context context, List<Address> sourceList) {
        try {
            DBOuter dbOuter = DBOuter.getInstance(context);
            dbOuter.openDataBase();
            dbOuter.delete(Address.TABLE_NAME, null, null);
            dbOuter.beginTransaction();
            Address address;
            ContentValues contentValues;
            for (int i = 0, size = sourceList.size(); i < size; i++) {
                address = sourceList.get(i);
                contentValues = new ContentValues();
                contentValues.put(Address.COL_CODE, address.code);
                contentValues.put(Address.COL_NAME, address.name);
                contentValues.put(Address.COL_EN_NAME, address.enName);
                contentValues.put(Address.COL_SHORT_NAME, address.shortName);
                contentValues.put(Address.COL_LEVEL, address.level);
                contentValues.put(Address.COL_PARENT_CODE, address.parentCode);
                dbOuter.insert(Address.TABLE_NAME, contentValues);
            }
            dbOuter.setTransactionSuccessful();
            dbOuter.endTransaction();
            dbOuter.closeDataBase();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
