package tekcays_addon;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import tekcays_addon.api.utils.TKCYALog;
import tekcays_addon.api.utils.TKCYAValues;
import tekcays_addon.api.worldgen.TKCYAWorldGenRegistry;
import tekcays_addon.common.CommonProxy;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.metatileentities.TKCYAMetaTileEntities;
import gregtech.api.GTValues;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.IOException;

@Mod(   modid        = TekCaysAddon.MODID,
        name         = TekCaysAddon.NAME,
        version      = TekCaysAddon.VERSION,
        dependencies = GTValues.MOD_VERSION_DEP)
public class TekCaysAddon {

    public static final String MODID = "tkcya";
    public static final String NAME = "TeK_CaY's Addon";
    public static final String VERSION = "@VERSION@";

    @SidedProxy(modId = MODID, clientSide = "tekcays_addon.common.ClientProxy", serverSide = "tekcays_addon.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        TKCYALog.init(event.getModLog());
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
    }


}
