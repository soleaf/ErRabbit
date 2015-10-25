package org.mintcode.errabbit.core.eventstream.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mintcode.errabbit.model.Log;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by soleaf on 10/12/15.
 */
public class EventChecker {

    private Set<Date> eventTimes = new TreeSet<>();

    private EventSetting setting;

    private Date sleepTime;

    private Logger logger = LogManager.getLogger();

    public EventChecker(EventSetting setting){
        this.setting = setting;
    }

    public void check(Log log){
        
    }

    /**
     * add occurred eventstream time
     * @param date
     */
    private void addPoint(Date date){
        clearExpiredPoints();
        eventTimes.add(date);
        checkStatus();
    }

    /**
     * Clear expired time points
     */
    protected void clearExpiredPoints(){
        Date now = new Date();
        Set<Date> expiredSet = new HashSet<>();
        for(Date date : eventTimes){
            long diff = now.getTime() - date.getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            if (minutes > setting.getRangeMinutes()){
                // expired
                expiredSet.add(date);
            }
            else{
                break;
            }
        }
        eventTimes.removeAll(expiredSet);
    }

    /**
     * Check current status
     */
    protected void checkStatus() {
        // check is now cool down
        if (sleepTime != null){
            Date now = new Date();
            long diff = now.getTime() - sleepTime.getTime();
            if (diff <= 0){
                // sleep time
                return;
            }
        }

        // check eventstream counts
        if (eventTimes.size() >= setting.getThresholdCount()) {
            fireEvent();
            // check cool down
            sleepTime = calcSleepTime(setting.getSleepMinutesAfterNotice());
        }

    }

    /**
     * Calculate sleep time point
     * @return
     */
    protected Date calcSleepTime(Integer SleepMinutesAfterNotice){
        if (setting.getSleepMinutesAfterNotice() != null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MINUTE, SleepMinutesAfterNotice);
            return cal.getTime();
        }
        else{
            return null;
        }
    }

    /**
     * Fire eventstream
     */
    protected void fireEvent(){
        // Event
    }

}
