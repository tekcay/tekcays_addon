package tekcays_addon.api.capability;

import tekcays_addon.api.utils.TKCYAValues;

import javax.annotation.Nonnull;

public interface IHeatContainer {

    /**
     * @return the amount of heat in the container
     */
    int getHeat();

    /**
     * Set the amount of heat in the container
     *
     * @param amount the amount to set
     */
    void setHeat(int amount);

    /**
     * Change the amount of heat in the container by a given amount
     *
     * @param amount   the amount to change by
     * @param simulate whether to actually change the value or not
     * @return true if the change is safe, else false
     */
    default boolean changeHeat(int amount, boolean simulate) {
        if (simulate) return isHeatSafe(getHeat() + amount);
        setHeat(getHeat() + amount);
        return isHeatSafe();
    }

    /**
     * @return the minimum heat this container can handle
     */
    int getMinHeat();

    /**
     * @return the maximum heat this container can handle
     */
    int getMaxHeat();

    /**
     * @return true if the Heat is safe for the container, else false
     */
    default boolean isHeatSafe() {
        return isHeatSafe(getHeat());
    }

    /**
     * @param Heat the Heat to check
     * @return true if the Heat is safe for the container, else false
     */
    default boolean isHeatSafe(int Heat) {
        return Heat <= getMaxHeat() && Heat >= getMinHeat();
    }

    /**
     * @return if the Heat is around atmospheric levels
     */
    default boolean isNormalHeat() {
        return getHeat() == 0;
    }


    IHeatContainer EMPTY = new IHeatContainer() {

        @Override
        public int getMinHeat() {
            return TKCYAValues.MIN_HEAT;
        }

        @Override
        public int getMaxHeat() {
            return TKCYAValues.MAX_HEAT;
        }

        @Override
        public int getHeat() {
            return 0;
        }

        @Override
        public void setHeat(int amount) {
        }

    };
}
