package org.mintcode.errabbit.core.notification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mintcode.errabbit.core.eventstream.event.EventCondition;
import org.mintcode.errabbit.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Notification Center
 * Check nc settings and notice to user through socket or email
 * Created by soleaf on 10/12/15.
 */
@Service
public class NotificationCenter {

    private Logger logger = LogManager.getLogger();

    private Map<String, EventCondition> settingsByStage = new HashMap<>();

    // Under stage : not detected any matched status
    public static final String NC_STAGE_UNDER = "UD";

    // Rise stage : detected some matched status, It is not reach threshold yet
    public static final String NC_STAGE_RISE = "RS";

    // Up stage : reach threshold and notice
    public static final String NC_STAGE_UP = "UP";


    @Autowired
    private NotificationSettingRepository settingRepository;

    @PostConstruct
    public void getReady(){
        settingsByStage.clear();
        List<EventCondition> all = settingRepository.findByActive(true);
        if (all != null && !all.isEmpty()){
            for (EventCondition setting : all)
            settingsByStage.put(NC_STAGE_UNDER, setting);
        }
    }

    /**
     * Update settings from repository
     */
    public void updateSettings(){

    }

    /**
     * Change stage
     * @param setting
     * @param toStage
     */
    protected void changeStage(EventCondition setting, String toStage){

    }

    /**
     * Check log
     * @param log
     */
    public void check(Log log){

    }

}
