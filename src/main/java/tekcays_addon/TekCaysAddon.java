package tekcays_addon;

import gregtech.api.GregTechAPI;
import gregtech.api.cover.CoverDefinition;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.utils.FuelWithProperties;
import tekcays_addon.api.utils.TKCYALog;
import tekcays_addon.api.worldgen.TKCYAWorldGenRegistry;
import tekcays_addon.common.CommonProxy;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.covers.Covers;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.metatileentities.TKCYAMetaTileEntities;
import gregtech.api.GTValues;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.IOException;

import static gregtech.api.GregTechAPI.COVER_REGISTRY;

@Mod(   modid        = TekCaysAddon.MODID,
        name         = TekCaysAddon.NAME,
        version      = TekCaysAddon.VERSION,
        dependencies = GTValues.MOD_VERSION_DEP + "required-after:gcym")
public class TekCaysAddon {

    public static final String MODID = "tkcya";
    public static final String NAME = "TeK_CaY's Addon";
    public static final String VERSION = "@VERSION@";

    @SidedProxy(modId = MODID, clientSide = "tekcays_addon.common.ClientProxy", serverSide = "tekcays_addon.common.CommonProxy")
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
        try {
            TKCYAWorldGenRegistry.INSTANCE.addRemoveVeins();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* Start Cover Definition Registration */
        Covers.init();
        MinecraftForge.EVENT_BUS.post(new GregTechAPI.RegisterEvent<>(COVER_REGISTRY, CoverDefinition.class));
        /* End Cover Definition Registration */

    }


}
