package org.mintcode.errabbit.core.console;

import org.mintcode.errabbit.model.ErStackTraceElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soleaf on 2/21/15.
 */
public class StackTraceGraph {

    private List<ErStackTraceElement> stackTraceElements = new ArrayList<ErStackTraceElement>();
    private String basePackage;
    private String className;
    private String packageName;
    private String fileName;
    private boolean isDefaultHidden = false;

    public StackTraceGraph(String basePackage) {
        this.basePackage = basePackage;
    }

    public void addElement(ErStackTraceElement element){

        if (className == null){

            String packageAndClassName = element.getDeclaringClass();
            className = packageAndClassName.substring(packageAndClassName.lastIndexOf(".")+1);
            packageName = packageAndClassName.substring(0,packageAndClassName.lastIndexOf("."));
            fileName = element.getFileName();

            if (checkBasePackages(packageAndClassName)){
                isDefaultHidden = true;
            }
        }

        stackTraceElements.add(element);
    }

    private boolean checkBasePackages(String className){

        if (basePackage == null){
            return false;
        }
        else{
            return className.startsWith(basePackage);
        }
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

    public List<ErStackTraceElement> getStackTraceElements() {
        return stackTraceElements;
    }

    public String getClassName() {
        return className;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isDefaultHidden() {
        return isDefaultHidden;
    }

    public void setDefaultHidden(boolean isDefaultHidden) {
        this.isDefaultHidden = isDefaultHidden;
    }

    @Override
    public String toString() {
        return "StackTraceGraph{" +
                "stackTraceElements=" + stackTraceElements +
                ", basePackage='" + basePackage + '\'' +
                ", className='" + className + '\'' +
                ", packageName='" + packageName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", isDefaultHidden=" + isDefaultHidden +
                '}';
    }
}
