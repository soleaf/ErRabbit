package org.mintcode.errabbit.core.rabbit.name;

import org.mintcode.errabbit.model.RabbitGroup;

import java.util.Comparator;

/**
 * For rabbit group sorting
 * Created by soleaf on 9/6/15.
 */
public class RabbitGroupNameComparator implements Comparator<RabbitGroup> {

    @Override
    public int compare(RabbitGroup o1, RabbitGroup o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
