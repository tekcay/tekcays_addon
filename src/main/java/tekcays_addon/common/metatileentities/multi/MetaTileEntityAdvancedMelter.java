package tekcays_addon.common.metatileentities.multi;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import tekcays_addon.api.metatileentity.multiblock.HeatContainerListMultiblockController;
import tekcays_addon.api.recipes.TKCYARecipeMaps;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;


public class MetaTileEntityAdvancedMelter extends HeatContainerListMultiblockController {



    public MetaTileEntityAdvancedMelter(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.HEATING);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAdvancedMelter(metaTileEntityId);
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



    @Override
    public void updateFormedValid() {
        super.updateFormedValid();
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
    public void addInformation(@Nonnull ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("tekcays_addon.machine.advanced_electrolyzer.tooltip.1"));
        tooltip.add(I18n.format("tekcays_addon.machine.advanced_electrolyzer.tooltip.2"));
        tooltip.add(I18n.format("tekcays_addon.machine.advanced_electrolyzer.tooltip.3"));
        tooltip.add(I18n.format("tekcays_addon.machine.advanced_electrolyzer.tooltip.4"));
    }



}
