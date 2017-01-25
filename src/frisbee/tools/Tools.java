package frisbee.tools;

import java.lang.reflect.Field;

public class Tools {


    public static String toString(Object instance) {

        Class<?> thisClass = instance.getClass();
        String out = thisClass.getSimpleName() + " {";

        boolean containsField = false;
        for (Field f : thisClass.getDeclaredFields()) {

            boolean isAccessible = f.isAccessible();

            f.setAccessible(true);
            Object value;
            try {
                value = f.get(instance);

            } catch (Exception e) {
                value = "null";
            }
            String fieldName = f.getName();
            out += fieldName + "=" + value + ",";
            containsField = true;
            f.setAccessible(isAccessible);

        }

        if (containsField) {
            out = out.substring(0, out.length() - 1);
        }

        out += "}";
        return out;
    }

}
