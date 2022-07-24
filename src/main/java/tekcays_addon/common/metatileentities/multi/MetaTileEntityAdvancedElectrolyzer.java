package tekcays_addon.common.metatileentities.multi;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.Recipe;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.api.capability.impl.MultiblockNoEnergyRecipeLogic;
import tekcays_addon.api.metatileentity.mutiblock.RecipeMapMultiblockNoEnergyController;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.utils.TKCYALog;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MetaTileEntityAdvancedElectrolyzer extends RecipeMapMultiblockController {

    List<CountableIngredient> inputs;
    HashSet<Ingredient> currentRecipeNonConsummIngredient = new HashSet<>();
    HashSet<Ingredient> nonConsummInInventory = new HashSet<>();

    //TODO Proper blocks and textures
    //TODO Temperature logic, based on electricity
    //TODO Add Aluminium and Zinc Chains as electrolysis is available

    public MetaTileEntityAdvancedElectrolyzer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.ELECTROLYSIS);
        this.recipeMapWorkable = new AdvancedElectrolyzerLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAdvancedElectrolyzer(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() { //TODO MutiblockStructure
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "X#X", "XXX")
                .aisle("XXX", "XSX", "XXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(14).or(autoAbilities()))
                .where('#', air())
                .build();
    }

    public void getCurrentRecipeElectrodes() {

        currentRecipeNonConsummIngredient.clear();
        for (CountableIngredient ingredient : inputs) {
            if (!ingredient.isNonConsumable()) continue;
            Ingredient ing = ingredient.getIngredient();

            currentRecipeNonConsummIngredient.add(ing);
        }
    }

    public void getCurrentInventory() {

        nonConsummInInventory.clear();
        for (int i = 0; i < inputInventory.getSlots(); i++) {
            ItemStack stack = inputInventory.getStackInSlot(i);
            if (stack == null) continue;
            nonConsummInInventory.add(Ingredient.fromStacks(stack));
        }
    }





    @Override
    public void updateFormedValid() {
        super.updateFormedValid();

        if (this.isActive()) {
            getCurrentRecipeElectrodes();
            getCurrentInventory();

            if (!nonConsummInInventory.containsAll(currentRecipeNonConsummIngredient)) this.causeMaintenanceProblems();

            if (getOffsetTimer() % 20 == 0) {
                damageElectrode(20);
            }
        }
    }

    private void damageElectrode(int damageAmount) {
        for (int i = 0; i < inputInventory.getSlots(); i++) {
            ItemStack electrodeStack;

            if (inputInventory.isItemValid(i, TKCYAMetaItems.ELECTRODE.getStackForm())) {
                electrodeStack = inputInventory.getStackInSlot(i);

                ElectrodeBehavior behavior = ElectrodeBehavior.getInstanceFor(electrodeStack);
                if (behavior == null) continue;
                behavior.applyElectrodeDamage(electrodeStack, damageAmount);
            }
        }
    }


    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.FROST_PROOF_CASING;
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(MetalCasingType.ALUMINIUM_FROSTPROOF);
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.VACUUM_FREEZER_OVERLAY;
    }

    ////////////////
    //CheckRecipe
    /////////////////

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        inputs = recipe.getInputs();
        return true;
    }


    ////////////////
    //Remove overlocking
    /////////////////

    private static class AdvancedElectrolyzerLogic extends MultiblockRecipeLogic {

        public AdvancedElectrolyzerLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        protected int[] calculateOverclock(Recipe recipe) {
            return new int[]{0, recipe.getDuration()};
        }


    }

}