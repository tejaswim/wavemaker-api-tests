/**
 * Copyright © 2015 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.api.rest.util;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.Source;

import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.util.ClassUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavemaker.api.rest.RestResponseModule;

/**
 * @author Uday Shankar
 */
public class WMRuntimeUtils {

    private static boolean romePresent =
            ClassUtils.isPresent("com.rometools.rome.feed.WireFeed", WMRuntimeUtils.class.getClassLoader());

    private static final boolean jaxb2Present =
            ClassUtils.isPresent("javax.xml.bind.Binder", WMRuntimeUtils.class.getClassLoader());

    private static final boolean jackson2Present =
            ClassUtils
                    .isPresent("com.fasterxml.jackson.databind.ObjectMapper", WMRuntimeUtils.class.getClassLoader()) &&
                    ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator",
                            WMRuntimeUtils.class.getClassLoader());

    private static final boolean jackson2XmlPresent =
            ClassUtils
                    .isPresent("com.fasterxml.jackson.dataformat.xml.XmlMapper", WMRuntimeUtils.class.getClassLoader());

    private static final boolean gsonPresent =
            ClassUtils.isPresent("com.google.gson.Gson", WMRuntimeUtils.class.getClassLoader());

    private static final List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

    private static final String BYTE_ARRAY = "byte[]";

    private static final String BLOB = "Blob";

    private static final ObjectMapper objectMapper  = Jackson2ObjectMapperBuilder.json().build();

    static {
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF8")));
        messageConverters.add(new ResourceHttpMessageConverter());
        messageConverters.add(new SourceHttpMessageConverter<Source>());
        messageConverters.add(new AllEncompassingFormHttpMessageConverter());
        messageConverters.add(new FormHttpMessageConverter());
        if (romePresent) {
            messageConverters.add(new AtomFeedHttpMessageConverter());
            messageConverters.add(new RssChannelHttpMessageConverter());
        }
        if (jackson2XmlPresent) {
            messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
        }
        if (jaxb2Present) {
            messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
        }
        if (jackson2Present) {
            objectMapper.registerModule(new RestResponseModule());
            messageConverters.add(new MappingJackson2HttpMessageConverter(objectMapper));
            messageConverters.add(new MappingJackson2HttpMessageConverter());
        } else if (gsonPresent) {
            messageConverters.add(new GsonHttpMessageConverter());
        }
    }

    public static List<HttpMessageConverter<?>> getMessageConverters() {
        return messageConverters;
    }

    public static boolean isLob(Class instance, String field) {
        Field declaredField = null;
        try {
            declaredField = instance.getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Filed " + field + " does not exist in class " + instance.getName(), e);
        }
        if (declaredField != null && (BYTE_ARRAY.equals(declaredField.getType().getSimpleName()) || BLOB
                .equals(declaredField.getType().getSimpleName()))) {
            return true;
        }
        return false;
    }

}
