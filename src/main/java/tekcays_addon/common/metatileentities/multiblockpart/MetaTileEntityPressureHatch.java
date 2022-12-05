package tekcays_addon.common.metatileentities.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.capability.IPressureContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.capability.impl.PressureContainer;
import tekcays_addon.api.metatileentity.multiblock.TKCYAMultiblockAbility;
import tekcays_addon.api.render.TKCYATextures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static tekcays_addon.api.utils.TKCYAValues.*;

public class MetaTileEntityPressureHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IPressureContainer>, IDataInfoProvider {

    private final IPressureContainer pressureContainer;
    private final int leakingRate;
    private final boolean canHandleVacuum;
    private final int minPressure;
    private final int maxPressure;
    private final int tierMultiplier = getTier() * getTier();

    public MetaTileEntityPressureHatch(@Nonnull ResourceLocation metaTileEntityId, boolean canHandleVacuum, int tier) {
        super(metaTileEntityId, tier);
        this.canHandleVacuum = canHandleVacuum;
        this.minPressure = canHandleVacuum ? (int) 100 / (tierMultiplier) : ATMOSPHERIC_PRESSURE;
        this.maxPressure = canHandleVacuum ? ATMOSPHERIC_PRESSURE * 1000 : 10 * tierMultiplier;
        this.leakingRate = canHandleVacuum ? (int) (10 / getTier()) : 10 * tierMultiplier;
        this.pressureContainer = new PressureContainer(this, canHandleVacuum, minPressure, maxPressure);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPressureHatch(metaTileEntityId, this.canHandleVacuum, this.getTier());
    }

    //Each second, it leaks
    @Override
    public void update() {
        super.update();
        if (getOffsetTimer() % 20 == 0) {
            if (!canHandleVacuum) leaksContainer(ATMOSPHERIC_PRESSURE, leakingRate);
            else leaksContainer(ABSOLUTE_VACUUM, -leakingRate);
        }
    }

    private void leaksContainer(int pressure, int leak) {
        if (pressureContainer.getPressure() - leak>= pressure) pressureContainer.changePressure(-leak);
        else pressureContainer.setPressure(pressure);
    }

    public IPressureContainer getPressureContainer() {
        return pressureContainer;
    }

    @Override
    public EnumFacing getFrontFacing() {
        return EnumFacing.DOWN;
    }

    @Override
    protected ModularUI createUI(@Nonnull EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        HORIZONTALS.forEach(enumFacing -> TKCYATextures.HEAT_ACCEPTOR_HORIZONTALS_OVERLAY.renderSided(enumFacing, renderState, translation, pipeline));
        VERTICALS.forEach(enumFacing -> TKCYATextures.HEAT_ACCEPTOR_VERTICALS_OVERLAY.renderOriented(renderState, translation, pipeline, enumFacing));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.machine.heat_acceptor.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.heat_acceptor.tooltip.2", leakingRate));
    }

    @Override
    public MultiblockAbility<IPressureContainer> getAbility() {
        return TKCYAMultiblockAbility.PRESSURE_CONTAINER;
    }

    @Override
    public void registerAbilities(@Nonnull List<IPressureContainer> list) {
        list.add(this.pressureContainer);
    }


    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER)) {
            return TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER.cast(pressureContainer);
        }
        return super.getCapability(capability, side);
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.current_heat", pressureContainer.getPressure()));
        list.add(new TextComponentTranslation("behavior.tricorder.min_heat", pressureContainer.getMinPressure()));
        list.add(new TextComponentTranslation("behavior.tricorder.max_heat", pressureContainer.getMaxPressure()));
        return list;
    }

}
