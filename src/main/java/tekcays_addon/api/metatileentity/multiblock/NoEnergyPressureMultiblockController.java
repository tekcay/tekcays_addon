package tekcays_addon.api.metatileentity.multiblock;

import gregicality.science.api.metatileentity.multiblock.GCYSMultiblockAbility;
import gregicality.science.api.metatileentity.multiblock.PressureMultiblockController;
import gregicality.science.api.utils.NumberFormattingUtil;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import tekcays_addon.api.capability.impl.NoEnergyPressureMultiblockRecipeLogic;
import tekcays_addon.api.utils.TKCYAValues;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class NoEnergyPressureMultiblockController extends PressureMultiblockController {


    public NoEnergyPressureMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new NoEnergyPressureMultiblockRecipeLogic(this);
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn, boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut, boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(false, checkMaintenance, checkItemIn, checkItemOut, checkFluidIn, checkFluidOut, checkMuffler);
        predicate = predicate.or(abilities(GCYSMultiblockAbility.PRESSURE_CONTAINER).setMaxGlobalLimited(1).setPreviewCount(1));
        return predicate;
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.machine.pressure_decrease.tooltip", NumberFormattingUtil.formatDoubleToCompactString(Math.abs(TKCYAValues.STEAM_AIR_COMPRESSOR_PRESSURE_INCREASE * 0.8))));
    }

}
