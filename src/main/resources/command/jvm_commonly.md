# JVM常用命令个人整理

## 通用
程序启动时，加上如下虚拟机参数，可查看参数默认值（JDK1.6以上）
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

## jinfo：Java配置信息⼯具
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

## jmap：Java内存映像⼯具
jmap（Memory Map for Java）命令⽤于⽣成堆转储快照（⼀般称为heapdump或dump⽂件）。如果不使⽤jmap命令，要想获取Java堆转储快照也还有⼀些⽐较“暴⼒”的⼿段：
譬如- XX:+HeapDumpOnOutOfMemoryError参数，可以让虚拟机在内存溢出异常出现之后⾃动⽣成堆转储快照⽂件，通过-XX:+HeapDumpOnCtrlBreak参数则可以使⽤[Ctrl]+[Break]键让虚拟机⽣成堆转储快照⽂件，
⼜或者在Linux系统下通过Kill-3命令发送进程退出信号“恐吓”⼀下虚拟机，也能顺利拿到堆转储快照。jmap的作⽤并不仅仅是为了获取堆转储快照，它还可以查询finalize执⾏队列、Java堆和⽅法区的详细信息，
如空间使⽤率、当前⽤的是哪种收集器等。 和jinfo命令⼀样，jmap有部分功能在Windows平台下是受限的，除了⽣成堆转储快照的-dump选项和⽤于查看每个类的实例、空间占⽤统计的-histo选项在所有操作系统中都可以使⽤之外，
其余选项都只能在Linux/Solaris中使⽤。

```shell
# jmap命令格式
jmap [ option ] vmid
```
option选项的合法值与具体含义如下表所⽰。
jmap工具主要选项
<table>
    <tr>
      <th>选项</th>
      <th>作用</th>
    </tr>
    <tr>
      <td>-dump</td>
      <td>⽣成Java堆转储快照。格式为-dump:[live,] format=b,file=， 其中live⼦参数说明是否只dump出存活的对象</td>
    </tr>
    <tr>
      <td>-finalizerinfo</td>
      <td>显⽰在F-Queue中等待Finalizer线程执⾏finalize⽅法的对象。只在Linux/Solaris平台下有效</td>
    </tr>
    <tr>
      <td>-heap</td>
      <td>显⽰Java堆详细信息， 如使⽤哪种回收器、参数配置、分代状况等。只在Linux/Solaris平台下有效</td>
    </tr>
    <tr>
      <td>-histo</td>
      <td>显⽰堆中对象统计信息，包括类、实例数量、合计容量</td>
    </tr>
    <tr>
      <td>-permstat</td>
      <td>以ClassLoader为统计⼝径显⽰永久代内存状态。只在Linux/Solaris平台下有效</td>
    </tr>
    <tr>
      <td>-F</td>
      <td>当虚拟机进程对-dump选项没有响应时，可使⽤这个选项强制⽣成dump快照。强制模式。如果指定的pid没有响应，可以配合-dump或-histo一起使用。此模式下，不支持live参数。只在Linux/Solaris平台下有效</td>
    </tr>
</table>

```shell
# 使⽤jmap⽣成dump⽂件
# format=b表示以hprof二进制格式转储Java堆的内存
# file=<filename>用于指定快照dump文件的文件名
jmap -dump:format=b,file=/home/develop/dump_demo vmid
# Dumping heap to C:\Users\IcyFenix\eclipse.bin …
# Heap dump file created
```

## jhat：虚拟机堆转储快照分析⼯具
JDK提供jhat（JVM Heap Analysis Tool）命令与jmap搭配使⽤，来分析jmap⽣成的堆转储快照。 jhat内置了⼀个微型的HTTP/Web服务器，⽣成堆转储快照的分析结果后，可以在浏览器中查看。
不过实事求是地说，在实际⼯作中，除⾮⼿上真的没有别的⼯具可⽤，否则多数⼈是不会直接使⽤jhat命令 来分析堆转储快照⽂件的，主要原因有两个⽅⾯。
⼀是⼀般不会在部署应⽤程序的服务器上直接分析堆转储快照，即使可以这样做，也会尽量将堆转储快照⽂件复制到其他机器[1]上进⾏分析，
因为分析⼯作是⼀个耗时⽽且极为耗费硬件资源的过程，既然都要在其他机器上进⾏，就没有必要再受命令⾏⼯具的限制了。 另外⼀个原因是jhat的分析功能相对来说⽐较简陋，
后⽂将会介绍到的VisualVM，以 及专业⽤于分析堆转储快照⽂件的Eclipse Memory Analyzer、IBM HeapAnalyzer等⼯具[2]，都能实现⽐jhat更强⼤专业的分析功能。

> [1] ⽤于分析的机器⼀般也是服务器，由于加载dump快照⽂件需要⽐⽣成dump更⼤的内存，所以⼀般在64位JDK、⼤内存的服务器上进⾏。\
> [2] IBM HeapAnalyzer⽤于分析IBM J9虚拟机⽣成的映像⽂件，各个虚拟机产⽣的映像⽂件格式并不⼀致，所以分析⼯具也不能通⽤。

来看示例
```shell
# 例如我将上面jmap工具得到的dump文件，用jhat指令分析，会生成一个端口为7000的服务，可供查看分析结果
D:\LocalDownload>jhat dump_demo
Reading from dump_demo...
Dump file created Thu Jul 14 16:38:10 CST 2022
Snapshot read, resolving...
Resolving 1596135 objects...
Chasing references, expect 319 dots....................................................................................
Eliminating duplicate references.......................................................................................
Snapshot resolved.
Started HTTP server on port 7000
Server is ready.
```
⽤户在浏览器中输⼊http://localhost:7000/ 可以看到分析结果。 分析结果默认以包为单位进⾏分组显⽰，分析内存泄漏问题主要会使⽤到其中的“Heap Histogram”【页面上的一块】（与jmap -histo功能⼀样）与OQL页签的功能，
前者可以找到内存中总容量最⼤的对象，后者是标准的对象查询语⾔，使⽤类似SQL的语法对内存中的对象进⾏查询统计。如果读者需要了解具体OQL（Object Query Language 对象查询语言）的语法和使⽤⽅法。

## jstack：Java堆栈跟踪⼯具
jstack（Stack Trace for Java）命令⽤于⽣成虚拟机当前时刻的线程快照（⼀般称为threaddump或者 javacore⽂件）。线程快照就是当前虚拟机内每⼀条线程正在执⾏的⽅法堆栈的集合，
⽣成线程快照的⽬的通常是定位线程出现长时间停顿的原因，如线程间死锁、死循环、请求外部资源导致的长时间挂起等，都是导致线程长时间停顿的常见原因。
线程出现停顿时通过jstack来查看各个线程的调⽤堆栈， 就可以获知没有响应的线程到底在后台做些什么事情，或者等待着什么资源。
```shell
# 命令格式
jstack [ option ] vmid option
```
jstack⼯具主要选项：
<table>
    <tr>
      <th>选项</th>
      <th>作用</th>
    </tr>
    <tr>
      <td>-F</td>
      <td>当正常输出的请求不被响应时，强制输出线程堆栈</td>
    </tr>
    <tr>
      <td>-l</td>
      <td>除堆栈外，显⽰关于锁的附加信息</td>
    </tr>
    <tr>
      <td>-m</td>
      <td>如果调⽤到本地⽅法的话，可以显⽰C/C++的堆栈</td>
    </tr>
</table>

```shell
# 例如查询进程下所有的线程信息
jstack vmid
```
常见问题线程定位：
- 程序运行很长时间没有结果，就有可能是死锁问题。若要定位死锁问题，则查看输出信息中包含"deadlock"的关键词
- cpu占用过高
  1. 先用top命令确定cpu占用过高的进程PID
  2. 使用如下ps命令去进一步定位线程对cpu的占用情况，可以看到返回结果的第2列就是线程ID
  ```shell
    # H 显示线程相关的信息
    # -eo 规定要输出哪些信息
    ps H -eo pid,tid,%cpu | grep PID
  ```
  3. 将上一步线程ID与jstack得到的线程信息结合起来，但是jstack打印出来的线程编号是16进制的，例如下面示例，其中：
  “#”号后面跟着的编号，就是对应Java中的"Thread.currentThread().getId()"代码得到的id值；而tid后面的值则是这个tid属性的内存地址；nid是Java线程对应的本地操作系统线程号（16进制）
  ```
  "default-pool-8" #70 prio=5 os_prio=0 tid=0x00007f82c0039800 nid=0x1ebd waiting on condition [0x00007f83002cf000]
   java.lang.Thread.State: WAITING (parking)
        at sun.misc.Unsafe.park(Native Method)
        - parking to wait for  <0x00000006c7320c70> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2039)
        at java.util.concurrent.LinkedBlockingQueue.take(LinkedBlockingQueue.java:442)
        at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1074)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1134)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:750)
  ```

从JDK 5起，java.lang.Thread类新增了⼀个getAllStackTraces()⽅法⽤于获取虚拟机中所有线程的 StackTraceElement对象。
使⽤这个⽅法可以通过简单的⼏⾏代码完成jstack的⼤部分功能，在实际项⽬中不妨调⽤这个⽅法做个管理员页⾯，可以随时使⽤浏览器来查看线程堆栈。
```html
<%@ page import="java.util.Map"%> 
<html> 
    <head> <title>服务器线程信息</title> </head> 
    <body> <pre> 
        <% for (Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStack-Traces().entrySet()) { 
         Thread thread = (Thread) stackTrace.getKey(); 
             StackTraceElement[] stack = (StackTraceElement[]) stackTrace.getValue(); 
             if (thread.equals(Thread.currentThread())){ 
                continue; 
             }
             out.print("\n线程：" + thread.getName() + "\n"); 
             for (StackTraceElement element : stack) { 
                out.print("\t"+element+"\n"); 
             }
         } 
        %>
    </pre> </body>
</html>
```


