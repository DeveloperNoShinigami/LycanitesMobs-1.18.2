package com.lycanitesmobs.newversion;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.lycanitesmobs.newversion.ModItems;
import com.lycanitesmobs.newversion.ModBlocks;

/**
 * Entry point for the Forge 1.20.1 port.
 * Currently sets up basic mod registration hooks via DeferredRegister.
 */
@Mod(LycanitesMobsForge.MODID)
public class LycanitesMobsForge {
    public static final String MODID = "lycanitesmobs";

    public LycanitesMobsForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        // TODO: Register entities using DeferredRegister
    }
}
