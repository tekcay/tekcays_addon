package tekcays_addon.common.metatileentities.multi;

import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.capability.containers.IHeatContainer;
import tekcays_addon.api.capability.containers.IPressureContainer;
import tekcays_addon.api.capability.containers.IPressureControl;
import tekcays_addon.api.metatileentity.multiblock.HeatedPressureContainerMultiblockController;
import tekcays_addon.api.utils.recipe.PressureContainerCheckRecipeHelper;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.utils.IPressureFormatting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static tekcays_addon.api.metatileentity.multiblock.TKCYAMultiblockAbility.*;
import static tekcays_addon.api.utils.TKCYAValues.*;


public class MetaTileEntityPressurizedCrackingUnit extends HeatedPressureContainerMultiblockController implements PressureContainerCheckRecipeHelper, IPressureFormatting {

    private int coilTier;
    private IHeatContainer heatContainer;
    private IPressureContainer pressureContainer;
    private IPressureControl pressureControl;
    private int volume;
    private int currentTemp = ROOM_TEMPERATURE;
    private long currentPressure = ATMOSPHERIC_PRESSURE;
    private int currentHeat;

    public MetaTileEntityPressurizedCrackingUnit(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.PRESSURE_CRACKING);
        this.volume = 1;
        this.initializeAbilities();
        //this.initializePressureContainer();
    }


    protected void initializePressureContainer() {
        this.pressureContainer = getPressureContainer();
        if (this.pressureContainer == null) return;
        //this.pressureContainer.setVolume(volume);
        //this.pressureContainer.initializeAirFluidStack();
        //this.pressureContainer.setPressure();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPressurizedCrackingUnit(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("-H-H-", "BCBCB", "BCBCB", "BCBCB")
                .aisle("-H-H-", "BCBCB", "B###B", "BCBCB")
                .aisle("-H-H-", "BCBCB", "BCSCB", "BCBCB")
                .where('S', selfPredicate())
                .where('B', states(getCasingState()).setMinGlobalLimited(17)
                        .or(autoAbilities()))
                .where('#', air())
                .where('C', heatingCoils())
                .where('H', abilities(HEAT_CONTAINER))
                .where('-', any())
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.CLEAN_STAINLESS_STEEL_CASING;
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
    }

    private String getCurrentPressureWithUnit() {
        return convertPressureToBar(currentPressure, true);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("gregtech.multiblock.cracking_unit.energy", 100 - 10 * coilTier));
            textList.add(new TextComponentTranslation("tkcya.machine.text.pressurized.fluid", pressureContainer.getPressurizedFluidStackLocalizedName(), getCurrentPressureWithUnit()));
            textList.add(new TextComponentTranslation("tkcya.machine.text.temperature", currentTemp));
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.cracker.tooltip.1"));
    }

    @Override
    protected void updateFormedValid() {
        super.updateFormedValid();
        if (!getWorld().isRemote) updateLogic();
    }

    private void updateLogic() {

        this.pressureContainer = getPressureContainer();
        this.pressureControl = getPressureControl();
        this.heatContainer = getHeatContainer();

        if (getOffsetTimer() % 20 == 0) {
            if (pressureControl != null) pressureControl.setPressure(currentPressure);
        }

        if (pressureContainer == null) return;
        currentPressure = pressureContainer.getPressure();

        if (heatContainer == null) return;
        currentHeat = heatContainer.getHeat();
        actualizeTemperature();
        currentTemp = heatContainer.getTemperature();
    }

    private void actualizeTemperature() {
        heatContainer.setTemperature(ROOM_TEMPERATURE + currentHeat / (20));
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.CRACKING_UNIT_OVERLAY;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializePressureContainer();
        Object type = context.get("CoilType");
        if (type instanceof IHeatingCoilBlockStats) {
            this.coilTier = ((IHeatingCoilBlockStats) type).getTier();
        } else this.coilTier = 0;
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.coilTier = -1;
    }

    protected int getCoilTier() {
        return this.coilTier;
    }

    @Override
    public long getCurrentPressure() {
        return currentPressure;
    }

    @Override
    public int getCurrentTemperature() {
        return currentTemp;
    }

    //Implementation
    @Override
    public FluidStack getFluidStack() {
        return pressureContainer.getPressurizedFluidStack();
    }

    @Override
    public IHeatContainer getHeatContainer() {
        return this.heatContainer;
    }


    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return checkRecipeHelper(recipe, consumeIfSuccess);
    }


    /*
    @SuppressWarnings("InnerClassMayBeStatic")
    private class CrackingUnitWorkableHandler extends MultiblockRecipeLogic {

        public CrackingUnitWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }


        @Override
        protected void performNonOverclockBonuses(int[] resultOverclock) {

            int coilTier = ((MetaTileEntityPressurizedCrackingUnit) metaTileEntity).getCoilTier();
            if (coilTier <= 0)
                return;

            resultOverclock[0] *= 1.0f - coilTier * 0.1; // each coil above cupronickel (coilTier = 0) uses 10% less energy
            resultOverclock[0] = Math.max(1, resultOverclock[0]);
        }


    }

         */
}
