package zhou.wu.mytest.serialize;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Lin.xc
 * @date 2020/5/12
 */
public class TransientTest {
    public static void main(String[] args) {
        LabeledPoint label = new LabeledPoint("Book", 5.00, 5.00);
        try {
            System.out.println("写入前："+label);// 写入前
            ObjectOutputStream out = new ObjectOutputStream(new
                    FileOutputStream("Label.txt"));
            out.writeObject(label);
            out.close();
            System.out.println("写入后："+label);// 写入后，不会影响原对象呀
            ObjectInputStream in = new ObjectInputStream(new
                    FileInputStream("Label.txt"));
            LabeledPoint label1 = (LabeledPoint) in.readObject();
            in.close();
            System.out.println("读出并加1.0后:"+label1);// 读出并加1.0后
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
