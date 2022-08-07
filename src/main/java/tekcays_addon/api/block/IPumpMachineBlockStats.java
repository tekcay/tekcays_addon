package tekcays_addon.api.block;


import javax.annotation.Nonnull;

/**
 * Implement this interface on the Block Enum for the Pump Machine Block
 *
 * @see tekcays_addon.common.blocks.blocks.BlockPump.PumpType
 */
public interface IPumpMachineBlockStats {

    /**
     * @return The Unique Name of the Pump Machine
     */
    @Nonnull
    String getName();

    /**
     * @return The Localized Name of the Pump Machine
     */
    @Nonnull
    String getLocalizedName();

    /**
     * @return the vacuum the Pump Machine can reach
     */
    int getTargetVacuum();

    /**
     * @return the required voltage to run the pump
     */
    int getVoltage();


}
