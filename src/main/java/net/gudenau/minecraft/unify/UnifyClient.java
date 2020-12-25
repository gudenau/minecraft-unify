package net.gudenau.minecraft.unify;

import java.util.List;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.gudenau.minecraft.unify.api.TypeRegistry;
import net.gudenau.minecraft.unify.api.UnifyRegistry;
import net.gudenau.minecraft.unify.mixin.BucketItemAccessor;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class UnifyClient implements ClientModInitializer{
    @Override
    public void onInitializeClient(){
        TypeRegistry<Item> itemRegistry = UnifyRegistry.getItemRegistry();
        TypeRegistry<Block> blockRegistry = UnifyRegistry.getBlockRegistry();
        TypeRegistry<Fluid> fluidRegistry = UnifyRegistry.getFluidRegistry();
        
        ItemTooltipCallback.EVENT.register((stack, context, lines)->{
            if(!context.isAdvanced()){
                return;
            }
            
            Item item = stack.getItem();
            addCategories(lines, itemRegistry, item, "item");
            
            if(item instanceof BlockItem){
                Block block = ((BlockItem)item).getBlock();
                addCategories(lines, blockRegistry, block, "block");
            }
            if(item instanceof BucketItem){
                Fluid fluid = ((BucketItemAccessor)item).getFluid();
                addCategories(lines, fluidRegistry, fluid, "fluid");
            }
        });
    }
    
    private <T> void addCategories(List<Text> lines, TypeRegistry<T> registry, T resource, String tooltipKey){
        List<Identifier> ids = registry.getCategoriesForResource(resource);
        if(!ids.isEmpty()){
            lines.add(new TranslatableText("tooltip.gud_unify." + tooltipKey).formatted(Formatting.GRAY));
            ids.forEach((id)->{
                Text text = new TranslatableText("category." + tooltipKey + "." + id.getNamespace() + "." + id.getPath());
                lines.add(text.shallowCopy().formatted(Formatting.GRAY));
            });
        }
    }
}
