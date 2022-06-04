package tekcays_addon.common.metatileentities;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.integration.jei.multiblock.MultiblockInfoCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import tekcays_addon.TekCaysAddon;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import gregtech.client.renderer.texture.Textures;

import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.utils.TKCYALog;

import java.util.function.Function;

import static gregtech.common.metatileentities.MetaTileEntities.*;

public class TKCYAMetaTileEntities {

    public static final SimpleMachineMetaTileEntity[] CLUSTER_MILL = new SimpleMachineMetaTileEntity[4];

    public static void init() {
        TKCYALog.logger.info("Registering MetaTileEntities");

        registerSimpleMetaTileEntity(CLUSTER_MILL, 11000, "cluster_mill", TKCYARecipeMaps.CLUSTER_MILL_RECIPES, TKCYATextures.CLUSTER_MILL_OVERLAY, true, TKCYAMetaTileEntities::tkcyaId, GTUtility.hvCappedTankSizeFunction);

    }



    private static ResourceLocation tkcyaId(String name) {
        return new ResourceLocation(TekCaysAddon.MODID, name);
    }



}
