package com.lokia;

import java.text.MessageFormat;

/**
 * @author gushu
 * @data 2018/8/25
 */
public class MsgFormatUtils {

    /**
     * 获取formatted消息. 占位符使用 <code>{}</code>
     *
     * @param detail 里面是包含<code>{}</code>的占位符
     * @param params 参数列表
     * @return
     */
    public static String generateFormattedMsg(String detail, Object... params) {
        String keyword = "{}";
        if(params == null || params.length == 0 || !detail.contains(keyword)){
            return detail;
        }
        StringBuilder pattern = generateMsgPattern(detail, keyword);
        try {
            return MessageFormat.format(pattern.toString(), params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static StringBuilder generateMsgPattern(String detail, String keyword) {
        StringBuilder pattern = new StringBuilder();
        int keywordNum = 0;
        int fromIndex = 0;
        int keywordIndex = -1;
        while((keywordIndex = detail.indexOf(keyword,fromIndex))!=-1){
            pattern.append(detail.substring(fromIndex,keywordIndex));
            pattern.append("{").append(keywordNum).append("}");
            fromIndex=keywordIndex+keyword.length();
            keywordNum++;
        }
        if (fromIndex != detail.length()) {
            pattern.append(detail.substring(fromIndex));
        }
        return pattern;
    }
}
