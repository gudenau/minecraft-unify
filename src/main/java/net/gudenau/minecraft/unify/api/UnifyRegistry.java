package net.gudenau.minecraft.unify.api;

import net.gudenau.minecraft.unify.impl.UnifyRegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

/**
 * The place to get handles to {@link TypeRegistry<> TypeRegistry}s.
 *
 * <p>The naming convention is as follows:
 * <ul>
 *     <li>Ingots should be called "gud_unify:resource_ingot"</li>
 *     <li>Nuggets should be called "gud_unify:resource_nugget"</li>
 *     <li>Dust should be called "gud_unify:resource_dust"</li>
 *     <li>Gems should be called "gud_unify:resource_gem"</li>
 *     <li>Material blocks should be called "gud_unify:resource_block"</li>
 *     <li>Ores should be called "gud_unify:resource_ore"</li>
 * </ul>
 *
 * <p>The following are some definitions:
 * <ul>
 *     <li>Ingot: A (normally) metallic resource that is in it's "final" form. Normally from smelting in a furnace.</li>
 *     <li>Nugget: A (normally) metallic resource that is a smaller unit of an ingot. Such as a gold nugget.</li>
 *     <li>Dust: A (normally) intermediate powdered form of a material, such as Iron ore -> Iron dust -> Iron ingot. Redstone and glowstone are also in this category.</li>
 *     <li>Gem: A (normally) finished product that drops from an ore. Coal is a gem because it drops directly from the ore, for example.</li>
 *     <li>Material block: The block form of a material, such as an diamond block.</li>
 *     <li>Ore: The in naturally occurring non-structure for of a resource. Such as emerald ore.</li>
 * </ul>
 *
 * <p>If you create a type that would not fit into the namespace of "gud_unify", feel free to use the namespace of your mod.
 *
 * <p>These are not hard and fast rules, but a guideline to try and help keep things from getting out of hand.
 *
 * @since 1.0.0
 */
public interface UnifyRegistry{
    /**
     * Gets the instance of the registry.
     *
     * @return The registry instance.
     */
    static UnifyRegistry getInstance(){
        return UnifyRegistryImpl.INSTANCE;
    }
    
    /**
     * Gets the registry for a type.
     *
     * @param minecraftRegistry The MC registry for the type
     * @param type The type of the registry
     * @param <T> The type of the registry
     * @return The registry
     */
    <T> TypeRegistry<T> getTypeRegistry(Registry<T> minecraftRegistry, Class<T> type);
    
    /**
     * A shortcut for getting the Item registry.
     *
     * @return The item registry
     */
    static TypeRegistry<Item> getItemRegistry(){
        return getInstance().getTypeRegistry(Registry.ITEM, Item.class);
    }
    
    /**
     * A shortcut for getting the Block registry.
     *
     * @return The block registry
     */
    static TypeRegistry<Block> getBlockRegistry(){
        return getInstance().getTypeRegistry(Registry.BLOCK, Block.class);
    }
    
    /**
     * A shortcut for getting the Fluid registry.
     *
     * @return The fluid registry
     */
    static TypeRegistry<Fluid> getFluidRegistry(){
        return getInstance().getTypeRegistry(Registry.FLUID, Fluid.class);
    }
}
