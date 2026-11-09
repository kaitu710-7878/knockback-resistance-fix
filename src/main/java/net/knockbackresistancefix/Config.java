package net.knockbackresistancefix;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        COMMON = new Common(builder);
        COMMON_SPEC = builder.build();
    }

    public static class Common {
        public final ForgeConfigSpec.BooleanValue fixVectorDirection;
        public final ForgeConfigSpec.BooleanValue preventStunAtMaxResistance;
        public final ForgeConfigSpec.DoubleValue maxResistanceThreshold;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("general");

            fixVectorDirection = builder
                    .comment("When true, scales the knockback vector down without altering its direction or pulling downwards in mid-air.",
                             "有効時、ノックバック耐性がある場合にノックバックベクトルの向きを変えずに（空中で下向きにならずに）大きさのみを縮小します。")
                    .define("fixVectorDirection", true);

            preventStunAtMaxResistance = builder
                    .comment("When true, prevents damage flinch/stun (hurtTime, hurt animation packet, motion overwrite) when knockback resistance reaches the threshold.",
                             "有効時、ノックバック耐性が閾値以上のエンティティはダメージ時の硬直（赤点滅・カメラ揺れパケット・モーション同期）を受けません。")
                    .define("preventStunAtMaxResistance", true);

            maxResistanceThreshold = builder
                    .comment("The threshold for maximum knockback resistance (1.0 = 100%).",
                             "ノックバック耐性が最大とみなされる閾値（1.0 = 100%）。")
                    .defineInRange("maxResistanceThreshold", 1.0D, 0.0D, 10.0D);

            builder.pop();
        }
    }
}
