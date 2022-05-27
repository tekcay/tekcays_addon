package tekcays_addon.multiblocks.common;

import tekcays_addon.multiblocks.TekCaysAddon;
import tekcays_addon.multiblocks.api.utils.TKCYALog;
import tekcays_addon.multiblocks.common.block.TKCYAMetaBlocks;
import tekcays_addon.multiblocks.loaders.recipe.TKCYARecipeLoader;
import gregtech.api.block.VariantItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Objects;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = TekCaysAddon.MODID)
public class CommonProxy {

    public void preLoad() {

    }

    @SubscribeEvent
    public static void syncConfigValues(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(TekCaysAddon.MODID)) {
            ConfigManager.sync(TekCaysAddon.MODID, Config.Type.INSTANCE);
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        TKCYALog.logger.info("Registering blocks...");
        IForgeRegistry<Block> registry = event.getRegistry();

        registry.register(TKCYAMetaBlocks.UNIQUE_CASING);
        registry.register(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING);
    }


    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        TKCYALog.logger.info("Registering Items...");
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(createItemBlock(TKCYAMetaBlocks.UNIQUE_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING, VariantItemBlock::new));
    }

    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
        itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
        return itemBlock;
    }

    @SubscribeEvent()
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        TKCYALog.logger.info("Registering recipe low...");

        // Main recipe registration
        // This is called AFTER GregTech registers recipes, so
        // anything here is safe to call removals in
        TKCYARecipeLoader.init();
    }
}
