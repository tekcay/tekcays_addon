package tekcays_addon.common.metatileentities.multiblockpart.brick;

import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.cover.ICoverable;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import lombok.AccessLevel;
import lombok.Setter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.covers.molds.CoverMoldWrapper;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.common.covers.CoverMold;
import tekcays_addon.gtapi.capability.containers.IMoldCoverable;
import tekcays_addon.gtapi.capability.impl.MoldCoverable;
import tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility;
import tekcays_addon.gtapi.render.TKCYATextures;
import tekcays_addon.gtapi.utils.TKCYALog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.unification.material.Materials.Air;
import static net.minecraft.util.EnumFacing.UP;
import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.CAPABILITY_MOLD_COVERABLE;

public class MetaTileEntityBrickCastingBus extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IMoldCoverable>, ICoverable {

    @Setter(AccessLevel.NONE)
    private final BlockBrick.BrickType brick;
    private OrePrefix outputOrePrefix;
    private IMoldCoverable moldCoverable;

    public MetaTileEntityBrickCastingBus(ResourceLocation metaTileEntityId, BlockBrick.BrickType brick) {
        super(metaTileEntityId, 0);
        this.brick = brick;
        this.moldCoverable = new MoldCoverable(this);
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityBrickCastingBus(metaTileEntityId, brick);
    }

    @Override
    public boolean placeCoverOnSide(EnumFacing side, ItemStack itemStack, CoverDefinition coverDefinition, EntityPlayer player) {
        TKCYALog.logger.info("Side : {}", side.getName());
        if (side != UP) return false;
        return super.placeCoverOnSide(side, itemStack, coverDefinition, player);
    }

    @Override
    public void update() {
        super.update();
        CoverBehavior coverBehavior = this.getCoverAtSide(UP);
        if (coverBehavior instanceof CoverMold) {
            CoverMoldWrapper wrapper = ((CoverMold) coverBehavior).getWrapper();
            TKCYALog.logger.info("needed fluid amount : {}", wrapper.getOutputPrefix().getMaterialAmount(Air));
        }
    }

    @Override
    public MultiblockAbility<IMoldCoverable> getAbility() {
        return TKCYAMultiblockAbility.MOLD_COVERABLE_CONTAINER;
    }

    @Override
    public void registerAbilities(@Nonnull List<IMoldCoverable> list) {
        list.add(this.moldCoverable);
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability.equals(CAPABILITY_MOLD_COVERABLE) && side.equals(UP)) {
            return CAPABILITY_MOLD_COVERABLE.cast(moldCoverable);
        }
        return super.getCapability(capability, side);
    }

    public BlockBrick.BrickType getBrick() {
        return this.brick;
    }

    @Override
    public ICubeRenderer getBaseTexture() {
        return TKCYATextures.BRICKS[brick.getTextureId()];
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }
}
