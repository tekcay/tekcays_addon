package tekcays_addon.multiblocks.api.metatileentity;

import tekcays_addon.multiblocks.api.capability.IParallelMultiblock;
import tekcays_addon.multiblocks.api.capability.impl.TKCYAMultiblockRecipeLogic;
import tekcays_addon.multiblocks.common.TKCYAConfigHolder;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class TKCYARecipeMapMultiblockController extends MultiMapMultiblockController implements IParallelMultiblock {

    public TKCYARecipeMapMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        this(metaTileEntityId, new RecipeMap<?>[]{recipeMap});
    }

    public TKCYARecipeMapMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMaps) {
        super(metaTileEntityId, recipeMaps);
        this.recipeMapWorkable = new TKCYAMultiblockRecipeLogic(this);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        if (isParallel())
            tooltip.add(I18n.format("tkcya.tooltip.parallel_enabled"));
        if (TKCYAConfigHolder.globalMultiblocks.enableTieredCasings && isTiered())
            tooltip.add(I18n.format("tkcya.tooltip.tiered_hatch_enabled"));
    }

    @Override
    protected void addExtraDisplayInfo(List<ITextComponent> textList) {
        super.addExtraDisplayInfo(textList);
        List<ITieredMetaTileEntity> list = getAbilities(TKCYAMultiblockAbility.TIERED_HATCH);
        if (TKCYAConfigHolder.globalMultiblocks.enableTieredCasings && !list.isEmpty()) {
            long maxVoltage = Math.min(GTValues.V[list.get(0).getTier()], Math.max(energyContainer.getInputVoltage(), energyContainer.getOutputVoltage()));
            String voltageName = GTValues.VNF[list.get(0).getTier()];
            textList.add(new TextComponentTranslation("tkcya.multiblock.tiered_hatch.tooltip", maxVoltage, voltageName));
        }
    }

    @Override
    public boolean isParallel() {
        return true;
    }

    @Override
    public int getMaxParallel() {
        return this.getAbilities(TKCYAMultiblockAbility.PARALLEL_HATCH).isEmpty() ? 1 :
                this.getAbilities(TKCYAMultiblockAbility.PARALLEL_HATCH).get(0).getCurrentParallel();
    }

    public boolean isTiered() {
        return TKCYAConfigHolder.globalMultiblocks.enableTieredCasings;
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn, boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut, boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(checkEnergyIn, checkMaintenance, checkItemIn, checkItemOut, checkFluidIn, checkFluidOut, checkMuffler);
        if (isParallel())
            predicate = predicate.or(abilities(TKCYAMultiblockAbility.PARALLEL_HATCH).setMaxGlobalLimited(1).setPreviewCount(1));
        return predicate;
    }

    @Nonnull
    public static TraceabilityPredicate tieredCasing() {
        return new TraceabilityPredicate(abilities(TKCYAMultiblockAbility.TIERED_HATCH).setMinGlobalLimited(TKCYAConfigHolder.globalMultiblocks.enableTieredCasings ? 1 : 0).setMaxGlobalLimited(1));
    }
}
