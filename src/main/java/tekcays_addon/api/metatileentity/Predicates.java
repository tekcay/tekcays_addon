package tekcays_addon.api.metatileentity;

import gregtech.api.pattern.TraceabilityPredicate;

import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.air;

public class Predicates {

    public static TraceabilityPredicate isAir(String matchContext) {

        return new TraceabilityPredicate((blockWorldState) -> {
            if (air().test(blockWorldState)) {
                blockWorldState.getMatchContext().increment(matchContext, 1);
                return true;
            } else
                return false;
        });
    }
}
