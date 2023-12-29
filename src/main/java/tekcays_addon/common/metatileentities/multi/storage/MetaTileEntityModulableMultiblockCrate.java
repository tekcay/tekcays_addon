package tekcays_addon.common.metatileentities.multi.storage;

import static gregtech.api.util.RelativeDirection.*;
import static tekcays_addon.api.consts.NBTKeys.ITEM_COUNT;
import static tekcays_addon.api.consts.NBTKeys.STORED_ITEM;
import static tekcays_addon.api.metatileentity.TankMethods.getBaseTextureForTank;
import static tekcays_addon.api.metatileentity.TankMethods.getBlockState;
import static tekcays_addon.api.metatileentity.predicates.Predicates.isAir;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Material;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.MetaTileEntities;

public class MetaTileEntityModulableMultiblockCrate extends MultiblockWithDisplayBase {

    private final int capacity;
    private int actualCapacity;
    private final Material material;
    private int itemStackSize = 0;
    private ItemStack storedItemStack = ItemStack.EMPTY;

    public MetaTileEntityModulableMultiblockCrate(ResourceLocation metaTileEntityId, Material material, int capacity) {
        super(metaTileEntityId);
        this.material = material;
        this.capacity = capacity;
        this.actualCapacity = capacity;
        resetTileAbilities();
    }

    protected void initializeAbilities() {
        this.importItems = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.exportItems = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
    }

    private void resetTileAbilities() {
        this.importItems = new ItemHandlerList(Collections.emptyList());
        this.exportItems = new ItemHandlerList(Collections.emptyList());
    }

    private boolean canExtract(ItemStack stack) {
        if (this.itemStackSize == this.actualCapacity) return false;
        return storedItemStack.isEmpty() || stack.isItemEqual(storedItemStack);
    }

    private void handleImportItems() {
        ItemStack importStack = importItems.getStackInSlot(0);
        if (importStack.isEmpty()) return;
        if (canExtract(importStack)) {
            int stackSizeToExtract = Math.min(this.actualCapacity - this.itemStackSize, importStack.getCount());
            this.storedItemStack = importItems.extractItem(0, stackSizeToExtract, false).copy();
            this.itemStackSize += stackSizeToExtract;
        }
    }

    private void handleExportItems() {
        ItemStack exportItemsStack = exportItems.getStackInSlot(0);
        if (storedItemStack.isEmpty()) return;

        if (exportItemsStack.isEmpty() || storedItemStack.isItemEqual(exportItemsStack)) {
            int exportCount = exportItemsStack.getCount();
            int exportableCount = Math.min(64 - exportCount, this.itemStackSize);
            exportItems.insertItem(0, new ItemStack(storedItemStack.getItem(), exportableCount), false);
            this.itemStackSize -= exportableCount;
        }
    }

    @Override
    public void update() {
        super.update();
        if (importItems.getSlots() < 1 || exportItems.getSlots() < 1) return;
        handleImportItems();
        handleExportItems();
    }

    @Override
    protected void updateFormedValid() {}

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityModulableMultiblockCrate(metaTileEntityId, material, capacity);
    }

    @NotNull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("XXX", "XXX", "XXX")
                .aisle("XSX", "X X", "XXX")
                .aisle("XXX", "XIX", "XXX").setRepeatable(1, 11)
                .aisle("XXX", "XXX", "XXX")
                .where('S', selfPredicate())
                .where('I', isAir("modulableTankHeight"))
                .where('X', states(getBlockState(material))
                        .or(metaTileEntities(MetaTileEntities.ITEM_EXPORT_BUS[GTValues.ULV]).setExactLimit(1))
                        .or(metaTileEntities(MetaTileEntities.ITEM_IMPORT_BUS[GTValues.ULV]).setExactLimit(1)))
                .where(' ', air())
                .build();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        int height = context.getOrDefault("modulableTankHeight", 0) + 1;
        this.actualCapacity = this.capacity * height;
        initializeAbilities();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return getBaseTextureForTank(material);
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    public boolean isCrateEmpty() {
        return this.itemStackSize == 0;
    }

    public String getFillPercentage() {
        return isCrateEmpty() ? "0% Filled" :
                String.format("%.1f%% Filled", (float) (100 * this.itemStackSize / this.actualCapacity));
    }

    public String getCrateFormatedContent() {
        if (isCrateEmpty()) return "Empty";
        if (this.itemStackSize > 1000) {
            return String.format("%.1f k of %s", (float) this.itemStackSize / 1000,
                    this.storedItemStack.getDisplayName());
        }
        return String.format("%d of %s", this.itemStackSize, this.storedItemStack.getDisplayName());
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.MULTIBLOCK_TANK_OVERLAY;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (!this.isStructureFormed()) {
            ITextComponent tooltip = new TextComponentTranslation("gregtech.multiblock.invalid_structure.tooltip");
            tooltip.setStyle((new Style()).setColor(TextFormatting.GRAY));
            textList.add((new TextComponentTranslation("gregtech.multiblock.invalid_structure")).setStyle((new Style())
                    .setColor(TextFormatting.RED).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip))));
        } else {
            textList.add(
                    new TextComponentTranslation("tkcya.multiblock.modulable_tank.content", getCrateFormatedContent()));
            textList.add(new TextComponentTranslation("tkcya.multiblock.modulable_crate.capacity",
                    this.actualCapacity / 1000));
            textList.add(new TextComponentTranslation("tkcya.multiblock.modulable_tank.fill.percentage",
                    getFillPercentage()));
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.multiblock.modulable_crate.capacity"));
        tooltip.add(I18n.format("tkcya.machine.modulable_crate.capacity", capacity, "per layer."));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        NBTTagCompound stackTag = new NBTTagCompound();
        storedItemStack.writeToNBT(stackTag);
        data.setTag(STORED_ITEM, stackTag);
        data.setInteger(ITEM_COUNT, this.itemStackSize);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.itemStackSize = data.getInteger(ITEM_COUNT);
        this.storedItemStack = new ItemStack(data.getCompoundTag(STORED_ITEM));
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(itemStackSize);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        itemStackSize = buf.readInt();
    }
}
