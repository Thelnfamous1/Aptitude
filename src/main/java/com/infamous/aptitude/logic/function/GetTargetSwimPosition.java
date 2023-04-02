package com.infamous.aptitude.logic.function;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

public record GetTargetSwimPosition(int[][] swimXYDistanceTiers) implements FunctionMaker<PathfinderMob, Vec3> {

    private static final int[][] SWIM_XY_DISTANCE_TIERS = new int[][]{{1, 1}, {3, 3}, {5, 5}, {6, 5}, {7, 7}, {10, 7}};

    public static GetTargetSwimPosition simple(){
        return new GetTargetSwimPosition(SWIM_XY_DISTANCE_TIERS);
    }

    private static Vec3 getTargetSwimPos(PathfinderMob mob, int[][] swimXYDistanceTiers) {
        Vec3 lastTargetSwimPos = null;
        Vec3 targetSwimPos = null;

        for(int[] swimXYDistanceTier : swimXYDistanceTiers) {
            if (lastTargetSwimPos == null) {
                targetSwimPos = BehaviorUtils.getRandomSwimmablePos(mob, swimXYDistanceTier[0], swimXYDistanceTier[1]);
            } else {
                targetSwimPos = mob.position().add(mob.position().vectorTo(lastTargetSwimPos).normalize().multiply(swimXYDistanceTier[0], swimXYDistanceTier[1], swimXYDistanceTier[0]));
            }

            if (targetSwimPos == null || mob.level.getFluidState(new BlockPos(targetSwimPos)).isEmpty()) {
                return lastTargetSwimPos;
            }

            lastTargetSwimPos = targetSwimPos;
        }

        return targetSwimPos;
    }

    @Override
    public Function<PathfinderMob, Vec3> make() {
        return pf -> getTargetSwimPos(pf, this.swimXYDistanceTiers);
    }

    @Override
    public Codec<? extends FunctionMaker<PathfinderMob, Vec3>> getCodec() {
        return null;
    }
}
