package com.bookstore.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtils {

    private static final Logger LOGGER = Logger.getLogger(FileUtils.class.getName());

    public static void writeObjectToFile(Object obj, String filepath, boolean fileAppend) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filepath, fileAppend);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, exception.toString(), exception);
        }
    }

    public static List<Object> readObjectArrayFromFile(String filePath) {
        List<Object> objectsList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            boolean readContinue = true;

            while (readContinue) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Object obj = objectInputStream.readObject();
                if (obj != null) {
                    objectsList.add(obj);
                } else {
                    readContinue = false;
                }
            }
        } catch (EOFException eofException) {
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, exception.toString(), exception);
        }
        return objectsList;
    }

    public static Object readObjectFromFile(String filePath) {
        Object result = null;
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            result = objectInputStream.readObject();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, exception.toString(), exception);
        }
        return result;
    }

    public static File[] getFilesMatchingPattern(String filePath, String fileNameStartsWith) {
        if (filePath == null || filePath.isEmpty())
            filePath = ".";
        File file = new File(filePath);
        File[] files = null;
        if (file.isDirectory()) {
            files = file.listFiles((dir, name) -> name.matches(fileNameStartsWith + ".*\\.txt"));
        }

        return files;
    }

    public static void deleteFilesMatchingFileNamePattern(String filePath, String fileNameStartsWith, List<String> ignoreFiles) {
        List<File> files = List.of(getFilesMatchingPattern(filePath, fileNameStartsWith));
        files.stream()
                .filter((file) -> !ignoreFiles.contains(file.getName()))
                .forEach(File::delete);
    }

    public static void flushFile(String filePath) {
        try {
            new FileOutputStream(filePath).close();
        } catch (IOException ioException) {
            LOGGER.log(Level.SEVERE, ioException.toString(), ioException);
        }
    }
}
