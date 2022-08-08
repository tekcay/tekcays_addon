package tekcays_addon.api.block;


import javax.annotation.Nonnull;

/**
 * Implement this interface on the Block Enum for the Pump Machine Block
 *
 * @see tekcays_addon.common.blocks.blocks.BlockPump.PumpType
 */
public interface IDistillationTraysBlockStats {

    /**
     * @return The Unique Name of the Distillation Trays block
     */
    @Nonnull
    String getName();

    /**
     * @return The Localized Name of the Distillation Trays block
     */
    @Nonnull
    String getLocalizedName();

    /**
     * @return the number of trays of the block
     */
    int getTraysNumber();



}
