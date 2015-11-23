package org.mintcode.errabbit.core.console;

import org.mintcode.errabbit.model.ErStackTraceElement;

import java.util.ArrayList;
import java.util.List;

/**
 * StackTraceGraph
 * Make graph stack trace view model set
 * Organizing stack traces to group as same class
 * Created by soleaf on 2/21/15.
 */
public class StackTraceGraph {

    private List<ErStackTraceElement> stackTraceElements = new ArrayList<ErStackTraceElement>();
    private String basePackage;
    private String className;
    private String packageName;
    private String fileName;
    private String declaringClass;
    private boolean isBasePackage = false;

    /**
     * Make with basePackage
     * @param basePackage
     */
    public StackTraceGraph(String basePackage) {
        this.basePackage = basePackage;
    }

    /**
     * Add stack trace element
     * @param element
     */
    public void addElement(ErStackTraceElement element){

        if (className == null){

            declaringClass = element.getDeclaringClass();
            if (declaringClass.contains(".")){
                className = declaringClass.substring(declaringClass.lastIndexOf(".")+1);
                packageName = declaringClass.substring(0,declaringClass.lastIndexOf("."));
            }
            else{
                className = declaringClass;
                packageName = "";
            }

            fileName = element.getFileName();

            if (checkBasePackages(declaringClass)){
                isBasePackage = true;
            }
        }

        stackTraceElements.add(element);
    }

    /**
     * Is it base packages
     * @param className
     * @return
     */
    private boolean checkBasePackages(String className){

        if (basePackage == null){
            return false;
        }
        else {
            return className.startsWith(basePackage);
        }
        // This block is function support major framework identity.
        // Not support UI now.
        //        if(className.startsWith("sun")){
        //            return true;
        //        }
        //        else if (className.startsWith("java"))
        //        {
        //            return true;
        //        }
        //        else{
        //            return false;
        //        }
    }

    /**
     * Get stackTraceElements
     * @return
     */
    public List<ErStackTraceElement> getStackTraceElements() {
        return stackTraceElements;
    }

    /**
     * Get className
     * @return
     */
    public String getClassName() {
        return className;
    }

    /**
     * Get packageName
     * @return
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Get fileName
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Is it basePackage?
     * @return
     */
    public boolean isBasePackage() {
        return isBasePackage;
    }

    /**
     * Set whether It's basePackage
     * @param isDefaultHidden
     */
    public void setBasePackage(boolean isDefaultHidden) {
        this.isBasePackage = isDefaultHidden;
    }

    /**
     * Get declaringClass
     * @return
     */
    public String getDeclaringClass() {
        return declaringClass;
    }

    @Override
    public String toString() {
        return "StackTraceGraph{" +
                "stackTraceElements=" + stackTraceElements +
                ", basePackage='" + basePackage + '\'' +
                ", className='" + className + '\'' +
                ", packageName='" + packageName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", isBasePackage=" + isBasePackage +
                '}';
    }
}
