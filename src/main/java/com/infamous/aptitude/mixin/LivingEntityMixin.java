package com.infamous.aptitude.mixin;

import com.infamous.aptitude.util.BrainUtil;
import com.infamous.aptitude.util.BrainDataAccess;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements BrainDataAccess {
    @Shadow protected Brain<?> brain;
    private Dynamic<Tag> lastKnownBrainData;

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;makeBrain(Lcom/mojang/serialization/Dynamic;)Lnet/minecraft/world/entity/ai/Brain;"), index = 0)
    private Dynamic<?> modifyDynamicInit(Dynamic<Tag> dynamic){
        this.lastKnownBrainData = dynamic;
        return dynamic;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void handlePostConstruction(EntityType<?> p_20966_, Level p_20967_, CallbackInfo ci){
        BrainUtil.runBrainModifiers((LivingEntity) (Object) this, this.brain, this.lastKnownBrainData);
    }

    @ModifyArg(method = "readAdditionalSaveData", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;makeBrain(Lcom/mojang/serialization/Dynamic;)Lnet/minecraft/world/entity/ai/Brain;"), index = 0)
    private Dynamic<?> modifyDynamicReadAdditional(Dynamic<Tag> dynamic){
        this.lastKnownBrainData = dynamic;
        return dynamic;
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void handlePostReadAdditional(CompoundTag tag, CallbackInfo ci){
        BrainUtil.runBrainModifiers((LivingEntity) (Object) this, this.brain, this.lastKnownBrainData);
    }

    @Override
    public Dynamic<Tag> getBrainData() {
        return this.lastKnownBrainData;
    }
}
