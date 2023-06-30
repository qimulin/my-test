# jcmd实用命令个人整理

## 基本介绍
jcmd全称JVM Command，用于向正在运行的Java虚拟机发送诊断命令请求，从JDK 7开始提供。
格式说明：
- pid：接受诊断命令请求的进程ID，根据格式和main class二选一即可。
- main class：接受诊断命令请求的进程main类。jcmd会将诊断命令请求发送给指定main class的所有Java进程。
- command：command必须是一个有效的jcmd命令，可以使用jcmd pid help命令查看可用的命令列表。如果pid是0，那么command将会被发送给所有Java进程。main class会用来去匹配（局部匹配或全量匹配）。如果未指定任何选项，他将会列出正在运行的Java进程标识符以及用于启动该进程的main class和命令参数（相当于使用了-l参数）
- PerfCounter.print：打印指定Java进程上可用的性能计数器。
- -f filename：从指定文件中读取命令并执行。在file中，每个命令必须写在单独的一行。以“#”开头的行会被忽略。当所有行的命令被调用完毕后，或者读取到含有stop关键字的命令，将会终止对file的处理。
- -l：查看所有JVM进程。jcmd不使用参数与jcmd -l效果相同。
```text
jcmd <pid | main class> <command ...| PerfCounter.print | -f file>
```

## 实用选项
### 导航

jcmd⼯具常用选项：

<table>
    <tr>
      <th>选项</th>
      <th>作用</th>
    </tr>
    <tr>
      <td>-h</td>
      <td>查看帮助</td>
    </tr>
    <tr>
      <td>help [options] [arguments]</td>
      <td>作用：查看指定命令的帮助信息</td>
    </tr>
    <tr>
      <td>GC.class_histogram [options]</td>
      <td>提供有关Java堆使用情况的统计信息</td>
    </tr>
    <tr>
      <td>GC.class_stats [options] [arguments]</td>
      <td>展示有关Java类元数据的统计信息</td>
    </tr>
     <tr>
      <td>GC.finalizer_info</td>
      <td>展示有关Java finalization queue的信息</td>
    </tr>
</table>


### help 

help [options] [arguments]

- 作用：查看指定命令的帮助信息
- arguments：想查看帮助的命令（STRING，无默认值）
- options：选项，必须使用key或者key=value的语法指定，可用的options如下：
    - -all：（可选）查看所有命令的帮助信息（BOOLEAN，false）
```text
# 获得指定进程可用的命令列表
jcmd <pid> help
# 获取指定进程、指定命令的帮助信息，如果参数包含空格，需要用 ' 或者 " 引起来
jcmd <pid> help <command>
```
上面提到，不带其他参数help命令可以查看所有可用的jcmd子命令及其用法，哪些情况有的jcmd子命令不可用？

jcmd命令的子命令可用性取决于正在运行的Java进程以及Java版本和操作系统等因素。具体来说，以下情况可能导致某些jcmd子命令不可用：

- 如果Java进程没有启用JMX（Java管理扩展）或未开启远程JMX连接，则jcmd命令无法连接到该进程并执行相应的子命令。
- 如果Java进程的启动参数中没有指定-XX:+UnlockCommercialFeatures选项，则jcmd命令的一些商业功能子命令（如VM.check_commercial_features）可能无法使用。
- 如果运行jcmd命令的用户没有足够的权限，例如没有足够的操作系统权限或Java安全管理策略限制，则可能会导致某些jcmd子命令无法执行。
- 如果Java版本较旧，可能会缺少某些jcmd子命令或某些子命令的功能。例如，在Java 6或更早的版本中，jcmd命令不可用。
- 如果正在运行的Java进程是使用其他语言编写的，而不是Java，则jcmd命令可能无法连接到该进程并执行相应的子命令。

因此，在使用jcmd命令时，需要注意上述因素可能导致某些子命令无法使用。在执行特定的子命令之前，最好查阅相关文档或使用jcmd help命令来了解该命令是否可用以及如何正确使用。

### GC.class_histogram 

GC.class_histogram [options]

- 作用：提供有关Java堆使用情况的统计信息
- 影响：高 （取决于Java堆的大小和内容）
- 所需权限：java.lang.management.ManagementPermission(monitor)
- options：选项，必须使用key或者key=value的语法指定，可用的options如下：
    - -all：（可选）检查所有对象，包括不可达的对象（BOOLEAN，false）

GC.class_stats子命令是jcmd命令的一部分，用于显示Java进程中每个类加载器加载的类的数量、大小和占用内存等信息。该命令需要使用JVM的诊断选项
-XX:+UnlockDiagnosticVMOptions来开启，否则会出现类似"GC.class_stats command requires -XX:+UnlockDiagnosticVMOptions"的错误提示。\
诊断选项是一组用于开启JVM诊断和调试功能的选项。由于诊断选项可能会影响JVM的性能和稳定性，因此默认情况下是关闭的。要使用诊断选项，需要在启动JVM时
显式指定-XX:+UnlockDiagnosticVMOptions选项，以开启诊断选项的使用。\
因此，如果你使用jcmd命令的GC.class_stats子命令时出现了"GC.class_stats command requires -XX:+UnlockDiagnosticVMOptions"的错误提示，
可以在启动Java进程时加上-XX:+UnlockDiagnosticVMOptions选项，或者使用jcmd命令的VM.unlock_commercial_features子命令来开启诊断选项，例如：
```text
jcmd <pid> VM.unlock_commercial_features
```
其中，<pid>是目标Java进程的PID。\
需要注意的是，开启诊断选项可能会影响JVM的性能和稳定性，因此建议在生产环境中谨慎使用，并根据需要进行测试和评估。

使用示例：
```text
# 数据头部取N行显示
jcmd <pid> GC.class_histogram | head -n N
```
### GC.class_stats 

GC.class_stats [options] [arguments]

- 作用：展示有关Java类元数据的统计信息
- 影响：高（取决于Java堆的大小和内容）
- options：选项，必须使用key或者key=value的语法指定，可用的options如下：
    - -all：（可选）显示所有列（BOOLEAN，false）
    - -csv：（可选）以CSV格式打印电子表格（BOOLEAN，false）
    - help：（可选）显示所有列的含义（BOOLEAN，false）
- arguments：参数，可选参数如下：
    - columns：（可选）要显示的列，以逗号分隔。如果不指定，则显示以下列：
        - InstBytes
        - KlassBytes
        - CpAll
        - annotations
        - MethodCount
        - Bytecodes
        - MethodAll
        - ROAll
        - RWAll
        - Total

使用示例：
```text
# 展示指定进程类的元数据的所有统计信息
jcmd 12737 GC.class_stats -all

# InstBytes、KlassBytes等列的含义
jcmd 12737 GC.class_stats -help

# 显示InstBytes,KlassBytes这两列，并生成csv
jcmd 12737 GC.class_stats -cvs InstBytes,KlassBytes > t.csv

```

### GC.finalizer_info
- 作用：展示有关Java finalization queue的信息
- 影响：中
- 所需权限：java.lang.management.ManagementPermission(monitor)

### GC.heap_dump 

- 作用：生成Java堆Dump文件（HPROF格式）
- 影响：高（取决于Java堆大小和内容。除非指定了-all选项，否则会导致Full GC
- 所需权限：java.lang.management.ManagementPermission(monitor)
- options：选项，必须使用key或者key=value的语法指定，可用的options如下：
  - -all：（可选）转储所有对象，包括不可达对象（BOOLEAN，false）
- arguments：参数，可用的参数如下：
  - filename：Dump文件的名称（STRING，无默认值）

使用示例：

```text
jcmd 12737 GC.heap_dump -all 1.hprof
```

### GC.heap_info
- 作用：展示Java堆信息

- 影响：中
- 所需权限：java.lang.management.ManagementPermission(monitor)

使用示例：

```text
[develop@node1 ~]$ jcmd 5870 GC.heap_info
5870:
 par new generation   total 471872K, used 128455K [0x00000000c0000000, 0x00000000e0000000, 0x00000000e0000000)
  eden space 419456K,  21% used [0x00000000c0000000, 0x00000000c5857040, 0x00000000d99a0000)
  from space 52416K,  72% used [0x00000000d99a0000, 0x00000000dbebad98, 0x00000000dccd0000)
  to   space 52416K,   0% used [0x00000000dccd0000, 0x00000000dccd0000, 0x00000000e0000000)
 concurrent mark-sweep generation total 524288K, used 77917K [0x00000000e0000000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 87246K, capacity 92527K, committed 92800K, reserved 1130496K
  class space    used 10219K, capacity 11053K, committed 11136K, reserved 1048576K
```

### GC.run

- 作用：调用`java.lang.System.gc()`
- 影响：中（取决于Java堆的大小和内容）

### GC.run_finalization

- 作用：`java.lang.System.runFianlization()`
- 影响：中（取决于Java内容）

### Thread.print 

Thread.print [options]

- 作用：打印所有带有堆栈跟踪的线程
- 影响：中（取决于线程数）
- 所需权限：java.lang.management.ManagementPermission(monitor)
- options：选项，必须使用key或者key=value的语法指定，可用的options如下：
  - -l：（可选）打印java.util.concurrent锁（BOLEAN，false）

使用示例：

```text
jcmd 12737 Thread.print -l
```

### VM.info

- 作用：打印有关JVM环境和状态的信息
- 影响：低
- 允许：java.lang.management.ManagementPermission(monitor)

> 需要注意的是，`jcmd`命令需要在Java虚拟机启动时指定`-XX:+UnlockDiagnosticVMOptions`选项，才能够执行`VM.info`子命令。
>
> 因此，在使用`jcmd VM.info`命令之前，需要确保Java虚拟机已经启动，并且启动时指定了`-XX:+UnlockDiagnosticVMOptions`选项。

使用示例：

````text
$ jcmd 1234 VM.info
1234:
Java Virtual Machine Specification:
  ...
Java Virtual Machine Implementation:
  ...
Java Runtime Environment:
  ...
JVM home directory:
  ...
JVM command line:
  ...
...
```
``jcmd`命令会输出一些Java虚拟机的基本信息，包括Java虚拟机的规范、实现和运行环境信息，以及Java虚拟机的启动参数、命令行等信息。
````



### VM.flags

VM.flags [options]

- 作用：打印VM标志及其当前值
- 影响：低
- 所需权限：java.lang.management.ManagementPermission(monitor)
- options：选项，必须使用key或者key=value的语法指定，可用的options如下：
  - -all：（可选）打印VM支持的所有标志（BOOLEAN，false）

使用示例：

```text
$ jcmd [pid] VM.flags
[pid]:
-XX:InitialHeapSize=1073741824
-XX:MaxHeapSize=4294967296
-XX:+PrintCommandLineFlags
...
sun.java.command = [HelloWorld](poe://www.poe.com/_api/key_phrase?phrase=HelloWorld&prompt=Tell%20me%20more%20about%20HelloWorld.)
sun.java.launcher = SUN_STANDARD
sun.management.compiler = HotSpot 64-Bit Tiered Compilers
...
```



