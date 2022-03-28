package zhou.wu.mytest.collection.list;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhou.wu
 * @description: TODO
 * @date 2022/3/28
 **/
public class ListMod {

    public static void main(String[] args) {
        String removeEle = "1";
        iteratorRemove(removeEle);
//        foreachRemove(removeEle);
        foriRemove(removeEle);
    }

    /**
     * 用迭代器操作移除
     * */
    public static void iteratorRemove(String removeEle){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (removeEle.equals(item)) {
                iterator.remove();
            }
        }
        System.out.println(JSON.toJSONString(list));
    }

    /**
     * foreach里进行移除
     * */
    public static void foreachRemove(String removeEle){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        for (String item : list) {
            if (removeEle.equals(item)) {
                list.remove(item);
            }
        }
        System.out.println(JSON.toJSONString(list));
    }

    /**
     * foreach里进行移除
     * */
    public static void foriRemove(String removeEle){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        for (int i=0; i<list.size(); i++) {
            if (removeEle.equals(list.get(i))) {
                list.remove(list.get(i));
            }
        }
        System.out.println(JSON.toJSONString(list));
    }

}
