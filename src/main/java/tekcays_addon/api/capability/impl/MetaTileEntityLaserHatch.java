package tekcays_addon.api.capability.impl;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregicality.science.api.GCYSValues;
import gregicality.science.api.utils.GCYSUtility;
import gregicality.science.api.utils.NumberFormattingUtil;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.capability.ILaserContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.metatileentity.mutiblock.TKCYAMultiblockAbility;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityLaserHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<ILaserContainer>, IDataInfoProvider {

    private final ILaserContainer pressureContainer;

    public MetaTileEntityLaserHatch(@Nonnull ResourceLocation metaTileEntityId, int tier, double minLaser, double maxLaser) {
        super(metaTileEntityId, tier);
        this.pressureContainer = new LaserContainer(this, minLaser, maxLaser, 1.0D);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityLaserHatch(metaTileEntityId, this.getTier(), this.pressureContainer.getMinLaser(), this.pressureContainer.getMaxLaser());
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote && getOffsetTimer() % 20 == 0) {
            // vacuum container, needs to increase pressure
            boolean needsLaserIncrease = pressureContainer.getLaser() > pressureContainer.getMinLaser() && this.pressureContainer.getMinLaser() < GCYSValues.EARTH_PRESSURE;
            // pressure container, needs to decrease pressure
            boolean needsLaserDecrease = pressureContainer.getLaser() < pressureContainer.getMaxLaser() && this.pressureContainer.getMaxLaser() > GCYSValues.EARTH_PRESSURE;
            boolean canChangeLaser = needsLaserDecrease || needsLaserIncrease;

            if (canChangeLaser) {
                TileEntity te = getWorld().getTileEntity(getPos().offset(getFrontFacing()));
                if (te != null) {
                    ILaserContainer container = te.getCapability(TKCYATileCapabilities.CAPABILITY_LASER_CONTAINER, this.getFrontFacing().getOpposite());
                    if (container != null) {
                        ILaserContainer.mergeContainers(false, container, pressureContainer);
                    }
                }
            }

            if (!this.pressureContainer.isLaserSafe()) {
                this.pressureContainer.causeLaserExplosion(getWorld(), getPos());
            }
        }
    }

    @Override
    protected ModularUI createUI(@Nonnull EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 166)
                .label(6, 6, getMetaFullName())

                // TODO add tooltip directly to ProgressWidget in CEu
                .widget(new ImageWidget(70, 26, 10, 54, GuiTextures.SLOT)
                        .setTooltip(NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getLaser()) + "Pa / " +
                                NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMaxLaser()) + "Pa"))
                .widget(new ProgressWidget(() -> pressureContainer.getLaserPercent(false), 70, 26, 10, 54)
                        .setProgressBar(GuiTextures.PROGRESS_BAR_BOILER_EMPTY.get(true),
                                GuiTextures.PROGRESS_BAR_BOILER_HEAT, ProgressWidget.MoveType.VERTICAL))

                .widget(new ImageWidget(96, 26, 10, 54, GuiTextures.SLOT)
                        .setTooltip(NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getLaser()) + "Pa / " +
                                NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMinLaser()) + "Pa"))
                .widget(new ProgressWidget(() -> pressureContainer.getLaserPercent(true), 96, 26, 10, 54)
                        .setProgressBar(GuiTextures.PROGRESS_BAR_BOILER_EMPTY.get(true),
                        GuiTextures.PROGRESS_BAR_BOILER_HEAT, ProgressWidget.MoveType.VERTICAL))


                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0)
                .build(getHolder(), entityPlayer);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.PIPE_IN_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    /*
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gcys.universal.tooltip.pressure.minimum", NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMinLaser()), GCYSValues.PNF[GCYSUtility.getTierByLaser(pressureContainer.getMinLaser())]));
        tooltip.add(I18n.format("gcys.universal.tooltip.pressure.maximum", NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMaxLaser()), GCYSValues.PNF[GCYSUtility.getTierByLaser(pressureContainer.getMaxLaser())]));

        tooltip.add(I18n.format("gregtech.universal.enabled"));
    }

     */

    @Override
    public MultiblockAbility<ILaserContainer> getAbility() {
        return TKCYAMultiblockAbility.LASER_CONTAINER;
    }

    @Override
    public void registerAbilities(@Nonnull List<ILaserContainer> list) {
        list.add(this.pressureContainer);
    }

    @Override
    public void doExplosion(float explosionPower) {
        if (getController() != null)
            getController().explodeMultiblock();
        else {
            super.doExplosion(explosionPower);
        }
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_LASER_CONTAINER)) {
            return TKCYATileCapabilities.CAPABILITY_LASER_CONTAINER.cast(pressureContainer);
        }
        return super.getCapability(capability, side);
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.current_pressure", new TextComponentString(NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getLaser())).setStyle(new Style().setColor(TextFormatting.AQUA))));
        list.add(new TextComponentTranslation("behavior.tricorder.min_pressure", new TextComponentString(NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMinLaser())).setStyle(new Style().setColor(TextFormatting.GREEN))));
        list.add(new TextComponentTranslation("behavior.tricorder.max_pressure", new TextComponentString(NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMaxLaser())).setStyle(new Style().setColor(TextFormatting.GREEN))));
        return list;
    }
}
