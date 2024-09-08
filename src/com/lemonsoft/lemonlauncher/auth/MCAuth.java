package com.lemonsoft.lemonlauncher.auth;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lemonsoft.lemonlauncher.data.MCAccount;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.lemonsoft.lemonlauncher.util.FileUtil.getInstance;
import static com.lemonsoft.lemonlauncher.util.FileUtil.createDirectory;

public class MCAuth {

    public MCAuth() {
        final File dataFolder = getInstance();
        if (!dataFolder.exists()) createDirectory(dataFolder);

        final File instancesFolder = getInstance("instances");
        if (!instancesFolder.exists()) createDirectory(instancesFolder);
    }

    public MCAccount login() {
        try {
            final MCAccount session = getLoadedSession();
            if (session == null) {
                final Gson gson = new Gson();
                final MCAccount account = new MicrosoftAuth().login();
                if (account == null) return null;
                try (Writer writer = Files.newBufferedWriter(getInstance("usercache.json").toPath())) {
                    gson.toJson(account.getJsonObject(), writer);
                }
                return account;
            }
            return session;
        } catch (final IOException e) {
            return null;
        }
    }
    
    public MCAccount account() {
        MCAccount account = getLoadedSession();
        if (account == null) return null;
        return account;
    }

    private MCAccount getLoadedSession() {
        try {
            final File file = getInstance("usercache.json");
            if (file.exists()) {
                final Gson gson = new Gson();
                final JsonObject serializedSession;
                try (Reader reader = Files.newBufferedReader(Paths.get(getInstance("usercache.json").getAbsolutePath()))) {
                    serializedSession = gson.fromJson(reader, JsonObject.class);
                }
                return new MicrosoftAuth().login(serializedSession);
            }
            return null;
        } catch (final IOException e) {
            return null;
        }
    }
    
    public static MCAuth getAPI() {
        return new MCAuth();
    }

}