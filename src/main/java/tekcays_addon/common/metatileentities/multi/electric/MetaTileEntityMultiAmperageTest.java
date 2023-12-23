package tekcays_addon.common.metatileentities.multi.electric;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import tekcays_addon.api.metatileentity.LogicType;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockLargeMultiblockCasing;
import tekcays_addon.gtapi.metatileentity.multiblock.ModulableRecipeMapController;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.render.TKCYATextures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static tekcays_addon.api.metatileentity.MultiAmperageControllerMethods.getMaxInputAmperage;

public class MetaTileEntityMultiAmperageTest extends ModulableRecipeMapController {

    public MetaTileEntityMultiAmperageTest(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.MULTI_AMPERAGE_RECIPE_BUILDER_RECIPE_MAP, LogicType.MULTI_AMPER, LogicType.NO_OVERCLOCK);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMultiAmperageTest(metaTileEntityId);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "X#X", "XXX")
                .aisle("XXX", "XSX", "XXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(16).or(autoAbilities()))
                .where('#', air())
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return TKCYATextures.MONEL;
    }

    protected IBlockState getCasingState() {
        return TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.MONEL_CASING);
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.VACUUM_FREEZER_OVERLAY;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add((new TextComponentTranslation(String.format("Max input amperage: %d A", getMaxInputAmperage(this.inputEnergyHatches)))));
        super.addDisplayText(textList);
        if (!areAllEnergyHatchesTheSameVoltage) {
            textList.add((new TextComponentTranslation("tkcya.multiblock.same.energy_hatches"))
                    .setStyle((new Style()).setColor(TextFormatting.RED)));

        }
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.machine.no_overclock.tooltip"));
        tooltip.add(I18n.format("tekcays_addon.machine.advanced_electrolyzer.tooltip.1"));
        tooltip.add(I18n.format("tekcays_addon.machine.advanced_electrolyzer.tooltip.2"));
        tooltip.add(I18n.format("tekcays_addon.machine.advanced_electrolyzer.tooltip.3"));
        tooltip.add(I18n.format("tekcays_addon.machine.advanced_electrolyzer.tooltip.4"));
    }



}
