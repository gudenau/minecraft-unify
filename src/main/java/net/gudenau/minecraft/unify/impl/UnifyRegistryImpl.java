package net.gudenau.minecraft.unify.impl;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import net.gudenau.minecraft.unify.api.TypeRegistry;
import net.gudenau.minecraft.unify.api.UnifyRegistry;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class UnifyRegistryImpl implements UnifyRegistry{
    public static final UnifyRegistryImpl INSTANCE = new UnifyRegistryImpl();
    
    private final Map<Class<?>, TypeRegistryImpl<?>> registries = new Object2ObjectOpenHashMap<>(3);
    private UnifyRegistryImpl(){
        getTypeRegistry(Registry.ITEM, Item.class);
        getTypeRegistry(Registry.BLOCK, Block.class);
        getTypeRegistry(Registry.FLUID, Fluid.class);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeRegistry<T> getTypeRegistry(Registry<T> minecraftRegistry, Class<T> type){
        return (TypeRegistry<T>)registries.computeIfAbsent(type, (t)->new TypeRegistryImpl<>(minecraftRegistry, type));
    }
    
    public void sort(){
        registries.values().forEach(TypeRegistryImpl::sort);
    }
}
