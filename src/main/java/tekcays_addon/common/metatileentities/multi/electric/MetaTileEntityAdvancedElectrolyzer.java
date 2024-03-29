package tekcays_addon.common.metatileentities.multi.electric;

import static tekcays_addon.gtapi.logic.DamageItemsLogic.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockLargeMultiblockCasing;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.recipes.recipeproperties.MultiAmperageProperty;
import tekcays_addon.gtapi.render.TKCYATextures;

public class MetaTileEntityAdvancedElectrolyzer extends RecipeMapMultiblockController {

    private List<GTRecipeInput> inputs = new ArrayList<>();
    private final List<ItemStack> electrodeInInventory = new ArrayList<>();
    private final HashSet<String> currentRecipeNonConsummIngredient = new HashSet<>();
    private final HashSet<String> nonConsummInInventory = new HashSet<>();
    private int recipeEUt;
    private long recipeAmperage;
    private long currentAmperage;
    private Recipe currentRecipe;

    // TODO Proper blocks and textures

    public MetaTileEntityAdvancedElectrolyzer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.ELECTROLYSIS);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAdvancedElectrolyzer(metaTileEntityId);
    }

    private List<String> getImportItemsName() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < itemInventory.getSlots(); i++) {
            names.add(itemInventory.getStackInSlot(i).getDisplayName());
        }
        return names;
    }

    private List<String> getImportFluidName() {
        List<String> names = new ArrayList<>();
        importFluids.getFluidTanks().forEach(iFluidTank -> {
            if (iFluidTank.equals(null) || iFluidTank.getFluid().equals(null)) names.add("rien");
            names.add(iFluidTank.getFluid().getLocalizedName());
        });
        return names;
    }

    private void setCurrentRecipe() {
        currentRecipe = this.recipeMap.findRecipe(getEnergyContainer().getInputVoltage(), getImportItems(),
                getInputFluidInventory());
        if (currentRecipe != null) {
            recipeAmperage = currentRecipe.getProperty(MultiAmperageProperty.getInstance(), 0);
            recipeEUt = currentRecipe.getEUt();
        }
    }

    @NotNull
    @Override
    protected BlockPattern createStructurePattern() { // TODO MutiblockStructure
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "X#X", "XXX")
                .aisle("XXX", "XSX", "XXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(14).or(autoAbilities()))
                .where('#', air())
                .build();
    }

    private void getElectrodeFromInventory() {
        electrodeInInventory.clear();

        for (int i = 0; i < inputInventory.getSlots(); i++) {
            ItemStack electrodeStack;

            if (inputInventory.isItemValid(i, TKCYAMetaItems.ELECTRODE.getStackForm())) {
                electrodeStack = inputInventory.getStackInSlot(i);

                ElectrodeBehavior behavior = ElectrodeBehavior.getInstanceFor(electrodeStack);
                if (behavior == null) continue;
                electrodeInInventory.add(electrodeStack);
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if (!this.isActive()) {
            setCurrentRecipe();
            if (currentRecipe == null) return;

        } else {
            if (currentRecipe == null) return;
            currentAmperage = getEnergyContainer().getInputAmperage();
            if (currentAmperage < recipeAmperage) {
                invalidate();
                return;
            }

            if (!drawEnergy(true)) {
                invalidate();
                return;
            }
            drawEnergy(false);

        }
    }

    public boolean drawEnergy(boolean simulate) {
        long resultEnergy = getEnergyContainer().getEnergyStored() - (recipeEUt * recipeAmperage);
        if (resultEnergy >= 0L && resultEnergy <= getEnergyContainer().getEnergyCapacity()) {
            if (!simulate) getEnergyContainer().changeEnergy(-recipeEUt * recipeAmperage);
            return true;
        } else return false;
    }

    @Override
    public void updateFormedValid() {
        super.updateFormedValid();

        if (!this.recipeMapWorkable.isActive()) return;
        if (inputs == null) return;

        getCurrentRecipeNonConsummables(currentRecipeNonConsummIngredient, inputs);
        getCurrentInventory(nonConsummInInventory, inputInventory);

        if (!doesInventoryContainDamageableItems(nonConsummInInventory, currentRecipeNonConsummIngredient))
            this.doExplosion(1);

        if (getOffsetTimer() % 20 == 0) {
            applyDamage(inputInventory, 20);
        }
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return TKCYATextures.MONEL;
    }

    protected IBlockState getCasingState() {
        return TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.MONEL_CASING);
    }

    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.VACUUM_FREEZER_OVERLAY;
    }

    ////////////////
    // CheckRecipe
    /////////////////

    /*
     * @Override
     * public boolean checkRecipe(@NotNull Recipe recipe, boolean consumeIfSuccess) {
     * if (inputs != recipe.getInputs()) return false;
     * return recipe.getProperty(MultiAmperageProperty.getInstance(), TKCYAValues.EMPTY_INT_TWO_ARRAY) >=
     * getEnergyContainer().getInputAmperage();
     * }
     * 
     */

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.machine.no_overclock.tooltip"));
        tooltip.add(I18n.format("tekcays_addon.machine.advanced_electrolyzer.tooltip.1"));
        tooltip.add(I18n.format("tekcays_addon.machine.advanced_electrolyzer.tooltip.2"));
        tooltip.add(I18n.format("tekcays_addon.machine.advanced_electrolyzer.tooltip.3"));
        tooltip.add(I18n.format("tekcays_addon.machine.advanced_electrolyzer.tooltip.4"));
    }
}
