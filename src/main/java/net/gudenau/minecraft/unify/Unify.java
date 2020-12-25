package net.gudenau.minecraft.unify;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.gudenau.minecraft.unify.api.TypeRegistry;
import net.gudenau.minecraft.unify.api.UnifyRegistry;
import net.gudenau.minecraft.unify.impl.UnifyRegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Unify implements ModInitializer{
    public static final String MOD_ID = "gud_unify";
    private static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    
    private boolean sorted = false;
    
    @Override
    public void onInitialize(){
        LOGGER.info("Get ready for unified resources.");
        initItemResources();
        initBlockResources();
        initFluidResources();
        ServerLifecycleEvents.SERVER_STARTING.register((server)->{
            if(sorted){
                return;
            }
            sorted = true;
            UnifyRegistryImpl.INSTANCE.sort();
        });
        Item fakeIron = new Item(new Item.Settings().group(Items.IRON_INGOT.getGroup()));
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fake_iron"), fakeIron);
        UnifyRegistry.getItemRegistry().registerResource(new Identifier(MOD_ID, "iron_ingot"), fakeIron);
    }
    
    private void initItemResources(){
        TypeRegistry<Item> registry = UnifyRegistry.getItemRegistry();
        registry.registerResource(new Identifier(MOD_ID, "iron_ingot"), Items.IRON_INGOT);
        registry.registerResource(new Identifier(MOD_ID, "iron_nugget"), Items.IRON_NUGGET);
        registry.registerResource(new Identifier(MOD_ID, "gold_ingot"), Items.GOLD_INGOT);
        registry.registerResource(new Identifier(MOD_ID, "gold_nugget"), Items.GOLD_NUGGET);
        registry.registerResource(new Identifier(MOD_ID, "netherite_ingot"), Items.NETHERITE_INGOT);
        registry.registerResource(new Identifier(MOD_ID, "redstone_dust"), Items.REDSTONE);
        registry.registerResource(new Identifier(MOD_ID, "glowstone_dust"), Items.GLOWSTONE_DUST);
        registry.registerResource(new Identifier(MOD_ID, "diamond_gem"), Items.DIAMOND);
        registry.registerResource(new Identifier(MOD_ID, "emerald_gem"), Items.EMERALD);
        registry.registerResource(new Identifier(MOD_ID, "coal_gem"), Items.COAL);
        registry.registerResource(new Identifier(MOD_ID, "quartz_gem"), Items.QUARTZ);
        registry.registerResource(new Identifier(MOD_ID, "lapis_gem"), Items.LAPIS_LAZULI);
    }
    
    private void initBlockResources(){
        TypeRegistry<Block> registry = UnifyRegistry.getBlockRegistry();
        registry.registerResource(new Identifier(MOD_ID, "iron_ore"), Blocks.IRON_ORE);
        registry.registerResource(new Identifier(MOD_ID, "iron_block"), Blocks.IRON_BLOCK);
        registry.registerResource(new Identifier(MOD_ID, "gold_ore"), Blocks.GOLD_ORE);
        registry.registerResource(new Identifier(MOD_ID, "gold_block"), Blocks.GOLD_BLOCK);
        registry.registerResource(new Identifier(MOD_ID, "netherite_ore"), Blocks.ANCIENT_DEBRIS);
        registry.registerResource(new Identifier(MOD_ID, "netherite_block"), Blocks.NETHERITE_BLOCK);
        registry.registerResource(new Identifier(MOD_ID, "redstone_ore"), Blocks.REDSTONE_ORE);
        registry.registerResource(new Identifier(MOD_ID, "redstone_block"), Blocks.REDSTONE_BLOCK);
        registry.registerResource(new Identifier(MOD_ID, "glowstone_ore"), Blocks.GLOWSTONE);
        registry.registerResource(new Identifier(MOD_ID, "glowstone_block"), Blocks.GLOWSTONE);
        registry.registerResource(new Identifier(MOD_ID, "diamond_ore"), Blocks.DIAMOND_ORE);
        registry.registerResource(new Identifier(MOD_ID, "diamond_block"), Blocks.DIAMOND_BLOCK);
        registry.registerResource(new Identifier(MOD_ID, "emerald_ore"), Blocks.EMERALD_ORE);
        registry.registerResource(new Identifier(MOD_ID, "emerald_block"), Blocks.EMERALD_BLOCK);
        registry.registerResource(new Identifier(MOD_ID, "coal_ore"), Blocks.COAL_ORE);
        registry.registerResource(new Identifier(MOD_ID, "coal_block"), Blocks.COAL_BLOCK);
        registry.registerResource(new Identifier(MOD_ID, "quartz_ore"), Blocks.NETHER_QUARTZ_ORE);
        registry.registerResource(new Identifier(MOD_ID, "quartz_block"), Blocks.QUARTZ_BLOCK);
        registry.registerResource(new Identifier(MOD_ID, "lapis_ore"), Blocks.LAPIS_ORE);
        registry.registerResource(new Identifier(MOD_ID, "lapis_block"), Blocks.LAPIS_BLOCK);
    }
    
    private void initFluidResources(){
        TypeRegistry<Fluid> registry = UnifyRegistry.getFluidRegistry();
        registry.registerResources(new Identifier(MOD_ID, "water_fluid"), Fluids.WATER, Fluids.FLOWING_WATER);
        registry.registerResources(new Identifier(MOD_ID, "lava_fluid"), Fluids.LAVA, Fluids.FLOWING_LAVA);
    }
}
