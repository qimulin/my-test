package zhou.wu.mytest.serialize;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 在序列化的过程中，有些数据字段我们不想将其序列化，对于此类字段我们只需要在定义时给它加上transient关键字即可，
 * 对于transient字段序列化机制会跳过不会将其写入文件，当然也不可被恢复。但有时我们想将某一字段序列化，
 * 但它在SDK中的定义却是不可序列化的类型，这样的话我们也必须把他标注为transient，可是不能写入又怎么恢复呢？
 * 好在序列化机制为包含这种特殊问题的类提供了如下的方法定义：
 * private void readObject(ObjectInputStream in) throws
 * IOException, ClassNotFoundException;
 * private void writeObject(ObjectOutputStream out) throws
 * IOException;
 * (注：这些方法定义时必须是私有的，因为不需要你显示调用，序列化机制会自动调用的)
 * 使用以上方法我们可以手动对那些你又想序列化又不可以被序列化的数据字段进行写出和读入操作。
 * 下面是一个典型的例子，java.awt.geom包中的Point2D.Double类就是不可序列化的，因为该类没有实现Serializable接口，
 * 在我的例子中将把它当作LabeledPoint类中的一个数据字段，并演示如何将其序列化！
 * @author Lin.xc
 * @date 2020/5/12
 */
public class LabeledPoint implements Serializable {
    public LabeledPoint(String str, double x, double y) {
        label = str;
        tStr = "transient string";
        point = new Point2D.Double(x, y);
    }
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeDouble(point.getX());
        out.writeDouble(point.getY());
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        double x = in.readDouble() + 1.0;
        double y = in.readDouble() + 1.0;
        point = new Point2D.Double(x, y);
    }
    public String toString() {
        return getClass().getName()+ "[label = " + label
                + ", point.getX() = " + point.getX()
                + ", point.getY() = " + point.getY()
                + "，tStr = "+tStr+"]";
    }
    private String label;
    transient private String tStr;
    transient private Point2D.Double point;
}
