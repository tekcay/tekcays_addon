package tekcays_addon.common.metatileentities.multiblockpart.storage;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
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
import tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility;
import tekcays_addon.gtapi.render.TKCYATextures;
import tekcays_addon.gtapi.unification.TKCYAMaterials;

public class MetaTileEntityCrateValve extends MetaTileEntityMultiblockPart
                                      implements IMultiblockAbilityPart<IItemHandler> {

    private final Material material;

    public MetaTileEntityCrateValve(ResourceLocation metaTileEntityId, Material material) {
        super(metaTileEntityId, 0);
        this.material = material;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCrateValve(metaTileEntityId, material);
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

        /*
         * 
         * if (getOffsetTimer() % 20 == 0) {
         * 
         * TKCYALog.logger.info("VALVE : itemInventory.getStackInSlot(0).getDisplayName()" +
         * itemInventory.getStackInSlot(0).getDisplayName());
         * TKCYALog.logger.info("VALVE : importItems.getStackInSlot(0).getDisplayName()" +
         * importItems.getStackInSlot(0).getDisplayName());
         * }
         * 
         */

        if (!getWorld().isRemote && getOffsetTimer() % 5 == 0L && isAttachedToMultiBlock() &&
                getFrontFacing() == EnumFacing.DOWN) {
            TileEntity tileEntity = getWorld().getTileEntity(getPos().offset(getFrontFacing()));
            IItemHandler iItemHandler = tileEntity == null ? null : tileEntity
                    .getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, getFrontFacing().getOpposite());
            if (iItemHandler != null) {
                GTTransferUtils.moveInventoryItems(itemInventory, iItemHandler);
            }
        }
    }

    /*
     * @Override
     * protected void initializeInventory() {
     * super.initializeInventory();
     * initializeDummyInventory();
     * }
     * 
     * 
     * //When this block is not connected to any multiblock it uses dummy inventory to prevent problems with capability
     * checks
     * 
     * private void initializeDummyInventory() {
     * //this.itemInventory = new ItemHandlerProxy(new ItemStackHandler(), new ItemStackHandler());
     * this.itemInventory = new ItemHandlerProxy(i);
     * }
     * 
     * @Override
     * public void removeFromMultiBlock(MultiblockControllerBase controllerBase) {
     * super.removeFromMultiBlock(controllerBase);
     * initializeDummyInventory();
     * }
     * 
     */

    @Override
    public void addToMultiBlock(MultiblockControllerBase controllerBase) {
        super.addToMultiBlock(controllerBase);
        this.itemInventory = controllerBase.getItemInventory(); // directly use controllers fluid inventory as there is
                                                                // no reason to proxy it
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
    protected boolean shouldSerializeInventories() {
        return false;
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.tank_valve.tooltip"));
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

    @Override
    public MultiblockAbility<IItemHandler> getAbility() {
        return TKCYAMultiblockAbility.CRATE_VALVE;
    }

    @Override
    public void registerAbilities(List<IItemHandler> abilityList) {
        abilityList.add(this.getImportItems());
    }
}
