package com.infamous.aptitude.server.goal.attack;

import com.infamous.aptitude.common.entity.IRearing;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.math.vector.Vector3d;

public class RearingAttackGoal<T extends CreatureEntity & IRearing> extends AptitudeAttackGoal<T> {

    public RearingAttackGoal(T rearingCreature, double speedModifierIn, boolean mustSee) {
        super(rearingCreature, speedModifierIn, mustSee);
    }

    protected void checkAndPerformAttack(LivingEntity target, double distSqToTarget) {
        double attackReachSqr = this.getAttackReachSqr(target);
        if (distSqToTarget <= attackReachSqr && this.isTimeToAttack()) {
            this.resetAttackCooldown();
            if(this.creature.hasPassenger(target)){
                target.stopRiding();
                this.flingTargetOffBack(target);
            } else{
                this.creature.doHurtTarget(target);
            }
            this.creature.stopRearing();
        } else if (distSqToTarget <= attackReachSqr * 2.0D) {
            if (this.isTimeToAttack()) {
                this.creature.stopRearing();
                this.resetAttackCooldown();
            }

            if (this.getTicksUntilNextAttack() <= 10) {
                this.creature.startRearing();
                this.creature.playAngrySound();
            }
        } else {
            this.resetAttackCooldown();
            this.creature.stopRearing();
        }

    }

    protected void flingTargetOffBack(LivingEntity target){
        double attackKnockback = this.creature.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        double knockbackResistance = target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
        double knockbackStrength = attackKnockback - knockbackResistance;
        if (!(knockbackStrength <= 0.0D)) {
            double xDiff = target.getX() - this.creature.getX();
            double zDiff = target.getZ() - this.creature.getZ();
            double pushbackStrength = knockbackStrength * 0.5D;
            Vector3d pushbackVector = (new Vector3d(xDiff, 0.0D, zDiff)).normalize().scale(pushbackStrength);
            double flingStrength = knockbackStrength * 0.5D;
            target.push(pushbackVector.x, flingStrength, pushbackVector.z);
            target.hurtMarked = true;
        }
    }

    public void stop() {
        this.creature.stopRearing();
        super.stop();
    }
}
