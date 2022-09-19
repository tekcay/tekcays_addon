package tekcays_addon.api.capability.impl;

import gregicality.science.api.GCYSValues;
import gregtech.api.capability.impl.RecipeLogicSteam;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.common.ConfigHolder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.IFluidTank;
import tekcays_addon.api.capability.ILaserContainer;
import tekcays_addon.api.capability.ILaserMachine;
import tekcays_addon.api.capability.LaserProperty;

import javax.annotation.Nonnull;

public class LaserSteamRecipeLogic extends RecipeLogicSteam {

    private double recipeLaser = GCYSValues.EARTH_PRESSURE;

    public LaserSteamRecipeLogic(MetaTileEntity tileEntity, RecipeMap<?> recipeMap, boolean isHighLaser, IFluidTank steamFluidTank, double conversionRate) {
        super(tileEntity, recipeMap, isHighLaser, steamFluidTank, conversionRate);
    }

    @Override
    protected void updateRecipeProgress() {
        // do not simulate pressure so it keeps growing towards atmospheric
        if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true) && drawLaser(this.recipeLaser, false)) {
            if (++this.progressTime > this.maxProgressTime) {
                this.completeRecipe();
            }

            if (this.hasNotEnoughEnergy) {
                if (this.getEnergyInputPerSecond() > 19L * (long)this.recipeEUt) {
                    this.hasNotEnoughEnergy = false;
                }
            }
        } else if (this.recipeEUt > 0 || this.recipeLaser != GCYSValues.EARTH_PRESSURE) {
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

    protected boolean drawLaser(double pressure, boolean simulate) {
        ILaserContainer container = this.getLaserContainer();
        final double containerLaser = container.getLaser();
        double pressureToChange;

        // pressure changes by 1 percent per tick
        if (pressure != GCYSValues.EARTH_PRESSURE) pressureToChange = containerLaser * 0.01;
        else return true;

        if (pressure > GCYSValues.EARTH_PRESSURE) pressureToChange = -pressureToChange;

        final double newLaser = containerLaser + pressureToChange;
        // pressure must be within +/- 1 exponent of the target
        if (newLaser < pressure / 10 || newLaser > pressure * 10) {
            return false;
        }

        // P * V = n
        return container.changeParticles(pressureToChange * container.getVolume(), simulate);
    }

    protected ILaserContainer getLaserContainer() {
        return ((ILaserMachine) this.metaTileEntity).getLaserContainer();
    }

    @Override
    protected void setupRecipe(Recipe recipe) {
        super.setupRecipe(recipe);
        if (recipe.getRecipePropertyStorage() != null && recipe.hasProperty(LaserProperty.getInstance())) {
            this.recipeLaser = recipe.getProperty(LaserProperty.getInstance(), GCYSValues.EARTH_PRESSURE);
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        if (this.progressTime > 0) {
            compound.setDouble("pressure", this.recipeLaser);
        }
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        super.deserializeNBT(compound);
        if (this.progressTime > 0) {
            this.recipeLaser = compound.getDouble("pressure");
        } else {
            this.recipeLaser = GCYSValues.EARTH_PRESSURE;
        }
    }
}
