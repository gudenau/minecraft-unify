package net.gudenau.minecraft.unify.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Ingredient.StackEntry.class)
public interface Ingredient$StackEntryAccessor{
    @SuppressWarnings("ConstantConditions")
    @Invoker("<init>") static Ingredient.StackEntry init(ItemStack stack){return (Ingredient.StackEntry)new Object();}
}
