package tekcays_addon.api.capability.impl;

import gregicality.science.api.GCYSValues;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;
import gregtech.common.ConfigHolder;
import net.minecraft.nbt.NBTTagCompound;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.IHeatMachine;
import tekcays_addon.api.recipes.recipeproperties.HeatProperty;

import javax.annotation.Nonnull;

public class HeatMultiblockRecipeLogic extends MultiblockRecipeLogic {

    private double recipeHeat = GCYSValues.EARTH_PRESSURE;

    public HeatMultiblockRecipeLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity);
    }

    @Override
    protected void updateRecipeProgress() {
        // do not simulate pressure so it keeps growing towards atmospheric
        if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true) && drawHeat(this.recipeHeat, false)) {
            if (++this.progressTime > this.maxProgressTime) {
                this.completeRecipe();
            }

            if (this.hasNotEnoughEnergy) {
                if (this.getEnergyInputPerSecond() > 19L * (long)this.recipeEUt) {
                    this.hasNotEnoughEnergy = false;
                }
            }
        } else if (this.recipeEUt > 0 || this.recipeHeat != GCYSValues.EARTH_PRESSURE) {
            this.hasNotEnoughEnergy = true;
            if (this.progressTime >= 2) {
                if (ConfigHolder.machines.recipeProgressLowEnergy) {
                    this.progressTime = 1;
                } else {
                    this.progressTime = Math.max(1, this.progressTime - 2);
                }
            }
        }
    }

    protected boolean drawHeat(double pressure, boolean simulate) {
        IHeatContainer container = this.getHeatContainer();
        final double containerHeat = container.getHeat();
        double pressureToChange;

        // pressure changes by 1 percent per tick
        if (pressure != GCYSValues.EARTH_PRESSURE) pressureToChange = containerHeat * 0.01;
        else return true;

        if (pressure > GCYSValues.EARTH_PRESSURE) pressureToChange = -pressureToChange;

        final double newHeat = containerHeat + pressureToChange;
        // pressure must be within +/- 1 exponent of the target
        if (newHeat < pressure / 10 || newHeat > pressure * 10) {
            return false;
        }

        // P * V = n
        return container.changeParticles(pressureToChange * container.getVolume(), simulate);
    }

    protected IHeatContainer getHeatContainer() {
        return ((IHeatMachine) this.metaTileEntity).getHeatContainer();
    }

    @Override
    protected void setupRecipe(Recipe recipe) {
        super.setupRecipe(recipe);
        if (recipe.getRecipePropertyStorage() != null && recipe.hasProperty(HeatProperty.getInstance())) {
            this.recipeHeat = recipe.getProperty(HeatProperty.getInstance(), GCYSValues.EARTH_PRESSURE);
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        if (this.progressTime > 0) {
            compound.setDouble("pressure", this.recipeHeat);
        }
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        super.deserializeNBT(compound);
        if (this.progressTime > 0) {
            this.recipeHeat = compound.getDouble("pressure");
        } else {
            this.recipeHeat = GCYSValues.EARTH_PRESSURE;
        }
    }
}
