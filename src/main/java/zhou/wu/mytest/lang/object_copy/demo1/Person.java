package zhou.wu.mytest.lang.object_copy.demo1;

/**
 * @author lin.xc
 * @date 2021/4/28
 **/
public class Person {

    private String name;
    private int age;

    public Person(){
    }

    public Person(int age, String name) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return super.toString()+"   Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
