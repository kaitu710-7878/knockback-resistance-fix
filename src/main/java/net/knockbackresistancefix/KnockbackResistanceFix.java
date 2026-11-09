package net.knockbackresistancefix;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(KnockbackResistanceFix.MOD_ID)
public class KnockbackResistanceFix {
    public static final String MOD_ID = "knockbackresistancefix";
    public static final Logger LOGGER = LogUtils.getLogger();

    public KnockbackResistanceFix() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
        LOGGER.info("Knockback Resistance Fix initialized.");
    }
}
