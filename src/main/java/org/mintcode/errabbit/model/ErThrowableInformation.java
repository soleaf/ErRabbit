package org.mintcode.errabbit.model;

import org.apache.log4j.Category;
import org.apache.log4j.spi.ThrowableInformation;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * ErThrowableInformation
 * It is equivalent to org.apache.log4j.spi.ThrowableInformation
 * (convert to save repository)
 * Created by soleaf on 2/21/15.
 */
public class ErThrowableInformation implements Serializable{

    private static final Logger logger = LoggerFactory.getLogger(ErThrowableInformation.class);

    private static final long serialVersionUID = 1L;

    private ErThrowable throwable;
    private ErCategory category;
    private String[] rep;

    public ErThrowableInformation(){}

    /***
     * Make from org.apache.log4j.spi.ThrowableInformation (Log4j 1.x)
     * @param tw
     * @return
     */
    public static ErThrowableInformation fromThrowableInformation(ThrowableInformation tw){

        ErThrowableInformation ert = new ErThrowableInformation();
        ert.setThrowable(ErThrowable.formThrowable(tw.getThrowable()));
        ert.setRep(tw.getThrowableStrRep());

        try {
            Field field = tw.getClass().getDeclaredField("category");
            field.setAccessible(true);
            ert.setCategory(ErCategory.fromCategory((Category) field.get(tw)));
        } catch (NoSuchFieldException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }

        return ert;
    }

    /***
     * Make from org.apache.logging.log4j.core.impl.ThrowableProxy (Log4j 2.x)
     * @param tp
     * @return
     */
    public static ErThrowableInformation fromThrowableProxy(ThrowableProxy tp){
        ErThrowableInformation ert = new ErThrowableInformation();
        ert.setThrowable(ErThrowable.fromThrowableProxy(tp));

        // getReps
        String[] rep = new String[tp.getExtendedStackTrace().length+1];
        rep[0] = tp.toString();
        for (int i = 0; i < rep.length-1; i++){
            rep[i+1] = tp.getExtendedStackTrace()[i].toString();
        }
        ert.setRep(rep);
        return ert;
    }

    /**
     * Get ErThrowable
     * @return
     */
    public ErThrowable getThrowable() {
        return throwable;
    }
    public void setThrowable(ErThrowable throwable) {
        this.throwable = throwable;
    }

    /**
     * Get ErCategory
     * @return
     */
    public ErCategory getCategory() {
        return category;
    }
    public void setCategory(ErCategory category) {
        this.category = category;
    }

    /**
     * Get text
     * @return
     */
    public String[] getRep() {
        return rep;
    }
    public void setRep(String[] rep) {
        this.rep = rep;
    }

    @Override
    public String toString() {
        return "ErThrowableInformation{" +
                "throwable=" + throwable +
                ", category=" + category +
                ", rep=" + Arrays.toString(rep) +
                '}';
    }
}
