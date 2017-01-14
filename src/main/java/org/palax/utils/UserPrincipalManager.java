package org.palax.utils;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * The {@code UserPrincipalManager} class used to obtain the properties
 * by key from {@code "user-principal-config.properties"} file which has information
 * about the page access rights
 *
 * @author Taras Palashynskyy
 */

public class UserPrincipalManager {
    /**{@link ResourceBundle} which bound to {@code "user-principal-config.properties"} file */
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("user-principal-config");

    private UserPrincipalManager() {

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

    /**
     * Method which used to get all {@code key} from properties file
     *
     * @return return all {@code key} from properties file
     */
    public static Enumeration<String> getKey() {
        return resourceBundle.getKeys();
    }
}
