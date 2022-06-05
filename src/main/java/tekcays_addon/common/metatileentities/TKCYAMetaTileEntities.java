package tekcays_addon.common.metatileentities;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.util.GTUtility;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import tekcays_addon.TekCaysAddon;
import tekcays_addon.api.recipes.TKCYARecipeMaps;


import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.utils.TKCYALog;
import tekcays_addon.common.TKCYAConfigHolder;

import java.util.function.Function;

import static gregtech.common.metatileentities.MetaTileEntities.*;

public class TKCYAMetaTileEntities {

    public static SimpleMachineMetaTileEntity[] CLUSTER_MILL = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] ADVANCED_POLARIZER = new SimpleMachineMetaTileEntity[5];

    public static void init() {
       // TKCYALog.logger.info("Registering MetaTileEntities");

        if (TKCYAConfigHolder.foilOverhaul.enableFoilOverhaul) {
            registerSimpleMetaTileEntity(CLUSTER_MILL, 11000, "cluster_mill", TKCYARecipeMaps.CLUSTER_MILL_RECIPES, TKCYATextures.CLUSTER_MILL_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);
        }

        if (TKCYAConfigHolder.magneticOverhaul.enableMagneticOverhaul) {
            registerSimpleMetaTileEntity(ADVANCED_POLARIZER, 11004, "advanced_polarizer", TKCYARecipeMaps.ADVANCED_POLARIZER_RECIPES, TKCYATextures.ADVANCED_POLARIZER_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);
        }
    }



    private static ResourceLocation tkcyaId(String name) {
        return new ResourceLocation(TekCaysAddon.MODID, name);
    }



}
