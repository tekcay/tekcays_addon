package tekcays_addon.common.metatileentities.multi;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
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
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;

import static tekcays_addon.api.capability.impl.DamageItemsLogic.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class MetaTileEntityAdvancedElectrolyzer extends RecipeMapMultiblockController {

    private List<GTRecipeInput> inputs;
    private final List<ItemStack> electrodeInInventory = new ArrayList<>();
    private final HashSet<String> currentRecipeNonConsummIngredient = new HashSet<>();
    private final HashSet<String> nonConsummInInventory = new HashSet<>();



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
    public void updateFormedValid() {
        super.updateFormedValid();

        if (this.isActive()) {
            getCurrentRecipeNonConsummables(currentRecipeNonConsummIngredient, inputs);
            getCurrentInventory(nonConsummInInventory, inputInventory);
            //getElectrodeFromInventory();
            //if (!currentRecipeNonConsummIngredient.stream().allMatch(nonConsummInInventory::contains)) this.causeMaintenanceProblems();

            if (!doesInventoryContainRequiredItem(nonConsummInInventory, currentRecipeNonConsummIngredient)) this.doExplosion(1);

            if (getOffsetTimer() % 20 == 0) {
                applyDamage(inputInventory, 20);
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

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("tekcays_addon.machine.advanced_electrolyzer.tooltip.1"));
        tooltip.add(I18n.format("tekcays_addon.machine.advanced_electrolyzer.tooltip.2"));
        tooltip.add(I18n.format("tekcays_addon.machine.advanced_electrolyzer.tooltip.3"));
        tooltip.add(I18n.format("tekcays_addon.machine.advanced_electrolyzer.tooltip.4"));
    }

}
