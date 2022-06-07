package tekcays_addon.api.recipes;

import gregtech.api.recipes.RecipeMap;
import tkcays_addon.api.recipes.builders.MelterRecipeBuilder;

public class MelterRecipeMap<R> extends RecipeMap<MelterRecipeBuilder> {


    public class MelterRecipeMap {

        public static MelterRecipeMap INSTANCE;

        public MelterRecipeMap(String unlocalizedName, R defaultRecipe) {
            super(unlocalizedName, 1, 1, 1, 1, 0, 0, 0, 0, (MelterRecipeBuilder) defaultRecipe, false);
            INSTANCE = this;
        }
    }
}
