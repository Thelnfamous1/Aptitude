package com.infamous.aptitude.behavior.vanilla;

import com.infamous.aptitude.logic.predicate.EntityIsEntityType;
import com.infamous.aptitude.logic.predicate.EntityIsMobCategory;
import com.infamous.aptitude.logic.predicate.utility.AlwaysTrue;
import com.infamous.aptitude.registry.VanillaBehaviorMakers;
import com.infamous.aptitude.behavior.BehaviorMaker;
import com.infamous.aptitude.logic.predicate.PredicateMaker;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;

public record SetEntityLookTargetMaker(PredicateMaker<LivingEntity> targetPredicate, float maxDistance) implements BehaviorMaker {


    public static SetEntityLookTargetMaker forMobCategory(MobCategory mobCategory, float maxDistance) {
        return new SetEntityLookTargetMaker((PredicateMaker<LivingEntity>) (Object) new EntityIsMobCategory(mobCategory), maxDistance);
    }

    public static SetEntityLookTargetMaker forEntityType(EntityType<?> entityType, float maxDistance) {
        return new SetEntityLookTargetMaker((PredicateMaker<LivingEntity>) (Object) new EntityIsEntityType(entityType), maxDistance);
    }

    public static SetEntityLookTargetMaker simple(float maxDistance) {
        return new SetEntityLookTargetMaker(new AlwaysTrue<>(), maxDistance);
    }

    @Override
    public BehaviorControl<?> make() {
        return SetEntityLookTarget.create(this.targetPredicate.make(), this.maxDistance);
    }

    @Override
    public Codec<? extends BehaviorMaker> getCodec() {
        return VanillaBehaviorMakers.SET_ENTITY_LOOK_TARGET.get();
    }
}
