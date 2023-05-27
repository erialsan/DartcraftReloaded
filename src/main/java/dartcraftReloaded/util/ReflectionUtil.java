package dartcraftReloaded.util;

import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Method;

public class ReflectionUtil {
    public static void callPrivateMethod(Class theClass, Object obj, String name, String obsName) {
        try {
            Method m = ReflectionHelper.findMethod(theClass, name, obsName);
            m.invoke(obj);
        }
        catch (Exception ignored) {}
    }
}
