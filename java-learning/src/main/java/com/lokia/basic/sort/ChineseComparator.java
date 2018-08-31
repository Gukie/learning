package com.lokia.basic.sort;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.Comparator;

/**
 * @author gushu
 * @data 2018/8/31
 */
public class ChineseComparator implements Comparator<String> {
    @Override
    public int compare(String str1, String str2) {
        String key1 = str1.toString();
        String key2 = str2.toString();

        for (int i = 0; i < key1.length() && i < key2.length(); i++) {
            int codePoint1 = key1.charAt(i);
            int codePoint2 = key2.charAt(i);

            if (Character.isSupplementaryCodePoint(codePoint1) || Character.isSupplementaryCodePoint(codePoint2)) {
                i++;
            }
            if (codePoint1 != codePoint2) {
                if (Character.isSupplementaryCodePoint(codePoint1) || Character.isSupplementaryCodePoint(codePoint2)) {
                    return codePoint1 - codePoint2;
                }

                String pinyin1 = pinyin((char) codePoint1);
                String pinyin2 = pinyin((char) codePoint2);

                if (pinyin1 != null && pinyin2 != null) { // 两个字符都是汉字
                    if (!pinyin1.equals(pinyin2)) {
                        return pinyin1.compareTo(pinyin2);
                    }
                } else {
                    return codePoint1 - codePoint2;
                }
            }
        }

        return key1.length() - key2.length();
    }

    private String pinyin(char c) {
        String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c);
        if (pinyins == null) {
            return null;   //如果转换结果为空，则返回null
        }
        return pinyins[0];   //如果为多音字返回第一个音节
    }
}
