package org.mintcode.errabbit.model;

import org.apache.log4j.Category;

import java.io.Serializable;

/**
 * Log Category
 * It is equivalent to org.apache.log4j.Category
 * (convert to save repository)
 * Created by soleaf on 2/21/15.
 */
public class ErCategory implements Serializable{

    private static final long serialVersionUID = 1L;

    // Category name
    private String name;

    // Category level
    private ErLevel level;

    public ErCategory(){

    }

    /**
     * Make from log4j Category class
     * @param category
     * @return
     */
    public static ErCategory fromCategory(Category category){

        if (category == null){
            return null;
        }

        ErCategory erCategory = new ErCategory();
        erCategory.setName(category.getName());
        erCategory.setLevel(ErLevel.fromLevel(category.getLevel()));

        return erCategory;
    }

    /**
     * Get category name
     * @return
     */
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get category level
     * @return
     */
    public ErLevel getLevel() {
        return level;
    }
    public void setLevel(ErLevel level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "ErCategory{" +
                "name='" + name + '\'' +
//                ", parent=" + parent +
                ", level=" + level +
                '}';
    }
}
