package tekcays_addon.api.units;

import static tekcays_addon.gtapi.consts.TKCYAValues.SECOND;
import static tekcays_addon.gtapi.consts.TKCYAValues.MINUTE;

public interface ITimeFormatting {

    /**
     * A method to convert duration in {@code ticks} into {@code seconds} or {@code minutes}.
     * @param durationInTicks must be a multiple of 20 or 1200.
     * @return a {@code String} containing the value of the duration with its unit.
     */
    default String convertTime(int durationInTicks) {
        if (durationInTicks > SECOND && durationInTicks < MINUTE) {
            return String.format("%d sec.", durationInTicks / SECOND);
        }
        else return String.format("%d min.", durationInTicks / MINUTE);
        
    }

}
