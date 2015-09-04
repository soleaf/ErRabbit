package org.mintcode.errabbit.core.rabbit.managing;

import org.mintcode.errabbit.model.RabbitGroup;

/**
 * Created by soleaf on 15. 9. 4..
 */
public class TryToUsedRabbitGroupException extends Exception{

    private RabbitGroup rabbitGroup;

    public TryToUsedRabbitGroupException(RabbitGroup rabbitGroup) {
        super(rabbitGroup + " is used. Cant' remove it now.");
        this.rabbitGroup = rabbitGroup;
    }
}
