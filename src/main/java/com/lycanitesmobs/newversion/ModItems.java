package com.lycanitesmobs.newversion;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Example item registry for the 1.20.1 Forge port.
 */
public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LycanitesMobsForge.MODID);

    public static final RegistryObject<Item> ANCIENT_FRUIT = ITEMS.register(
            "ancient_fruit",
            () -> new Item(new Properties().tab(CreativeModeTab.TAB_MISC))
    );
}
