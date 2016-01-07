package org.mintcode.errabbit.core.eventstream.event;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mintcode.errabbit.core.eventstream.event.action.EventAction;
import org.mintcode.errabbit.model.Log;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * EventChecker
 * Check log is acceptable to mapped condition,
 * and run, remember status
 * Created by soleaf on 10/12/15.
 */
public class EventChecker {

    private Set<Date> eventTimes = new TreeSet<>();

    private EventMapping mapping;

    private Date sleepTime;

    private Logger logger = LogManager.getLogger();

    // Count for matched
    private Long metricMatched = 0l;
    // Count for matched but ignored by sleeping status
    private Long metricIgnoreFromSleep = 0l;

    public EventChecker(){

    }

    public EventChecker(EventMapping mapping){
        this.mapping = mapping;
    }

    public boolean check(Log log){
        // Check log is acceptable with event condition
        EventCondition ec = mapping.getCondition();
        if (!ec.getRabbitId().equals(log.getRabbitId())){
            return false;
        }
        if (null != ec.getMatchLevel()
                &&!levelCheck(ec.getMatchLevel(), log.getLoggingEvent().getLevel())){
            return false;
        }
        if (null != ec.getMatchClass()
                && !ec.getMatchClass().equals(log.getLoggingEvent().getCategoryName())){
            return false;
        }
        if (null != ec.getIncludeMessage()
                && !log.getLoggingEvent().getRenderedMessage().contains(ec.getIncludeMessage())){
            return false;
        }
        if (null != ec.getHasException()
                && null ==log.getLoggingEvent().getThrowableInfo()){
            return false;
        }
        metricMatched++;
        return addPoint(new Date(), log);
    }

    /**
     * Level check
     * If l1 is warn, l2 should be equal or upper then warn
     * @param l1 l1 is standard level
     * @param l2 l2 is comparing level
     * @return
     */
    protected Boolean levelCheck(String l1, String l2){
        Level level1 = Level.getLevel(l1);
        Level level2 = Level.getLevel(l2);
        return level1.isLessSpecificThan(level2);
    }

    /**
     * add occurred eventstream time
     * @param date
     */
    private boolean addPoint(Date date, Log log){
        clearExpiredPoints();
        eventTimes.add(date);
        return checkStatus(log);
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
            if (minutes > mapping.getCondition().getRangeMinutes()){
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
    protected boolean checkStatus(Log log) {
        // check is now cool down
        if (sleepTime != null){
            Date now = new Date();
            long diff = now.getTime() - sleepTime.getTime();
            if (diff <= 0){
                // sleep time
                metricIgnoreFromSleep++;
                return false;
            }
        }

        // check eventstream counts
        if (eventTimes.size() >= mapping.getCondition().getThresholdCount()) {
            callAction(log);
            // check cool down
            if (mapping.getCondition().getSleepMinutesAfterNotice() > 0){
                sleepTime = calcSleepTime(mapping.getCondition().getSleepMinutesAfterNotice());
                logger.trace("set sleep time : " + sleepTime);
            }
            return true;
        }
        else{
            logger.trace("not reached to threshold " + eventTimes.size() +
                    "/" + mapping.getCondition().getThresholdCount());
            return false;
        }

    }

    /**
     * Calculate sleep time point
     * @return
     */
    protected Date calcSleepTime(Integer SleepMinutesAfterNotice){
        if (mapping.getCondition().getSleepMinutesAfterNotice() != null){
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
    protected void callAction(Log log){
        logger.trace("call action");
        for (EventAction action : mapping.getActions()){
            action.run(mapping.getCondition(), log);
        }
    }

    public void setMetricIgnoreFromSleep(Long metricIgnoreFromSleep) {
        this.metricIgnoreFromSleep = metricIgnoreFromSleep;
    }

    public void setMetricMatched(Long metricMatched) {
        this.metricMatched = metricMatched;
    }

    public void setMapping(EventMapping mapping) {
        this.mapping = mapping;
    }

    public void setSleepTime(Date sleepTime) {
        this.sleepTime = sleepTime;
    }

    public Set<Date> getEventTimes() {
        return eventTimes;
    }

    public EventMapping getMapping() {
        return mapping;
    }

    public Date getSleepTime() {
        return sleepTime;
    }

    public Long getMetricMatched() {
        return metricMatched;
    }

    public Long getMetricIgnoreFromSleep() {
        return metricIgnoreFromSleep;
    }
}
