package tekcays_addon.api.recipes.recipeproperties;

import tekcays_addon.api.utils.IPressureFormatting;

public class MaxPressureProperty extends PressureProperty implements IPressureFormatting {

    public static final String KEY = "maxPressure";
    @Override
    public String getKey() {
        return KEY;
    }
    private static MaxPressureProperty INSTANCE;

    private MaxPressureProperty() {
        super();
    }

    public static MaxPressureProperty getInstance() {
        if (INSTANCE == null) INSTANCE = new MaxPressureProperty();
        return INSTANCE;
    }

}
