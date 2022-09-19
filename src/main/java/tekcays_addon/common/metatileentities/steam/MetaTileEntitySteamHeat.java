package tekcays_addon.common.metatileentities.steam;

import gregicality.science.api.recipes.GCYSRecipeMaps;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SteamMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.client.renderer.texture.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.lwjgl.input.Keyboard;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.IHeatMachine;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.capability.impl.HeatContainer;
import tekcays_addon.api.capability.impl.HeatSteamRecipeLogic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntitySteamHeat extends SteamMetaTileEntity implements IHeatMachine {

    private HeatContainer heatContainer;

    public MetaTileEntitySteamHeat(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GCYSRecipeMaps.PRESSURE_CHAMBER_RECIPES, Textures.COMPRESSOR_OVERLAY, true);
        this.workableHandler = new HeatSteamRecipeLogic(this, GCYSRecipeMaps.PRESSURE_CHAMBER_RECIPES, isHighPressure, this.steamFluidTank, 1.0);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntitySteamHeat(metaTileEntityId);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.heatContainer = new HeatContainer(this, 0, 100);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(4, this, false);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new NotifiableItemStackHandler(1, this, true);
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote && getOffsetTimer() % 20 == 0) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                if (facing == getFrontFacing() || facing == this.workableHandler.getVentingSide()) continue;

                TileEntity te = getWorld().getTileEntity(getPos().offset(facing));
                if (te != null) {
                    IHeatContainer container = te.getCapability(TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER, facing.getOpposite());
                    if (container != null) {

                    }
                }
            }
        }
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
        return createUITemplate(player)
                .slot(this.importItems, 0, 35, 25, GuiTextures.SLOT_STEAM.get(isHighPressure), GuiTextures.COMPRESSOR_OVERLAY_STEAM.get(isHighPressure))
                .slot(this.importItems, 1, 53, 25, GuiTextures.SLOT_STEAM.get(isHighPressure), GuiTextures.COMPRESSOR_OVERLAY_STEAM.get(isHighPressure))
                .slot(this.importItems, 2, 35, 43, GuiTextures.SLOT_STEAM.get(isHighPressure), GuiTextures.COMPRESSOR_OVERLAY_STEAM.get(isHighPressure))
                .slot(this.importItems, 3, 53, 43, GuiTextures.SLOT_STEAM.get(isHighPressure), GuiTextures.COMPRESSOR_OVERLAY_STEAM.get(isHighPressure))

                .progressBar(workableHandler::getProgressPercent, 79, 35, 20, 18,
                        GuiTextures.PROGRESS_BAR_COMPRESS_STEAM.get(isHighPressure), ProgressWidget.MoveType.HORIZONTAL, workableHandler.getRecipeMap())

                .slot(this.exportItems, 0, 107, 35, true, false, GuiTextures.SLOT_STEAM.get(isHighPressure))

                .build(getHolder(), player);
    }

    @Override
    public ModularUI.Builder createUITemplate(@Nonnull EntityPlayer player) {
        return ModularUI.builder(GuiTextures.BACKGROUND_STEAM.get(this.isHighPressure), 176, 166)
                .label(6, 6, this.getMetaFullName()).shouldColor(false)

                .widget((new ImageWidget(79, 51, 18, 18, GuiTextures.INDICATOR_NO_STEAM.get(this.isHighPressure)))
                        .setPredicate(() -> this.workableHandler.isHasNotEnoughEnergy()))

                .bindPlayerInventory(player.inventory, GuiTextures.SLOT_STEAM.get(this.isHighPressure), 0);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER)) {
            return TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER.cast(heatContainer);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public IHeatContainer getHeatContainer() {
        return this.heatContainer;
    }

    // the vacuum chamber does not emit particles
    @Override
    public void randomDisplayTick() {/**/}
}
