package tekcays_addon.common.metatileentities.steam;

import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SteamMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.texture.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;

public class SteamCooler extends SteamMetaTileEntity {

    protected FluidTank airFluidTank;

        public SteamCooler(ResourceLocation metaTileEntityId, boolean isHighPressure) {
            super(metaTileEntityId, RecipeMaps.GAS_COLLECTOR_RECIPES, Textures.GAS_COLLECTOR_OVERLAY, isHighPressure);
        }

        @Override
        public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
            return new gregtech.common.metatileentities.steam.SteamMacerator(metaTileEntityId, isHighPressure);
        }

        @Override
        protected FluidTankList createExportFluidHandler() {
            this.airFluidTank = new FilteredFluidHandler(STEAM_CAPACITY);
            return new FluidTankList(false, airFluidTank);
        }

        @Override
        public ModularUI createUI(EntityPlayer player) {
            return createUITemplate(player)
                    .widget(new TankWidget(airFluidTank, 20, 38, 20, 18))
                    .build(getHolder(), player);
        }

}
