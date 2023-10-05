package tekcays_addon.common.metatileentities.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.capability.impl.ItemHandlerProxy;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTTransferUtils;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility;
import tekcays_addon.gtapi.render.TKCYATextures;
import tekcays_addon.gtapi.unification.TKCYAMaterials;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class MetaTileEntityItemValve extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IItemHandler> {

    private final Material material;

    public MetaTileEntityItemValve(ResourceLocation metaTileEntityId, Material material) {
        super(metaTileEntityId, 0);
        this.material = material;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityItemValve(metaTileEntityId, material);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.PIPE_IN_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Override
    public ICubeRenderer getBaseTexture() {
        if (getController() == null) {
            if (material.equals(Materials.TreatedWood)) return Textures.WOOD_WALL;
            if (material.equals(Materials.Steel)) return TKCYATextures.STEEL_GT;
            if (material.equals(TKCYAMaterials.GalvanizedSteel)) return TKCYATextures.WHITE_GT;
            if (material.equals(Materials.StainlessSteel)) return TKCYATextures.STAINLESS_STEEL_GT;
        }
        return super.getBaseTexture();
    }

    @Override
    public int getDefaultPaintingColor() {
        return 0xFFFFFF;
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote && getOffsetTimer() % 5 == 0L && isAttachedToMultiBlock() && getFrontFacing() == EnumFacing.DOWN) {
            TileEntity tileEntity = getWorld().getTileEntity(getPos().offset(getFrontFacing()));
            IItemHandler iItemHandler = tileEntity == null ? null : tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, getFrontFacing().getOpposite());
            if (iItemHandler != null) {
                GTTransferUtils.moveInventoryItems(iItemHandler, itemInventory);
            }
        }
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        initializeDummyInventory();
    }

    /**
     * When this block is not connected to any multiblock it uses dummy inventory to prevent problems with capability checks
     */
    private void initializeDummyInventory() {
        //this.itemInventory = new ItemHandlerProxy(new ItemHandlerList(Collections.singleton()));
        this.itemInventory = new ItemHandlerProxy(new ItemStackHandler(0), new ItemStackHandler(0));
    }

    @Override
    public void addToMultiBlock(MultiblockControllerBase controllerBase) {
        super.addToMultiBlock(controllerBase);
        this.itemInventory = controllerBase.getItemInventory();
        //this.itemInventory = new ItemHandlerProxy(controllerBase.getImportItems(), controllerBase.getExportItems());
    }

    @Override
    public void removeFromMultiBlock(MultiblockControllerBase controllerBase) {
        super.removeFromMultiBlock(controllerBase);
        initializeDummyInventory();
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
    public boolean canPartShare() {
        return false;
    }

    @Override
    public MultiblockAbility<IItemHandler> getAbility() {
        return TKCYAMultiblockAbility.CRATE_VALVE;
    }

    @Override
    public void registerAbilities(@Nonnull List<IItemHandler> abilityList) {
        abilityList.add(this.getImportItems());
    }

    @Override
    protected boolean shouldSerializeInventories() {
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.crate_valve.tooltip"));
    }

    @Override
    public boolean needsSneakToRotate() {
        return true;
    }

    @Override
    public void addToolUsages(ItemStack stack, @Nullable World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.tool_action.screwdriver.access_covers"));
        tooltip.add(I18n.format("gregtech.tool_action.wrench.set_facing"));
        super.addToolUsages(stack, world, tooltip, advanced);
    }
}
