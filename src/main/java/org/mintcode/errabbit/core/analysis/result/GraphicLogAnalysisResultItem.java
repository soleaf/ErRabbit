package org.mintcode.errabbit.core.analysis.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by soleaf on 7/5/15.
 */
public class GraphicLogAnalysisResultItem implements Serializable {

    @Transient
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    String field;
    Integer count = 0;
    double percent = 0.0;
    GraphicLogAnalysisResultItem superItem;
    List<GraphicLogAnalysisResultItem> subItems = new ArrayList<>();

    public GraphicLogAnalysisResultItem(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public List<GraphicLogAnalysisResultItem> getSubItems() {
        return subItems;
    }

    public GraphicLogAnalysisResultItem getSuperItem() {
        return superItem;
    }

    public void addSub(GraphicLogAnalysisResultItem subItem) {
        subItems.add(subItem);
        subItem.setSuperItem(this);
    }

    public void setSuperItem(GraphicLogAnalysisResultItem superItem) {
        this.superItem = superItem;
    }

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
        if (!subItems.isEmpty()) {
            for (GraphicLogAnalysisResultItem item : subItems) {
                item.setPercent((item.getCount() * 100.0f) / count);
                item.calcPercents();
            }
        }
    }

    public String debug(String prefix) {
        String thisDesc = "\n" + prefix + "- " + field + " (" + count + ", %" + percent + ")";
        if (!subItems.isEmpty()) {
            for (GraphicLogAnalysisResultItem item : subItems) {
                thisDesc += item.debug(prefix + "  ");
            }
        }
        return thisDesc;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (!subItems.isEmpty()) {
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
