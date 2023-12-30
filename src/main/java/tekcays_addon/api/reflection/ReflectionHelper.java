package tekcays_addon.api.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionHelper {

    public static Field getPrivateStaticFinalField(Class<?> clazz, String fieldName) throws NoSuchFieldException,
                                                                                     IllegalAccessException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        return field;
    }

    public static <T> void modifyPrivateStaticFinalField(Class<?> fieldClass, String fieldName, Class<T> clazz,
                                                         T value) throws NoSuchFieldException, IllegalAccessException {
        getPrivateStaticFinalField(fieldClass, fieldName).set(clazz, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getCastedPrivateStaticFinalField(Class<?> fieldClass,
                                                         String fieldName) throws NoSuchFieldException,
                                                                           IllegalAccessException {
        return (T) getPrivateStaticFinalField(fieldClass, fieldName).get(null);
    }
}
