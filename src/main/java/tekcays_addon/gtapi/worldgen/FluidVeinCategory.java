package tekcays_addon.gtapi.worldgen;

import gregtech.api.gui.GuiTextures;
import gregtech.api.util.GTStringUtils;
import gregtech.api.util.GTUtility;
import gregtech.integration.jei.basic.BasicRecipeCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import tekcays_addon.api.consts.UnitSymbol;
import tekcays_addon.gtapi.utils.NumberFormatting;
import tekcays_addon.integration.FluidDepositInfo;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

import static tekcays_addon.gtapi.utils.NumberFormatting.tryToConvertToKNumber;

public class FluidVeinCategory extends BasicRecipeCategory<FluidDepositInfo, FluidDepositInfo> {

    protected final IDrawable slot;
    private final int SLOT_CENTER = 79;
    private final int textStartX = 5;
    private int weightLength;
    private final int startPosY = 40;

    private FluidDepositDefinition definition;
    private String name;
    private String description,depositDescriptionLength;
    private int weight;
    private int minDepth, minDepthLength;
    private int maxDepth, maxDepthLength;
    private int maxFluidAmount, maxFluidAmountLength;
    private int minFluidAmount, minFluidAmountLength;


    public FluidVeinCategory(IGuiHelper guiHelper) {
        super("fluid_spawn_location_",
                "fluid.spawnlocation.name",
                guiHelper.createBlankDrawable(176, 166),
                guiHelper);

        this.slot = guiHelper.drawableBuilder(GuiTextures.SLOT.imageLocation, 0, 0, 18, 18).setTextureSize(18, 18).build();

    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, FluidDepositInfo fluidDepositInfo, @Nonnull IIngredients ingredients) {

        IGuiFluidStackGroup fluidStackGroup = recipeLayout.getFluidStacks();

        fluidStackGroup.init(0, true, SLOT_CENTER, 19, 16, 16, 1, false, null);

        fluidStackGroup.addTooltipCallback(fluidDepositInfo::addTooltip);
        fluidStackGroup.set(ingredients);

        this.definition = fluidDepositInfo.getDefinition();
        this.name = fluidDepositInfo.getName();
        this.description = fluidDepositInfo.getDescription();
        this.weight = fluidDepositInfo.getWeight();
        this.minDepth = fluidDepositInfo.getMinDepth();
        this.maxDepth = fluidDepositInfo.getMaxDepth();
        this.minFluidAmount = fluidDepositInfo.getMinFluidAmount();
        this.maxFluidAmount = fluidDepositInfo.getMaxFluidAmount();

    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull FluidDepositInfo fluidVeinInfo) {
        return fluidVeinInfo;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {

        GTStringUtils.drawCenteredStringWithCutoff(name, minecraft.fontRenderer, 176);

        this.slot.draw(minecraft, SLOT_CENTER - 1, 18);

        // Vein Minimum Yield information
        String depositMinDepth = I18n.format("tkcya.jei.fluid.min_depth", minDepth);
        minDepthLength = minecraft.fontRenderer.getStringWidth(depositMinDepth);
        minecraft.fontRenderer.drawString(depositMinDepth, textStartX, startPosY + FONT_HEIGHT + 1, 0x111111);

        // Vein Maximum depth information
        String depositMaxDepth = I18n.format("tkcya.jei.fluid.max_depth", maxDepth);
        maxDepthLength = minecraft.fontRenderer.getStringWidth(depositMaxDepth);
        minecraft.fontRenderer.drawString(depositMaxDepth, textStartX, startPosY + 2 * FONT_HEIGHT + 1, 0x111111);

        // Vein fluid amounf information
        String depositMinFluidAmount = I18n.format("tkcya.jei.fluid.min_fluid_amount",tryToConvertToKNumber(minFluidAmount) + UnitSymbol.LITER);
        minFluidAmountLength = minecraft.fontRenderer.getStringWidth(depositMinFluidAmount);
        minecraft.fontRenderer.drawString(depositMinFluidAmount, textStartX, startPosY + 3 * FONT_HEIGHT + 1, 0x111111);

        // Vein Depletion Amount information
        String depositMaxFluidAmount= I18n.format("tkcya.jei.fluid.max_fluid_amount",tryToConvertToKNumber(maxFluidAmount) + UnitSymbol.LITER);
        maxFluidAmountLength = minecraft.fontRenderer.getStringWidth(depositMaxFluidAmount);
        minecraft.fontRenderer.drawString(depositMaxFluidAmount, textStartX, startPosY + 4 * FONT_HEIGHT + 1, 0x111111);

    }

    @Nonnull
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {

        if(GTUtility.isPointWithinRange(textStartX, startPosY, weightLength, FONT_HEIGHT, mouseX, mouseY)) {
            return Collections.singletonList(I18n.format("gregtech.jei.fluid.weight_hover"));
        }
        else if (GTUtility.isPointWithinRange(textStartX, startPosY + FONT_HEIGHT + 1, minDepthLength, FONT_HEIGHT + 1, mouseX, mouseY)) {
            return Collections.singletonList(I18n.format("gregtech.jei.fluid.min_hover"));
        }
        else if (GTUtility.isPointWithinRange(textStartX, startPosY + 2 * FONT_HEIGHT + 1, maxDepthLength, FONT_HEIGHT + 1, mouseX, mouseY)) {
            return Collections.singletonList(I18n.format("gregtech.jei.fluid.max_hover"));
        }
        else if (GTUtility.isPointWithinRange(textStartX, startPosY + 3 * FONT_HEIGHT + 1, minFluidAmountLength, FONT_HEIGHT + 1, mouseX, mouseY)) {
            return Collections.singletonList(I18n.format("gregtech.jei.fluid.dep_chance_hover"));
        }
        else if (GTUtility.isPointWithinRange(textStartX, startPosY + 4 * FONT_HEIGHT + 1, maxFluidAmountLength, FONT_HEIGHT + 1, mouseX, mouseY)) {
            return Collections.singletonList(I18n.format("gregtech.jei.fluid.dep_amount_hover"));
        }

        return Collections.emptyList();
    }
}
