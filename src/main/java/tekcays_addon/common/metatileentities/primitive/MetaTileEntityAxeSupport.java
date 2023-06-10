package tekcays_addon.common.metatileentities.primitive;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.ibm.icu.impl.data.HolidayBundle_it_IT;
import gregtech.api.gui.ModularUI;
import gregtech.api.items.toolitem.ItemGTAxe;
import gregtech.api.items.toolitem.ToolClasses;
import gregtech.api.items.toolitem.ToolHelper;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.items.ToolItems;
import gregtech.core.sound.GTSoundEvents;
import gregtech.tools.ToolsModule;
import net.minecraft.block.Block;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.common.blocks.blocks.BlockCutWood;
import tekcays_addon.gtapi.render.TKCYATextures;
import tekcays_addon.gtapi.utils.TKCYALog;

import java.util.Set;

import static gregtech.api.capability.GregtechDataCodes.IS_WORKING;
import static net.minecraft.util.EnumFacing.DOWN;
import static net.minecraft.util.EnumFacing.UP;
import static tekcays_addon.api.consts.NBTKeys.HIT_NUMBER;
import static tekcays_addon.api.consts.NBTKeys.IS_RUNNING;


public class MetaTileEntityAxeSupport extends MetaTileEntity {

    private int hitNumber = 0;
    private final int HIT_NUMBER_TO_TRANSFORM = 5;

    public MetaTileEntityAxeSupport(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if (facing.equals(EnumFacing.UP))
            return super.onRightClick(playerIn, hand, facing, hitResult);

        if (!super.onRightClick(playerIn, hand, facing, hitResult)) {

            ItemStack itemStack = playerIn.getHeldItem(hand);
            if (!playerIn.isSneaking() || itemStack.isEmpty()) return true;

            Set<String> toolClasses = itemStack.getItem().getToolClasses(itemStack);
            if (toolClasses.isEmpty()) return true;

            TKCYALog.logger.info(toolClasses);
            if (!toolClasses.iterator().next().equals(ToolClasses.AXE)) return true;

            TKCYALog.logger.info("hitNumber = {}", hitNumber);

            ToolHelper.damageItem(itemStack, playerIn);
            return onAxeClick();
        }
        return false;
    }

    private void incrementAndTryDestroy(World world, BlockPos blockPos, BlockCutWood.CutWoodType cutWoodType) {
        hitNumber++;
        if (hitNumber == HIT_NUMBER_TO_TRANSFORM) {
            world.setBlockState(blockPos, TKCYAMetaBlocks.BLOCK_CUT_WOOD.getState(cutWoodType));
            hitNumber = 0;
        }
    }

    public boolean onAxeClick() {

        World world = this.getWorld();
        BlockPos blockPos = getPos().offset(EnumFacing.UP);

        IBlockState block = world.getBlockState(blockPos);
        if (block.equals(Blocks.AIR.getDefaultState())) return true;

        if (block.equals(Blocks.LOG2.getDefaultState())) {
            incrementAndTryDestroy(world, blockPos, BlockCutWood.CutWoodType.ACACIA);
            return true;
        } else if (block.equals(Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK))) {
            incrementAndTryDestroy(world, blockPos, BlockCutWood.CutWoodType.OAK);
            return true;
        } else if (block.equals(Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE))) {
            incrementAndTryDestroy(world, blockPos, BlockCutWood.CutWoodType.JUNGLE);
            return true;
        } else if (block.equals(Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH))) {
            incrementAndTryDestroy(world, blockPos, BlockCutWood.CutWoodType.BIRCH);
            return true;
        } else if (block.equals(Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE))) {
            incrementAndTryDestroy(world, blockPos, BlockCutWood.CutWoodType.SPRUCE);
            return true;
        } else if (block.equals(MetaBlocks.RUBBER_LOG.getDefaultState())) {
            incrementAndTryDestroy(world, blockPos, BlockCutWood.CutWoodType.RUBBER);
            return true;
        }

        hitNumber = 0;
        return true;
    }

    @SideOnly(Side.CLIENT)
    protected SimpleOverlayRenderer getBaseRenderer() {
        return TKCYATextures.STEAM_CASING[1];
    }

    /*
    @Override
    @SideOnly(Side.CLIENT)
    public SoundEvent getSound() {
        return ToolItems.AXE.getSound();
    }

     */

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        ColourMultiplier multiplier = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        colouredPipeline = ArrayUtils.add(pipeline, multiplier);
        TKCYATextures.HEAT_ACCEPTOR_VERTICALS_OVERLAY.renderSided(UP, renderState, translation, pipeline);
        TKCYATextures.BRICKS[BlockBrick.BrickType.BRICK.getTextureId()].renderSided(DOWN, renderState, translation, pipeline);
        TKCYATextures.HALF_BRICK.renderSided(getFrontFacing().getOpposite(), renderState, translation, pipeline);
        TKCYATextures.HALF_BRICK.renderSided(getFrontFacing().rotateY(), renderState, translation, pipeline);
        TKCYATextures.HALF_BRICK.renderSided(getFrontFacing().rotateYCCW(), renderState, translation, pipeline);
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

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger(HIT_NUMBER, hitNumber);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.hitNumber = data.getInteger(HIT_NUMBER);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.hitNumber);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.hitNumber = buf.readInt();
    }

}
