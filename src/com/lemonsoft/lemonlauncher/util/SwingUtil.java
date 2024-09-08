package com.lemonsoft.lemonlauncher.util;

import java.awt.Choice;

/**
 *
 * @author lone64dev
 */
public class SwingUtil {
    
    public static void clearChoice(Choice choice) {
        for (int i = 0; i < choice.getItemCount(); i++) {
            String item = choice.getItem(i);
            if (item != null) choice.remove(i);
        }
    }
    
}