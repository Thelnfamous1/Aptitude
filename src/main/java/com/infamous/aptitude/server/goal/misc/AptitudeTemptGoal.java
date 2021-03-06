package com.infamous.aptitude.server.goal.misc;

import java.util.EnumSet;
import java.util.function.Predicate;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Tags;

public class AptitudeTemptGoal extends Goal {
   private static final EntityPredicate TEMP_TARGETING = (new EntityPredicate()).range(10.0D).allowInvulnerable().allowSameTeam().allowNonAttackable().allowUnseeable();
   protected final CreatureEntity mob;
   private final double speedModifier;
   private double px;
   private double py;
   private double pz;
   private double pRotX;
   private double pRotY;
   protected PlayerEntity player;
   private int calmDown;
   private boolean isRunning;
   private final Predicate<ItemStack> temptItemPredicate;
   private final boolean canScare;

   public AptitudeTemptGoal(CreatureEntity creature, double p_i47822_2_, Predicate<ItemStack> p_i47822_4_, boolean p_i47822_5_) {
      this(creature, p_i47822_2_, p_i47822_5_, p_i47822_4_);
   }

   public AptitudeTemptGoal(CreatureEntity creature, double p_i47823_2_, boolean p_i47823_4_, Predicate<ItemStack> p_i47823_5_) {
      this.mob = creature;
      this.speedModifier = p_i47823_2_;
      this.temptItemPredicate = p_i47823_5_;
      this.canScare = p_i47823_4_;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   public boolean canUse() {
      if(this.mob.isAggressive()){
         return false;
      }
      if (this.calmDown > 0) {
         --this.calmDown;
         return false;
      } else {
         this.player = this.mob.level.getNearestPlayer(TEMP_TARGETING, this.mob);
         if (this.player == null) {
            return false;
         } else {
            return this.shouldFollowItem(this.player.getMainHandItem()) || this.shouldFollowItem(this.player.getOffhandItem());
         }
      }
   }

   protected boolean shouldFollowItem(ItemStack stack) {
      return temptItemPredicate.test(stack);
   }

   public boolean canContinueToUse() {
      if (this.canScare()) {
         if (this.mob.distanceToSqr(this.player) < 36.0D) {
            if (this.player.distanceToSqr(this.px, this.py, this.pz) > 0.010000000000000002D) {
               return false;
            }

            if (Math.abs((double)this.player.xRot - this.pRotX) > 5.0D || Math.abs((double)this.player.yRot - this.pRotY) > 5.0D) {
               return false;
            }
         } else {
            this.px = this.player.getX();
            this.py = this.player.getY();
            this.pz = this.player.getZ();
         }

         this.pRotX = (double)this.player.xRot;
         this.pRotY = (double)this.player.yRot;
      }

      return this.canUse();
   }

   protected boolean canScare() {
      return this.canScare;
   }

   public void start() {
      this.px = this.player.getX();
      this.py = this.player.getY();
      this.pz = this.player.getZ();
      this.isRunning = true;
   }

   public void stop() {
      this.player = null;
      this.mob.getNavigation().stop();
      this.calmDown = 100;
      this.isRunning = false;
   }

   public void tick() {
      this.mob.getLookControl().setLookAt(this.player, (float)(this.mob.getMaxHeadYRot() + 20), (float)this.mob.getMaxHeadXRot());
      if (this.mob.distanceToSqr(this.player) < 6.25D) {
         this.mob.getNavigation().stop();
      } else {
         this.mob.getNavigation().moveTo(this.player, this.speedModifier);
      }

   }

   public boolean isRunning() {
      return this.isRunning;
   }
}