package net.gudenau.minecraft.unify.api;

import net.gudenau.minecraft.unify.impl.UnifyRegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public interface UnifyRegistry{
    static UnifyRegistry getInstance(){
        return UnifyRegistryImpl.INSTANCE;
    }
    
    <T> TypeRegistry<T> getTypeRegistry(Registry<T> minecraftRegistry, Class<T> type);
    
    static TypeRegistry<Item> getItemRegistry(){
        return getInstance().getTypeRegistry(Registry.ITEM, Item.class);
    }
    
    static TypeRegistry<Block> getBlockRegistry(){
        return getInstance().getTypeRegistry(Registry.BLOCK, Block.class);
    }
    
    static TypeRegistry<Fluid> getFluidRegistry(){
        return getInstance().getTypeRegistry(Registry.FLUID, Fluid.class);
    }
}
