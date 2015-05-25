package kwtransient;

import com.yimin.javase.kwtransient.ClassWithTransientFields;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * java-libs
 * Created by WuYimin on 2015/5/25.
 */
public class ClassWithTransientFieldsTest {

    @Test
    public void testTransientFieldsIgnoredDuringSerialization() throws Exception {
        ClassWithTransientFields transientFieldsInstance = new ClassWithTransientFields("Name", "Male");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(transientFieldsInstance);


        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

        ClassWithTransientFields deSerializationInstance = (ClassWithTransientFields) objectInputStream.readObject();

        System.out.println(transientFieldsInstance.toString());
        System.out.println(deSerializationInstance.toString());

        Assert.assertEquals("no-transient field name", "Name", deSerializationInstance.getName());
        Assert.assertNotEquals("transient field sex", "Male", deSerializationInstance.getSex());


        transientFieldsInstance = new ClassWithTransientFields();

        byteArrayOutputStream = new ByteArrayOutputStream();
        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(transientFieldsInstance);


        objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

        deSerializationInstance = (ClassWithTransientFields) objectInputStream.readObject();

        System.out.println(transientFieldsInstance.toString());
        System.out.println(deSerializationInstance.toString());

        Assert.assertEquals("no-transient field name", "DefaultName", deSerializationInstance.getName());
        Assert.assertNotEquals("transient field sex", "Male", deSerializationInstance.getSex());
    }
}
