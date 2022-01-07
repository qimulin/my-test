package zhou.wu.mytest;
/**
 * 当类编译成class文件，即二进制字节码（主要包含：类基本信息、常量池、类方法定义-包含了虚拟机指令）
 * 但是我们通常无法直接阅读二进制字节码，所以需要一些解释，用JDK提供的javap工具
 * javap是JDK自带的反汇编器，可以查看java编译器为我们生成的字节码。通过它，我们可以对照源代码和字节码，从而了解很多编译器内部的工作。
 * */
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("hello world");
    }
}
