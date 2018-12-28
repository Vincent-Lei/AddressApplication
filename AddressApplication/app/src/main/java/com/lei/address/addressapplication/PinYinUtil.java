package com.lei.address.addressapplication;

import android.content.Context;
import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.util.HashMap;

/**
 * Created by Vincent.Lei on 2018/12/27.
 * Title：
 * Note：
 */
public class PinYinUtil {
    private static final HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
    private static HashMap<String, String> dyzSourcePinyinMap = new HashMap<>();
    private static HashMap<String, String> dyzSourceSxMap = new HashMap<>();

    public static void init(Context context) {
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);

        String[] dyz = context.getResources().getStringArray(R.array.dyz_chinese);
        String[] dyz_pinyin = context.getResources().getStringArray(R.array.dyz_pinyin);
        String[] dyz_sx = context.getResources().getStringArray(R.array.dyz_sx);
        for (int i = 0; i < dyz.length; i++) {
            dyzSourcePinyinMap.put(dyz[i], dyz_pinyin[i]);
            dyzSourceSxMap.put(dyz[i], dyz_sx[i]);
        }
    }

    public static String[] getPinyinAndSX(String str) {
        if (TextUtils.isEmpty(str))
            throw new IllegalArgumentException("null Pinyin source");
        if (dyzSourcePinyinMap.containsKey(str))
            return new String[]{dyzSourcePinyinMap.get(str), dyzSourceSxMap.get(str)};

        StringBuilder pinyinBuilder = new StringBuilder();
        StringBuilder sxBuilder = new StringBuilder();
        int length = str.length();
        String[] temp;
        try {
            for (int i = 0; i < length; i++) {
                temp = PinyinHelper.toHanyuPinyinStringArray(str.charAt(i), format);
                if (temp != null) {
                    pinyinBuilder.append(temp[0]);
                    sxBuilder.append(temp[0].substring(0, 1));
                } else {
                    pinyinBuilder.append(str.charAt(i));
                    sxBuilder.append(str.charAt(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[]{pinyinBuilder.toString(), sxBuilder.toString()};
    }

}
