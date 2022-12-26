package tekcays_addon.api.recipes.recipeproperties;

import tekcays_addon.api.utils.IPressureFormatting;

public class MinPressureProperty extends PressureProperty implements IPressureFormatting {

    public static final String KEY = "minPressure";
    @Override
    public String getKey() {
        return KEY;
    }
    private static MinPressureProperty INSTANCE;

    private MinPressureProperty() {
        super();
    }

    public static MinPressureProperty getInstance() {
        if (INSTANCE == null) INSTANCE = new MinPressureProperty();
        return INSTANCE;
    }

}
