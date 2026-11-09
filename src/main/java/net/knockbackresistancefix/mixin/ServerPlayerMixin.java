package net.knockbackresistancefix.mixin;

import net.knockbackresistancefix.Config;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

    /**
     * Prevents sending hurt animation packet (screen shake / flinch) to the client
     * when knockback resistance reaches the threshold.
     */
    @Inject(method = "indicateDamage", at = @At("HEAD"), cancellable = true)
    private void knockbackresistancefix$onIndicateDamage(double x, double z, CallbackInfo ci) {
        if (Config.COMMON.preventStunAtMaxResistance.get()) {
            ServerPlayer player = (ServerPlayer) (Object) this;
            double resistance = player.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            if (resistance >= Config.COMMON.maxResistanceThreshold.get()) {
                ci.cancel();
            }
        }
    }
}
