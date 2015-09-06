package org.mintcode.errabbit.model;

/**
 * Created by soleaf on 9/6/15.
 */
public class NoneRabbitGroup extends RabbitGroup{

    NoneRabbitGroup(){
        super("(no group)");
    }

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