package tekcays_addon.common.metatileentities.primitive;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import gregtech.api.gui.ModularUI;
import gregtech.api.items.toolitem.ToolClasses;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockSteamCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static gregtech.api.util.RelativeDirection.*;

public class MetaTileEntityAxeSupport extends MultiblockControllerBase {


    public MetaTileEntityAxeSupport(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    private IBlockState getCasingState() {
        return MetaBlocks.STEAM_CASING. getDefaultState();
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("X")
                .aisle("S")
                .where('S', selfPredicate())
                .where('X', states(MetaBlocks.STEAM_CASING.getState(BlockSteamCasing.SteamCasingType.PUMP_DECK)))
                .build();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    protected void updateFormedValid() {

    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        ItemStack itemStack = playerIn.getHeldItem(hand);
        if (!playerIn.isSneaking() || itemStack.isEmpty()) return true;


        Set<String> toolClasses = itemStack.getItem().getToolClasses(itemStack);
        if (toolClasses.isEmpty()) return true;
        if (!toolClasses.iterator().next().equals(ToolClasses.AXE)) return true;


        if (this.onToolClick(playerIn, new HashSet<>(Collections.singleton(ToolClasses.AXE)), hand, hitResult)) {
            return false;
        }
        //onAxeClick(playerIn, hand, facing, hitResult);
        return true;

    }

    /*
    public boolean onAxeClick(EntityPlayer playerIn, EnumHand hand, EnumFacing wrenchSide, CuboidRayTraceResult hitResult) {
        this.metaTileEntityId
    }

     */

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.WOOD_WALL;
    }

    @Override
    public boolean shouldRenderOverlay(IMultiblockPart sourcePart) {
        return false;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAxeSupport(metaTileEntityId);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return false;
    }

}
