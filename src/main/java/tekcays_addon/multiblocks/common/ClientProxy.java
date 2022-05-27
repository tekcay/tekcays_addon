package tekcays_addon.multiblocks.common;

import codechicken.lib.texture.TextureUtils;
import tekcays_addon.multiblocks.api.fluids.TKCYAMetaFluids;
import tekcays_addon.multiblocks.api.render.TKCYATextures;
import tekcays_addon.multiblocks.common.block.TKCYAMetaBlocks;
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
        TextureUtils.addIconRegister(TKCYAMetaFluids::registerSprites);
    }


    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        TKCYAMetaBlocks.registerItemModels();
    }
}
