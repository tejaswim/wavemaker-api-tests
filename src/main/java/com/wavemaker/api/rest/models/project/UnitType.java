/**
 * Copyright (c) 2013 - 2014 WaveMaker Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker Inc.
 */
package com.wavemaker.api.rest.models.project;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ankitk on 6/10/15.
 */
public enum UnitType {

    MONTH("month"),
    DAY("DAY");


    private static Map<String, UnitType> stateRegistry;

    static {
        stateRegistry = new HashMap<String, UnitType>(values().length);
        initStatus();
    }

    private static void initStatus()
    {
        for (UnitType unitType : values()) {
            stateRegistry.put(unitType.getValue().toLowerCase(), unitType);
        }
    }

    public static UnitType getUnitType(String value)
    {
        if (value != null) {
            UnitType unitType = stateRegistry.get(value.trim().toLowerCase());
            if (unitType != null) {
                return unitType;
            }
        }

        return null;
    }

    private String value;

    private UnitType(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
