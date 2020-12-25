package net.gudenau.minecraft.unify.impl;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.*;
import java.util.function.Supplier;
import net.gudenau.minecraft.unify.api.TypeRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TypeRegistryImpl<T> implements TypeRegistry<T>{
    private final Map<Identifier, List<T>> resources = new Object2ObjectOpenHashMap<>();
    private final Map<List<T>, List<T>> readOnlyResources = new Object2ObjectOpenHashMap<>();
    private final Map<T, List<Identifier>> reverseMap = new Object2ObjectOpenHashMap<>();
    private final Map<List<Identifier>, List<Identifier>> readOnlyReverseMap = new Object2ObjectOpenHashMap<>();
    private final Registry<T> registry;
    private final Class<T> type;
    
    TypeRegistryImpl(Registry<T> registry, Class<T> type){
        this.registry = registry;
        this.type = type;
    }
    
    public T registerResource(Identifier identifier, Supplier<T> factory){
        List<T> list = resources.get(identifier);
        if(list != null && !list.isEmpty()){
            return list.get(0);
        }else{
            T resource = factory.get();
            registerResource(identifier, resource);
            return resource;
        }
    }
    
    @Override
    public T registerResource(Identifier identifier, T resource){
        if(resource == null){
            throw new IllegalArgumentException("Null " + type.getSimpleName() + " resource");
        }
        if(!type.isInstance(resource)){
            throw new IllegalArgumentException("Illegal resource, \"" + resource + "\" is not a " + type.getSimpleName());
        }
        if(registry.getId(resource) == null){
            throw new IllegalArgumentException("Illegal resource, \"" + resource + "\" is not registered");
        }
        resources.computeIfAbsent(identifier, (id)->new ArrayList<>()).add(resource);
        reverseMap.computeIfAbsent(resource, (res)->new ArrayList<>()).add(identifier);
        return resource;
    }
    
    @Override
    public void registerResources(Identifier identifier, T... resources){
        for(T resource : resources){
            registerResource(identifier, resource);
        }
    }
    
    @Override
    public Optional<T> getResource(Identifier identifier){
        List<T> list = resources.get(identifier);
        if(list == null || list.isEmpty()){
            return Optional.empty();
        }else{
            return Optional.of(list.get(0));
        }
    }
    
    @Override
    public List<T> getResources(Identifier identifier){
        List<T> list = resources.get(identifier);
        if(list == null){
            return Collections.emptyList();
        }
        return readOnlyResources.computeIfAbsent(list, Collections::unmodifiableList);
    }
    
    @Override
    public List<Identifier> getCategoriesForResource(T resource){
        List<Identifier> list = reverseMap.get(resource);
        if(list == null || list.isEmpty()){
            return Collections.emptyList();
        }
        return readOnlyReverseMap.computeIfAbsent(list, Collections::unmodifiableList);
    }
    
    @Override
    public T getPrimary(T resource){
        List<Identifier> ids = getCategoriesForResource(resource);
        if(ids.size() != 1){
            return resource;
        }
        
        return getResource(ids.get(0)).orElse(resource);
    }
    
    public void sort(){
        resources.values().forEach((list)->list.sort((a, b)->{
            if(a == b){
                return 0;
            }
            Identifier idA = registry.getId(a);
            Identifier idB = registry.getId(b);
    
            //noinspection ConstantConditions
            if(!idA.getNamespace().equals(idB.getNamespace())){
                if(idA.getNamespace().equals("minecraft")){
                    return -1;
                }
                if(idB.getNamespace().equals("minecraft")){
                    return 1;
                }
            }
            return idA.compareTo(idB);
        }));
    }
}
