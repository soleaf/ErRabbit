package org.mintcode.errabbit.controller.console;

import org.mintcode.errabbit.model.ErStackTraceElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soleaf on 2/21/15.
 */
public class StackTraceGraph {

    List<ErStackTraceElement> stackTraceElements = new ArrayList<ErStackTraceElement>();
    String className;
    String fileName;
    boolean isDefaultHidden = false;

    public void addElement(ErStackTraceElement element){

        if (className == null){
            className = element.getDeclaringClass();
            fileName = element.getFileName();
            if (checkBasePackages(className)){
                isDefaultHidden = true;
            }

        }

        stackTraceElements.add(element);
    }

    private boolean checkBasePackages(String className){
        if(className.startsWith("sun")){
            return true;
        }
        else if (className.startsWith("java"))
        {
            return true;
        }
        else{
            return false;
        }
    }

    public List<ErStackTraceElement> getStackTraceElements() {
        return stackTraceElements;
    }

    public String getClassName() {
        return className;
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
                ", className='" + className + '\'' +
                ", fileName='" + fileName + '\'' +
                ", isDefaultHidden=" + isDefaultHidden +
                '}';
    }
}
