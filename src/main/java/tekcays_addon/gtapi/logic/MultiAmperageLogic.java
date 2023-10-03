package tekcays_addon.gtapi.logic;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.EnumFacing;
import tekcays_addon.gtapi.metatileentity.multiblock.MultiAmperageMultiblockController;
import tekcays_addon.gtapi.recipes.builders.MultiAmperageRecipeBuilder;
import tekcays_addon.gtapi.recipes.recipeproperties.MultiAmperageProperty;
import tekcays_addon.gtapi.recipes.recipeproperties.VoltageProperty;
import tekcays_addon.gtapi.utils.TKCYALog;

import javax.annotation.Nonnull;

import static gregtech.api.util.RelativeDirection.FRONT;

/**
 * A {@link MultiblockRecipeLogic} that works only with recipes built with {@link MultiAmperageRecipeBuilder}.
 * Recipes will drain energy with a specific {@code voltage} and {@code amperage}.
 * <br>
 * <br>
 * <b>!!IMPORTANT!!</b>: overclock is <b>disabled!</b>
 *  <br>
 *  <br>
 */
@Setter
@Getter
public class MultiAmperageLogic extends MultiblockRecipeLogic {

    public MultiAmperageLogic(MultiAmperageMultiblockController tileEntity) {
        super(tileEntity);
    }

    protected int toConsumeEUt;
    protected int recipeAmperage;
    protected int recipeVoltage;

    /**
     * Overclock is disabled.
     * @param recipe the recipe to run
     * @return
     */
    @Nonnull
    @Override
    protected int[] calculateOverclock(Recipe recipe) {
        return new int[]{0, recipe.getDuration()};
    }

    public long getAmperage() {
        return this.getEnergyContainer().getInputAmperage();
    }

    public long getVoltage() {
        RecipeMapMultiblockController controller = (RecipeMapMultiblockController) metaTileEntity;
        return controller.getEnergyContainer().getInputVoltage();
    }

    public int getRecipeAmperage(Recipe recipe) {
        return recipe.getProperty(MultiAmperageProperty.getInstance(), 0);
    }

    public int getRecipeVoltage(Recipe recipe) {
        return recipe.getProperty(VoltageProperty.getInstance(), 0);
    }

    public boolean isAmperageValid(Recipe recipe) {
        return getAmperage() >= getRecipeAmperage(recipe);
    }

    public boolean isVoltageValid(Recipe recipe) {
        return getVoltage() >= getRecipeVoltage(recipe);
    }

    public int getEUt(Recipe recipe) {
        return getRecipeVoltage(recipe) * getRecipeAmperage(recipe);
    }

    @Override
    public void update() {
        if (metaTileEntity.getOffsetTimer() % 20 == 0) {
            TKCYALog.logger.info("Voltage : " + getVoltage());
            TKCYALog.logger.info("Amperage : " + getAmperage());
            TKCYALog.logger.info("RecipeAmperage : " + getRecipeAmperage());
            TKCYALog.logger.info("RecipeVoltage : " + getRecipeVoltage());
            TKCYALog.logger.info("toConsumeEUt : " + getToConsumeEUt());
        }

        if (toConsumeEUt == 0) return;

        if (drawEnergy(toConsumeEUt, true)) {
            TKCYALog.logger.info("it did drain");
            drawEnergy(toConsumeEUt, false);
        } else invalidate();
    }


    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe) {
        setRecipeAmperage(getRecipeAmperage(recipe));
        setRecipeVoltage(getRecipeVoltage(recipe));
        setToConsumeEUt(getEUt(recipe));
        return isAmperageValid(recipe) && isVoltageValid(recipe);
    }

}
