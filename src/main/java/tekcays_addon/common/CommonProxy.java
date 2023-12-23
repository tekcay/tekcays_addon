package tekcays_addon.common;

import gregtech.api.GregTechAPI;
import gregtech.api.block.VariantItemBlock;
import gregtech.api.unification.material.registry.MaterialRegistry;
import gregtech.common.pipelike.cable.BlockCable;
import gregtech.common.pipelike.fluidpipe.BlockFluidPipe;
import gregtech.common.pipelike.itempipe.BlockItemPipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import tekcays_addon.TekCaysAddon;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.pipelike.cable.ItemBlockCableExtraInfo;
import tekcays_addon.common.pipelike.fluidpipe.ItemBlockFluidPipeExtraInfo;
import tekcays_addon.common.pipelike.itempipe.ItemBlockPipeExtraInfo;
import tekcays_addon.gtapi.unification.material.ore.OreDictAdditions;
import tekcays_addon.gtapi.utils.FuelWithProperties;
import tekcays_addon.gtapi.utils.TKCYALog;
import tekcays_addon.loaders.DamageableItemsLoader;
import tekcays_addon.loaders.recipe.TKCYARecipeLoader;

import java.util.Objects;
import java.util.function.Function;

import static gregtech.common.blocks.MetaBlocks.*;

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

        registry.register(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING);
        registry.register(TKCYAMetaBlocks.BLOCK_BRICK);
        registry.register(TKCYAMetaBlocks.BLOCK_DIRT);
        registry.register(TKCYAMetaBlocks.BLOCK_CUT_WOOD);
    }


    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        TKCYALog.logger.info("Registering Items...");
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(createItemBlock(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(TKCYAMetaBlocks.BLOCK_BRICK, VariantItemBlock::new));
        registry.register(createItemBlock(TKCYAMetaBlocks.BLOCK_DIRT, VariantItemBlock::new));
        registry.register(createItemBlock(TKCYAMetaBlocks.BLOCK_CUT_WOOD, VariantItemBlock::new));

        for (MaterialRegistry materialRegistry : GregTechAPI.materialManager.getRegistries()) {
            for (BlockCable cable : CABLES.get(materialRegistry.getModid())) registry.register(createItemBlock(cable, ItemBlockCableExtraInfo::new));
            for (BlockFluidPipe pipe : FLUID_PIPES.get(materialRegistry.getModid())) registry.register(createItemBlock(pipe, ItemBlockFluidPipeExtraInfo::new));
            for (BlockItemPipe pipe : ITEM_PIPES.get(materialRegistry.getModid())) registry.register(createItemBlock(pipe, ItemBlockPipeExtraInfo::new));
        }
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
        OreDictAdditions.woodOreInit();

        DamageableItemsLoader.initElectrodes();
        DamageableItemsLoader.initFilters();
        TKCYARecipeLoader.load();

        FuelWithProperties.addCombustionRecipeToList();
        FuelWithProperties.addGasTurbineRecipeToList();
    }

    //this is called last, so all mods finished registering their stuff, as example, CraftTweaker
    //if it registered some kind of ore dictionary entry, late processing will hook it and generate recipes
    //
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerRecipesLowest(RegistryEvent.Register<IRecipe> event) {
        TKCYALog.logger.info("Running late material handlers...");
        TKCYARecipeLoader.loadLatest();
    }
}
