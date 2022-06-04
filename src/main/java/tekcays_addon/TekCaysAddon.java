package tekcays_addon;

import tekcays_addon.api.fluids.TKCYAMetaFluids;
import tekcays_addon.api.utils.TKCYALog;
import tekcays_addon.common.CommonProxy;
import tekcays_addon.common.block.TKCYAMetaBlocks;
import tekcays_addon.common.metatileentities.TKCYAMetaTileEntities;
import gregtech.api.GTValues;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(   modid        = TekCaysAddon.MODID,
        name         = TekCaysAddon.NAME,
        version      = TekCaysAddon.VERSION,
        dependencies = GTValues.MOD_VERSION_DEP)
public class TekCaysAddon {

    public static final String MODID = "tkcya";
    public static final String NAME = "TeK_CaY's Addon";
    public static final String VERSION = "@VERSION@";

    @SidedProxy(modId = MODID, clientSide = "ClientProxy", serverSide = "CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        TKCYALog.init(event.getModLog());

        TKCYAMetaFluids.init();
        TKCYAMetaBlocks.init();
        TKCYAMetaTileEntities.init();

        proxy.preLoad();
    }
}
