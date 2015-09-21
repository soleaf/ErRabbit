package org.mintcode.errabbit.core.rabbit.managing;

import org.mintcode.errabbit.model.RabbitGroup;

/**
 * the Deleting rabbit group is used on some rabbits
 * Created by soleaf on 15. 9. 4..
 */
public class TryToUsedRabbitGroupException extends Exception{

    public TryToUsedRabbitGroupException(RabbitGroup rabbitGroup) {
        super(rabbitGroup.getName() + " is used. Cant' remove it now.");
    }

}
