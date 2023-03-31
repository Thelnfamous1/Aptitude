package com.infamous.aptitude.logic.function;

import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public record AllaylikePlayerTracker(MemoryModuleType<UUID> uuidMemory, int closeEnough) implements FunctionMaker<LivingEntity, Optional<PositionTracker>> {

    private static Optional<PositionTracker> getPlayerTracker(LivingEntity entity, MemoryModuleType<UUID> uuidMemory, int closeEnough) {
        Optional<ServerPlayer> result = Optional.empty();
        Level level = entity.getLevel();
        if (!level.isClientSide() && level instanceof ServerLevel serverlevel) {
            Optional<UUID> optional = entity.getBrain().getMemory(uuidMemory);
            if (optional.isPresent()) {
                Entity retrievedEntity = serverlevel.getEntity(optional.get());
                if (retrievedEntity instanceof ServerPlayer serverPlayer) {
                    if ((serverPlayer.gameMode.isSurvival() || serverPlayer.gameMode.isCreative()) && serverPlayer.closerThan(entity, closeEnough)) {
                        result = Optional.of(serverPlayer);
                    }
                }

            }
        }

        return result.map((sp) -> new EntityTracker(sp, true));
    }

    @Override
    public Function<LivingEntity, Optional<PositionTracker>> make() {
        return entity -> getPlayerTracker(entity, this.uuidMemory, this.closeEnough);
    }

    @Override
    public Codec<? extends FunctionMaker<LivingEntity, Optional<PositionTracker>>> getCodec() {
        return null;
    }
}
