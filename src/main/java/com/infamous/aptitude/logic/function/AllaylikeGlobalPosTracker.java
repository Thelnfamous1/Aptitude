package com.infamous.aptitude.logic.function;

import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public record AllaylikeGlobalPosTracker(MemoryModuleType<GlobalPos> globalPosMemory, PredicateMaker<LivingEntity> selfPredicate, PredicateMaker<BlockState> blockPredicate, boolean eraseMemoryOnFailure) implements FunctionMaker<LivingEntity, Optional<PositionTracker>>{

    private static Optional<PositionTracker> getGlobalPositionTracker(LivingEntity entity, MemoryModuleType<GlobalPos> globalPosMemory, Predicate<LivingEntity> selfPredicate, Predicate<BlockState> blockPredicate, boolean eraseMemoryOnFailure) {
        Brain<?> brain = entity.getBrain();
        Optional<GlobalPos> memory = brain.getMemory(globalPosMemory);
        Optional<PositionTracker> result = Optional.empty();
        if (memory.isPresent()) {
            GlobalPos globalPos = memory.get();
            Level level = entity.getLevel();
            if (level.dimension() == globalPos.dimension() && blockPredicate.test(level.getBlockState(globalPos.pos())) && selfPredicate.test(entity)) {
                result = Optional.of(new BlockPosTracker(globalPos.pos().above()));
            } else if(eraseMemoryOnFailure){
                brain.eraseMemory(globalPosMemory);
            }
        }
        return result;
    }

    @Override
    public Function<LivingEntity, Optional<PositionTracker>> make() {
        return entity -> getGlobalPositionTracker(entity, this.globalPosMemory, this.selfPredicate.make(), this.blockPredicate.make(), this.eraseMemoryOnFailure);
    }

    @Override
    public Codec<? extends FunctionMaker<LivingEntity, Optional<PositionTracker>>> getCodec() {
        return null;
    }
}
