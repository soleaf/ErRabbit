package org.mintcode.errabbit.core.rabbit.managing;

/**
 * AlreadyExistRabbitIDException
 * used on rabbit insert form
 * Created by soleaf on 2/19/15.
 */
public class AlreadyExistRabbitIDException extends Exception {

    public AlreadyExistRabbitIDException(String rabbitID){
        super(String.format("Rabbit Id '%s' is already exist.", rabbitID));
    }
}
