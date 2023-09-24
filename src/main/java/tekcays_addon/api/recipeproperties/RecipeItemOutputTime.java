package tekcays_addon.api.recipeproperties;

import lombok.Value;
import net.minecraft.item.ItemStack;

@Value
public class RecipeItemOutputTime {

    private int outputTime;
    private ItemStack outputItemStack;

    }
