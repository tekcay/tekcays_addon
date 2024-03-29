package tekcays_addon;

import static gregtech.api.GregTechAPI.COVER_REGISTRY;

import java.io.IOException;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import gregtech.api.GregTechAPI;
import gregtech.api.cover.CoverDefinition;
import tekcays_addon.common.CommonProxy;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.covers.Covers;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.metatileentities.TKCYAMetaTileEntities;
import tekcays_addon.gtapi.TKCYAInternalTags;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.utils.TKCYALog;
import tekcays_addon.gtapi.worldgen.TKCYAWorldGenRegistry;

@Mod(modid = TekCaysAddon.MODID,
     name = TekCaysAddon.NAME,
     version = TekCaysAddon.VERSION,
     dependencies = TKCYAInternalTags.DEP_VERSION_STRING)
public class TekCaysAddon {

    public static final String MODID = "tkcya";
    public static final String NAME = "TeK_CaY's Addon";
    public static final String VERSION = "@VERSION@";

    @SidedProxy(modId = MODID,
                clientSide = "tekcays_addon.common.ClientProxy",
                serverSide = "tekcays_addon.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        TKCYALog.init(event.getModLog());
        TKCYATileCapabilities.init();
        TKCYAMetaBlocks.init();
        TKCYAMetaItems.init();
        TKCYAMetaTileEntities.init();

        proxy.preLoad();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        boolean doesDummyFileExist = TKCYAWorldGenRegistry.INSTANCE.doesDummyFileExist();

        if (TKCYAConfigHolder.miscOverhaul.enableTKCYACustomOreGen) {
            try {
                if (!doesDummyFileExist) {
                    TKCYAWorldGenRegistry.INSTANCE.addRemoveVeins();
                    TKCYAWorldGenRegistry.INSTANCE.createDummyFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /* Start Cover Definition Registration */
        Covers.init();
        MinecraftForge.EVENT_BUS.post(new GregTechAPI.RegisterEvent<>(COVER_REGISTRY, CoverDefinition.class));
        /* End Cover Definition Registration */
    }
}
