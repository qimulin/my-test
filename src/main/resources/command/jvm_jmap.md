# Java内存映像⼯具jmap实用命令个人整理

## jmap基本介绍
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

## 实用参数
### -dump
一般情况下，在Java应用启动的时候，可以添加如下启动命令配置，使其能在堆内存溢出的时候自动生成dump文件
```text
# file参数，如果指定绝对路径，那文件名前的目录必须存在；如果只指定文件名，则通常会生成在项目根目录下
-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=[file]
```
-XX:+HeapDumpOnOutOfMemoryError和-XX:HeapDumpPath是两个用于控制Java堆转储文件生成的JVM选项。\
-XX:+HeapDumpOnOutOfMemoryError选项启用堆转储文件生成功能，当JVM发生OutOfMemoryError错误时，会自动生成一个Java堆转储文件。该文件可以用于分析内存问题，查看堆中对象的分布和数量等信息。\
-XX:HeapDumpPath选项指定生成的Java堆转储文件的路径和文件名。例如，-XX:HeapDumpPath=/path/to/dump.hprof将生成的Java堆转储文件保存到/path/to目录下，并命名为dump.hprof。\
因此，结合使用-XX:+HeapDumpOnOutOfMemoryError和-XX:HeapDumpPath选项可以在JVM发生OutOfMemoryError错误时自动生成Java堆转储文件，并将其保存到指定的路径下。这些文件可以用于后续的内存问题分析和调试。\
需要注意的是，生成Java堆转储文件会对JVM的性能和响应时间产生影响，因此建议在生产环境中谨慎使用，并根据需要进行测试和评估。另外，Java堆转储文件可能会非常大，因此建议在使用时仔细控制文件大小，以免占用过多的磁盘空间。

但如果已经发现堆内存占用变多，想得到Java堆当下的转储快照，可使用如下命令：
```shell
# 使⽤jmap⽣成dump⽂件
# format=b用于指定转储dump文件格式，没有指定-dump选项后的format参数，则默认将生成HPROF格式的堆转储文
# file=<filename>用于指定快照dump文件的文件名
jmap -dump:file=/home/develop/dump_demo.hprof vmid
# Dumping heap to C:\Users\IcyFenix\eclipse.bin …
# Heap dump file created
```
针对Oracle HotSpot虚拟机而言，主要有以下三种类型的堆转储文件格式：
<table>
    <tr>
        <th>格式</th>
        <th>介绍</th>
        <th>指定方式</th>
    </tr>
    <tr>
        <td>HPROF格式</td>
        <td>是Java虚拟机诊断工具（如jmap、jstack等）生成堆转储文件的默认格式，可以通过工具（如MAT、VisualVM等）进行分析和处理。HPROF格式的堆转储文件通常比较容易分析，因为它们包含了大量的元数据和调试信息。</td>
        <td>jmap -dump:format=hprof pid</td>
    </tr>
    <tr>
        <td>bin格式</td>
        <td>是Java虚拟机专有的二进制格式，用于在Java虚拟机内部传输和处理堆转储数据。bin格式的堆转储文件通常比HPROF格式的文件更加紧凑和高效，因为它们不包含额外的元数据和调试信息。但是，由于bin格式是Java虚拟机专有的格式，因此需要使用特定的工具进行解析和处理。可以使用jhat或Java Mission Control等工具进行分析和调试。</td>
        <td>jmap -dump:format=b,file=dump.bin pid</td>
    </tr>
    <tr>
        <td>SA格式</td>
        <td>是HotSpot虚拟机的一部分，包含了HotSpot虚拟机内部的各种调试信息和状态信息，可以用于分析和调试HotSpot虚拟机的内部状态。SA格式堆转储文件通常比HPROF和bin格式的文件更加详细和全面，因为它们包含了更多的HotSpot虚拟机内部信息。但是，由于SA格式是HotSpot虚拟机专有的格式，因此需要使用特定的工具进行解析和处理。可以使用SA Tool或Java Mission Control等工具进行分析和调试。</td>
        <td>可以使用jcmd命令的GC.heap_dump选项，例如：jcmd pid GC.heap_dump filename=sadump.bin</td>    
    </tr>
</table>

需要注意的是，不同的Java虚拟机实现可能会使用不同的堆转储格式。对于其他Java虚拟机实现，可能会使用其他不同的堆转储格式。在使用堆转储文件进行分析和调试时，
需要了解Java虚拟机实现和堆转储格式的差异，并选择合适的工具进行分析和处理。生成堆转储文件可能会对Java应用程序的性能和响应时间产生影响。在生成堆转储文件时，建议选择合适的时间点和选项，避免对应用程序的正常运行产生过大的影响。

### -heap
```shell
# 查看java 堆（heap）使用情况
jmap -heap [pid]
```
结果示例解读(Java8)：
```
Debugger attached successfully.
Server compiler detected.
JVM version is 25.181-b13

using parallel threads in the new generation.
using thread-local object allocation.
Concurrent Mark-Sweep GC  // GC 方式

Heap Configuration: // 堆内存初始化配置
   # 空闲堆空间的最小百分比，计算公式为：HeapFreeRatio =(CurrentFreeHeapSize/CurrentTotalHeapSize) * 100，值的区间为0到100，默认值为 40。如果HeapFreeRatio < MinHeapFreeRatio，则需要进行堆扩容，扩容的时机应该在每次垃圾回收之后。
   MinHeapFreeRatio         = 40  // 对应jvm启动参数-XX:MinHeapFreeRatio设置JVM堆最小空闲比率(default 40) 
   # 空闲堆空间的最大百分比，计算公式为：HeapFreeRatio =(CurrentFreeHeapSize/CurrentTotalHeapSize) * 100，值的区间为0到100，默认值为 70。如果HeapFreeRatio > MaxHeapFreeRatio，则需要进行堆缩容，缩容的时机应该在每次垃圾回收之后。
   MaxHeapFreeRatio         = 70  // 对应jvm启动参数-XX:MaxHeapFreeRatio设置JVM堆最大空闲比率(default 70) 
   MaxHeapSize              = 4294967296 (4096.0MB) // 对应jvm启动参数-XX:MaxHeapSize=设置JVM堆的最大大小
   NewSize                  = 2147483648 (2048.0MB) // 对应jvm启动参数-XX:NewSize=设置JVM堆的‘新生代’的默认大小
   MaxNewSize               = 2147483648 (2048.0MB) // 对应jvm启动参数-XX:MaxNewSize=设置JVM堆的‘新生代’的最大大小
   OldSize                  = 2147483648 (2048.0MB) // 对应jvm启动参数-XX:OldSize=<value>:设置JVM堆的‘老生代’的大小
   NewRatio                 = 2 // 对应jvm启动参数-XX:NewRatio=:‘新生代’和‘老生代’的大小比率 
   SurvivorRatio            = 8 // 对应jvm启动参数-XX:SurvivorRatio=设置年轻代中Eden区与Survivor区的大小比值
   MetaspaceSize            = 134217728 (128.0MB) // 对应jvm启动参数-XX:MetaspaceSize=<value>:设置元空间初始大小
   CompressedClassSpaceSize = 528482304 (504.0MB)
   MaxMetaspaceSize         = 536870912 (512.0MB) // 对应jvm启动参数-XX:MaxMetaspaceSize=设置元空间最大大小
   G1HeapRegionSize         = 0 (0.0MB)

Heap Usage: // 堆内存分步 
New Generation (Eden + 1 Survivor Space): // 新生代内存分布（Eden区+FROM区的空间[因为新生代的TO区是需要保持始终是空的，所以不参与新生代总量的计算]）
   capacity = 1932787712 (1843.25MB)
   used     = 1659739224 (1582.8506698608398MB)
   free     = 273048488 (260.39933013916016MB)
   85.87281540001844% used
Eden Space: // Eden区内存分布
   capacity = 1718091776 (1638.5MB) // Eden区总容量 
   used     = 1655535712 (1578.8418884277344MB) // Eden区已使用
   free     = 62556064 (59.658111572265625MB) // Eden区剩余容量
   96.35898006882724% used  // Eden区使用比率 
From Space: // 其中一个Survivor区的内存分布
   capacity = 214695936 (204.75MB)
   used     = 4203512 (4.008781433105469MB)
   free     = 210492424 (200.74121856689453MB)
   1.9578908098195207% used
To Space: // 另一个Survivor区的内存分布 
   capacity = 214695936 (204.75MB)
   used     = 0 (0.0MB)
   free     = 214695936 (204.75MB)
   0.0% used
concurrent mark-sweep generation:
   capacity = 2147483648 (2048.0MB)
   used     = 556202976 (530.4364929199219MB)
   free     = 1591280672 (1517.5635070800781MB)
   25.90021938085556% used

38755 interned Strings occupying 4107448 bytes.
```
### -histo
```shell
# 将堆中对象统计信息输出到文件/tmp/hi.txt中
jmap -histo [pid] >/tmp/hi.txt
```