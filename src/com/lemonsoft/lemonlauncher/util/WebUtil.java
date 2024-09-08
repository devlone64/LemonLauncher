package com.lemonsoft.lemonlauncher.util;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author lone64dev
 * 
 */
public class WebUtil {

    public static void openWeb(final String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException ignored) { }
    }

}