package jvm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        HelloClassLoader classLoader = new HelloClassLoader();
        Class<?> hello = classLoader.findClass("Hello");
        Object object = hello.newInstance();
        hello.getMethod("hello").invoke(object);
    }


    /**
     * Finds the class with the specified <a href="#name">binary name</a>.
     * This method should be overridden by class loader implementations that
     * follow the delegation model for loading classes, and will be invoked by
     * the {@link #loadClass <tt>loadClass</tt>} method after checking the
     * parent class loader for the requested class.  The default implementation
     * throws a <tt>ClassNotFoundException</tt>.
     *
     * @param name The <a href="#name">binary name</a> of the class
     * @return The resulting <tt>Class</tt> object
     * @throws ClassNotFoundException If the class could not be found
     * @since 1.2
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
//        String path = "C:\\workspace\\week01\\src\\jvm\\Hello.class";
//        String path = "C:\\workspace\\week01\\src\\jvm\\Hello.xlass";
        String path = System.getProperty("user.dir")+"\\src\\jvm\\Hello.xlass";
        byte[] bytes = null;
        File file = new File(path);
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }

            bytes = baos.toByteArray();

            fis.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0 ; i < bytes.length ; i++) {
            bytes[i] = (byte)(255-bytes[i]);
        }
        return defineClass(name , bytes , 0 , bytes.length);
    }
}
