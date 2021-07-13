package zhou.wu.mytest.lang.object_copy.demo1;

/**
 * @author lin.xc
 * @date 2021/4/28
 **/
public class PersonCp extends Person implements Cloneable{

    private Address address;

    public PersonCp(){

    }

    public PersonCp(int age, String name) {
        super(age, name);
    }

    @Override
    protected PersonCp clone() throws CloneNotSupportedException {
        PersonCp result = (PersonCp)super.clone();
        result.setAddress(getAddress().clone());
        return result;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        if(getAddress()==null){
            return super.toString();
        }else{
            return super.toString() + " --- " + getAddress().toString();
        }
    }

}
