package javaopt;

import java.io.*;

public class ALoader extends ClassLoader {
    private final String pathtobin;
    private final String packageName;

    public ALoader(String pathtobin, String packageName, ClassLoader parent) {
        super(parent);
        this.pathtobin = pathtobin;
        this.packageName = packageName;
    }


    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            byte b[] = readBytes(pathtobin + className + ".class");
            return defineClass(packageName + className, b, 0, b.length);
        } catch (IOException ex) {
            return super.findClass(className);
        }
    }

    private byte[] readBytes(String path) throws IOException {
        InputStream is = new FileInputStream(new File(path));

        byte[] bytes = is.readAllBytes();

        is.close();
        return bytes;
    }
}
