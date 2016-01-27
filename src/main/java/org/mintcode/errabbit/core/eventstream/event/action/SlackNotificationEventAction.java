package org.mintcode.errabbit.core.eventstream.event.action;

import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
import org.mintcode.errabbit.core.eventstream.event.EventCondition;
import org.mintcode.errabbit.model.ErrLoggingEvent;
import org.mintcode.errabbit.model.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by soleaf on 1/8/16.
 */
@Document(collection = "event.action")
public class SlackNotificationEventAction extends AbstractEventAction {

    @Transient
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // ErRabbit's host
    private String host;

    // Webhook URL
    private String webhookURL = null;

    // Channel
    private String channel = null;

    @Transient
    private static final String
            icon = "https://raw.githubusercontent.com/soleaf/ErRabbit/master/graphics/icon_face_rect.png";

    public String getTypeName() {
        return getClass().getSimpleName();
    }

    @Override
    public List<EventActionUIElement> getUIElements() {
        List<EventActionUIElement> uiEelements = new ArrayList<>();
        uiEelements.add(new EventActionUIElement("p_host", "Host", "text", "http://localhost:8080", "Reachable ErRabbit host", false, host));
        uiEelements.add(new EventActionUIElement("p_webhookURL", "WebhookURL", "text", "https://hooks.slack.com/services/xxx/yyy/zzz", "App Integration > Enable Incoming WebHooks > get Webhook URL!", true, webhookURL));
        uiEelements.add(new EventActionUIElement("p_channel", "Channel", "text", "#general", "Post to Channel", true, channel));
        return uiEelements;
    }

    @Override
    public void validationUISetting(Map<String, String> dataSet) throws InvalidEventActionUISettingValueException {
        super.validationUISetting(dataSet);
        if (!dataSet.containsKey("p_host") || !StringUtils.hasLength(dataSet.get("p_host"))
                || !dataSet.get("p_host").startsWith("http")){
            throw new InvalidEventActionUISettingValueException("invalid value for host");
        }
        if (!dataSet.containsKey("p_webhookURL") || !StringUtils.hasLength(dataSet.get("p_webhookURL"))){
            throw new InvalidEventActionUISettingValueException("invalid value for webhookURL");
        }
        if (!dataSet.containsKey("p_channel") || !StringUtils.hasLength(dataSet.get("p_channel"))
                || !dataSet.get("p_channel").startsWith("#")){
            throw new InvalidEventActionUISettingValueException("invalid value for channel");
        }
    }

    @Override
    public void settingFromUI(Map<String, String> dataSet) throws InvalidEventActionUISettingValueException {
        validationUISetting(dataSet);
        super.settingFromUI(dataSet);

        host = dataSet.get("p_host");
        webhookURL = dataSet.get("p_webhookURL");
        channel = dataSet.get("p_channel");
    }

    @Override
    public Boolean run(EventCondition eventCondition, Log log) {
        logger.debug("Run slack notification action");
        if (webhookURL == null){
            errorMessage = "Slack webhook should not be empty.";
            return false;
        }
        if (channel == null){
            errorMessage = "Slack channel should not be empty.";
            return false;
        }

        SlackApi api = new SlackApi(webhookURL);
        SlackMessage message = new SlackMessage("ErRabbit",generateMessage(log));
        message.setChannel(channel);
        message.setUnfurlLinks(true);
        message.setIcon(icon);
        api.call(message);
        return true;
    }

    @Override
    public EventAction copy() {
        SlackNotificationEventAction action = new SlackNotificationEventAction();
        action.setHost(host);
        action.setWebhookURL(webhookURL);
        action.setChannel(channel);
        return action;
    }

    @Override
    public String getDescription() {
        return "This is action sending log message to slack";
    }

    /**
     * Generate message from log
     * ----- sample
     * [ ERROR ] Application ID
     *  It's error message here.
     *  :mag: http://localhost:8080/
     * -----
     * @param log
     * @return
     */
    protected String generateMessage(Log log){
        ErrLoggingEvent ev = log.getLoggingEvent();
        if (host != null) {
            return String.format("[ %s ] %s\n%s\n:mag: %s/log/list.err?id=%s",
                    ev.getLevel(), log.getRabbitId(), ev.getRenderedMessage(), host, log.getRabbitId());
        }
        else{
            return String.format("[ %s ] %s\n%s",
                    ev.getLevel(), log.getRabbitId(), ev.getRenderedMessage());
        }
    }

    protected String getLevelIcon(String level){
        if (level.equals("TRACE")){
            return ":white_circle:";
        }
        else if (level.equals("DEBUG")){
            return ":white_circle:";
        }
        else if (level.equals("INFO")){
            return ":large_blue_circle:";
        }
        else if (level.equals("WARN")){
            return ":white_circle:";
        }
        else if (level.equals("ERROR")){
            return ":red_circle:";
        }
        else if (level.equals("FATAL")){
            return ":red_circle:";
        }
        else{
            return "";
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getWebhookURL() {
        return webhookURL;
    }

    public void setWebhookURL(String webhookURL) {
        this.webhookURL = webhookURL;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "SlackNotificationEventAction{" +
                "logger=" + logger +
                ", host='" + host + '\'' +
                ", webhookURL='" + webhookURL + '\'' +
                ", channel='" + channel + '\'' +
                "} " + super.toString();
    }
}
