package tekcays_addon.common.metatileentities.multi;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.PrimitiveRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.RecipeProgressWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapPrimitiveMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockDirt;
import tekcays_addon.gtapi.capability.containers.IHinderedContainer;
import tekcays_addon.gtapi.capability.impl.HinderedContainer;
import tekcays_addon.gtapi.capability.machines.IHinderedMachine;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.render.TKCYATextures;
import tekcays_addon.gtapi.utils.TKCYALog;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

import static gregtech.api.util.RelativeDirection.*;
import static tekcays_addon.api.item.ItemStackUtils.tryChangeItemStack;

public class MetaTileEntityPrimitiveFurnace extends RecipeMapPrimitiveMultiblockController implements IHinderedMachine, IDataInfoProvider {

    private final IBlockState dirt = TKCYAMetaBlocks.BLOCK_DIRT.getState(BlockDirt.DirtType.DIRT);
    private final int IGNITION_CHANCE_WOOD_STICK = 30;
    private final int WASTE_GENERATION = 1;
    private IHinderedContainer hinderedContainer;
    private boolean ignition = false;
    private PrimitiveFurnaceLogic primitiveFurnaceLogic;
    private ItemStack fuelStack, wasteStack;

    public MetaTileEntityPrimitiveFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.PRIMITIVE_FURNACE);
        this.hinderedContainer = new HinderedContainer(this);
        this.primitiveFurnaceLogic = new PrimitiveFurnaceLogic(this, TKCYARecipeMaps.PRIMITIVE_FURNACE);
        this.hinderedContainer.initFuelStack(new ItemStack(Items.STICK, 1));
        this.hinderedContainer.initWasteStack(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ash, 1));
    }

    /*
    @Override
    public TraceabilityPredicate autoAbilities() {
        return autoAbilities(false, false, true, true, false, false, false);
    }

     */

    /*
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.3", "2"));
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.4", brick.getBrickTemperature()));
    }

     */

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (!this.isStructureFormed()) {
            ITextComponent tooltip = new TextComponentTranslation("gregtech.multiblock.invalid_structure.tooltip");
            tooltip.setStyle((new Style()).setColor(TextFormatting.GRAY));
            textList.add((new TextComponentTranslation("gregtech.multiblock.invalid_structure"))
                    .setStyle((new Style()).setColor(TextFormatting.RED).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip))));
        } else {
            if (this.primitiveFurnaceLogic.getParallelLimit() != 1) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.parallel", this.primitiveFurnaceLogic.getParallelLimit()));
            }
            if (!this.primitiveFurnaceLogic.isWorkingEnabled()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.work_paused"));
            } else if (this.primitiveFurnaceLogic.isActive()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.running"));
                int currentProgress = (int) (this.primitiveFurnaceLogic.getProgressPercent() * 100.0D);


                textList.add(new TextComponentTranslation("gregtech.multiblock.progress", currentProgress));
            } else {
                textList.add(new TextComponentTranslation("gregtech.multiblock.idling"));
            }
        }
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.PRIMITIVE_BACKGROUND, 176, 166)
                .shouldColor(false)
                .widget(new LabelWidget(5, 5, getMetaFullName()))


                .widget(new SlotWidget(importItems, 0, 42, 32, true, true)
                        .setBackgroundTexture(GuiTextures.PRIMITIVE_SLOT, GuiTextures.PRIMITIVE_FURNACE_OVERLAY))

                .widget(new SlotWidget(importItems, 1, 12, 32, true, true)
                        .setBackgroundTexture(GuiTextures.PRIMITIVE_SLOT, GuiTextures.PRIMITIVE_FURNACE_OVERLAY))

                .widget(new RecipeProgressWidget(primitiveFurnaceLogic::getProgressPercent, 76, 32, 20, 15,
                        GuiTextures.PRIMITIVE_BLAST_FURNACE_PROGRESS_BAR, ProgressWidget.MoveType.HORIZONTAL, TKCYARecipeMaps.PRIMITIVE_FURNACE))

                .widget(new SlotWidget(exportItems, 0, 113, 32, true, false)
                        .setBackgroundTexture(GuiTextures.PRIMITIVE_SLOT))

                .widget(new SlotWidget(exportItems, 1, 143, 32, true, false)
                        .setBackgroundTexture(GuiTextures.PRIMITIVE_SLOT))

                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.PRIMITIVE_SLOT, 0);
    }

    private static class PrimitiveFurnaceLogic extends PrimitiveRecipeLogic {

        public PrimitiveFurnaceLogic(RecipeMapPrimitiveMultiblockController tileEntity, RecipeMap<?> recipeMap) {
            super(tileEntity, recipeMap);
        }

        public void setActive(boolean isActive) {
            super.setActive(isActive);
        }

        @Override
        public boolean checkRecipe(@Nonnull Recipe recipe) {
            return isActive();
        }
    }


    @Override
    public void update() {

        super.update();
    }

    private void setEfficiency() {
    }

    private void modifyDurationRecipe() {

    }

    private int findItemStackSlot(ItemStack stackToCheck) {
        int slots = importItems.getSlots();
        for (int slot = 0; slot < slots; slot++) {
            ItemStack stack = importItems.getStackInSlot(slot);
            if (stack.isItemEqual(stackToCheck) && stack.getCount() > stackToCheck.getCount()) {
                return slot;
            }
        }
        return -1;
    }

    private void incrementWaste() {
        if (getOffsetTimer() % 20 == 0) {
            hinderedContainer.changeWasteAmount(WASTE_GENERATION);
        }
    }


    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {

        ItemStack itemInHand = playerIn.getHeldItemMainhand();

        if (itemInHand.getItem().equals(Items.STICK)) {
            TKCYALog.logger.info("Item in hand is stick");
            if (playerIn.isSneaking() && trySetActiveAndConsumeFuel(itemInHand)) {
                TKCYALog.logger.info("sneaking");
                return true;
            } else {
                TKCYALog.logger.info("not sneaking");
                itemInHand.setCount(itemInHand.getCount() - 1);
                this.hinderedContainer.changeFuelAmount(1);
                return true;
            }
        }

        return super.onRightClick(playerIn, hand, facing, hitResult);
    }

    private boolean trySetActiveAndConsumeFuel(ItemStack itemInHand) {
        Random rand = new Random();
        if (rand.nextInt(100) < IGNITION_CHANCE_WOOD_STICK && this.hinderedContainer.getFuelAmount() > 0) {
            this.primitiveFurnaceLogic.setActive(true);
        }
        itemInHand.setCount(itemInHand.getCount() - 1);
        return true;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), ignition, primitiveFurnaceLogic.isWorkingEnabled());
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.COKE_OVEN_OVERLAY;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("DDD", "DDD", "DDD")
                .aisle("-S-", "D#D", "-D-")
                .aisle("---", "-D-", "---")
                .where('S', selfPredicate())
                .where('D', states(dirt))
                .where('#', air())
                .where('-', any())
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return TKCYATextures.DIRTS[BlockDirt.DIRT];
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPrimitiveFurnace(metaTileEntityId);
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.fuel_amount", hinderedContainer.getFuelAmount()));
        list.add(new TextComponentTranslation("behavior.tricorder.waste_amount", hinderedContainer.getWasteAmount()));
        return list;
    }

    @Override
    public IHinderedContainer getHinderedContainer() {
        return this.hinderedContainer;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.ignition = data.getBoolean("ignition");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(this.ignition);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.ignition = buf.readBoolean();
    }
}

