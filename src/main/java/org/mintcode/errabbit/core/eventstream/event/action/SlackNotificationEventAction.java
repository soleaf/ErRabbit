package org.mintcode.errabbit.core.eventstream.event.action;

import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mintcode.errabbit.core.eventstream.event.EventCondition;
import org.mintcode.errabbit.model.ErrLoggingEvent;
import org.mintcode.errabbit.model.Log;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by soleaf on 1/8/16.
 */
@Document(collection = "event.action")
public class SlackNotificationEventAction extends AbstractEventAction {

    @Transient
    private Logger logger = LogManager.getLogger(getClass());

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
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        sb.append(ev.getLevel());
        sb.append(" ] ");
        sb.append(" ");
        sb.append(log.getRabbitId());
        sb.append("\n");;
        sb.append(ev.getRenderedMessage());
        if (host != null){
            sb.append("\n");
            sb.append(":mag: ");
            sb.append(host);
            sb.append("/log/list.err?id=");
            sb.append(log.getRabbitId());
        }
        return sb.toString();
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
}
