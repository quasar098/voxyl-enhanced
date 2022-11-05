package com.quasar.voxylenhanced;

import gg.essential.vigilance.data.Category;
import gg.essential.vigilance.data.SortingBehavior;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

class VoxylSortingBehavior extends SortingBehavior {
    @NotNull
    @Override
    public Comparator<? super Category> getCategoryComparator() {
        return (Comparator<Category>) (o1, o2) -> {
            if (o1.getName().equals("General")) return -1;
            else if (o2.getName().equals("General")) return 1;
            else {
                return super.getCategoryComparator().compare(o1, o2);
            }
        };
    }
}