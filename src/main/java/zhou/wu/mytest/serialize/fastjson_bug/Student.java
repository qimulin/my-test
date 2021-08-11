package zhou.wu.mytest.serialize.fastjson_bug;

import java.io.IOException;

/**
 * @author lin.xc
 * @date 2021/8/11
 **/
public class Student {

    private String name;
    private int age;

    public Student(){
        System.out.println("构造函数");
    }

    public String getName() {
        System.out.println("getName");
        return name;
    }

    public void setName(String name) {
        // 模拟恶意代码，调用系统的计算器
        try {
            Runtime.getRuntime().exec("calc.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("setName");
        this.name = name;
    }

    public int getAge() {
        System.out.println("getAge");
        return age;
    }

    public void setAge(int age) {
        System.out.println("setAge");
        this.age = age;
    }
}
