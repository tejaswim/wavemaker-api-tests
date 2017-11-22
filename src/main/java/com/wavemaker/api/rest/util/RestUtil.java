package com.wavemaker.api.rest.util;

import java.util.List;

/**
 * Created by ArjunSahasranam on 4/2/16.
 */
public class RestUtil {
    public static String[] getStringList(Object obj) {
        if (obj instanceof String) {
            return new String[]{(String) obj};
        }
        if (obj instanceof String[]) {
            return (String[]) obj;
        }
        if (obj instanceof List) {
            List o = (List) obj;
            return (String[]) o.toArray(new String[]{});
        }
        throw new RuntimeException("obj of type " + obj.getClass() + " not supported by this method");
    }
}
