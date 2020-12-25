package net.gudenau.minecraft.unify.mixin;

import net.gudenau.minecraft.unify.api.UnifyRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin{
    @ModifyArg(
        method = "setStack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/data/DataTracker;set(Lnet/minecraft/entity/data/TrackedData;Ljava/lang/Object;)V"
        )
    )
    private Object setStack(Object rawStack){
        ItemStack stack = (ItemStack)rawStack;
        if(!stack.isEmpty() && !stack.hasTag()){
            Item item = stack.getItem();
            Item replacementItem = item;
            if(item instanceof BlockItem){
                Block block = ((BlockItem)item).getBlock();
                Block replacementBlock = UnifyRegistry.getBlockRegistry().getPrimary(block);
                if(block != replacementBlock){
                    replacementItem = replacementBlock.asItem();
                }
            }else{
                replacementItem = UnifyRegistry.getItemRegistry().getPrimary(item);
            }
            if(replacementItem != item){
                return new ItemStack(replacementItem, stack.getCount());
            }
        }
        return stack;
    }
}
