package tekcays_addon.common.metatileentities.multi;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MetaTileEntityAdvancedElectrolyzer extends RecipeMapMultiblockController {

    private ItemStack electrodeStack;

    public MetaTileEntityAdvancedElectrolyzer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.ELECTROLYSIS);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAdvancedElectrolyzer(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
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
        getElectrodeStack();

        if (this.isActive()) {

            if (getOffsetTimer() % 20 == 0)
                damageElectrode(20);
            }
    }

    private ItemStack getElectrodeStack() {
        for (int i = 0; i < inputInventory.getSlots(); i++) {
            if (inputInventory.isItemValid(i, TKCYAMetaItems.ELECTRODE.getStackForm()))
                return electrodeStack = inputInventory.getStackInSlot(i);
        } return null;
    }

    @Nullable
    private ElectrodeBehavior getElectrodeBehavior() {

        if (electrodeStack == null) return null;
        ItemStack stack = electrodeStack;

        return ElectrodeBehavior.getInstanceFor(stack);
    }

    private void damageElectrode (int damageAmount) {
        if (electrodeStack == null) return;
        getElectrodeBehavior().applyElectrodeDamage(electrodeStack, damageAmount);
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
}
