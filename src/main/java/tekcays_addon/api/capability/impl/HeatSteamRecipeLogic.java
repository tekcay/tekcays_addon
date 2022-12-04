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
