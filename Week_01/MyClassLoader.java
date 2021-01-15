package Week_01;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName MyClassLoader
 * @Description 自定义类加载器
 * @Author Piero Lin <linyouxu@qq.com>
 * @Date 2021/1/15 17:26
 **/
public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<?> helloClass = new MyClassLoader().findClass("Hello");
        Object helloObject = helloClass.newInstance();
        Method hello = helloClass.getMethod("hello");
        hello.invoke(helloObject, null);

    }

    @Override
    protected Class<?> findClass(String name) {
        InputStream inputStream = getClass().getResourceAsStream(name + ".xlass");
        byte[] bytes = null;
        try {
            bytes = getFileByteArray(inputStream);
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    private static byte[] getFileByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
