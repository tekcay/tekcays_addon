package tekcays_addon.common.metatileentities.steam;

import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SteamMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.client.renderer.texture.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;
import tekcays_addon.api.recipes.TKCYARecipeMaps;

public class MetaTileEntitySteamAutoclave extends SteamMetaTileEntity {

        public MetaTileEntitySteamAutoclave(ResourceLocation metaTileEntityId, boolean isHighPressure) {
            super(metaTileEntityId, TKCYARecipeMaps.STEAM_AUTOCLAVE, Textures.AUTOCLAVE_OVERLAY, isHighPressure);
        }

        @Override
        public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
            return new MetaTileEntitySteamAutoclave(metaTileEntityId, isHighPressure);
        }

         @Override
         protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(2, this, false);
        }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new NotifiableItemStackHandler(1, this, true);
    }

        @Override
        public ModularUI createUI(EntityPlayer player) {
            return createUITemplate(player)
                    .slot(this.importItems, 0, 33, 25, GuiTextures.SLOT)
                    .slot(this.importItems, 1, 53, 25, GuiTextures.SLOT)
                    .progressBar(workableHandler::getProgressPercent, 79, 25, 20, 18,
                            GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL, workableHandler.getRecipeMap())
                    .slot(this.exportItems, 0, 104, 25, GuiTextures.SLOT)
                    .build(getHolder(), player);
        }


}
