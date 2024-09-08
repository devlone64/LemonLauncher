package com.lemonsoft.lemonlauncher.util;

import java.io.File;

/**
 *
 * @author lone64dev
 * 
 */
public enum OSHelper {

    WINDOWS("AppData" + File.separator + "Roaming" + File.separator + ".minecraft"),
    MAC("Library" + File.separator + "Applacation Support" + File.separator + "minecraft"),
    LINUX(".minecraft");

    private final String mc;

    OSHelper(final String mc) {
        this.mc = mc;
    }

    public String getMc() {
        return System.getProperty("user.home") + this.mc;
    }

    public static OSHelper getOS() {
        String currentOS = System.getProperty("os.name").toLowerCase();
        if (currentOS.startsWith("windows")) {
            return WINDOWS;
        } else if (currentOS.startsWith("mac")) {
            return WINDOWS;
        }
        return LINUX;
    }

}