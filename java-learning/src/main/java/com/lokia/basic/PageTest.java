package com.lokia.basic;

import com.lokia.CollectionUtils;

import java.util.*;

/**
 * @author gushu
 * @data 2018/8/30
 */
public class PageTest {

    public static void main(String[] args) {


        Map<String, List<Integer>> itemDatetimeListMap = new HashMap<>();
        List<String> itemList = Arrays.asList("A","B","C","D","E");

        itemDatetimeListMap.put("A",Arrays.asList(1));
        itemDatetimeListMap.put("B",Arrays.asList(2,2));
        itemDatetimeListMap.put("C",Arrays.asList(3,3,3));
        itemDatetimeListMap.put("D",Arrays.asList(4,4,4,4));
        itemDatetimeListMap.put("E",Arrays.asList(5,5,5,5,5));


        int currentPage = 2;
        int limit = 3;
        int pageStartIndex = getStartIndex(currentPage, limit);
        int pageEndIndex = getEndIndex(currentPage, limit);
        List<String> pageItems = getCurrentPageItemList(pageStartIndex,pageEndIndex,itemDatetimeListMap,itemList);
        pageItems.forEach(item->System.out.println(item));
    }

    private static int getEndIndex(int currentPage, int limit) {
        if (currentPage < 1) {
            return limit;
        }
        return currentPage * limit;
    }

    private static int getStartIndex(int currentPage, int limit) {
        if (currentPage < 1) {
            return 0;
        }
        return (currentPage - 1) * limit;
    }


    private static List<String> getCurrentPageItemList(int pageStartIndex, int pageEndIndex, Map<String, List<Integer>> itemDatetimeListMap, List<String> itemList) {
        List<String> currPageItemList = new ArrayList<>();
        int dataCount = 0;
        for(String item: itemList){
            List<Integer> dateList =  itemDatetimeListMap.get(item);
            if(CollectionUtils.isEmpty(dateList)){
                continue;
            }
            dataCount += dateList.size();
            if(dataCount > pageStartIndex){
                currPageItemList.add(item);
            }
            if(dataCount>=pageEndIndex){
                break;
            }
        }
        return currPageItemList;
    }
}
