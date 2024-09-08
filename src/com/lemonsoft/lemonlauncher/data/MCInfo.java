package com.lemonsoft.lemonlauncher.data;

import fr.flowarg.flowupdater.download.json.Mod;
import fr.flowarg.openlauncherlib.NoFramework;
import java.util.List;
import lombok.Getter;

/**
 *
 * @author lone64dev
 * 
 */
@Getter
public class MCInfo {
    
    private final String name;
    private final String mcVersion;
    private final String modVersion;
    private final NoFramework.ModLoader modLoader;
    private final List<Mod> mods;
    
    public MCInfo(String name, String mcVersion, String modVersion, NoFramework.ModLoader modLoader, List<Mod> mods) {
        this.name = name;
        this.mcVersion = mcVersion;
        this.modVersion = modVersion;
        this.modLoader = modLoader;
        this.mods = mods;
    }
    
}