package tekcays_addon.common.metatileentities;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.metatileentities.MetaTileEntities;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.TekCaysAddon;
import tekcays_addon.api.metatileentity.single.SimpleNoEnergyMachineMetaTileEntity;
import tekcays_addon.api.recipes.TKCYARecipeMaps;


import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.common.metatileentities.multi.*;
import tekcays_addon.common.metatileentities.multi.MetaTileEntityCastingTable;
import tekcays_addon.common.metatileentities.steam.SteamCooler;

import java.util.function.Function;

import static gregtech.common.metatileentities.MetaTileEntities.*;

public class TKCYAMetaTileEntities {


    public static SimpleMachineMetaTileEntity[] CLUSTER_MILL = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] ADVANCED_POLARIZER = new SimpleMachineMetaTileEntity[5];
    public static SimpleNoEnergyMachineMetaTileEntity[] CASTING_TABLE = new SimpleNoEnergyMachineMetaTileEntity[1];

    public static MetaTileEntityPrimitiveMelter PRIMITIVE_MELTER;
    public static MetaTileEntityElectricMelter ELECTRIC_MELTER;
    public static MetaTileEntityFuelMelter FUEL_MELTER;
    public static MetaTileEntityAlloyingCrucible ALLOYING_CRUCIBLE;
    //public static MetaTileEntityCastingTable CASTING_TABLE;

    public static SteamCooler STEAM_COOLER_BRONZE;
    public static SteamCooler STEAM_COOLER_STEEL;



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
            //CASTING_TABLE = registerMetaTileEntity(11014, new MetaTileEntityCastingTable(tkcyaId("casting_table")));
            registerSimpleNoEnergyMetaTileEntity(CASTING_TABLE, 11014, "casting_table", TKCYARecipeMaps.CASTING_TABLE_RECIPES,
                    TKCYATextures.CASTING_TABLE_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);

            STEAM_COOLER_BRONZE = registerMetaTileEntity(11015, new SteamCooler(tkcyaId("steam_cooler_bronze"), false));
            STEAM_COOLER_STEEL = registerMetaTileEntity(11016, new SteamCooler(tkcyaId("steam_cooler_steel"), true));

        }


    }

    private static ResourceLocation tkcyaId(String name) {
        return new ResourceLocation(TekCaysAddon.MODID, name);
    }



    private static void registerSimpleNoEnergyMetaTileEntity(SimpleNoEnergyMachineMetaTileEntity[] machines,
                                                     int startId,
                                                     String name,
                                                     RecipeMap<?> map,
                                                     ICubeRenderer texture,
                                                     boolean hasFrontFacing,
                                                     Function<Integer, Integer> tankScalingFunction) {
        registerSimpleNoEnergyMetaTileEntity(machines, startId, name, map, texture, hasFrontFacing, TKCYAMetaTileEntities::tkcyaId, tankScalingFunction);
    }

    private static void registerSimpleNoEnergyMetaTileEntity(SimpleNoEnergyMachineMetaTileEntity[] machines,
                                                     int startId,
                                                     String name,
                                                     RecipeMap<?> map,
                                                     ICubeRenderer texture,
                                                     boolean hasFrontFacing) {
        registerSimpleNoEnergyMetaTileEntity(machines, startId, name, map, texture, hasFrontFacing, GTUtility.defaultTankSizeFunction);
    }

    public static void registerSimpleNoEnergyMetaTileEntity(SimpleNoEnergyMachineMetaTileEntity[] machines,
                                                    int startId,
                                                    String name,
                                                    RecipeMap<?> map,
                                                    ICubeRenderer texture,
                                                    boolean hasFrontFacing,
                                                    Function<String, ResourceLocation> resourceId,
                                                    Function<Integer, Integer> tankScalingFunction) {
        for (int i = 0; i < machines.length - 1; i++) {
            if (i > 4 && !getMidTier(name)) continue;
            if (i > 7 && !getHighTier(name)) break;

            String voltageName = GTValues.VN[i + 1].toLowerCase();
            machines[i + 1] = registerMetaTileEntity(startId + i,
                    new SimpleNoEnergyMachineMetaTileEntity(resourceId.apply(String.format("%s.%s", name, voltageName)), map, texture, i + 1, hasFrontFacing, tankScalingFunction));
        }
    }





}
