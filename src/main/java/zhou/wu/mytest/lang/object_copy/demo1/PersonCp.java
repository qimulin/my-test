package zhou.wu.mytest.lang.object_copy.demo1;

/**
 * @author lin.xc
 * @date 2021/4/28
 **/
public class PersonCp extends Person implements Cloneable{

    public PersonCp(){

    }

    public PersonCp(int age, String name) {
        super(age, name);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
