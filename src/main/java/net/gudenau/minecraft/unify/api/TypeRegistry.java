package net.gudenau.minecraft.unify.api;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.util.Identifier;

/**
 * The primary place where you will be interacting with this API. Provides all of the dictionary features.
 *
 * @param <T> The type of this registry.
 *
 * @since 1.0.0
 */
public interface TypeRegistry<T>{
    /**
     * Registers a resource for a resource type if it is currently empty. Only calls the factory when needed.
     *
     * @param identifier The identifier for the resource type
     * @param factory The factory for the resource
     * @return The created resource or an existing one
     */
    T registerResource(Identifier identifier, Supplier<T> factory);
    
    /**
     * Registers a resource for a resource type.
     *
     * @param identifier The identifier for the resource type
     * @param resource The resource
     * @return The supplied resource
     */
    T registerResource(Identifier identifier, T resource);
    
    /**
     * The same as {@link TypeRegistry#registerResource(Identifier, T) registerResource} but with varargs.
     *
     * @param identifier The identifier for the resource type
     * @param resources The resources
     */
    void registerResources(Identifier identifier, T... resources);
    
    /**
     * Gets the "primary" resource for a resource type, if it exists.
     *
     * @param identifier The identifier for the resource type
     * @return The resource or empty
     */
    Optional<T> getResource(Identifier identifier);
    
    /**
     * Gets all registered resources for a resource type.
     *
     * @param identifier The identifier for the resource type
     * @return The resource ore empty
     */
    List<T> getResources(Identifier identifier);
    
    /**
     * Gets all categories that a resource is in.
     *
     * @param resource The resource to check
     * @return All categories the resource is in
     */
    List<Identifier> getCategoriesForResource(T resource);
    
    /**
     * Gets the "primary" version of a resource, if present.
     *
     * @param resource The resource to check
     * @return The "primary" version of the resource, or the passed resource
     */
    T getPrimary(T resource);
}
