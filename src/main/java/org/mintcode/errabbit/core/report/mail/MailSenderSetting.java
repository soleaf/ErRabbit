package org.mintcode.errabbit.core.report.mail;

import java.io.Serializable;
import java.util.Properties;

/**
 * MailSenderSetting for MailSender
 * include mail account and server settings
 * Created by soleaf on 15. 8. 9..
 */
public interface MailSenderSetting extends Serializable {

    /**
     * MailServer Host
     * @return
     */
    public String getHost();
    public void setHost(String host);

    /**
     * MailServer Port
     * @return
     */
    public String getPort();
    public void setPort(String port);

    /**
     * MailServer account
     * @return
     */
    public String getUsername();
    public void setUsername(String username);

    /**
     * * MailServer account
     * @return
     */
    public String getPassword();
    public void setPassword(String password);

    /**
     * Other options
     * @return
     */
    public Properties getJavaMailProperties();
    public void setJavaMailProperties(Properties javaMailProperties);
}