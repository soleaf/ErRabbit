package org.mintcode.errabbit.model;

import org.apache.logging.log4j.core.impl.ExtendedStackTraceElement;
import org.apache.logging.log4j.core.impl.ThrowableProxy;

import java.io.Serializable;
import java.util.Arrays;

/**
 * No Use
 * Created by soleaf on 2/21/15.
 */
public class ErThrowable implements Serializable{

    private static final long serialVersionUID = 1L;

    private ErStackTraceElement[] stackTraceElements;
    private String detailMessage;

    public ErThrowable(){

    }

    /**
     * Make from java.lang.Throwable (Log4j 1.x)
     * @param tw
     * @return
     */
    public static ErThrowable formThrowable(Throwable tw){

        if (tw == null){
            return null;
        }

        ErThrowable ert = new ErThrowable();
        ert.setDetailMessage(tw.getMessage());
        StackTraceElement[] stackTraceElements = tw.getStackTrace();
        ErStackTraceElement[] erStackTraceElements = new ErStackTraceElement[tw.getStackTrace().length];
        for (int i = 0 ; i < stackTraceElements.length; i++){
            erStackTraceElements[i] = ErStackTraceElement.fromStackTraceElement(stackTraceElements[i]);
        }
        ert.setStackTraceElements(erStackTraceElements);
        return ert;
    }

    /**
     * Make from org.apache.logging.log4j.core.impl.ThrowableProxy (Log4j 2.x)
     * @param tp
     * @return
     */
    public static ErThrowable fromThrowableProxy(ThrowableProxy tp){

        if (tp == null){
            return null;
        }

        ErThrowable ert = new ErThrowable();
        ert.setDetailMessage(tp.getMessage());
        ExtendedStackTraceElement[] est = tp.getExtendedStackTrace();
        ErStackTraceElement[] erStackTraceElements = new ErStackTraceElement[est.length];
        for (int i = 0 ; i < est.length; i++){
            erStackTraceElements[i] = ErStackTraceElement.fromExtendedStackTraceElement(est[i]);
        }
        ert.setStackTraceElements(erStackTraceElements);

        return ert;
    }

    public ErStackTraceElement[] getStackTraceElements() {
        return stackTraceElements;
    }

    public void setStackTraceElements(ErStackTraceElement[] stackTraceElements) {
        this.stackTraceElements = stackTraceElements;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    @Override
    public String toString() {
        return "ErThrowable{" +
                "stackTraceElements=" + Arrays.toString(stackTraceElements) +
                ", detailMessage='" + detailMessage + '\'' +
                '}';
    }
}
