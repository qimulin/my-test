package zhou.wu.mytest.lang.object_copy.demo1;

import lombok.Data;

/**
 * @author lin.xc
 * @date 2021/7/13
 **/
@Data
public class Address implements Cloneable{

    // 地市
    public String city;
    // 区县
    public String county;
    // 乡镇街道
    public String street;

    @Override
    protected Address clone() throws CloneNotSupportedException {
        return (Address) super.clone();
    }

    @Override
    public String toString() {
        return super.toString()+"   Address{" +
                "city='" + city + '\'' +
                ", county=" + county +
                ", street=" + street +
                '}';
    }
}
