package javaopt;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String path = args.length == 0 ? "C:\\Users\\User\\IdeaProjects\\javaopt3\\A\\" : args[0];

        File dir = new File(path);
        String[] modules = dir.list();

        ALoader aLoader = new ALoader(path, ClassLoader.getSystemClassLoader());

        assert modules != null;

        for (String module: modules) {
            String moduleName = module.split(".class")[0];
            Class<?> loadClass = aLoader.loadClass(moduleName);

            if(Arrays.stream(loadClass.getDeclaredMethods())
                    .anyMatch(method -> method.getName().equals("getSecurityMessage")
                            && method.getReturnType().isAssignableFrom(String.class)
                            && method.getParameterCount() == 0)
                            ) {
                Constructor<?> constructor = loadClass.getConstructor();
                Object obj = constructor.newInstance();

                Method getSecurityMessageMethod = loadClass.getDeclaredMethod("getSecurityMessage");
                getSecurityMessageMethod.setAccessible(true);
                String result = (String) getSecurityMessageMethod.invoke(obj);

                System.out.println(loadClass.getPackageName() + "." + loadClass.getName());
                System.out.println(result);
            }
        }
    }
}
