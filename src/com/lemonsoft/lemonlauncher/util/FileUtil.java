package com.lemonsoft.lemonlauncher.util;

import com.lemonsoft.lemonlauncher.Lemon;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author lone64dev
 * 
 */
public class FileUtil {

    public static boolean createFile(final File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
                return true;
            }
            return false;
        } catch (final IOException e) {
            return false;
        }
    }

    public static boolean createDirectory(final File file) {
        if (!file.exists()) {
            file.mkdir();
            return true;
        }
        return false;
    }

    public static boolean deleteFile(final File file) {
        try {
            if (file.exists()) {
                FileUtils.delete(file);
            }
            return false;
        } catch (final IOException e) {
            return false;
        }
    }

    public static boolean deleteDirectory(final File file) {
        try {
            if (file.exists()) {
                FileUtils.deleteDirectory(file);
            }
            return false;
        } catch (final IOException e) {
            return false;
        }
    }

    public static boolean downloadFile(final String url, final File file, final boolean replaced) {
        try {
            if (replaced) {
                FileUtils.copyURLToFile(new URL(url), file);
                return true;
            }

            if (!file.exists()) {
                FileUtils.copyURLToFile(new URL(url), file);
                return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean unzip(final String s, final String path) {
        try {
            final UnzipUtil wrapper = new UnzipUtil();
            wrapper.unzip(s, path);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static File getInstance() {
        return new File("C:/Users/%s/AppData/Roaming/%s".formatted(System.getProperty("user.name"), Lemon.getInstance().getClientPath()));
    }

    public static File getInstance(final String path) {
        return new File("C:/Users/%s/AppData/Roaming/%s/%s".formatted(System.getProperty("user.name"), Lemon.getInstance().getClientPath(), path));
    }

}