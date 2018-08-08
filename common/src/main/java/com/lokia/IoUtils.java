package com.lokia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class IoUtils {

    private final static String NON_WINDOWS_IO_FILE_DIR = "/Users/lokia.gu/data/tmp";
    private final static String WINDOWS_IO_FILE_DIR = "D:\\tmp\\io-test\\se";
    public final static String IO_FILE_NAME = "io_tmp.txt";

    private static Set<String> SUPPORTED_FILE_SUFFIX_SET = new HashSet<>();


    public final static String[] OUT_MSG_LST = {"杭州", "西安", "上海"};
    public static CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(OUT_MSG_LST.length);
//    public final static  Object WRITER_LOCK = new Object();


    static {
        SUPPORTED_FILE_SUFFIX_SET = new HashSet<>();
        SUPPORTED_FILE_SUFFIX_SET.add(".txt");
        SUPPORTED_FILE_SUFFIX_SET.add(".json");
        SUPPORTED_FILE_SUFFIX_SET.add(".java");
        SUPPORTED_FILE_SUFFIX_SET.add(".xml");
    }

    public static String getIoFileDir() {

        if (isWindowsOS()) {
            return WINDOWS_IO_FILE_DIR;
        }
        return NON_WINDOWS_IO_FILE_DIR;
    }

    private static boolean isWindowsOS() {
        String osName = System.getProperty("os.name");
        return osName != null && osName.toLowerCase().contains("windows");
    }

//    public static File getFile(String dir, String fileName) {
//
//
//        Path path = Paths.get(dir, fileName);
//        File file = path.toFile();
//
//        if (!file.exists()) {
//            try {
//                FileAttribute fileAttribute = generateFileAttrIfNeeded();
//                if(fileAttribute != null){
//                    Files.createFile(path, fileAttribute);
//                }else{
//                    Files.createFile(path);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return file;
//
//
////        dirOne.setExecutable(true);
////        dirOne.setReadable(true);
////        dirOne.setWritable(true);
//    }


    public static File getFile(String dir, String fileName) {

        // create Directory if they not exists
        Path dirPath = Paths.get(dir);
        if(!Files.exists(dirPath)){
            try {
                Files.createDirectories(dirPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // handle file if it not existing
        Path filePath = Paths.get(dir, fileName);
        if (!Files.exists(filePath)) {
            try {
                FileAttribute fileAttribute = generateFileAttr4Unix();
                // create file
                if (fileAttribute != null) {
                    Files.createFile(filePath, fileAttribute);
                } else {
                    Files.createFile(filePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return filePath.toFile();


//        dirOne.setExecutable(true);
//        dirOne.setReadable(true);
//        dirOne.setWritable(true);
    }

    public static boolean isDir(File file) {
        String fileName1 = file.getName();
        int dotIndex = fileName1.lastIndexOf(".");
        boolean isDir = true; //由于 file.isDirectory在文件不存在的时候返回false，需要自己手动做
        if (dotIndex != -1) {
            String suffix = fileName1.substring(dotIndex);
            if (SUPPORTED_FILE_SUFFIX_SET.contains(suffix)) {
                isDir = false;
            }
        }

        return isDir;
    }

    public static FileAttribute generateFileAttr4Unix() {
        if (!isWindowsOS()) {
            return PosixFilePermissions.asFileAttribute(EnumSet.allOf(PosixFilePermission.class));
        }
        return null;
    }

    private static void grantAccess(File dirOne) {

//        System.

        //      Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-rw-rw-");
//      FileAttribute<Set<PosixFilePermission>> attrs = PosixFilePermissions.asFileAttribute(perms);


    }
}
