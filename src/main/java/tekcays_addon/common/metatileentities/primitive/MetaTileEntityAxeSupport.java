package tekcays_addon.common.metatileentities.primitive;

import static tekcays_addon.api.consts.NBTKeys.HIT_NUMBER;

import java.util.List;
import java.util.Set;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.ModularUI;
import gregtech.api.items.toolitem.ToolClasses;
import gregtech.api.items.toolitem.ToolHelper;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockCutWood;
import tekcays_addon.gtapi.render.TKCYATextures;

public class MetaTileEntityAxeSupport extends MetaTileEntity {

    private IBlockState upperBlock;
    private int hitNumber = 0;
    private final int HIT_NUMBER_TO_TRANSFORM = 5;
    private BlockPos upperBlockPos;
    private BlockCutWood.CutWoodType woodType;
    private IBlockState upperBlockState;
    private String blockName;

    public MetaTileEntityAxeSupport(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                CuboidRayTraceResult hitResult) {
        if (facing.equals(EnumFacing.UP))
            return super.onRightClick(playerIn, hand, facing, hitResult);

        if (!super.onRightClick(playerIn, hand, facing, hitResult)) {

            ItemStack itemStack = playerIn.getHeldItem(hand);
            if (!playerIn.isSneaking() || itemStack.isEmpty()) return true;

            Set<String> toolClasses = itemStack.getItem().getToolClasses(itemStack);
            if (toolClasses.isEmpty()) return true;
            ToolHelper.damageItem(itemStack, playerIn);

            upperBlockPos = getPos().offset(EnumFacing.UP);

            if (isUpFaceFree()) return true;

            upperBlockState = upperBlock.getBlock().getDefaultState();

            if (toolClasses.iterator().next().equals(ToolClasses.AXE)) {
                onAxeClick(playerIn);
            } else if (toolClasses.iterator().next().equals(ToolClasses.SAW)) {
                blockName = BlockCutWood.getInfo(upperBlockState)[0];
                onSawClick(playerIn);
            }
        }
        return true;
    }

    private boolean isUpFaceFree() {
        World world = this.getWorld();
        upperBlock = world.getBlockState(upperBlockPos);
        return upperBlock.equals(Blocks.AIR.getDefaultState());
    }

    private boolean incrementAndTryDestroy(BlockPos blockPos, IBlockState toAdd, EntityPlayer player) {
        hitNumber++;
        if (hitNumber == HIT_NUMBER_TO_TRANSFORM) {
            getWorld().setBlockState(blockPos, toAdd);
            woodPulpToInventory(player);
            hitNumber = 0;
            return true;
        }
        return false;
    }

    private boolean incrementAndTryDestroy(BlockPos blockPos) {
        hitNumber++;

        if (hitNumber == HIT_NUMBER_TO_TRANSFORM) {
            getWorld().destroyBlock(blockPos, false);
            hitNumber = 0;
            return true;
        }
        return false;
    }

    private void woodPulpToInventory(EntityPlayer player) {
        ItemStack stack = OreDictUnifier.get(OrePrefix.dust, Materials.Wood);
        player.addItemStackToInventory(stack);
    }

    private void onSawClick(EntityPlayer player) {
        IBlockState plank = BlockCutWood.getPlankFromCutWood(upperBlock);
        if (plank != null && blockName.equals(BlockCutWood.TRANSLATION_KEY)) {
            incrementAndTryDestroy(upperBlockPos, plank, player);
            return;
        }

        IBlockState slab = BlockCutWood.getSlabFromPlank(upperBlock);
        if (plank != null && slab != null && blockName.equals(BlockCutWood.PLANK)) {
            if (!incrementAndTryDestroy(upperBlockPos, slab, player)) return;
            slab.getBlock().dropBlockAsItem(getWorld(), upperBlockPos, slab, 0);
            return;
        }

        if (blockName.equals(BlockCutWood.SLAB)) {
            if (!incrementAndTryDestroy(upperBlockPos)) return;
            player.addItemStackToInventory(new ItemStack(Items.STICK, 4));
            woodPulpToInventory(player);
        }

        hitNumber = 0;
    }

    private void onAxeClick(EntityPlayer player) {
        woodType = BlockCutWood.getCutWoodTypeFromLog(upperBlock);

        if (woodType != null) {
            IBlockState toAdd = TKCYAMetaBlocks.BLOCK_CUT_WOOD.getState(woodType);
            incrementAndTryDestroy(upperBlockPos, toAdd, player);
            return;
        }
        hitNumber = 0;
    }

    @SideOnly(Side.CLIENT)
    protected SimpleOverlayRenderer getBaseRenderer() {
        return TKCYATextures.DIRTS[0];
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline,
                new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        ColourMultiplier multiplier = new ColourMultiplier(
                GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        colouredPipeline = ArrayUtils.add(pipeline, multiplier);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.SOUTH);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.NORTH);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.EAST);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.WEST);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.axe_support.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.axe_support.tooltip.2"));
        super.addInformation(stack, player, tooltip, advanced);
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
