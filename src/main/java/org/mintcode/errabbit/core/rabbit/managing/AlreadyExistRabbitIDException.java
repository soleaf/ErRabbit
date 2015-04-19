package org.mintcode.errabbit.core.rabbit.managing;

/**
 * Created by soleaf on 2/19/15.
 */
public class AlreadyExistRabbitIDException extends Exception {

    public AlreadyExistRabbitIDException(String rabbitID){
        super(String.format("A Rabbit Id '%s' is already exist.", rabbitID));
    }
}
