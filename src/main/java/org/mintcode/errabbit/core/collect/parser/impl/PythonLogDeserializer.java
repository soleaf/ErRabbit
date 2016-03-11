package org.mintcode.errabbit.core.collect.parser.impl;

import com.google.gson.*;
import org.apache.log4j.Level;
import org.mintcode.errabbit.model.*;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Python ErRabbit logger log deserializer
 * Matching info.
 * python  ---  java
 * module  ---  class
 * funcName---  methodName
 *
 * Created by soleaf on 1/28/16.
 */
public class PythonLogDeserializer implements JsonDeserializer<ErrLoggingEvent> {

    @Override
    public ErrLoggingEvent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject log = (JsonObject) jsonElement;
        ErrLoggingEvent ee = new ErrLoggingEvent();
        ee.setCategoryName(log.get("module").getAsString());
        ee.setRenderedMessage(log.get("msg").getAsString());
        ee.setLevel(convertLevelName(log.get("levelName").getAsString() ));
        ee.setThreadName(log.get("threadName").getAsString());

        Double crated = (log.get("created").getAsDouble() + log.get("msecs").getAsDouble()) * 1000;
        ee.setTimeStamp(crated.longValue()); // todo : this time is not correct
        ee.setTimeStampDate(new Date(ee.getTimeStamp()));

        ErLocationInfo locationInfo = new ErLocationInfo();
        locationInfo.setClassName(log.get("module").getAsString());
        locationInfo.setFileName(log.get("fileName").getAsString());
        locationInfo.setLineNumber(log.get("lineNo").getAsString());
        locationInfo.setMethodName(log.get("funcName").getAsString());
        ee.setLocationInfo(locationInfo);

        // Exception information
        if (log.has("exc_info")){
            JsonObject exc = log.get("exc_info").getAsJsonObject();

            JsonArray trace = exc.get("trace").getAsJsonArray(); // StackTraceList
            ErThrowableInformation twi = new ErThrowableInformation();
            ee.setThrowableInfo(twi);

            ErCategory category = new ErCategory();
            category.setLevel(ErLevel.fromLevel(Level.toLevel(ee.getLevel())));
            category.setName(exc.get("type").getAsString());
            twi.setCategory(category);

            ErThrowable tw = new ErThrowable();
            tw.setDetailMessage(exc.get("value").getAsString());
            tw.setStackTraceElements(new ErStackTraceElement[trace.size()]);
            twi.setThrowable(tw);
            twi.setRep(new String[trace.size()]);

            for (int i = 0 ; i < trace.size() ; i++){
                JsonObject repElement = trace.get(i).getAsJsonObject();
                twi.getRep()[i] = String.format("%s (line: %d)",
                        repElement.get("name").getAsString(),
                        repElement.get("lineno").getAsInt());

                String pyFileName = getFilename(repElement.get("filename").getAsString());

                ErStackTraceElement sElement = new ErStackTraceElement();
                sElement.setDeclaringClass(getModuleName(pyFileName));
                sElement.setMethodName(repElement.get("name").getAsString());
                sElement.setLineNumber(repElement.get("lineno").getAsInt());
                sElement.setFileName(pyFileName);
                tw.getStackTraceElements()[i] = sElement;
            }
        }

        return ee;
    }

    /***
     * Get file name from full path
     * @param filePath
     * @return
     */
    protected String getFilename(String filePath){
        String simbol;
        if (filePath.contains("/")) // Unix
            simbol = "/";
        else {
            simbol = "\\\\"; // Windows
        }
        String[] paths = filePath.split(simbol);
        return paths[paths.length-1];
    }

    /**
     * Get module name from filename(*.py)
     * @param fileName
     * @return
     */
    protected String getModuleName(String fileName){
        return fileName.replace(".py", "");
    }

    /**
     * From python logger level name to Log4j logger level name string
     * @param levelName
     * @return
     */
    protected String convertLevelName(String levelName){
        switch (levelName){
            case "CRITICAL":
                return "FATAL";
            case "WARNING":
                return "WARN";
            case "INFO":
                return "INFO";
            case "DEBUG":
                return "DEBUG";
            default:
                return "TRACE";  // 'NOTSET' and another
        }
    }

}
