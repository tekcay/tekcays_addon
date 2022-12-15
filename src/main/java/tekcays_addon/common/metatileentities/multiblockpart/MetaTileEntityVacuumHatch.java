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
import gregtech.client.renderer.texture.Textures;
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
import tekcays_addon.api.capability.IVacuumContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.capability.impl.VacuumContainer;
import tekcays_addon.api.metatileentity.multiblock.TKCYAMultiblockAbility;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static tekcays_addon.api.utils.TKCYAValues.ATMOSPHERIC_PRESSURE;

public class MetaTileEntityVacuumHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IVacuumContainer>, IDataInfoProvider {

    private final IVacuumContainer vacuumContainer;
    private final int leakingRate;
    private final boolean canHandleVacuum;
    private final int minPressure;
    private final int maxPressure;
    private final int volume;
    private final int tierMultiplier = getTier() * getTier() + 1;

    public MetaTileEntityVacuumHatch(@Nonnull ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.canHandleVacuum = true;
        this.minPressure = ATMOSPHERIC_PRESSURE / tierMultiplier;
        this.maxPressure = ATMOSPHERIC_PRESSURE;
        this.leakingRate = (int) (10 / (getTier() + 1));
        this.vacuumContainer = new VacuumContainer(this, true, this.minPressure, this.maxPressure);
        this.volume = 1;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityVacuumHatch(metaTileEntityId, this.getTier());
    }

    //Each second, it leaks
    @Override
    public void update() {
        super.update();
        if (getOffsetTimer() % 20 == 0) {
            getVacuumContainer().leaksContainer(leakingRate);
            getVacuumContainer().setPressure();
        }
    }

    public IVacuumContainer getVacuumContainer() {
        return this.vacuumContainer;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.PIPE_IN_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Override
    protected ModularUI createUI(@Nonnull EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
            tooltip.add(I18n.format("tkcya.machine.pressure_hatch.tooltip.vacuum", vacuumContainer.convertPressureToBar(minPressure)));
            tooltip.add(I18n.format("tkcya.machine.pressure_hatch.tooltip.vacuum.leak", leakingRate));
    }

    @Override
    public MultiblockAbility<IVacuumContainer> getAbility() {
        return TKCYAMultiblockAbility.VACUUM_CONTAINER;
    }

    @Override
    public void registerAbilities(@Nonnull List<IVacuumContainer> list) {
        list.add(this.vacuumContainer);
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_VACUUM_CONTAINER)) {
            return TKCYATileCapabilities.CAPABILITY_VACUUM_CONTAINER.cast(vacuumContainer);
        }
        return super.getCapability(capability, side);
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.pressure.vacuum", vacuumContainer.getPressure()));
        list.add(new TextComponentTranslation("behavior.tricorder.min_pressure.pressure", vacuumContainer.getMinPressure()));
        list.add(new TextComponentTranslation("behavior.tricorder.max_pressure.pressure", vacuumContainer.getMaxPressure()));
        return list;
    }

}
