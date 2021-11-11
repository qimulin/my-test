package zhou.wu.mytest.lambda.custom_function;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lin.xc
 * @date 2021/1/7
 **/
public class MainTest {
    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        String str = "1234567";
        int maxInx = 5;
        if(str.length()>=maxInx){
            System.out.println("有截取："+str.substring(0, maxInx));
        }else{
            System.out.println("无截取："+str);
        }

/*        List<AObj> aList = new ArrayList<>();
        aList.add(new AObj(1, "张三"));
        aList.add(new AObj(2, "李四"));
        aList.add(new AObj(3, "王五"));
        ToOtherObjectListFunction<AObj, BObj> func = (a,b) ->{
//            Integer bInt = (Integer)b[0];
//            BObj bObj = new BObj();
//            bObj.setBId(a.getAId()+bInt);
//            bObj.setBName(a.getAName());
//            return bObj;
            return null;
        };
        List<BObj> bObjs = func.castList(aList, 10);
        System.out.println(bObjs);*/
    }
}
