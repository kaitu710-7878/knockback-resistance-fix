package net.knockbackresistancefix.mixin;

import net.knockbackresistancefix.Config;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public int hurtTime;
    @Shadow public int hurtDuration;

    @Shadow public abstract double getAttributeValue(Attribute attribute);

    protected LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Fixes knockback resistance behavior:
     * - If resistance is at maximum (>= threshold), no knockback velocity is applied.
     * - If resistance is between 0 and 1, the full knockback impulse vector is scaled down without changing direction or pulling downward.
     */
    @Inject(method = "knockback", at = @At("HEAD"), cancellable = true)
    private void knockbackresistancefix$onKnockback(double strength, double ratioX, double ratioZ, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        LivingKnockBackEvent event = ForgeHooks.onLivingKnockBack(entity, (float) strength, ratioX, ratioZ);
        if (event.isCanceled()) {
            ci.cancel();
            return;
        }

        strength = event.getStrength();
        ratioX = event.getRatioX();
        ratioZ = event.getRatioZ();

        double resistance = this.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
        double maxThreshold = Config.COMMON.maxResistanceThreshold.get();

        // When resistance reaches or exceeds max threshold, nullify all knockback velocity
        if (resistance >= maxThreshold) {
            ci.cancel();
            return;
        }

        // If fixVectorDirection is disabled, fallback to vanilla logic
        if (!Config.COMMON.fixVectorDirection.get()) {
            strength *= 1.0D - resistance;
            if (!(strength <= 0.0D)) {
                this.hasImpulse = true;
                Vec3 current = this.getDeltaMovement();
                Vec3 push = (new Vec3(ratioX, 0.0D, ratioZ)).normalize().scale(strength);
                this.setDeltaMovement(
                        current.x / 2.0D - push.x,
                        this.onGround() ? Math.min(0.4D, current.y / 2.0D + strength) : current.y,
                        current.z / 2.0D - push.z
                );
            }
            ci.cancel();
            return;
        }

        double factor = Math.max(0.0D, 1.0D - resistance);
        if (factor <= 0.0D || strength <= 0.0D) {
            ci.cancel();
            return;
        }

        Vec3 currentVel = this.getDeltaMovement();
        Vec3 push = (new Vec3(ratioX, 0.0D, ratioZ)).normalize().scale(strength);

        // Calculate target velocity at zero resistance (standard vanilla knockback target)
        double targetX = currentVel.x / 2.0D - push.x;
        double targetY = this.onGround() ? Math.min(0.4D, currentVel.y / 2.0D + strength) : currentVel.y;
        double targetZ = currentVel.z / 2.0D - push.z;

        // The true knockback impulse vector (delta velocity at 0 resistance)
        double impulseX = targetX - currentVel.x;
        double impulseY = targetY - currentVel.y;
        double impulseZ = targetZ - currentVel.z;

        // Scale the impulse vector by factor (1.0 - resistance), preserving the exact vector direction
        Vec3 newVel = new Vec3(
                currentVel.x + impulseX * factor,
                currentVel.y + impulseY * factor,
                currentVel.z + impulseZ * factor
        );

        this.hasImpulse = true;
        this.setDeltaMovement(newVel);
        ci.cancel();
    }

    /**
     * Prevents stun / flinch and motion sync packets when knockback resistance is at maximum.
     */
    @Inject(method = "hurt", at = @At("TAIL"))
    private void knockbackresistancefix$onHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && Config.COMMON.preventStunAtMaxResistance.get()) {
            double resistance = this.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            if (resistance >= Config.COMMON.maxResistanceThreshold.get()) {
                this.hurtDuration = 0;
                this.hurtTime = 0;
                this.hurtMarked = false;
            }
        }
    }

    /**
     * Prevents client-side hurt animation / flinch when knockback resistance is at maximum.
     */
    @Inject(method = "handleDamageEvent", at = @At("TAIL"))
    private void knockbackresistancefix$onHandleDamageEvent(DamageSource source, CallbackInfo ci) {
        if (Config.COMMON.preventStunAtMaxResistance.get()) {
            double resistance = this.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            if (resistance >= Config.COMMON.maxResistanceThreshold.get()) {
                this.hurtDuration = 0;
                this.hurtTime = 0;
            }
        }
    }
}
