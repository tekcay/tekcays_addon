package tekcays_addon.common.metatileentities.multiblockpart.capabilities;

import static tekcays_addon.gtapi.consts.TKCYAValues.HORIZONTALS;
import static tekcays_addon.gtapi.consts.TKCYAValues.VERTICALS;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.IHeatContainer;
import tekcays_addon.gtapi.capability.impl.HeatContainer;
import tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility;
import tekcays_addon.gtapi.render.TKCYATextures;

public class MetaTileEntityHeatAcceptor extends MetaTileEntityMultiblockPart
                                        implements IMultiblockAbilityPart<IHeatContainer>, IDataInfoProvider {

    private final IHeatContainer heatContainer;
    private final int tierMultiplier = (getTier() + 1) * (getTier() + 1);
    private final int coolingRate = 10 * tierMultiplier;
    private final int heatCapacity = tierMultiplier * 20000;

    public MetaTileEntityHeatAcceptor(@NotNull ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.heatContainer = new HeatContainer(this, 0, heatCapacity);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityHeatAcceptor(metaTileEntityId, this.getTier());
    }

    // Each second, it cools down
    @Override
    public void update() {
        super.update();
        if (getOffsetTimer() % 20 == 0) {
            if (heatContainer.getHeat() - coolingRate >= 0) heatContainer.changeHeat(-coolingRate);
            else heatContainer.setHeat(0);
        }
    }

    public IHeatContainer getHeatContainer() {
        return heatContainer;
    }

    @Override
    public EnumFacing getFrontFacing() {
        return EnumFacing.DOWN;
    }

    @Override
    protected ModularUI createUI(@NotNull EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        HORIZONTALS.forEach(enumFacing -> TKCYATextures.HEAT_ACCEPTOR_HORIZONTALS_OVERLAY.renderSided(enumFacing,
                renderState, translation, pipeline));
        VERTICALS.forEach(enumFacing -> TKCYATextures.HEAT_ACCEPTOR_VERTICALS_OVERLAY.renderOriented(renderState,
                translation, pipeline, enumFacing));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.machine.heat_acceptor.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.heat_acceptor.tooltip.2", coolingRate));
        tooltip.add(I18n.format("tkcya.machine.heat_acceptor.tooltip.3", heatCapacity / 1000));
    }

    @Override
    public MultiblockAbility<IHeatContainer> getAbility() {
        return TKCYAMultiblockAbility.HEAT_CONTAINER;
    }

    @Override
    public void registerAbilities(@NotNull List<IHeatContainer> list) {
        list.add(this.heatContainer);
    }

    @Override
    @Nullable
    public <T> T getCapability(@NotNull Capability<T> capability, EnumFacing side) {
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER)) {
            return TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER.cast(heatContainer);
        }
        return super.getCapability(capability, side);
    }

    @NotNull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.current_heat", heatContainer.getHeat()));
        list.add(new TextComponentTranslation("behavior.tricorder.min_heat", heatContainer.getMinHeat()));
        list.add(new TextComponentTranslation("behavior.tricorder.max_heat", heatContainer.getMaxHeat()));
        return list;
    }
}
