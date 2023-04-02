package com.infamous.aptitude.datagen;

import com.google.common.collect.ImmutableList;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.mojang.datafixers.util.Pair;

public class DataGenUtil {
    public static ImmutableList<Pair<String, BehaviorMaker>> createPriorityPairs(int priorityStart, ImmutableList<BehaviorMaker> behaviors) {
        int priority = priorityStart;
        ImmutableList.Builder<Pair<String, BehaviorMaker>> builder = ImmutableList.builder();

        for(BehaviorMaker behavior : behaviors) {
            builder.add(Pair.of(Integer.toString(priority), behavior));
            priority++;
        }

        return builder.build();
    }
}
