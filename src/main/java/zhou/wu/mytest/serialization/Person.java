package zhou.wu.mytest.serialization;

import java.io.Serializable;

/**
 * @author zhou.wu
 * @date 2024/1/12
 **/
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void displayInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }

}