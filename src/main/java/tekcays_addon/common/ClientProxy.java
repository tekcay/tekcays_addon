package tekcays_addon.common;

import tekcays_addon.gtapi.render.TKCYATextures;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preLoad() {
        super.preLoad();
        TKCYATextures.preInit();
    }


    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        TKCYAMetaBlocks.registerItemModels();
    }
}
