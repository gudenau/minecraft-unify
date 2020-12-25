package net.gudenau.minecraft.unify.mixin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import net.gudenau.minecraft.unify.api.TypeRegistry;
import net.gudenau.minecraft.unify.api.UnifyRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Ingredient.class)
public class IngredientMixin{
    @ModifyArg(
        method = "ofEntries",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/recipe/Ingredient;<init>(Ljava/util/stream/Stream;)V"
        )
    )
    private static Stream<? extends Ingredient.Entry> init(Stream<? extends Ingredient.Entry> entries){
        TypeRegistry<Item> itemRegistry = UnifyRegistry.getItemRegistry();
        TypeRegistry<Block> blockRegistry = UnifyRegistry.getBlockRegistry();
        return entries.flatMap((entry)->{
            Collection<ItemStack> stacks = entry.getStacks();
            List<Identifier> itemCategories = new ArrayList<>();
            List<Identifier> blockCategories = new ArrayList<>();
            for(ItemStack stack : stacks){
                Item item = stack.getItem();
                if(item instanceof BlockItem){
                    Block block = ((BlockItem)item).getBlock();
                    blockCategories.addAll(blockRegistry.getCategoriesForResource(block));
                }else{
                    itemCategories.addAll(itemRegistry.getCategoriesForResource(item));
                }
            }
            if(itemCategories.isEmpty() && blockCategories.isEmpty()){
                return Stream.of(entry);
            }
            return Stream.of(
                stacks.stream(),
                itemCategories.stream().flatMap((c)->itemRegistry.getResources(c).stream()).map(ItemStack::new),
                blockCategories.stream().flatMap((c)->blockRegistry.getResources(c).stream()).map(ItemStack::new)
            ).flatMap((s)->s).distinct().map(Ingredient$StackEntryAccessor::init);
        });
    }
}
