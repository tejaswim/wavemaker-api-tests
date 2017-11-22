/**
 * Copyright (c) 2013 - 2014 WaveMaker Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker Inc.
 */
package com.wavemaker.api.rest.models.project;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 18/8/14
 */
public abstract class BaseEntity<ID> {

    @JsonIgnore
    public abstract ID getId();

    public abstract void setId(ID id);
}
