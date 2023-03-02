package tekcays_addon.api.consts;

public enum DataIds {

    AIR_FLUID_STACK(300, "airFluidStack"),
    PRESSURIZED_FLUID_STACK(301, "fluidStack");

    private final int id;
    private final String name;

    DataIds(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
