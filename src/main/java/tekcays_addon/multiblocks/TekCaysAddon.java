package tekcays_addon.multiblocks;

import tekcays_addon.multiblocks.api.fluids.TKCY_AMetaFluids;
import tekcays_addon.multiblocks.api.utils.TKCY_ALog;
import tekcays_addon.multiblocks.common.CommonProxy;
import tekcays_addon.multiblocks.common.block.TKCY_AMetaBlocks;
import tekcays_addon.multiblocks.common.metatileentities.TKCY_AMetaTileEntities;
import gregtech.api.GTValues;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(   modid        = TekCaysAddon.MODID,
        name         = TekCaysAddon.NAME,
        version      = TekCaysAddon.VERSION,
        dependencies = GTValues.MOD_VERSION_DEP)
public class TekCaysAddon {

    public static final String MODID = "tkcy_a";
    public static final String NAME = "TeK_CaY's Addon";
    public static final String VERSION = "@VERSION@";

    @SidedProxy(modId = MODID, clientSide = "tekcays_addon.multiblocks.common.ClientProxy", serverSide = "tekcays_addon.multiblocks.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        TKCY_ALog.init(event.getModLog());

        TKCY_AMetaFluids.init();
        TKCY_AMetaBlocks.init();
        TKCY_AMetaTileEntities.init();

        proxy.preLoad();
    }
}
