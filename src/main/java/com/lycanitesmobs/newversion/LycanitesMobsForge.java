package com.lycanitesmobs.newversion;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Entry point for the Forge 1.20.1 port.
 * Currently sets up basic mod registration hooks via DeferredRegister.
 */
@Mod(LycanitesMobsForge.MODID)
public class LycanitesMobsForge {
    public static final String MODID = "lycanitesmobs";

    public LycanitesMobsForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // TODO: Register blocks, items and entities using DeferredRegister
    }
}
