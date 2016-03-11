package org.mintcode.errabbit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * LoggerType handling utilities
 * Created by soleaf on 1/31/16.
 */
public class LoggerTypeUtil {

    /**
     * Get LoggerType from integer value
     * @param value
     * @return
     */
    public static LoggerType loggerTypeFromValue(Integer value){
        switch (value){
            case 0:
                return LoggerType.Log4j;
            case 1:
                return LoggerType.PythonLogger;
            default:
                return null;
        }
    }

    /**
     * Get all LoggerTypes as list
     * @return
     */
    public static List<LoggerType> allLoggerTypes(){
        List<LoggerType> loggerTypeList = new ArrayList<>();
        loggerTypeList.add(LoggerType.Log4j);
        loggerTypeList.add(LoggerType.PythonLogger);
        return loggerTypeList;
    }
}
