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
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.capability.impl.HeatContainer;
import tekcays_addon.api.metatileentity.multiblock.TKCYAMultiblockAbility;
import tekcays_addon.api.render.TKCYATextures;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import tekcays_addon.api.utils.TKCYALog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static tekcays_addon.api.utils.TKCYAValues.HORIZONTALS;
import static tekcays_addon.api.utils.TKCYAValues.VERTICALS;

public class MetaTileEntityHeatAcceptor extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IHeatContainer>, IDataInfoProvider {

    private final IHeatContainer heatContainer;
    private final int coolingRate = 10 * getTier() * getTier();
    private final int heatCapacity = getTier() * getTier() * 20000;

    public MetaTileEntityHeatAcceptor(@Nonnull ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.heatContainer = new HeatContainer(this, 0, heatCapacity);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityHeatAcceptor(metaTileEntityId, this.getTier());
    }

    //Each second, it cools down
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
        tooltip.add(I18n.format("tkcya.machine.heat_acceptor.tooltip.2", coolingRate));
        tooltip.add(I18n.format("tkcya.machine.heat_acceptor.tooltip.3", heatCapacity / 1000));
    }

    @Override
    public MultiblockAbility<IHeatContainer> getAbility() {
        return TKCYAMultiblockAbility.HEAT_CONTAINER;
    }

    @Override
    public void registerAbilities(@Nonnull List<IHeatContainer> list) {
        list.add(this.heatContainer);
    }


    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER)) {
            return TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER.cast(heatContainer);
        }
        return super.getCapability(capability, side);
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.current_heat", heatContainer.getHeat()));
        list.add(new TextComponentTranslation("behavior.tricorder.min_heat", heatContainer.getMinHeat()));
        list.add(new TextComponentTranslation("behavior.tricorder.max_heat", heatContainer.getMaxHeat()));
        return list;
    }

}
