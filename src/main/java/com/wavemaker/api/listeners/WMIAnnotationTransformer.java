package com.wavemaker.api.listeners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Test;

import com.wavemaker.api.config.StudioTestConfig;

/**
 * Created by Tejaswi Maryala on 11/28/2017.
 */
public class WMIAnnotationTransformer implements IAnnotationTransformer {
    private static final Logger logger = LoggerFactory.getLogger(WMIAnnotationTransformer.class);

    @Override
    public void transform(
            final ITestAnnotation annotation, final Class testClass, final Constructor testConstructor, final Method testMethod) {
        Annotation[] declaredAnnotations = testMethod.getDeclaredAnnotations();
        for (Annotation annotation1 : declaredAnnotations) {
            if (annotation1 instanceof Test) {
                int count = StudioTestConfig.getInstance().getInvocationCount();
                annotation.setInvocationCount(count);
                logger.info("Successfully set invocation count as {} for test {}", count, testMethod.getName());
            }
        }
    }
}
