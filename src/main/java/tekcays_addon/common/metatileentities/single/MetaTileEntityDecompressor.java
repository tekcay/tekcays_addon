package tekcays_addon.common.metatileentities.single;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableFluidTank;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleSidedCubeRenderer;
import gregtech.core.sound.GTSoundEvents;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import tekcays_addon.api.metatileentity.WrenchAbleTieredMetaTileEntity;
import tekcays_addon.gtapi.capability.containers.IDecompression;
import tekcays_addon.gtapi.capability.impl.DecompressionContainer;
import tekcays_addon.gtapi.render.TKCYATextures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityDecompressor extends WrenchAbleTieredMetaTileEntity implements IActiveOutputSide {

    private IFluidTank exportFluidTank;
    private final IDecompression decompression;
    private final int tankCapacity;
    private EnumFacing outputSide;
    private final int ENERGY_BASE_CONSUMPTION = (int) (GTValues.V[getTier()]);

    public MetaTileEntityDecompressor(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.tankCapacity = 1000 * (tier + 1);
        super.initializeInventory();
        this.decompression = new DecompressionContainer(this, fluidInventory);
        this.outputSide = getFrontFacing().getOpposite();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityDecompressor(metaTileEntityId, tier);
    }
    
    @Override
    public FluidTankList createImportFluidHandler() {
        this.exportFluidTank = new NotifiableFluidTank(tankCapacity, this, true);
        return new FluidTankList(false, exportFluidTank);
    }

    @Override
    public void update() {
        super.update();
        if (energyContainer.getEnergyStored() > ENERGY_BASE_CONSUMPTION) {
            decompression.setCompressAbility(true);
        }
        else {
            decompression.setCompressAbility(false);
            return;
        }
            if (decompression.isActive()) energyContainer.removeEnergy(ENERGY_BASE_CONSUMPTION);
    }

    @Override
    protected ModularUI createUI(EntityPlayer player) {
        return createUITemplate(player).build(getHolder(), player);
    }

    protected ModularUI.Builder createUITemplate(@Nonnull EntityPlayer entityPlayer) {
        return ModularUI.defaultBuilder()
                .widget(new LabelWidget(6, 6, getMetaFullName()))
                .widget(new TankWidget(exportFluidTank, 52, 18, 72, 61)
                        .setBackgroundTexture(GuiTextures.SLOT)
                        .setContainerClicking(true, true))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        Textures.PIPE_OUT_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        TKCYATextures.ROTATION_WATER_OUTPUT_OVERLAY.renderSided(getOutputSide(), renderState, translation, pipeline);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.1"));
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public boolean isAutoOutputItems() {
        return false;
    }

    @Override
    public boolean isAutoOutputFluids() {
        return true;
    }

    @Override
    public boolean isAllowInputFromOutputSideItems() {
        return false;
    }

    @Override
    public boolean isAllowInputFromOutputSideFluids() {
        return false;
    }

    @Override
    public SoundEvent getSound() {
        return GTSoundEvents.TURBINE;
    }
}
