package com.lycanitesmobs.newversion;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Example block registry for the 1.20.1 Forge port.
 */
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, LycanitesMobsForge.MODID);

    public static final RegistryObject<Block> TEST_BLOCK = BLOCKS.register(
            "test_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE))
    );
}
