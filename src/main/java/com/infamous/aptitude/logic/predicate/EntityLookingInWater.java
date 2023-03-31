package com.infamous.aptitude.logic.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.function.Predicate;

public class EntityLookingInWater implements PredicateMaker<LivingEntity>{
    private static boolean check(LivingEntity entity) {
        Level level = entity.level;
        Optional<PositionTracker> lookTarget = entity.getBrain().getMemory(MemoryModuleType.LOOK_TARGET);
        if (lookTarget.isPresent()) {
            BlockPos currentBlockPosition = lookTarget.get().currentBlockPosition();
            return level.isWaterAt(currentBlockPosition) == entity.isInWaterOrBubble();
        } else {
            return false;
        }
    }

    @Override
    public Predicate<LivingEntity> make() {
        return EntityLookingInWater::check;
    }

    @Override
    public Codec<? extends PredicateMaker<LivingEntity>> getCodec() {
        return null;
    }
}
