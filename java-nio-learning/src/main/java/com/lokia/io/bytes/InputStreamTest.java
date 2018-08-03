package com.lokia.io.bytes;

import com.lokia.io.IoUtils;

import java.io.*;

public class InputStreamTest {

    public static void main(String[] args) {

        File file = IoUtils.getFile(IoUtils.getIoFileDir(),IoUtils.IO_FILE_NAME);
        try {
            // fetch the file descriptor; file descriptor represents the opened file/socket or other opened source.
//            FileOutputStream outputStream = new FileOutputStream(file);
//            FileDescriptor fileDescriptor = outputStream.getFD();
            FileInputStream first = new FileInputStream(file);
            FileDescriptor fileDescriptor = first.getFD();

            // use the file descriptor to create a InputStream.
            FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
            int read = fileInputStream.read();
            while(read!= -1){
                System.out.print((char) read);
//                System.out.print(read);
                read = fileInputStream.read();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
