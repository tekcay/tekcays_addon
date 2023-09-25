package tekcays_addon.api.metatileentity;

import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FilteredItemHandler;
import gregtech.api.fluids.MaterialFluid;
import gregtech.api.fluids.MetaFluids;
import gregtech.api.fluids.fluidType.FluidType;
import gregtech.api.fluids.fluidType.FluidTypes;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.unification.material.Material;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockSteamCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import tekcays_addon.common.blocks.blocks.BlockLargeMultiblockCasing;
import tekcays_addon.gtapi.render.TKCYATextures;

import javax.annotation.Nullable;

import static gregtech.api.fluids.MetaFluids.getMaterialFromFluid;
import static gregtech.api.fluids.fluidType.FluidTypes.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.blocks.MetaBlocks.STEAM_CASING;
import static gregtech.common.metatileentities.MetaTileEntities.WOODEN_TANK_VALVE;
import static tekcays_addon.common.blocks.TKCYAMetaBlocks.*;
import static tekcays_addon.common.metatileentities.TKCYAMetaTileEntities.*;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.GalvanizedSteel;

public class TankMethods {

    @Nullable
    public static MetaTileEntity getValve(Material material) {
        if (material.equals(TreatedWood)) return WOODEN_TANK_VALVE;
        if (material.equals(Steel)) return STEEL_TANK_VALVE;
        if (material.equals(GalvanizedSteel)) return GALVANIZED_STEEL_TANK_VALVE;
        if (material.equals(StainlessSteel)) return STAINLESS_STEEL_TANK_VALVE;
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
        if (material.equals(Steel)) return LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.STEEL_WALL);
        if (material.equals(GalvanizedSteel)) return LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.GALVANIZED_STEEL_WALL);
        if (material.equals(StainlessSteel)) return LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.STAINLESS_STEEL_WALL);
        return null;
    }

    @Nullable
    public static int[] getMinMaxPressure(Material material) {
        if (material.equals(Steel)) return new int[]{0, 20};
        if (material.equals(GalvanizedSteel)) return new int[]{0, 100};
        if (material.equals(StainlessSteel)) return new int[]{0, 200};
        return null;
    }

    public static boolean canContainThisFluid(FluidStack fluid, Material material) {

        if (!canHandleFluidTemperature(fluid.getFluid(), material)) return false;

        FluidType fluidType = getFluidType(fluid.getFluid());

        if (fluidType.equals(LIQUID)) return true;

        return fluidType.equals(ACID) && material != TreatedWood;
    }

    private static FluidType getFluidType(Fluid fluid) {
        if (!(fluid instanceof MaterialFluid)) return LIQUID;
        return (((MaterialFluid) fluid).getFluidType());
    }

    private static boolean canHandleFluidTemperature(Fluid fluid, Material material) {
        return getMaterialFromFluid(fluid).getFluid().getTemperature() < material.getFluid().getTemperature();
    }

    public static FluidTank createFilteredFluidHandler(int capacity, Material material) {

        return new FilteredFluidHandler(capacity).setFilter(
                fluidStack -> canContainThisFluid(fluidStack, material));
    }
    public static ItemStackHandler createFilteredItemHandler(int capacity) {

        return new FilteredItemHandler(capacity);
    }

    public static void createPressurizedFilteredFluidHandler(int capacity, Material material) {

    }





}
