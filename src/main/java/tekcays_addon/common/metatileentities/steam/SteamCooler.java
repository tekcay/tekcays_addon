package tekcays_addon.common.metatileentities.steam;

import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SteamMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.texture.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import tekcays_addon.api.recipes.TKCYARecipeMaps;

public class SteamCooler extends SteamMetaTileEntity {

        protected FluidTank airFluidTank;

        public SteamCooler(ResourceLocation metaTileEntityId, boolean isHighPressure) {
            super(metaTileEntityId, RecipeMaps.GAS_COLLECTOR_RECIPES, Textures.GAS_COLLECTOR_OVERLAY, isHighPressure);
        }

        @Override
        public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
            return new SteamCooler(metaTileEntityId, isHighPressure);
        }

         @Override
         protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(1, this, false);
        }

        @Override
        protected FluidTankList createExportFluidHandler() {
            this.airFluidTank = new FilteredFluidHandler(1000);
            return new FluidTankList(false, airFluidTank);
        }

        @Override
        public ModularUI createUI(EntityPlayer player) {
            return createUITemplate(player)
                    .slot(this.importItems, 0, 53, 25, GuiTextures.SLOT)
                    .progressBar(workableHandler::getProgressPercent, 79, 25, 20, 18,
                            GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, ProgressWidget.MoveType.HORIZONTAL, workableHandler.getRecipeMap())
                    .widget(new TankWidget(airFluidTank, 104, 25, 20, 18)
                            .setBackgroundTexture(GuiTextures.FLUID_TANK_BACKGROUND))
                    .build(getHolder(), player);
        }


}
