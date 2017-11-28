package com.wavemaker.api.manager;

/**
 * Created by ArjunSahasranam on 5/2/16.
 */
public class SecurityManager {

    private static final ThreadLocal<String> SECURITY_CONTEXT = new ThreadLocal<String>();

    public static void setAuthCookie(String authCookie) {
        SECURITY_CONTEXT.set(authCookie);
    }

    public static String getAuthCookie() {
        return SECURITY_CONTEXT.get();
    }

    public static void clear(){
        SECURITY_CONTEXT.remove();
    }

}
