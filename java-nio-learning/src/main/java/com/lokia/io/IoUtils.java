package com.lokia.io;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class IoUtils {

    public final static String IO_FILE_DIR = "/Users/lokia.gu/data/tmp";
    public final static String IO_FILE_NAME = "io_tmp.txt";

    public static File getFile(String dir, String fileName) {

        Path path = Paths.get(dir,fileName);
        File file = path.toFile();



        String osName = System.getProperty("os.name");


            if(!file.exists()){

                if(osName!=null && !osName.toLowerCase().contains("windows")){
                    try {
                        Set<PosixFilePermission> filePermissionSet= PosixFilePermissions.fromString("rwxrwxrwx");
                        FileAttribute fileAttribute = PosixFilePermissions.asFileAttribute(filePermissionSet);

                        Files.createFile(path,fileAttribute);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }{
                    System.out.print("windows file");
                }

            }

            return file;


//        dirOne.setExecutable(true);
//        dirOne.setReadable(true);
//        dirOne.setWritable(true);
    }

    private static void grantAccess(File dirOne) {

//        System.

                //      Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-rw-rw-");
//      FileAttribute<Set<PosixFilePermission>> attrs = PosixFilePermissions.asFileAttribute(perms);


    }
}
