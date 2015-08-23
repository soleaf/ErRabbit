package org.mintcode.errabbit.core.report;

import org.bson.types.ObjectId;
import org.mintcode.errabbit.core.report.mail.MailSenderSetting;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by soleaf on 15. 8. 9..
 */
@Document(collection = "settings.report")
public class ReportDescription {

    @Id
    private ObjectId id;

    private Set<String> targets;
    private ReportDescriptionTime time;
    private Set<String> subscribers;
    private Boolean active = false;
    private MailSenderSetting mailSenderSetting;

    public Set<String> getTargets() {
        return targets;
    }

    public void setTargets(Set<String> targets) {
        this.targets = targets;
    }

    public void addTarget(String target) {
        if (targets == null) {
            targets = new HashSet<>();
        }
        targets.add(target);
    }

    public ReportDescriptionTime getTime() {
        return time;
    }

    public void setTime(ReportDescriptionTime time) {
        this.time = time;
    }

    public Set<String> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<String> subscribers) {
        this.subscribers = subscribers;
    }

    public void addSubscriber(String subscriber) {
        if (subscribers == null) {
            subscribers = new HashSet<>();
        }
        subscribers.add(subscriber);
    }

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
