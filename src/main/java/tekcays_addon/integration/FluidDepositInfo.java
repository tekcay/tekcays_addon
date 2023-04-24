package tekcays_addon.integration;

import gregtech.api.util.FileUtility;
import lombok.Getter;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import tekcays_addon.gtapi.worldgen.FluidDepositDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class FluidDepositInfo implements IRecipeWrapper {

    private final FluidDepositDefinition definition;
    private String name;
    private final String description;
    private final int weight;
    private final int minDepth;
    private final int maxDepth;
    private final int maxFluidAmount;
    private final int minFluidAmount;
    private final FluidStack fluid;

    private final List<List<FluidStack>> fluidList = new ArrayList<>();
    private final List<List<ItemStack>> bucketList = new ArrayList<>();

    public FluidDepositInfo(FluidDepositDefinition definition) {

        this.definition = definition;

        //Get the Name and trim unneeded information
        this.name = definition.getAssignedName();
        if (this.name == null) {
            this.name = FileUtility.trimFileName(definition.getDepositName());
        }

        this.description = definition.getDescription();
        this.weight = definition.getWeight();
        this.minDepth = definition.getMinDepth();
        this.maxDepth = definition.getMaxDepth();
        this.minFluidAmount = definition.getMinFluidAmount();
        this.maxFluidAmount = definition.getMaxFluidAmount();
        this.fluid = new FluidStack(definition.getFluid(), 1);

        List<FluidStack> fluidList2 = new ArrayList<>();
        fluidList2.add(fluid);
        fluidList.add(fluidList2);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.FLUID, fluidList);
        ingredients.setOutputLists(VanillaTypes.FLUID, fluidList);

        ItemStack bucket = FluidUtil.getFilledBucket(fluid);
        if(!bucket.isEmpty()) {
            bucketList.add(Collections.singletonList(bucket));
            ingredients.setInputLists(VanillaTypes.ITEM, bucketList);
            ingredients.setOutputLists(VanillaTypes.ITEM, bucketList);
        }
    }

    public void addTooltip(int slotIndex, boolean input, Object ingredient, List<String> tooltip) {
        if(description != null) {
            tooltip.add(description);
        }
    }

}
