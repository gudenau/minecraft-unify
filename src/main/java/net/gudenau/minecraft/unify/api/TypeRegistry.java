package net.gudenau.minecraft.unify.api;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.util.Identifier;

public interface TypeRegistry<T>{
    T registerResource(Identifier identifier, Supplier<T> factory);
    T registerResource(Identifier identifier, T resource);
    void registerResources(Identifier identifier, T... resources);
    Optional<T> getResource(Identifier identifier);
    List<T> getResources(Identifier identifier);
    List<Identifier> getCategoriesForResource(T resource);
    T getPrimary(T resource);
}
