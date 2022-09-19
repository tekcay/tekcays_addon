package tekcays_addon.api.capability.impl;

import gregtech.api.capability.impl.RecipeLogicSteam;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.common.ConfigHolder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.IFluidTank;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.IHeatMachine;
import tekcays_addon.api.recipes.recipeproperties.HeatProperty;

import javax.annotation.Nonnull;

public class HeatSteamRecipeLogic extends RecipeLogicSteam {

    private int recipeHeat = 0;

    public HeatSteamRecipeLogic(MetaTileEntity tileEntity, RecipeMap<?> recipeMap, boolean isHighPressure, IFluidTank steamFluidTank, double conversionRate) {
        super(tileEntity, recipeMap, isHighPressure, steamFluidTank, conversionRate);
    }

    @Override
    protected void updateRecipeProgress() {
        // do not simulate heat so it keeps growing towards atmospheric
        if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true) && drawHeat(this.recipeHeat, false)) {
            if (++this.progressTime > this.maxProgressTime) {
                this.completeRecipe();
            }

            if (this.hasNotEnoughEnergy) {
                if (this.getEnergyInputPerSecond() > 19L * (long)this.recipeEUt) {
                    this.hasNotEnoughEnergy = false;
                }
            }
        } else if (this.recipeEUt > 0 || this.recipeHeat != 0) {
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

    protected boolean drawHeat(int heat, boolean simulate) {
        IHeatContainer container = this.getHeatContainer();
        if (container.changeHeat(heat, true)) {
            container.changeHeat(heat, simulate);
            return true;
        } else return false;
    }

    protected IHeatContainer getHeatContainer() {
        return ((IHeatMachine) this.metaTileEntity).getHeatContainer();
    }

    @Override
    protected void setupRecipe(Recipe recipe) {
        super.setupRecipe(recipe);
        if (recipe.getRecipePropertyStorage() != null && recipe.hasProperty(HeatProperty.getInstance())) {
            this.recipeHeat = recipe.getProperty(HeatProperty.getInstance(), 0);
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        if (this.progressTime > 0) {
            compound.setDouble("heat", this.recipeHeat);
        }
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        super.deserializeNBT(compound);
        if (this.progressTime > 0) {
            this.recipeHeat = compound.getInteger("heat");
        } else {
            this.recipeHeat = 0;
        }
    }
}
