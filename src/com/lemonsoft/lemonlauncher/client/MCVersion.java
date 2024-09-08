package com.lemonsoft.lemonlauncher.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lemonsoft.lemonlauncher.data.MCInfo;
import java.io.File;

import static com.lemonsoft.lemonlauncher.util.FileUtil.getInstance;
import static com.lemonsoft.lemonlauncher.util.FileUtil.downloadFile;
import fr.flowarg.flowupdater.download.json.Mod;
import fr.flowarg.openlauncherlib.NoFramework;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lone64dev
 * 
 */
public class MCVersion {
    
    public MCVersion() {
        final File versionFile = getInstance("clientcache.json");
        downloadFile("https://raw.githubusercontent.com/devlone64/mc-repo/main/lemon/clientcache.json", versionFile, true);
    }
    
    public MCInfo getClient(String name) {
        for (MCInfo client : getVersionSelections()) {
            if (client.getName().equals(name)) {
                return client;
            }
        }
        return null;
    }

    public List<MCInfo> getVersionSelections() {
        try {
            final Gson gson = new Gson();
            final JsonArray versions;
            try (Reader reader = Files.newBufferedReader(getInstance("clientcache.json").toPath())) {
                versions = gson.fromJson(reader, JsonArray.class);
            }
            
            List<MCInfo> selections = new ArrayList<>();
            for (final JsonElement e : versions.asList()) {
                final List<Mod> mods = new ArrayList<>();
                for (final JsonElement e1 : e.getAsJsonObject().get("mods").getAsJsonArray().asList()) {
                    final JsonObject object = e1.getAsJsonObject();
                    String modName = object.get("name").getAsString();
                    String modUrl = object.get("downloadUrl").getAsString();
                    String modSha1 = object.get("sha1").getAsString();
                    int modSize = object.get("size").getAsInt();
                    mods.add(new Mod(modName, modUrl, modSha1, modSize));
                }

                JsonObject object = e.getAsJsonObject();
                String name = object.get("name").getAsString();
                String mcVersion = object.get("mcVersion").getAsString();
                String modVersion = object.get("modVersion").getAsString();
                NoFramework.ModLoader type = NoFramework.ModLoader.valueOf(object.get("type").getAsString().toUpperCase());
                selections.add(new MCInfo(name, mcVersion, modVersion, type, mods));
            }
            return selections;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    
    public static MCVersion getAPI() {
        return new MCVersion();
    }
    
}