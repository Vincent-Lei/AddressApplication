package com.lei.address.addressapplication;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent.Lei on 2018/12/27.
 * Title：
 * Note：
 */
public class AddressFormatter {

    private static final int CN_COUNT_CODE = 41;

    public void formatAddress(Context context, List<Address> sourceList) {
        if (sourceList == null)
            throw new IllegalArgumentException("sourceList can not be null");
        PinYinUtil.init(context);
        Address address;
        String[] pysx;
        for (int i = 0, size = sourceList.size(); i < size; i++) {
            address = sourceList.get(i);
            if (isChineseAddress(sourceList, address) && address.code != CN_COUNT_CODE) {
                pysx = PinYinUtil.getPinyinAndSX(address.name);
                address.enName = pysx[0];
                address.shortName = pysx[1];
            } else {
                address.enName = address.enName.toLowerCase();
                address.shortName = address.enName;
            }
        }
    }


    private boolean isChineseAddress(List<Address> sourceList, Address address) {
        if (address == null)
            return false;
        if (address.level == 1) {
            return address.code == CN_COUNT_CODE;
        }
        for (int i = 0, size = sourceList.size(); i < size; i++) {
            if (address.parentCode == sourceList.get(i).code)
                return isChineseAddress(sourceList, sourceList.get(i));
        }
        return false;
    }

    public List<Address> getTree(List<Address> sourceList) {
        List<Address> outList = new ArrayList<>();
        Address temp;
        for (int i = 0, size = sourceList.size(); i < size; i++) {
            temp = sourceList.get(i);
            if (temp.level == 1) {
                temp.children = getChildNodes(sourceList, temp, 4);
                outList.add(temp);
            }
        }
        return outList;
    }

    private List<Address> getChildNodes(List<Address> sourceList, Address address, int maxLevel) {
        List<Address> list = null;
        Address temp;
        for (int i = 0, size = sourceList.size(); i < size; i++) {
            temp = sourceList.get(i);
            if (temp.parentCode == address.code) {
                if (list == null)
                    list = new ArrayList<>();
                if (temp.level <= maxLevel)
                    temp.children = getChildNodes(sourceList, temp, maxLevel);
                list.add(temp);
            }
        }
        return list;
    }
}
