package tekcays_addon.gtapi.logic;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.IFluidTank;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.RecipeLogicSteam;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import tekcays_addon.gtapi.capability.containers.IHeatContainer;
import tekcays_addon.gtapi.capability.machines.IHeatMachine;
import tekcays_addon.gtapi.recipes.recipeproperties.HeatProperty;

public class HeatSteamRecipeLogic extends RecipeLogicSteam {

    private int recipeHeat = 0;

    public HeatSteamRecipeLogic(MetaTileEntity tileEntity, RecipeMap<?> recipeMap, boolean isHighPressure,
                                IFluidTank steamFluidTank, double conversionRate) {
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

    @NotNull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        if (this.progressTime > 0) {
            compound.setDouble("heat", this.recipeHeat);
        }
        return compound;
    }

    @Override
    public void deserializeNBT(@NotNull NBTTagCompound compound) {
        super.deserializeNBT(compound);
        if (this.progressTime > 0) {
            this.recipeHeat = compound.getInteger("heat");
        } else {
            this.recipeHeat = 0;
        }
    }
}
