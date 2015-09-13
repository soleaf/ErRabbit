package org.mintcode.errabbit.core.analysis.result;

import java.io.Serializable;

/**
 * Created by soleaf on 9/13/15.
 */
public class GraphicLogAnalysisErrorRankItem implements Serializable, Comparable{

    private String rabbitId;
    private String className;
    private String level;
    private Integer count;

    public String getRabbitId() {
        return rabbitId;
    }

    public void setRabbitId(String rabbitId) {
        this.rabbitId = rabbitId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassNameOnly(){
        return className.substring(className.lastIndexOf(".")+1, className.length());
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "GraphicLogAnalysisErrorRankItem{" +
                "rabbitId='" + rabbitId + '\'' +
                ", className='" + className + '\'' +
                ", level='" + level + '\'' +
                ", count=" + count +
                '}';
    }

    private int comparableLevel(String level){
        if (level.equals("FATAL")){
            return 5;
        }
        else if (level.equals("ERROR")){
            return 4;
        }
        else if (level.equals("WARN")){
            return 3;
        }
        else if (level.equals("INFO")){
            return 2;
        }
        else if (level.equals("DEBUG")){
            return 1;
        }
        else{
            return 0; // TRACE
        }
    }

    @Override
    public int compareTo(Object o) {
        GraphicLogAnalysisErrorRankItem o2 = (GraphicLogAnalysisErrorRankItem) o;
        if (comparableLevel(getLevel()) != comparableLevel(o2.getLevel())){
            return comparableLevel(getLevel()) - comparableLevel(o2.getLevel());
        }
        else if (getCount() != o2.getCount()){
            return getCount() - o2.getCount();
        }
        else if (!getRabbitId().equals(o2.getRabbitId())){
            return getRabbitId().compareTo(o2.getRabbitId());
        }
        else{
            return getClassName().compareTo(o2.getClassName());
        }
    }
}
