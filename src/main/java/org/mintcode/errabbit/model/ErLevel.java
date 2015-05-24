package org.mintcode.errabbit.model;


import org.apache.log4j.Level;

import java.io.Serializable;

/**
 * Created by soleaf on 2/21/15.
 */
public class ErLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    private int level;
    private String levelStr;

    public ErLevel(){}
    public static ErLevel fromLevel(Level level){

        if (level == null){
            return null;
        }

        ErLevel erLevel = new ErLevel();
        erLevel.setLevel(level.toInt());
        erLevel.setLevelStr(level.toString());
        return erLevel;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLevelStr() {
        return levelStr;
    }

    public void setLevelStr(String levelStr) {
        this.levelStr = levelStr;
    }

    @Override
    public String toString() {
        return levelStr;
    }
}
