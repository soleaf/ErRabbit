package org.mintcode.errabbit.model;

import com.google.gson.Gson;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soleaf on 15. 9. 22..
 */
public class Graph {

    Gson gson = new Gson();

    // Level :
    //  - Hour :
    //    - counts
    private Map<String,Map<Integer, Integer>> data = new HashMap<>();

    // All levels
    String[] levels = new  String[]{Level.TRACE.name(), Level.DEBUG.name(), Level.INFO.name(),
            Level.WARN.name(), Level.ERROR.name(), Level.FATAL.name()};


    HashMap<String, String > colorSet = new HashMap<String, String>(){{
        put(Level.TRACE.name(),"#A3A3A3");
        put(Level.DEBUG.name(),"#868686");
        put(Level.INFO.name(),"#828282");
        put(Level.ERROR.name(),"#FF8166");
        put(Level.WARN.name(),"#ffbf5e");
        put(Level.FATAL.name(),"#ff5572");
    }};

    /**
     * Init with zeros
     */
    public Graph(){

    }

    /**
     * Get timeline by level
     * @param level
     * @return
     */
    public Map<Integer, Integer> getTimeLine(String level){
        if (!data.containsKey(level)){
            Map<Integer, Integer> timeLine = new HashMap<Integer, Integer>();
            for (int h=0 ; h <24; h++){
                timeLine.put(h, 0); // set zero counts
            }
            data.put(level, timeLine);
        }
        return data.get(level);
    }

    /**
     * Add count
     * @param level
     * @param hour
     * @param value
     */
    public void add(String level, Integer hour, Integer value){
        if (value == null  || value == 0)
            return;

        getTimeLine(level).put(hour, getTimeLine(level).get(hour) + value);
    }

    /**
     * Add from hourStatistics
     * @param statistics
     */
    public void add(LogLevelHourStatistics statistics){
        Integer hour = statistics.getHour();
        add(Level.TRACE.name(), hour, statistics.getLevel_TRACE());
        add(Level.DEBUG.name(), hour, statistics.getLevel_DEBUG());
        add(Level.INFO.name(), hour, statistics.getLevel_INFO());
        add(Level.WARN.name(), hour, statistics.getLevel_WARN());
        add(Level.ERROR.name(), hour, statistics.getLevel_ERROR());
        add(Level.FATAL.name(), hour, statistics.getLevel_FATAL());
    }

    /**
     * Add from List<LogLevelHourStatistics>
     * @param statisticses
     */
    public void add(List<LogLevelHourStatistics> statisticses){
        for (LogLevelHourStatistics statistics : statisticses){
            add(statistics);
        }
    }

    /**
     * Get all levels graphs
     * @return
     */
    public String getGraph(){

        Map<String,Object> dataSet = new HashMap<>();
        dataSet.put("color", getColorSet());

        List<List<Object>> dataList = new ArrayList<>();
        dataSet.put("data", dataList);

        // header
        List<Object> columns = new ArrayList<>();
        dataList.add(columns);
        columns.add("Time");
        for (String level : levels){
            if (data.containsKey(level)){
                columns.add(level);
            }
        }

        // counts
        for (Integer h = 0 ; h < 24; h ++){
            List<Object> conts = new ArrayList<>();
            conts.add(h);
            for (String level : levels){
                if (data.containsKey(level)){
                    conts.add(getTimeLine(level).get(h));
                }
            }
            dataList.add(conts);
        }

        return gson.toJson(dataSet);
    }

    /**
     * Get graph color set
     * @return
     */
    public List<String>getColorSet(){
        List<String> selectedColors = new ArrayList<>();
        for (String level : levels) {
            if (data.containsKey(level)) {
                selectedColors.add(colorSet.get(level));
            }
        }
        return selectedColors;
    }

}