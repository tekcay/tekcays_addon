package tekcays_addon.common.metatileentities.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.capability.IPressureControl;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.metatileentity.multiblock.TKCYAMultiblockAbility;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityPressureController extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IPressureControl> {

    private IPressureControl pressureControl;

    public MetaTileEntityPressureController(@Nonnull ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, 1);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPressureController(metaTileEntityId);
    }

    @Override
    public void update() {
        super.update();
        if (getOffsetTimer() % 20 == 0) {
        }
    }

    @Override
    protected ModularUI createUI(@Nonnull EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.CONVERTER_FE_IN.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.machine.pressure_controller.tooltip.1"));
    }

    @Override
    public MultiblockAbility<IPressureControl> getAbility() {
        return TKCYAMultiblockAbility.PRESSURE_CONTROL;
    }

    @Override
    public void registerAbilities(List<IPressureControl> abilityList) {
        abilityList.add(pressureControl);
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTROL)) {
            return TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTROL.cast(pressureControl);
        }
        return super.getCapability(capability, side);
    }

}
