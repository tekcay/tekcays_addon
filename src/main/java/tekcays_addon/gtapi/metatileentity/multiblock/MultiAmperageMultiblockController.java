package tekcays_addon.gtapi.metatileentity.multiblock;

import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import tekcays_addon.gtapi.recipes.recipeproperties.MultiAmperageProperty;
import tekcays_addon.gtapi.recipes.recipeproperties.VoltageProperty;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public abstract class MultiAmperageMultiblockController extends RecipeMapMultiblockController {

    protected boolean areAllEnergyHatchesTheSameVoltage;
    protected List<IEnergyContainer> inputEnergyHatches;

    public MultiAmperageMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
        this.inputEnergyHatches = new ArrayList<>();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        inputEnergyHatches = this.getAbilities(MultiblockAbility.INPUT_ENERGY);
    }

    public void setAreAllEnergyHatchesTheSameVoltage() {
        areAllEnergyHatchesTheSameVoltage = inputEnergyHatches
                .stream()
                .map(IEnergyContainer::getInputVoltage)
                .distinct()
                .count() == 1;
    }

    public long getMaxInputAmperage() {
        return this.inputEnergyHatches.stream()
                .mapToLong(IEnergyContainer::getInputAmperage)
                .sum();
    }

    public boolean isVoltageTierValidForRecipe(Recipe recipe) {
        return areAllEnergyHatchesTheSameVoltage && this.inputEnergyHatches.get(0).getInputVoltage() == getRecipeVoltage(recipe);
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn,
                                               boolean checkMaintenance,
                                               boolean checkItemIn,
                                               boolean checkItemOut,
                                               boolean checkFluidIn,
                                               boolean checkFluidOut,
                                               boolean checkMuffler) {

        TraceabilityPredicate predicate = super.autoAbilities(checkEnergyIn, checkMaintenance, checkItemIn, checkItemOut, checkFluidIn, checkFluidOut, checkMuffler);

        if (checkEnergyIn) {
            predicate = predicate.or(abilities(MultiblockAbility.INPUT_ENERGY)
                    .setMinGlobalLimited(1)
                    .setPreviewCount(1));
        }
        return predicate;
    }

    @Override
    public boolean isStructureFormed() {
        setAreAllEnergyHatchesTheSameVoltage();
        return areAllEnergyHatchesTheSameVoltage && super.isStructureFormed();
    }

    public int getRecipeAmperage(Recipe recipe) {
        return recipe.getProperty(MultiAmperageProperty.getInstance(), 0);
    }

    public long getRecipeVoltage(Recipe recipe) {
        return GTValues.V[recipe.getProperty(VoltageProperty.getInstance(), 0)];
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return this.getMaxInputAmperage() >= getRecipeAmperage(recipe) && this.isVoltageTierValidForRecipe(recipe);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add((new TextComponentTranslation(String.format("Max input amperage: %d A", getMaxInputAmperage()))));
        super.addDisplayText(textList);
        if (!areAllEnergyHatchesTheSameVoltage) {
            textList.add((new TextComponentTranslation("tkcya.multiblock.same.energy_hatches"))
                    .setStyle((new Style()).setColor(TextFormatting.RED)));

        }
    }


}
