package org.mintcode.errabbit.core.rabbit.name;

import org.mintcode.errabbit.model.Rabbit;

import java.util.Comparator;

/**
 * Rabbit list sort by rabbitId
 * Created by soleaf on 9/6/15.
 */
public class RabbitNameComparator implements Comparator<Rabbit> {
    @Override
    public int compare(Rabbit o1, Rabbit o2) {
        return o1.getLabel().compareTo(o2.getLabel());
    }
}
