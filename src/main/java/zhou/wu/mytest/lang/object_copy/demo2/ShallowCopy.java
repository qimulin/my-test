package zhou.wu.mytest.lang.object_copy.demo2;

/**
 * @author lin.xc
 * @date 2021/4/29
 **/
public class ShallowCopy {
    public static void main(String[] args) {
        Subject subject = new Subject("语文");
        Student studentA = new Student();
        studentA.setSubject(subject);
        studentA.setName("张三");
        studentA.setAge(20);
        Student studentB = (Student) studentA.clone();
        studentB.setName("李四");
        studentB.setAge(18);
        Subject subjectB = studentB.getSubject();
        subjectB.setName("王五");
        System.out.println("studentA:" + studentA.toString());
        System.out.println("studentB:" + studentB.toString());
    }
}
