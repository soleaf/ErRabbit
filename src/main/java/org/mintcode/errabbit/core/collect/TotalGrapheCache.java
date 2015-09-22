package org.mintcode.errabbit.core.collect;

import org.apache.logging.log4j.Level;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by soleaf on 9/22/15.
 */
@Component
public class TotalGrapheCache {

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

    Map<Integer, Map<String,Integer>> queue = new LinkedHashMap<Integer, Map<String,Integer>>()
    {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Map<String,Integer>> eldest)
        {
            return this.size() > 10;
        }
    };

    @Scheduled(cron = "0 * * * * *")
    public void updateTime(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        Map<String,Integer> blank = new HashMap<>();
        for (String level : levels){
            blank.put(level, 0);
        }
        queue.put(cal.get(Calendar.HOUR_OF_DAY), blank);
    }

    public Map<String,Integer> getLevelCountsMapByTime(Integer time){
        if (!queue.containsKey(time)){
            updateTime();
        }
        return queue.get(time);
    }

    public void add(Date time, String level){
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        getLevelCountsMapByTime(cal.get(Calendar.HOUR_OF_DAY))
                .put(level, getLevelCountsMapByTime(cal.get(Calendar.HOUR_OF_DAY)).get(level) + 1);
    }

    /**
     * Get all levels graphs
     * @return
     */
    public String getGraph(){
        StringBuffer sb = new StringBuffer();

        sb.append("{\"color\":" + getColorSet());
        sb.append(",\"data\" :");

        sb.append("[");

        // header
        sb.append("[");
        sb.append("\"Time\"");
        for (String level : levels){
            if (queue.containsKey(level)){
                sb.append(",\"" + level + "\"");
            }
        }
        sb.append("]");

        // counts
        for (Integer h = 0 ; h < 24; h ++){

            sb.append(",");

            sb.append("[");
            sb.append(h);
            for (String level : levels){
                if (queue.containsKey(level)){
                    sb.append(",");
                    sb.append(getLevelCountsMapByTime(h).get(level));
                }
            }
            sb.append("]");
        }

        sb.append("]}");
        return sb.toString();
    }

    public String getColorSet(){
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        boolean isFirst = true;
        for (String level : levels) {
            if (queue.containsKey(level)) {
                if (isFirst){
                    isFirst = false;
                }
                else{
                    sb.append(",");
                }
                sb.append("\"" + colorSet.get(level) +"\"");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
