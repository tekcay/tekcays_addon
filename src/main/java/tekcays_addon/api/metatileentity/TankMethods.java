package tekcays_addon.api.metatileentity;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.blocks.MetaBlocks.STEAM_CASING;
import static gregtech.common.metatileentities.MetaTileEntities.WOODEN_TANK_VALVE;
import static tekcays_addon.common.blocks.TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING;
import static tekcays_addon.common.metatileentities.TKCYAMetaTileEntities.*;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.GalvanizedSteel;

import net.minecraft.block.state.IBlockState;

import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.unification.material.Material;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockSteamCasing;
import tekcays_addon.common.blocks.blocks.BlockLargeMultiblockCasing;
import tekcays_addon.gtapi.render.TKCYATextures;

public class TankMethods {

    @Nullable
    public static MetaTileEntity getTankValve(Material material) {
        if (material.equals(TreatedWood)) return WOODEN_TANK_VALVE;
        if (material.equals(Steel)) return STEEL_TANK_VALVE;
        if (material.equals(GalvanizedSteel)) return GALVANIZED_STEEL_TANK_VALVE;
        if (material.equals(StainlessSteel)) return STAINLESS_STEEL_TANK_VALVE;
        return null;
    }

    @Nullable
    public static MetaTileEntity getCrateValve(Material material) {
        if (material.equals(TreatedWood)) return TREATED_WOOD_CRATE_VALVE;
        if (material.equals(Steel)) return STEEL_CRATE_VALVE;
        if (material.equals(GalvanizedSteel)) return GALVANIZED_STEEL_CRATE_VALVE;
        if (material.equals(StainlessSteel)) return STAINLESS_STEEL_CRATE_VALVE;
        return null;
    }

    @Nullable
    public static ICubeRenderer getBaseTextureForTank(Material material) {
        if (material.equals(TreatedWood)) return Textures.WOOD_WALL;
        if (material.equals(Steel)) return TKCYATextures.STEEL_GT;
        if (material.equals(GalvanizedSteel)) return TKCYATextures.WHITE_GT;
        if (material.equals(StainlessSteel)) return TKCYATextures.STAINLESS_STEEL_GT;
        return null;
    }

    @Nullable
    public static IBlockState getBlockState(Material material) {
        if (material.equals(TreatedWood)) return STEAM_CASING.getState(BlockSteamCasing.SteamCasingType.WOOD_WALL);
        if (material.equals(Steel))
            return LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.STEEL_WALL);
        if (material.equals(GalvanizedSteel))
            return LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.GALVANIZED_STEEL_WALL);
        if (material.equals(StainlessSteel))
            return LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.STAINLESS_STEEL_WALL);
        return null;
    }

    /*
     * @Nullable
     * public static int[] getMinMaxPressure(Material material) {
     * if (material.equals(Steel)) return new int[]{0, 20};
     * if (material.equals(GalvanizedSteel)) return new int[]{0, 100};
     * if (material.equals(StainlessSteel)) return new int[]{0, 200};
     * return null;
     * }
     * 
     * 
     * public static boolean canContainThisFluid(FluidStack fluid, Material material) {
     * 
     * if (!canHandleFluidTemperature(fluid.getFluid(), material)) return false;
     * 
     * FluidState fluidState = getFluidState(fluid.getFluid());
     * 
     * if (fluidState.equals(LIQUID)) return true;
     * 
     * return fluidState.equals(ACID) && material != TreatedWood;
     * }
     * 
     * private static FluidState getFluidState(Fluid fluid) {
     * if (!(fluid instanceof GTFluidMaterial)) return LIQUID;
     * return (((GTFluidMaterial) fluid).getFluidType());
     * }
     * 
     * private static boolean canHandleFluidTemperature(Fluid fluid, Material material) {
     * return getMaterialFromFluid(fluid).getFluid().getTemperature() < material.getFluid().getTemperature();
     * }
     * 
     * /*
     * public static FluidTank createFilteredFluidHandler(int capacity, Material material) {
     * 
     * return new FilteredFluidHandler(capacity).setFilter(
     * fluidStack -> canContainThisFluid(fluidStack, material));
     * }
     * 
     * /*
     * public static ItemStackHandler createFilteredItemHandler(int capacity) {
     * 
     * return new FilteredItemHandler(capacity);
     * }
     * 
     */

    public static void createPressurizedFilteredFluidHandler(int capacity, Material material) {}
}
