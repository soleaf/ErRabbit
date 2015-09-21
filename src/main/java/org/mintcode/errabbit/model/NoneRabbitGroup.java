package org.mintcode.errabbit.model;

/**
 * NoneRabbitGroup
 * It look like Null object, used to identify none group on UI
 * Created by soleaf on 9/6/15.
 */
public class NoneRabbitGroup extends RabbitGroup{

    NoneRabbitGroup(){
        super("(no group)");
    }

    /**
     * Check this object is None group?
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        try{
            NoneRabbitGroup ng = (NoneRabbitGroup) obj;
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}