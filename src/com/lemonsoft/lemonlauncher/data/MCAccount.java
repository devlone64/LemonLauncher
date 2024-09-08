package com.lemonsoft.lemonlauncher.data;

import com.google.gson.JsonObject;
import net.raphimc.minecraftauth.step.java.StepMCProfile;
import net.raphimc.minecraftauth.step.java.StepPlayerCertificates;

/**
 *
 * @author lone64dev
 * 
 */
public class MCAccount {

    private final JsonObject jsonObject;

    private final String username;
    private final StepMCProfile.MCProfile profile;
    private final StepPlayerCertificates.PlayerCertificates playerCertificates;

    public MCAccount(JsonObject jsonObject, String username, StepMCProfile.MCProfile profile, StepPlayerCertificates.PlayerCertificates playerCertificates) {
        this.jsonObject = jsonObject;
        this.username = username;
        this.profile = profile;
        this.playerCertificates = playerCertificates;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public String getUsername() {
        return username;
    }

    public StepMCProfile.MCProfile getProfile() {
        return profile;
    }

    public StepPlayerCertificates.PlayerCertificates getPlayerCertificates() {
        return playerCertificates;
    }
    
}