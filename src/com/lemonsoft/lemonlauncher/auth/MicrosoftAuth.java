package com.lemonsoft.lemonlauncher.auth;

import com.google.gson.JsonObject;
import com.lemonsoft.lemonlauncher.data.MCAccount;
import com.lemonsoft.lemonlauncher.util.WebUtil;
import net.lenni0451.commons.httpclient.HttpClient;
import net.raphimc.minecraftauth.MinecraftAuth;
import net.raphimc.minecraftauth.step.java.session.StepFullJavaSession;
import net.raphimc.minecraftauth.step.msa.StepMsaDeviceCode;

/**
 *
 * @author lone64dev
 * 
 */
public class MicrosoftAuth {
    
        public MCAccount login() {
        try {
            final HttpClient httpClient = MinecraftAuth.createHttpClient();
            final StepFullJavaSession.FullJavaSession javaSession = MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.getFromInput(httpClient, new StepMsaDeviceCode.MsaDeviceCodeCallback(msaDeviceCode -> {
                System.out.println("[Auth] Preparing to microsoft login...");
                WebUtil.openWeb(msaDeviceCode.getDirectVerificationUri());
            }));
            if (javaSession.isExpired()) {
                System.out.println("[Auth] Failed to microsoft login!");
                return null;
            }

            final JsonObject jsonObject = MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.toJson(javaSession);
            System.out.println("[Auth] Successfully for logged as " + javaSession.getMcProfile().getName() + "!");
            return new MCAccount(jsonObject, javaSession.getMcProfile().getName(), javaSession.getMcProfile(), javaSession.getPlayerCertificates());
        } catch (final Exception e) {
            return null;
        }
    }

    public MCAccount login(final JsonObject serializedSession) {
        try {
            StepFullJavaSession.FullJavaSession javaSession = MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.fromJson(serializedSession);
            if (javaSession == null) {
                final HttpClient httpClient = MinecraftAuth.createHttpClient();
                javaSession = MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.refresh(httpClient, javaSession);
            }

            System.out.println("[Auth] Preparing to microsoft login...");
            if (javaSession.isExpired()) {
                System.out.println("[Auth] Failed to microsoft login!");
                return null;
            }

            final JsonObject jsonObject = MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.toJson(javaSession);
            System.out.println("[Auth] Successfully for logged as " + javaSession.getMcProfile().getName() + "!");
            return new MCAccount(jsonObject, javaSession.getMcProfile().getName(), javaSession.getMcProfile(), javaSession.getPlayerCertificates());
        } catch (final Exception e) {
            return null;
        }
    }
    
}