package tekcays_addon.api.metatileentity.predicates;

import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.air;

import gregtech.api.pattern.TraceabilityPredicate;

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

    public static TraceabilityPredicate heightIndicatorPredicate(TraceabilityPredicate predicate, String matchContext,
                                                                 int incrementValue) {
        return new TraceabilityPredicate((blockWorldState) -> {
            if (predicate.test(blockWorldState)) {
                blockWorldState.getMatchContext().increment(matchContext, incrementValue);
                return true;
            } else
                return false;
        });
    }
}
