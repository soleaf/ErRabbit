package org.mintcode.errabbit.model;

/**
 * Logger Type
 * Support multi logger
 * Created by soleaf on 1/29/16.
 */
public enum LoggerType {

    Log4j(0),
    PythonLogger(1) //Custom python logging handler(logging.handlers.ErRabbitLoggingHandler)
    ;

    Integer value;
    LoggerType(Integer value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }

}
