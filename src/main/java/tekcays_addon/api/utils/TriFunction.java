package tekcays_addon.api.utils;


@FunctionalInterface
public interface TriFunction<A, B, C, D> {

    D apply(A var1, B var2, C var3);
}
