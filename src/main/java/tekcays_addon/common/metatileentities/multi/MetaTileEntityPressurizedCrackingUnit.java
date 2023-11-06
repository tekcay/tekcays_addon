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
import tekcays_addon.api.metatileentity.LogicType;
import tekcays_addon.api.recipe.PressureContainerCheckRecipeHelper;
import tekcays_addon.api.units.IPressureFormatting;
import tekcays_addon.gtapi.metatileentity.multiblock.ModulableRecipeMapController;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static tekcays_addon.gtapi.consts.TKCYAValues.ROOM_TEMPERATURE;
import static tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility.HEAT_CONTAINER;


public class MetaTileEntityPressurizedCrackingUnit extends ModulableRecipeMapController implements IPressureFormatting, PressureContainerCheckRecipeHelper {

    private int coilTier;
    private String displayedPressure;
    private int displayedTemp;

    public MetaTileEntityPressurizedCrackingUnit(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.PRESSURE_CRACKING, LogicType.HEAT, LogicType.PRESSURE);
        //this.volume = 1;
        this.initializeAbilities();
        this.displayedPressure = getCurrentPressureWithUnit();
        this.displayedTemp = getCurrentTemperature();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPressurizedCrackingUnit(metaTileEntityId);
    }

    @Nonnull
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
        return convertPressureToBar(getCurrentPressure(), true);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (getOffsetTimer() % 20 == 0) {
            displayedPressure = getCurrentPressureWithUnit();
            displayedTemp = getCurrentTemperature();
        }
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("gregtech.multiblock.cracking_unit.energy", 100 - 10 * coilTier));
            textList.add(new TextComponentTranslation("tkcya.machine.text.pressurized.fluid", getPressureContainer().getPressurizedFluidStackLocalizedName(), displayedPressure));
            textList.add(new TextComponentTranslation("tkcya.machine.text.temperature", displayedTemp));
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.cracker.tooltip.1"));
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.CRACKING_UNIT_OVERLAY;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
        Object type = context.get("CoilType");
        if (type instanceof IHeatingCoilBlockStats) {
            this.coilTier = ((IHeatingCoilBlockStats) type).getTier();
        } else this.coilTier = 0;
    }

    @Override
    protected void actualizeTemperature() {
        getHeatContainer().setTemperature(ROOM_TEMPERATURE + getCurrentHeat() / (20));
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.coilTier = -1;
    }


    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return checkRecipeHelper(recipe, consumeIfSuccess);
    }

    @Override
    public int getCurrentTemperature() {
        return super.getCurrentTemp();
    }

    @Override
    public FluidStack getFluidStack() {
        return getPressureContainer().getPressurizedFluidStack();
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
