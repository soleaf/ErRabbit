package org.mintcode.errabbit.core.eventstream.stream;

/**
 * EventStreamPriority
 * This value is used to order along event streams
 * Created by soleaf on 10/18/15.
 */
public enum EventStreamPriority {

    high(0),
    midium(1),
    low(2);

    private int value;

    EventStreamPriority(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
