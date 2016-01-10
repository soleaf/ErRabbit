package org.mintcode.errabbit.model;

import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.spi.ThrowableInformation;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Created by soleaf on 9/29/15.
 */
public class ErThrowableInformationTest {

    @Test
    public void testFromThrowableInformation() throws Exception {

        StackTraceElement[] stackTraceElements = new StackTraceElement[]{
                new StackTraceElement("className", "methodName", "fileName", 1),
                new StackTraceElement("className2", "methodName2", "fileName2", 2)
        };
        Throwable tw = new Throwable("detailMessage");
        tw.setStackTrace(stackTraceElements);
        Category category = Category.getInstance("category");
        category.setLevel(Level.WARN);
        ThrowableInformation twi = new ThrowableInformation(tw, category);

        ErThrowableInformation erThrowableInformation
                = ErThrowableInformation.fromThrowableInformation(twi);

        assertTrue(equalsWithThrowableInformation(erThrowableInformation, twi));
    }

    @Test
    public void testFromThrowableProxy() throws Exception {
        StackTraceElement[] stackTraceElements = new StackTraceElement[]{
                new StackTraceElement("className", "methodName", "fileName", 1),
                new StackTraceElement("className2", "methodName2", "fileName2", 2)
        };
        Throwable tw = new Throwable("detailMessage");
        tw.setStackTrace(stackTraceElements);
        Category category = Category.getInstance("category");
        category.setLevel(Level.WARN);
        ThrowableProxy twp = new ThrowableProxy(tw);
        ErThrowableInformation erThrowableInformation
                = ErThrowableInformation.fromThrowableProxy(twp);

        assertTrue(equalsWithThrowableInformation(erThrowableInformation, twp));
    }

    /**
     * ErThrowableInformation is equals with ThrowableInformation
     * @param erTWI
     * @param twi
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static  boolean equalsWithThrowableInformation(ErThrowableInformation erTWI,
                                                         ThrowableInformation twi) throws NoSuchFieldException, IllegalAccessException {

        Field field = twi.getClass().getDeclaredField("category");
        field.setAccessible(true);

        if (!ErCategoryTest.equalsWithCategory(erTWI.getCategory(), (Category) field.get(twi))){
            return false;
        }

        if (!ErThrowableTest.equalsWithThrowable(erTWI.getThrowable(), twi.getThrowable())){
            return false;
        }

        return true;
    }

    /**
     * ErThrowableInformation is equals with ThrowableProxy
     * @param erTWI
     * @param twp
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static  boolean equalsWithThrowableInformation(ErThrowableInformation erTWI,
                                                         ThrowableProxy twp) throws NoSuchFieldException, IllegalAccessException {


        if (!ErThrowableTest.equalsWithThrowable(erTWI.getThrowable(), twp)){
            return false;
        }

        return true;
    }
}