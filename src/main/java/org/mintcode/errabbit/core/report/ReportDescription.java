package org.mintcode.errabbit.core.report;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.core.report.mail.MailSenderSetting;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * Report description
 * To generate report
 * Created by soleaf on 15. 8. 9..
 */
@Document(collection = "settings.report")
public class ReportDescription {

    @Id
    private ObjectId id;

    // Targer RabbitId
    private Set<String> targets;

    // Target batch time
    private ReportDescriptionTime time;

    // Report subscription user mail to alert
    private Set<String> subscribers;

    // Active or inactive
    private Boolean active = false;

    // MailSender to alert
    private MailSenderSetting mailSenderSetting;

    // Target RabbitId
    public Set<String> getTargets() {
        return targets;
    }

    public void setTargets(Set<String> targets) {
        this.targets = targets;
    }

    /**
     * Add target rabbitId
     * @param target
     */
    public void addTarget(String target) {
        if (targets == null) {
            targets = new HashSet<>();
        }
        targets.add(target);
    }

    /**
     * target batch time
     * @return
     */
    public ReportDescriptionTime getTime() {
        return time;
    }

    public void setTime(ReportDescriptionTime time) {
        this.time = time;
    }

    /**
     * Subscription user mails
     * @return
     */
    public Set<String> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<String> subscribers) {
        this.subscribers = subscribers;
    }

    /**
     * Add subscription mail
     * @param subscriber
     */
    public void addSubscriber(String subscriber) {
        if (subscribers == null) {
            subscribers = new HashSet<>();
        }
        subscribers.add(subscriber);
    }

    /**
     * Active status
     * @return
     */
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ObjectId getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ReportDescription{" +
                "id=" + id +
                ", targets=" + targets +
                ", time=" + time +
                ", subscribers=" + subscribers +
                ", active=" + active +
                ", mailSenderSetting=" + mailSenderSetting +
                '}';
    }
}
