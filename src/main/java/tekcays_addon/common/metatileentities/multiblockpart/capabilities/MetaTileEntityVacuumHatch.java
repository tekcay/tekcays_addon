package tekcays_addon.common.metatileentities.multiblockpart.capabilities;

import static tekcays_addon.gtapi.consts.TKCYAValues.ATMOSPHERIC_PRESSURE;

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
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import tekcays_addon.api.units.PressureFormatting;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.IVacuumContainer;
import tekcays_addon.gtapi.capability.impl.VacuumContainer;
import tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility;

public class MetaTileEntityVacuumHatch extends MetaTileEntityMultiblockPart
                                       implements IMultiblockAbilityPart<IVacuumContainer>, IDataInfoProvider {

    private final IVacuumContainer vacuumContainer;
    private final int leakingRate;
    private final boolean canHandleVacuum;
    private final int minPressure, maxPressure, volume;
    private final int tierMultiplier = getTier() * getTier() + 1;

    public MetaTileEntityVacuumHatch(@NotNull ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.canHandleVacuum = true;
        this.minPressure = ATMOSPHERIC_PRESSURE / tierMultiplier;
        this.maxPressure = ATMOSPHERIC_PRESSURE;
        this.leakingRate = (int) (10 / (getTier() + 1));
        this.vacuumContainer = new VacuumContainer(this, true, this.minPressure, this.maxPressure);
        this.volume = 1;
        this.vacuumContainer.initializeAirFluidStack();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityVacuumHatch(metaTileEntityId, this.getTier());
    }

    // Each second, it leaks
    @Override
    public void update() {
        super.update();
        /*
         * if (getOffsetTimer() % 20 == 0) {
         * getVacuumContainer().leaksContainer(leakingRate);
         * getVacuumContainer().setPressure();
         * }
         * 
         */
        getVacuumContainer().setPressure();
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
    protected ModularUI createUI(@NotNull EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.machine.pressure_hatch.tooltip.vacuum",
                PressureFormatting.convertPressureToMillibar(minPressure, true)));
        tooltip.add(I18n.format("tkcya.machine.pressure_hatch.tooltip.vacuum.leak", leakingRate));
    }

    @Override
    public MultiblockAbility<IVacuumContainer> getAbility() {
        return TKCYAMultiblockAbility.VACUUM_CONTAINER;
    }

    @Override
    public void registerAbilities(@NotNull List<IVacuumContainer> list) {
        list.add(this.vacuumContainer);
    }

    @Override
    @Nullable
    public <T> T getCapability(@NotNull Capability<T> capability, EnumFacing side) {
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_VACUUM_CONTAINER) && side.equals(getFrontFacing())) {
            return TKCYATileCapabilities.CAPABILITY_VACUUM_CONTAINER.cast(vacuumContainer);
        }
        return null;
    }

    @NotNull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.air.amount", vacuumContainer.getAirAmount()));
        list.add(new TextComponentTranslation("behavior.tricorder.pressure.vacuum", vacuumContainer.getPressure()));
        list.add(new TextComponentTranslation("behavior.tricorder.min_pressure.pressure",
                vacuumContainer.getMinPressure()));
        list.add(new TextComponentTranslation("behavior.tricorder.max_pressure.pressure",
                vacuumContainer.getMaxPressure()));
        return list;
    }
}
