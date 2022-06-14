package tekcays_addon.common.metatileentities.multi;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.*;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.recipes.builders.ElectricMelterRecipeBuilder;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.common.metatileentities.TKCYAMetaTileEntities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nullable;

public class MetaTileEntityElectricMelter extends RecipeMapMultiblockController{

    private int temp;
    private int targetTemp;
    private int increaseTemp;
    private boolean canAchieveTargetTemp;
    private boolean hasEnoughEnergy;
    public int size;

    public MetaTileEntityElectricMelter(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.ELECTRIC_MELTER_RECIPES);
        //this.recipeMapWorkable = new ElectricMelterLogic(this);

        temp = 300;
        targetTemp = 300;
    }

    private static final MultiblockAbility<?>[] ALLOWED_ABILITIES = {
            MultiblockAbility.IMPORT_ITEMS, MultiblockAbility.EXPORT_ITEMS,
            MultiblockAbility.INPUT_ENERGY, MultiblockAbility.MAINTENANCE_HATCH
    };

    @Override
    protected void updateFormedValid() {
        if (getWorld().isRemote) {
            return;
        }

        super.updateFormedValid();

        if (temp > 300)
            hasEnoughEnergy = drainEnergy();
        else
            hasEnoughEnergy = true;

        if (getOffsetTimer() % 20 == 0 && !recipeMapWorkable.isActive())
            stepTowardsTargetTemp();
        else if (targetTemp == temp) {
            canAchieveTargetTemp = true;
        }

    }

    private void stepTowardsTargetTemp() {
        canAchieveTargetTemp = true;
        if (temp > 300 && (!this.recipeMapWorkable.isWorkingEnabled() || targetTemp < temp)) {
            setTemp(temp - increaseTemp);
            if (temp == 300)
                markDirty();
            return;
        }
        if (temp == targetTemp) return;
        if (temperatureEnergyCost(this.temp + increaseTemp) <= this.getEnergyContainer().getInputVoltage() * this.getEnergyContainer().getInputAmperage() && hasEnoughEnergy) {
            setTemp(temp + increaseTemp);
            if (temp == temp + increaseTemp)
                markDirty();
        } else {
            canAchieveTargetTemp = false;
        }
    }

    private boolean drainEnergy() {
        if (energyContainer.getEnergyStored() >= temperatureEnergyCost(this.temp)) {
            energyContainer.removeEnergy(temperatureEnergyCost(this.temp));
            return true;
        }
        setTemp(temp - increaseTemp);
        return false;
    }

    /*
    private class ElectricMelterLogic extends MultiblockRecipeLogic {
        public ElectricMelterLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        protected int[] calculateOverclock(Recipe recipe) {
            return new int[]{0, recipe.getDuration()};
        }
    }

     */




    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("tekcays_addon.machine.electric_melter.tooltip.1"));
        tooltip.add(I18n.format("tekcays_addon.machine.electric_melter.tooltip.2"));
        tooltip.add(I18n.format("tekcays_addon.machine.electric_melter.tooltip.3"));
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (!this.isStructureFormed()) {
            ITextComponent tooltip = new TextComponentTranslation("gregtech.multiblock.invalid_structure.tooltip");
            tooltip.setStyle((new Style()).setColor(TextFormatting.GRAY));
            textList.add((new TextComponentTranslation("gregtech.multiblock.invalid_structure")).setStyle((new Style()).setColor(TextFormatting.RED).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip))));
        } else {
            if (this.hasMaintenanceMechanics() && ConfigHolder.machines.enableMaintenance) {
                this.addMaintenanceText(textList);
            }
            if (!this.recipeMapWorkable.isWorkingEnabled()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.work_paused"));
            } else if (this.recipeMapWorkable.isActive()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.running"));
                int currentProgress = (int) (this.recipeMapWorkable.getProgressPercent() * 100.0D);
                if (this.recipeMapWorkable.getParallelLimit() != 1) {
                    textList.add(new TextComponentTranslation("gregtech.multiblock.parallel", this.recipeMapWorkable.getParallelLimit()));
                }

                textList.add(new TextComponentTranslation("gregtech.multiblock.progress", currentProgress));
            } else {
                textList.add(new TextComponentTranslation("gregtech.multiblock.idling"));
            }


            if (this.recipeMapWorkable.isHasNotEnoughEnergy()) {
                textList.add((new TextComponentTranslation("gregtech.multiblock.not_enough_energy")).setStyle((new Style()).setColor(TextFormatting.RED)));
            }


            textList.add(new TextComponentTranslation("tekcays_addon.multiblock.electric_melter.tooltip.1", temp));
            textList.add(new TextComponentTranslation("tekcays_addon.multiblock.electric_melter.tooltip.4", temperatureEnergyCost(temp)));

            /*

            ITextComponent buttonText = new TextComponentTranslation("tekcays_addon.multiblock.electric_melter.tooltip.3");
            buttonText.appendText(" ");
            buttonText.appendSibling(withButton(new TextComponentString("[-]"), "sub"));
            buttonText.appendText(" ");
            buttonText.appendSibling(withButton(new TextComponentString("[+]"), "add"));
            textList.add(buttonText);

             */

            textList.add(new TextComponentTranslation("tekcays_addon.multiblock.electric_melter.tooltip.5", targetTemp));


            if (!canAchieveTargetTemp && hasEnoughEnergy)
                textList.add(new TextComponentTranslation("tekcays_addon.multiblock.electric_melter.tooltip.2")
                        .setStyle(new Style().setColor(TextFormatting.RED)));
            /*
            if (!hasEnoughEnergy)
                textList.add(new TextComponentTranslation("gregtech.multiblock.not_enough_energy")
                        .setStyle(new Style().setColor(TextFormatting.RED)));

             */

        }
    }

    /*
    @Override
    protected void handleDisplayClick(String componentData, Widget.ClickData clickData) {
        super.handleDisplayClick(componentData, clickData);
        int modifier = componentData.equals("add") ? 1 : -1;
        targetTemp += 5 * modifier;
        if (targetTemp < 300)
            targetTemp = 300;
    }

     */

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF);
    }


    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.HEAT_PROOF_CASING;
    }

    @Override
    protected ICubeRenderer getFrontOverlay() {
        return TKCYATextures.ELECTRIC_MELTER_OVERLAY;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.FRONT, RelativeDirection.UP, RelativeDirection.RIGHT)
                .aisle("XXX", "CCC", "CCC", "XXX")
                .aisle("SXX", "C#C", "C#C", "XMX")
                .aisle("XXX", "CCC", "CCC", "XXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(9)
                        .or(autoAbilities(true, true, true, true, true, true, false)))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('C', heatingCoils())
                .where('#', air())
                .build();

    }


    /*
    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("XEM", "CCC", "CCC", "XXX")
                .aisle("FXD", "C#C", "C#C", "XHX")
                .aisle("ISO", "CCC", "CCC", "XXX")
                .where('X', MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF))
                .where('S', TKCYAMetaTileEntities.ELECTRIC_MELTER, EnumFacing.SOUTH)
                .where('#', Blocks.AIR.getDefaultState())
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.MV], EnumFacing.NORTH)
                .where('I', MetaTileEntities.ITEM_IMPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .where('O', MetaTileEntities.ITEM_EXPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .where('F', MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.LV], EnumFacing.WEST)
                .where('D', MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.LV], EnumFacing.EAST)
                .where('H', MetaTileEntities.MUFFLER_HATCH[GTValues.LV], EnumFacing.UP)
                .where('M', () -> ConfigHolder.machines.enableMaintenance ? MetaTileEntities.MAINTENANCE_HATCH : MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF), EnumFacing.NORTH);
        Arrays.stream(BlockWireCoil.CoilType.values())
                .sorted(Comparator.comparingInt(BlockWireCoil.CoilType::getLevel))
                .forEach(coilType -> shapeInfo.add(builder.where('C', MetaBlocks.WIRE_COIL.getState(coilType)).build()));
        return shapeInfo;
    }


     */
    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return true;
    }

    @Override
    public void invalidateStructure() {
        setTemp(300);
        super.invalidateStructure();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        if (type instanceof BlockWireCoil.CoilType) {
            this.targetTemp = ((BlockWireCoil.CoilType) type).getCoilTemperature();
            this.increaseTemp = (int) (((BlockWireCoil.CoilType) type).getCoilTemperature() / 200);
        } else {
            this.targetTemp = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();
            this.increaseTemp = (int) ((BlockWireCoil.CoilType.CUPRONICKEL).getCoilTemperature() / 200);
        }

    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityElectricMelter(metaTileEntityId);
    }

    public int temperatureEnergyCost(int temp) {
        return temp <= 300 ? 0 : (int) Math.exp(((double) temp - 100 + (size * 5)) / 100);
    }

    // Is the inverse of the previous function.
    public static int temperatureForEnergy(int EUt) {
        if (EUt <= 8)
            return 300;
        return (int) (Math.log(EUt) * 100) + 100;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("temp", this.temp);
        data.setInteger("targetTemp", this.targetTemp);
        data.setBoolean("canAchieveTargetTemp", this.canAchieveTargetTemp);
        data.setBoolean("hasEnoughEnergy", this.hasEnoughEnergy);
        return data;
    }

    public void setTemp(int temp) {
        this.temp = temp;
        if (!getWorld().isRemote) {
            writeCustomData(600, buf -> buf.writeInt(temp));
            markDirty();
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == 600) {
            this.temp = buf.readInt();
            scheduleRenderUpdate();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.temp = data.getInteger("temp");
        this.targetTemp = data.getInteger("targetTemp");
        this.canAchieveTargetTemp = data.getBoolean("canAchieveTargetTemp");
        this.hasEnoughEnergy = data.getBoolean("hasEnoughEnergy");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.temp);
        buf.writeInt(this.targetTemp);
        buf.writeBoolean(this.canAchieveTargetTemp);
        buf.writeBoolean(this.hasEnoughEnergy);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.temp = buf.readInt();
        this.targetTemp = buf.readInt();
        this.canAchieveTargetTemp = buf.readBoolean();
        this.hasEnoughEnergy = buf.readBoolean();
    }


    @Override
    public boolean checkRecipe(Recipe recipe, boolean consumeIfSuccess) {
        return this.temp >= recipe.getProperty(TemperatureProperty.getInstance(), 0);
    }

    @Override
    public int getLightValueForPart(IMultiblockPart sourcePart) {
        return sourcePart == null && temp > 300 ? 15 : 0;
    }

    public boolean canCreateSound() {
        return temp > 300 && this.recipeMapWorkable.isWorkingEnabled();
    }

    /*
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, this.getFrontFacing(), temp > 300, this.recipeMapWorkable.isWorkingEnabled());
    }

     */
}

