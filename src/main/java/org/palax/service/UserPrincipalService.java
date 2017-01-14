package org.palax.service;

import org.apache.log4j.Logger;
import org.palax.entity.User;
import org.palax.utils.PathManager;
import org.palax.utils.UserPrincipalManager;

import java.util.*;

/**
 * The {@code UserPrincipalService} class that provides check the access rights to the resources
 *
 * @author Taras Palashynskyy
 */

public class UserPrincipalService {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(UserPrincipalService.class);

    /**Map which store {@link User} base mapping to redirection page. */
    private static final Map<String, String> userBaseMapping = new HashMap<>();
    /**Map which store {@link User} permission mapping, where store
     * page {@code path} and {@code roleType} which has permission to page. */
    private static final Map<String, Set<String>> userPermissionMapping = new HashMap<>();

    /**
     * Initialization user base mapping and permission mapping
     */
    static {
        logger.debug("Initialization user base mapping");
        userBaseMapping.put("ADMIN", PathManager.getProperty("path.redirect.admin"));
        userBaseMapping.put("CREW", PathManager.getProperty("path.redirect.crew"));
        userBaseMapping.put("ADVISOR", PathManager.getProperty("path.redirect.advisor"));
        userBaseMapping.put("TENANT", PathManager.getProperty("path.redirect.tenant"));

        Enumeration<String> key = UserPrincipalManager.getKey();

        while (key.hasMoreElements()) {
            String keys = key.nextElement();
            Set<String> value = new HashSet<>();

            StringTokenizer stringTokenizer = new StringTokenizer(UserPrincipalManager.getProperty(keys));
            while(stringTokenizer.hasMoreTokens()) {
                value.add(stringTokenizer.nextToken());
            }
            userPermissionMapping.put(keys, value);
        }
    }

    private UserPrincipalService() {

    }

    /**
     * Method checked {@link User} permission to {@code path}
     *
     * @param user the {@code user} who checked for access
     * @param path the {@code path} access to which is requested
     * @return returns {@code true} if the {@code user} has permission to {@code path}
     *         or else {@code false}
     */
    public static boolean permission(User user, String path) {

        if(user == null)
            return false;

        return userPermissionMapping.get(path).contains(user.getRole().getRoleType()) ||
                userPermissionMapping.get(path).contains("ALL");

    }

    /**
     * Method finds the {@link User} base page on which it is necessary
     * to redirect at login to system
     *
     * @param user a {@code user} who is logged on
     * @return return {@code user} base page or {@code "path.page.error500"}
     *         if {@code user} was {@code null}
     */
    public static String userBaseMapping(User user) {
        String page = PathManager.getProperty("path.page.login");

        if (user != null) {
            page = userBaseMapping.getOrDefault(user.getRole().getRoleType(),
                    PathManager.getProperty("path.page.error500"));
        }

        return page;
    }
}
