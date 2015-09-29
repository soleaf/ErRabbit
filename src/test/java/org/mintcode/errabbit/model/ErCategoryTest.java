package org.mintcode.errabbit.model;

import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by soleaf on 9/29/15.
 */
public class ErCategoryTest {

    /**
     * Parsing test from Category
     * @throws Exception
     */
    @Test
    public void testFromCategory() throws Exception {
        Category category = Category.getInstance("category");
        category.setLevel(Level.WARN);

        ErCategory erCategory = ErCategory.fromCategory(category);
        assertTrue(equalsWithCategory(erCategory, category));

    }

    public static boolean equalsWithCategory(ErCategory erCategory, Category category){
        return erCategory.getLevel().getLevelStr()
                .equals(category.getLevel().toString())
                && erCategory.getName().equals(category.getName());
    }
}