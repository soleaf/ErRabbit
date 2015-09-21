package org.mintcode.errabbit.model;

import org.apache.logging.log4j.core.impl.ExtendedStackTraceElement;

import java.io.Serializable;

/**
 * ErStackTraceElement
 * It is equivalent to java.lang.StackTraceElement
 * (convert to save repository)
 * Created by soleaf on 2/21/15.
 */
public class ErStackTraceElement implements Serializable{

    private static final long serialVersionUID = 1L;

    private String declaringClass;
    private String methodName;
    private String fileName;
    private int    lineNumber;

    public ErStackTraceElement(){}

    /**
     * Make from java.lang.StackTraceElement (Log4j 1.x)
     * @param element
     * @return
     */
    public static ErStackTraceElement fromStackTraceElement(StackTraceElement element){
        ErStackTraceElement erl = new ErStackTraceElement();
        erl.setDeclaringClass(element.getClassName());
        erl.setFileName(element.getFileName());
        erl.setMethodName(element.getMethodName());
        erl.setLineNumber(element.getLineNumber());
        return erl;
    }


    /**
     * Make from org.apache.logging.log4j.core.impl.ExtendedStackTraceElement (Log4j 2.x)
     * @param element
     * @return
     */
    public static ErStackTraceElement fromExtendedStackTraceElement(ExtendedStackTraceElement element){
        ErStackTraceElement erl = new ErStackTraceElement();
        erl.setDeclaringClass(element.getClassName());
        erl.setFileName(element.getFileName());
        erl.setMethodName(element.getMethodName());
        erl.setLineNumber(element.getLineNumber());
        return erl;
    }

    public String getDeclaringClass() {
        return declaringClass;
    }

    public void setDeclaringClass(String declaringClass) {
        this.declaringClass = declaringClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return "ErStackTraceElement{" +
                "declaringClass='" + declaringClass + '\'' +
                ", methodName='" + methodName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", lineNumber=" + lineNumber +
                '}';
    }
}
