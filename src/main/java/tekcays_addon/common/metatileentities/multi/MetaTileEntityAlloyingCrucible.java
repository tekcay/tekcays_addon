package tekcays_addon.common.metatileentities.multi;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import tekcays_addon.api.capability.impl.NoEnergyMultiblockLogic;
import tekcays_addon.api.metatileentity.multiblock.NoEnergyRecipeMapMultiBlockController;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.recipes.recipeproperties.NoEnergyTemperatureProperty;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockBrick;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;

import static tekcays_addon.api.utils.blastfurnace.BlastFurnaceUtils.*;

public class MetaTileEntityAlloyingCrucible extends NoEnergyRecipeMapMultiBlockController {

    private final BlockBrick.BrickType brick;
    private final IBlockState iBlockState;

    public MetaTileEntityAlloyingCrucible(ResourceLocation metaTileEntityId, BlockBrick.BrickType brick) {
        super(metaTileEntityId, TKCYARecipeMaps.ALLOYING_CRUCIBLE_RECIPES);
        this.recipeMapWorkable = new NoEnergyMultiblockLogic(this);
        this.brick = brick;
        this.iBlockState = TKCYAMetaBlocks.BLOCK_BRICK.getState(brick);
    }
    
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAlloyingCrucible(metaTileEntityId, brick);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return getBrickTexture(brick);
    }

    protected IBlockState getCasingState() {
        return iBlockState;
    }

    public BlockBrick.BrickType getBrick() {
        return this.brick;
    }


    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return brick.getBrickTemperature() >= recipe.getProperty(NoEnergyTemperatureProperty.getInstance(), 0);
    }
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return TKCYATextures.ALLOYING_CRUCIBLE_OVERLAY;
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.machine.brick_temperature.tooltip", brick.getBrickTemperature()));
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.FRONT, RelativeDirection.UP, RelativeDirection.RIGHT)
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "Y#X", "X#X")
                .aisle("XXX", "XXX", "XXX")
                .where('X', states(getCasingState())
                        .or(getOutputBrickFluidHatch(brick).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                        .or(getInputBrickFluidHatch(brick).setMinGlobalLimited(1).setMaxGlobalLimited(8)))
                .where('#', air())
                .where('Y', selfPredicate())
                .build();
    }


}
