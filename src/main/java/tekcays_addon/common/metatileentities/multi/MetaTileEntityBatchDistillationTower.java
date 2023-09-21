package tekcays_addon.common.metatileentities.multi;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockLargeMultiblockCasing;
import tekcays_addon.gtapi.render.TKCYATextures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.util.RelativeDirection.*;

public class MetaTileEntityBatchDistillationTower extends RecipeMapMultiblockController {

    public MetaTileEntityBatchDistillationTower(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.DISTILLATION_RECIPES);
        //super(metaTileEntityId, TKCYARecipeMaps.SPIRAL_SEPARATION);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityBatchDistillationTower(metaTileEntityId);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("    ", " MA ", " AA ", "    ")
                .aisle("    ", " AA ", " LA ", "    ")
                .aisle("    ", " CC ", " CC ", "    ").setRepeatable(6)
                .aisle("FFFF", "FCCF", "FLCF", "F FF")
                .aisle("    ", " CC ", " CC ", "    ").setRepeatable(6)
                .aisle("FFFF", "FCCF", "FLCF", "F FF")
                .aisle("    ", " CC ", " CC ", "    ").setRepeatable(6)
                .aisle("FFFF", "FCCF", "FLCF", "F FF")
                .aisle("    ", " CC ", " CC ", "    ").setRepeatable(6)
                .aisle("    ", " IC ", " LC ", "    ")
                .where('A', states(getCasingState())
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1)
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setExactLimit(2))))
                .where('M', selfPredicate())
                .where('F', states(getFrameState()))
                .where('C', states(getCasingState()))
                .where('L', abilities(MultiblockAbility.EXPORT_FLUIDS))
                .where('I', abilities(MultiblockAbility.IMPORT_FLUIDS))
                .where('#', air())
                .where(' ', any())
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return TKCYATextures.WHITE_GT;
    }

    protected IBlockState getCasingState() {
        return TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.GALVANIZED_STEEL_WALL);
    }

    protected IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.CRACKING_UNIT_OVERLAY;
    }


}
