package tekcays_addon.api.metatileentity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum LogicType {

    NO_ENERGY,
    NO_OVERCLOCK,
    DETECTOR,
    PRESSURE,
    HEAT,
    ROTATION,
    MULTI_AMPER,
    NO_MUFFLER,
    NO_MAINTENANCE;

    public static List<LogicType> setLogicType(LogicType logicType, LogicType... logicTypes) {
        List<LogicType> list = new ArrayList<>();
        list.add(logicType);
        Collections.addAll(list, logicTypes);
        return list;
    }
}
