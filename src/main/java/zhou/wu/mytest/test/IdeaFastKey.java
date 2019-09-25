package zhou.wu.mytest.test;

import java.io.File;

/**
 * @author Lin.xc
 * @date 2019/9/25
 */
public class IdeaFastKey implements intf{
    // Ctr+shift+U 大小写转化
    private final static String CONSTANT_F = "lin.xc";

    public static void main(String[] args) {
        /**
         * 自动代码演示
         * */
        // Ctrl+Alt+T 生成try catch 或者 Alt+enter
        try {
            File file = new File("D://text.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = "lxc";
        // Shift+F6 重构-重命名 (包、类、方法、变量、甚至注释等)
        // CTRL+J 自动代码 展示类型sout、fori等自动代码供选择
        // ALT+/ 代码提示，一般情况下，自己会提示
        // CTRL+ALT+L 格式化代码
        // Ctrl+Shift+J，整合两行为一行
        // CTRL+P 方法参数提示
        // CTRL+Q，可以看到当前方法的声明
        String m1
                = testMethod("jay");
        // CTRL+ALT+I 自动缩进 某种程度上用CTRL+ALT+L 格式化代码 也行
        // Ctrl+Alt+V 提取变量
        String mayday = "mayday";
        String m2 = testMethod(mayday);
        // CTRL+E 最近更改的代码
    }

    // Ctrl + O 重写方法
    // Ctrl + I 实现方法
    @Override
    public void impl() {
        // TODO: 实现方法
        System.out.println("实现方法");
    }

    /**
     * @author Lin.xc
     * @decription 方法描述
     * */
    private static String testMethod(String p1){
        return "2019092501"+p1;
    }
}

/**
 * 查询快捷键
 * Ctrl＋Shift＋Backspace可以跳转到上次编辑的地
 * CTRL+ALT+ left/right 前后导航编辑过的地方
 * F4 查找变量来源
 * F3 向下查找关键字出现位置
 * Ctrl+D 复制行
 * Ctrl+H 显示类结构图
 * SHIFT+F3 向上一个关键字出现位置
 * 选中文本，按Alt+F3 ，高亮相同文本，F3逐个往下查找相同文本
 * Ctrl+F12 浮动显示当前文件的结构
 * ALT+7 靠左窗口显示当前文件的结构
 * ALT+F7 找到你的函数或者变量或者类的所有引用到的地方
 * CTRL+SHIFT+R 在指定窗口替换文本
 * Ctrl+B 快速打开光标处的类或方法
 * CTRL+ALT+B 找所有的子类
 * CTRL+SHIFT+B 找变量的类
 * Ctrl+Shift+上下键 上下移动代码
 * Ctrl+W 选中代码，连续按会一层一层往外选中
 * CTRL+ALT+F7 找到你的函数或者变量或者类的所有引用到的地方
 * Ctrl+Shift+Alt+N 查找类中的方法或变量
 * F2 或Shift+F2 高亮错误或警告快速定位
 * ALT+SHIFT+C 查找修改的文件
 * Ctrl+Up/Down 光标跳转到代码编辑串口屏幕的第一行或最后一行下
 * Alt+ left/right 切换代码视图
 * ALT+ ↑/↓ 在方法间快速移动定位，就是跳上/下一个方法
 * Alt+6 全文查找TODO
 * */

/**
 * 其他快捷键
 * CTRL+ALT+F12 资源管理器打开文件夹 我本机有冲突
 * SHIFT+ALT+INSERT 竖编辑模式 同目录创建文件等，可以按这个快捷键直接打开窗口
 * ALT+F1 查找文件所在目录位置 没什么用，可以直接用左上角圆圈图标按钮定位
 * CTRL+F4 关闭当前窗口
 * Ctrl+Alt+V，可以引入变量。例如：new String(); 自动导入变量定义
 * trl+~，快速切换方案（界面外观、代码风格、快捷键映射等菜单）
 * */
interface intf{

    default void impl(){
        System.out.println("default修饰接口中有方法体的方法");
    }
}
