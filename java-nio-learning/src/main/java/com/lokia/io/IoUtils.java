package com.lokia.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class IoUtils {

    private final static String NON_WINDOWS_IO_FILE_DIR = "/Users/lokia.gu/data/tmp";
    private final static String WINDOWS_IO_FILE_DIR = "D:\\tmp";
    public final static String IO_FILE_NAME = "io_tmp.txt";

    public static String getIoFileDir(){

        if(isWindowsOS()){
            return WINDOWS_IO_FILE_DIR;
        }
        return NON_WINDOWS_IO_FILE_DIR;
    }

    private static boolean isWindowsOS() {
        String osName = System.getProperty("os.name");
        return osName !=null && osName.toLowerCase().contains("windows");
    }

    public static File getFile(String dir, String fileName) {

        Path path = Paths.get(dir, fileName);
        File file = path.toFile();

        if (!file.exists()) {
            try {
                FileAttribute fileAttribute = generateFileAttrIfNeeded();
                if(fileAttribute != null){
                    Files.createFile(path, fileAttribute);
                }else{
                    Files.createFile(path);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;


//        dirOne.setExecutable(true);
//        dirOne.setReadable(true);
//        dirOne.setWritable(true);
    }

    private static FileAttribute generateFileAttrIfNeeded() {
        if (!isWindowsOS()) {
            Set<PosixFilePermission> filePermissionSet = PosixFilePermissions.fromString("rwxrwxrwx");
            return PosixFilePermissions.asFileAttribute(filePermissionSet);
        }
        return null;
    }

    private static void grantAccess(File dirOne) {

//        System.

        //      Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-rw-rw-");
//      FileAttribute<Set<PosixFilePermission>> attrs = PosixFilePermissions.asFileAttribute(perms);


    }
}
