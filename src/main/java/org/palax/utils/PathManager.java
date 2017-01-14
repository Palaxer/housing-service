package org.palax.utils;

import java.util.ResourceBundle;

/**
 * The {@code PathManager} class used to obtain the properties
 * by key from {@code "path-config.properties"} file which have the URL to the JSP page
 *
 * @author Taras Palashynskyy
 */

public class PathManager {
    /**{@link ResourceBundle} which bound to {@code "path-config.properties"} file */
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("path-config");

    private PathManager() {

    }

    /**
     * Method which used to get value of the property obtained by the key
     *
     * @param key {@code key} used to find properties
     * @return returns the value of the property obtained by the key
     */
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
