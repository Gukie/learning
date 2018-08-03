package com.lokia.io.chars;

import com.lokia.io.IoUtils;

import java.io.*;
import java.nio.charset.Charset;

public class IoReaderTest {
    public static void main(String[] args) {

        File file = IoUtils.getFile(IoUtils.getIoFileDir(),IoUtils.IO_FILE_NAME);
        try {
            InputStream inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("utf-8")));

            String line = reader.readLine();
            while(line !=null){
                System.out.println(line);
                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
