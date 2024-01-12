package zhou.wu.mytest.serialization;

import java.io.*;

/**
 * 演示如何通过序列化机制创建Java对象
 * @author zhou.wu
 * @date 2024/1/12
 **/
public class SerializationExample {

    /**
     * 在这个例子中，Person类实现了Serializable接口，使得它的对象可以被序列化。serializeObject方法将Person对象序列化到文件中，
     * 而deserializeObject方法从文件中反序列化对象。主程序创建一个Person对象，将其序列化到文件，然后再从文件中反序列化对象，并显示信息。
     * */
    public static void main(String[] args) {
        // 创建一个Person对象
        Person person = new Person("John Doe", 25);

        // 将对象序列化到文件
        serializeObject(person, "person.ser");

        // 从文件中反序列化对象
        Person deserializedPerson = deserializeObject("person.ser");

        // 显示反序列化后的对象信息
        if (deserializedPerson != null) {
            deserializedPerson.displayInfo();
        }
    }

    /**
     * 序列化对象到文件
     * */
    private static void serializeObject(Object obj, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(obj);
            System.out.println("Object serialized and saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中反序列化对象
     * */
    private static Person deserializeObject(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Object obj = ois.readObject();
            if (obj instanceof Person) {
                System.out.println("Object deserialized from " + fileName);
                return (Person) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
