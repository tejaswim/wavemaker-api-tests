/**
 * Copyright © 2013 - 2016 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.tests.api.rest;

import org.apache.http.impl.cookie.BasicClientCookie;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Created by ArjunSahasranam on 10/9/15.
 */
public class RestResponseModule extends SimpleModule {

    public RestResponseModule() {
        super("RestResponseModule", new Version(8, 0, 0, null, null, null));
    }

    @Override
    public void setupModule(final SetupContext context) {
        context.setMixInAnnotations(BasicClientCookie.class, BasicClientCookieMixIn.class);
    }
}
