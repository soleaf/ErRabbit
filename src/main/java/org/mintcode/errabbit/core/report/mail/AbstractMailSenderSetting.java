package org.mintcode.errabbit.core.report.mail;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Properties;

/**
 * AbstractMailSenderSetting
 * General settings
 * Created by soleaf on 8/23/15.
 */
public class AbstractMailSenderSetting implements MailSenderSetting{


    @Id
    private ObjectId id;

    private String host;
    private String port;
    private String username;
    private String password;

    private Properties javaMailProperties = new Properties();

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Properties getJavaMailProperties() {
        return javaMailProperties;
    }

    public void setJavaMailProperties(Properties javaMailProperties) {
        this.javaMailProperties = javaMailProperties;
    }
}
