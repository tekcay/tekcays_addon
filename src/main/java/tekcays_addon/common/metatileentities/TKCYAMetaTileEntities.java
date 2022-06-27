package tekcays_addon.common.metatileentities;

import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.util.GTUtility;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.TekCaysAddon;
import tekcays_addon.api.recipes.TKCYARecipeMaps;


import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.common.metatileentities.multi.*;
import tekcays_addon.common.metatileentities.steam.SteamCooler;

import static gregtech.common.metatileentities.MetaTileEntities.*;

public class TKCYAMetaTileEntities {


    public static SimpleMachineMetaTileEntity[] CLUSTER_MILL = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] ADVANCED_POLARIZER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] ELECTRIC_CASTING_TABLE = new SimpleMachineMetaTileEntity[5];

    public static MetaTileEntityPrimitiveMelter PRIMITIVE_MELTER;
    public static MetaTileEntityElectricMelter ELECTRIC_MELTER;
    public static MetaTileEntityFuelMelter FUEL_MELTER;
    public static MetaTileEntityAlloyingCrucible ALLOYING_CRUCIBLE;
    public static MetaTileEntityCastingTable CASTING_TABLE;
    public static SteamCooler STEAM_COOLER_BRONZE;
    public static SteamCooler STEAM_COOLER_STEEL;
    public static MetaTileEntityConverter ELECTRIC_CONVERTER;
    public static MetaTileEntityPrimitiveBath PRIMITIVE_BATH;
    public static MetaTileEntityTKCYABlastFurnace TKCYA_BLAST_FURNACE;





    public static void init() {

        if (TKCYAConfigHolder.miscOverhaul.enableFoilOverhaul) {
            registerSimpleMetaTileEntity(CLUSTER_MILL, 11000, "cluster_mill", TKCYARecipeMaps.CLUSTER_MILL_RECIPES,
                    TKCYATextures.CLUSTER_MILL_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);
        }

        if (TKCYAConfigHolder.miscOverhaul.enableMagneticOverhaul) {
            registerSimpleMetaTileEntity(ADVANCED_POLARIZER, 11005, "advanced_polarizer", TKCYARecipeMaps.ADVANCED_POLARIZER_RECIPES,
                    TKCYATextures.ADVANCED_POLARIZER_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableMeltingOverhaul) {
            PRIMITIVE_MELTER = registerMetaTileEntity(11010, new MetaTileEntityPrimitiveMelter(tkcyaId("primitive_melter")));
            ELECTRIC_MELTER = registerMetaTileEntity(11011, new MetaTileEntityElectricMelter(tkcyaId("electric_melter")));
            FUEL_MELTER = registerMetaTileEntity(11012, new MetaTileEntityFuelMelter(tkcyaId("fuel_melter")));
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableAlloyingOverhaul) {
            ALLOYING_CRUCIBLE = registerMetaTileEntity(11013, new MetaTileEntityAlloyingCrucible(tkcyaId("alloying_crucible")));
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableCastingOverhaul) {
            CASTING_TABLE = registerMetaTileEntity(11014, new MetaTileEntityCastingTable(tkcyaId("casting_table")));

            registerSimpleMetaTileEntity(ELECTRIC_CASTING_TABLE, 11015, "electric_casting_table", TKCYARecipeMaps.ELECTRIC_CASTING_RECIPES,
                    TKCYATextures.CASTING_TABLE_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);


            STEAM_COOLER_BRONZE = registerMetaTileEntity(11020, new SteamCooler(tkcyaId("steam_cooler_bronze"), false));
            STEAM_COOLER_STEEL = registerMetaTileEntity(11021, new SteamCooler(tkcyaId("steam_cooler_steel"), true));

        }

        if (TKCYAConfigHolder.meltingOverhaul.enableBlastingOverhaul) {
            ELECTRIC_CONVERTER = registerMetaTileEntity(11022, new MetaTileEntityConverter(tkcyaId("electric_converter")));
            TKCYA_BLAST_FURNACE = registerMetaTileEntity(11023, new MetaTileEntityTKCYABlastFurnace(tkcyaId("tkcya_blast_furnace")));
        }

        if (TKCYAConfigHolder.miscOverhaul.enableGalvanizedSteel) {
            PRIMITIVE_BATH = registerMetaTileEntity(11024, new MetaTileEntityPrimitiveBath(tkcyaId("primitive_bath")));
        }


    }

    private static ResourceLocation tkcyaId(String name) {
        return new ResourceLocation(TekCaysAddon.MODID, name);
    }








}
