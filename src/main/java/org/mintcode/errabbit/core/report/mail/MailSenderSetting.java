package org.mintcode.errabbit.core.report.mail;

import java.io.Serializable;
import java.util.Properties;

/**
 * Created by soleaf on 15. 8. 9..
 */
public interface MailSenderSetting extends Serializable {

    public String getHost();
    public void setHost(String host);
    public String getPort();
    public void setPort(String port);
    public String getUsername();
    public void setUsername(String username);
    public String getPassword();
    public void setPassword(String password);
    public Properties getJavaMailProperties();
    public void setJavaMailProperties(Properties javaMailProperties);
}