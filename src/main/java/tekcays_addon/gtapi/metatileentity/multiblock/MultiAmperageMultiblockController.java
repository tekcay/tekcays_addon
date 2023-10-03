package tekcays_addon.gtapi.metatileentity.multiblock;

import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.gtapi.logic.MultiAmperageLogic;


public abstract class MultiAmperageMultiblockController extends RecipeMapMultiblockController {

    public MultiAmperageMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new MultiAmperageLogic(this);
    }
}
