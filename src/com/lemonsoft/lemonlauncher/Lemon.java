package com.lemonsoft.lemonlauncher;

import lombok.Getter;

/**
 *
 * @author lone64dev
 * 
 */
@Getter
public class Lemon {
    
    private final String clientName = "Lemon Launcher";
    private final String clientPath = ".lemonlauncher";
    
    public static Lemon getInstance() {
        return new Lemon();
    }
    
}