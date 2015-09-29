package org.mintcode.errabbit.core.analysis.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Analysis result type Graphics Item
 * Tree structure
 * Created by soleaf on 7/5/15.
 */
public class GraphicLogAnalysisResultItem implements Serializable {

    @Transient
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    String field;
    Integer count = 0;
    Double percent = 0.0;

    @Transient
    GraphicLogAnalysisResultItem superItem;
    List<GraphicLogAnalysisResultItem> subItems;

    public GraphicLogAnalysisResultItem(){

    }

    /**
     * Set target field
     * @param field
     */
    public GraphicLogAnalysisResultItem(String field) {
        this.field = field;
    }

    /**
     * Get target field
     * @param field
     */
    public void setField(String field) {
        this.field = field;
    }


    /**
     * Get target field
     * @return
     */
    public String getField() {
        return field;
    }

    /**
     * Set count
     * @param count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * Set percent
     * @param percent
     */
    public void setPercent(Double percent) {
        this.percent = percent;
    }

    /**
     * Get percent of siblings
     * @return
     */
    public double getPercent() {
        return percent;
    }

    /**
     * Set percent of siblings
     * @param percent
     */
    public void setPercent(double percent) {
        this.percent = percent;
    }

    /**
     * Set subItems
     * like tree type
     * @param subItems
     */
    public void setSubItems(List<GraphicLogAnalysisResultItem> subItems) {
        this.subItems = subItems;
    }

    /**
     * Get subItems
     * @return
     */
    public List<GraphicLogAnalysisResultItem> getSubItems() {
        return subItems;
    }

    /**
     * Get Parents
     * @return
     */
    public GraphicLogAnalysisResultItem getSuperItem() {
        return superItem;
    }

    /**
     * Add subItem
     * @param subItem
     */
    public void addSub(GraphicLogAnalysisResultItem subItem) {
        if (subItems == null){
            subItems = new ArrayList<>();
        }
        subItems.add(subItem);
        subItem.setSuperItem(this);
    }

    /**
     * Set parents
     * @param superItem
     */
    public void setSuperItem(GraphicLogAnalysisResultItem superItem) {
        this.superItem = superItem;
    }

    /**
     * Get counts
     * @return
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Add count this and super items
     *
     * @param count
     */
    public void addCount(Integer count) {
        this.count += count;
        if (superItem != null) {
            superItem.addCount(count);
        }
    }

    /**
     * Calculate percents of sub items and sub items
     */
    public void calcPercents() {
        if (count == 0){
            logger.info("can't divide by zero");
            return;
        }
        if (subItems != null && !subItems.isEmpty()) {
            for (GraphicLogAnalysisResultItem item : subItems) {
                item.setPercent((item.getCount() * 100.0f) / count);
                item.calcPercents();
            }
        }
    }

    /**
     * Print debug informations
     * @param prefix
     * @return
     */
    public String debug(String prefix) {
        String thisDesc = "\n" + prefix + "- " + field + " (" + count + ", %" + percent + ")";
        if (subItems != null && !subItems.isEmpty()) {
            for (GraphicLogAnalysisResultItem item : subItems) {
                thisDesc += item.debug(prefix + "  ");
            }
        }
        return thisDesc;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (subItems != null && !subItems.isEmpty()) {
            for (GraphicLogAnalysisResultItem item : subItems) {
                sb.append(item.debug("  "));
            }
        }
        return "GraphicLogAnalysisResultItem : "
                + field + " (" + count + ", %" + percent + ")"
                + " >"
                + sb.toString();
    }
}
