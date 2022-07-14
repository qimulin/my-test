# JVM常用命令个人整理

## 通用
查看参数默认值（JDK1.6以上）
```shell
java -XX:+PrintFlagsFinal
```
## jps虚拟机进程状况工具
JDK很多小工具的名字都是参考了UNIX命令的命名方式，jps（JVM Process Status Tool）是其中的典型。除了名字像UNIX的ps命令之外，
它的功能也和ps命令类似：可以列出正在运行的虚拟机进程，并显示虚拟机执行主类（Main Class，main()函数所在的类）名称以及这些进程的本地虚拟机唯一ID（LVMID，Local Virtual MachineIdentifier）。
虽然功能比较单一，但它绝对是使用频率最高的JDK命令行工具，因为其他的JDK工具大多需要输入它查询到的LVMID来确定要监控的是哪一个虚拟机进程。
对于本地虚拟机进程来说，LVMID与操作系统的进程ID（PID，Process Identifier）是一致的，使用Windows的任务管理器或者UNIX的ps命令也可以查询到虚拟机进程的LVMID，
但如果同时启动了多个虚拟机进程，无法根据进程名称定位时，那就必须依赖jps命令显示主类的功能才能区分了。
```shell
# 命令格式
jps  [options] [hostid]
```
jps工具主要选项
<table>
    <tr>
      <th>选项</th>
      <th>作用</th>
    </tr>
    <tr>
      <td>-q</td>
      <td>只输出LVMID、省略主类的名称</td>
    </tr>
    <tr>
      <td>-m</td>
      <td>输出虚拟机进程启动时传递给主类main()函数的参数</td>
    </tr>
    <tr>
      <td>-l</td>
      <td>输出主类的全名，如果进程执行的是JAR包，则输出JAR路径</td>
    </tr>
    <tr>
      <td>-v</td>
      <td>输出虚拟机进程启动时的JVM参数</td>
    </tr>
</table>

## jstat：虚拟机统计信息监视⼯具
jstat（JVM Statistics Monitoring Tool）是⽤于监视虚拟机各种运⾏状态信息的命令⾏⼯具。它可以显⽰本地或者远程[1]虚拟机进程中的类加载、内存、垃圾收集、即时编译等运⾏时数据，
在没有GUI图形界⾯、只提供了纯⽂本控制台环境的服务器上，它将是运⾏期定位虚拟机性能问题的常⽤⼯具。
```shell
# 命令格式
# interval 查询间隔，默认单位ms
# count 要查询的次数
jstat [ option vmid [interval[s|ms] [count]] ]
```
选项option代表⽤户希望查询的虚拟机信息，主要分为三类：类加载、垃圾收集、运⾏期编译状况。详细请参考下表。

jstat工具主要选项
<table>
    <tr>
      <th>选项</th>
      <th>作用</th>
    </tr>
    <tr>
      <td>-class</td>
      <td>监视类加载、卸载数量、总空间以及类装载所耗费的时间</td>
    </tr>
    <tr>
      <td>-gc</td>
      <td>监视Java堆状况， 包括Eden区、2个Survivor区、⽼年代、永久代等的容量，已⽤空间，垃圾收集时间合计等信息</td>
    </tr>
    <tr>
      <td>-gccapacity</td>
      <td>监视内容与-gc基本相同， 但输出主要关注Java堆各个区域使⽤到的最⼤、最⼩空间</td>
    </tr>
    <tr>
      <td>-gcutil</td>
      <td>监视内容与-gc基本相同，但输出主要关注已使⽤空间占总空间的百分⽐</td>
    </tr>
    <tr>
      <td>-gccause</td>
      <td>与-gcutil功能⼀样， 但是会额外输出导致上⼀次垃圾收集产⽣的原因</td>
    </tr>
    <tr>
      <td>-gcnew</td>
      <td>监视新⽣代垃圾收集状况</td>
    </tr>
    <tr>
      <td>-gcnewcapacity</td>
      <td>监视内容与-gcnew基本相同， 输出主要关注使⽤到的最⼤、最⼩空间</td>
    </tr>
    <tr>
      <td>-gcold</td>
      <td>监视⽼年代垃圾收集状况</td>
    </tr>
    <tr>
      <td>-gcoldcapacity</td>
      <td>监视内容与-g cold基本相同， 输出主要关注使⽤到的最⼤、最⼩空间</td>
    </tr>
    <tr>
      <td>-gcpermcapacity</td>
      <td>输出永久代使⽤到的最⼤、最⼩空间</td>
    </tr>
    <tr>
      <td>-compiler</td>
      <td>输出即时编译器编译过的⽅法、耗时等信息</td>
    </tr>
    <tr>
      <td>-printcompilation</td>
      <td>输出已经被即时编译的⽅法</td>
    </tr>
</table>

## jinfo
jinfo（Configuration Info for Java）的作⽤是实时查看和调整虚拟机各项参数。使⽤jps命令的-v参数可以查看虚拟机启动时显式指定的参数列表，
但如果想知道未被显式指定的参数的系统默认值，除了去找资料外，就只能使⽤jinfo的-flag选项进⾏查询了（如果只限于JDK 6或以上版本的话，
使⽤java- XX:+PrintFlagsFinal查看参数默认值也是⼀个很好的选择）。jinfo还可以使⽤-sysprops选项把虚拟机 进程的System.getProperties()的内容打印出来。
这个命令在JDK 5时期已经随着Linux版的JDK发布，当 时只提供了信息查询的功能，JDK 6之后，jinfo在Windows和Linux平台都有提供，
并且加⼊了在运⾏期修改部分参数值的能⼒（可以使⽤-flag[+|-]name或者-flag name=value在运⾏期修改⼀部分运⾏期可写的虚拟机参数值）。
在JDK 6中，jinfo对于Windows平台功能仍然有较⼤限制，只提供了最基本的-flag选项。

```shell
# 命令格式
jinfo [ option ] pid
```

例如在启动java程序的时候，指定了参数MaxHeapSize，那此时该参数值就不是默认值了，可以通过以下命令查看当前配置的值
```shell
# 示例：查询进程对应的MaxHeapSize参数值
jinfo -flag MaxHeapSize 进程号
```

## 表格示例
