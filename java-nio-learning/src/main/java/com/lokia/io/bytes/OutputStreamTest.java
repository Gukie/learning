package com.lokia.io.bytes;

import com.lokia.io.IoUtils;

import java.io.*;

public class OutputStreamTest {

    public static void main(String[] args) {


        File file = IoUtils.getFile(IoUtils.IO_FILE_DIR,IoUtils.IO_FILE_NAME);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            for(int i =0;i < 5;i++){
                String message = generateMsg(i);
                fileOutputStream.write(message.getBytes("UTF-8"));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String generateMsg(int currentLoop) {

        StringBuilder result = new StringBuilder();

        result.append("你好, this is from Mars, you are the "+currentLoop+" one\n");
        return result.toString();
    }


}
