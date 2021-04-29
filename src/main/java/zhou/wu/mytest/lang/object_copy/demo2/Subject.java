package zhou.wu.mytest.lang.object_copy.demo2;

/**
 * @author lin.xc
 * @date 2021/4/29
 **/
public class Subject {
    private String name;

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "[Subject: " + this.hashCode() + ",name:" + name + "]";
    }
}
