package org.mintcode.errabbit.model;

import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by soleaf on 9/29/15.
 */
public class ErThrowableTest {

    /**
     * Check parsing from log4j 1.x
     * @throws Exception
     */
    @Test
    public void testFormThrowable() throws Exception {
        StackTraceElement[] stackTraceElements = new StackTraceElement[]{
                new StackTraceElement("className", "methodName", "fileName", 1),
                new StackTraceElement("className2", "methodName2", "fileName2", 2)
        };
        Throwable tw = new Throwable("detailMessage");
        tw.setStackTrace(stackTraceElements);
        ErThrowable erThrowable = ErThrowable.formThrowable(tw);

        assertTrue(equalsWithThrowable(erThrowable, tw));
    }

    /**
     * Parsing check from log4j 2.x
     * @throws Exception
     */
    @Test
    public void testFromThrowableProxy() throws Exception {
        StackTraceElement[] stackTraceElements = new StackTraceElement[]{
                new StackTraceElement("className", "methodName", "fileName", 1),
                new StackTraceElement("className2", "methodName2", "fileName2", 2)
        };
        Throwable tw = new Throwable("detailMessage");
        tw.setStackTrace(stackTraceElements);
        ThrowableProxy twp = new ThrowableProxy(tw);
        ErThrowable erThrowable = ErThrowable.fromThrowableProxy(twp);

        assertTrue(equalsWithThrowable(erThrowable, twp));
    }

    /**
     * is Equal between ErStackTrace and StacktraceElement
     * @param erStackTraceElement
     * @param stackTraceElement
     * @return
     */
    public static boolean equalsStackTraceElements(ErStackTraceElement erStackTraceElement,
                                              StackTraceElement stackTraceElement){
        return erStackTraceElement.getDeclaringClass().equals(stackTraceElement.getClassName())
                && erStackTraceElement.getFileName().equals(stackTraceElement.getFileName())
                && erStackTraceElement.getMethodName().equals(stackTraceElement.getMethodName())
                && erStackTraceElement.getLineNumber() == stackTraceElement.getLineNumber();
    }

    /**
     * ErThrowable equals with Throwable
     * @param erThrowable
     * @param throwable
     * @return
     */
    public static boolean equalsWithThrowable(ErThrowable erThrowable, Throwable throwable){
        if (!erThrowable.getDetailMessage().equals(throwable.getMessage())){
            return false;
        }
        for (int i = 0; i < erThrowable.getStackTraceElements().length ; i++){
            StackTraceElement el = throwable.getStackTrace()[i];
            ErStackTraceElement rel = erThrowable.getStackTraceElements()[i];
            if (!equalsStackTraceElements(rel, el)){
                return false;
            }
        }
        return true;
    }

    /**
     * ErThrowable equals with ThrowableProxy
     * @param erThrowable
     * @param throwable
     * @return
     */
    public static boolean equalsWithThrowable(ErThrowable erThrowable, ThrowableProxy throwable){
        if (!erThrowable.getDetailMessage().equals(throwable.getMessage())){
            return false;
        }
        for (int i = 0; i < erThrowable.getStackTraceElements().length ; i++){
            StackTraceElement el = throwable.getStackTrace()[i];
            ErStackTraceElement rel = erThrowable.getStackTraceElements()[i];
            if (!equalsStackTraceElements(rel, el)){
                return false;
            }
        }
        return true;
    }
}