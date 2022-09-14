package tekcays_addon.api.metatileentity.mutiblock;

import gregicality.multiblocks.api.capability.IParallelMultiblock;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import tekcays_addon.api.capability.impl.NoEnergyParallelLogic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class NoEnergyParallelRecipeMapMultiblockController extends MultiMapMultiblockController implements IParallelMultiblock {

    public NoEnergyParallelRecipeMapMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        this(metaTileEntityId, new RecipeMap<?>[]{recipeMap});
    }

    public NoEnergyParallelRecipeMapMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMaps) {
        super(metaTileEntityId, recipeMaps);
        this.recipeMapWorkable = new NoEnergyParallelLogic(this);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        if (isParallel())
            tooltip.add(I18n.format("gcym.tooltip.parallel_enabled"));
    }

    @Override
    protected void addExtraDisplayInfo(List<ITextComponent> textList) {
        super.addExtraDisplayInfo(textList);
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn, boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut, boolean checkMuffler) {
        return super.autoAbilities(checkEnergyIn, checkMaintenance, checkItemIn, checkItemOut, checkFluidIn, checkFluidOut, checkMuffler);
    }


}
