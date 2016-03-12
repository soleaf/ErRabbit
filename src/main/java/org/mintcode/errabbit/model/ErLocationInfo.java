package org.mintcode.errabbit.model;

import java.io.Serializable;

/**
 * ErLocationInfo
 * It is equivalent to org.apache.log4j.spi.LocationInfo
 * (convert to save repository)
 * Created by soleaf on 2/21/15.
 */
public class ErLocationInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Caller's line number.
     */
    String lineNumber;
    /**
     * Caller's file name.
     */
    String fileName;
    /**
     * Caller's fully qualified class name.
     */
    String className;
    /**
     * Caller's method name.
     */
    String methodName;

    public ErLocationInfo() {

    }

    /**
     * Make from org.apache.log4j.spi.LocationInfo
     * @param locationInfo
     * @return
     */
    public static ErLocationInfo fromLocationInfo(org.apache.log4j.spi.LocationInfo locationInfo) {

        ErLocationInfo erLocationInfo = new ErLocationInfo();

        erLocationInfo.setClassName(locationInfo.getClassName());
        erLocationInfo.setFileName(locationInfo.getFileName());
        erLocationInfo.setLineNumber(locationInfo.getLineNumber());
        erLocationInfo.setMethodName(locationInfo.getMethodName());

        return erLocationInfo;
    }

    /**
     * Get lineNumber
     * @return
     */
    public String getLineNumber() {
        return lineNumber;
    }
    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * Get fileName
     * @return
     */
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Get className
     * @return
     */
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Get methodName
     * @return
     */
    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "ErLocationInfo{" +
                "lineNumber='" + lineNumber + '\'' +
                ", fileName='" + fileName + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                '}';
    }
}
