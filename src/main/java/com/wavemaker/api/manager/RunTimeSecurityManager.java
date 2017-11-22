package com.wavemaker.api.manager;

/**
 * Created by Prithvi Medavaram on 6/5/16.
 */
public class RunTimeSecurityManager {
    private static final ThreadLocal<String> SECURITY_CONTEXT = new ThreadLocal<>();

    public static void setAuthCookie(String authCookie) {
        SECURITY_CONTEXT.set(authCookie);
    }

    public static String getAuthCookie() {
        return SECURITY_CONTEXT.get();
    }

    public static void clear() {
        SECURITY_CONTEXT.remove();
    }
}
