package com.infamous.aptitude.datagen;

import com.google.common.collect.ImmutableList;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.codec.PrioritizedBehavior;

public class DataGenUtil {
    public static ImmutableList<PrioritizedBehavior> createPriorityPairs(int priorityStart, ImmutableList<BehaviorMaker> behaviors) {
        int priority = priorityStart;
        ImmutableList.Builder<PrioritizedBehavior> builder = ImmutableList.builder();

        for(BehaviorMaker behavior : behaviors) {
            builder.add(PrioritizedBehavior.of(priority++, behavior));
        }

        return builder.build();
    }
}
