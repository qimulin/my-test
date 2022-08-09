# Linux常用命令个人整理

## 资源使用篇
top命令是Linux下常用的性能分析工具，能够实时显示系统中各个进程的资源占用状况
> top  
> 显示解释：  
> PID：进程的ID  
> USER：进程所有  
> PR：进程的优先级别，越小越优先被执  
> VIRT：进程占用的虚拟内  
> RES：进程占用的物理内  
> SHR：进程使用的共享内  
> S：进程的状态,S表示休眠，R表示正在运行，Z表示僵死状态，N表示该进程优先值为负  
> %CPU：进程占用CPU的使用  
> %MEM：进程使用的物理内存和总内存的百分  
> TIME+：该进程启动后占用的总的CPU时间，即占用CPU使用时间的累加值  
> COMMAND：进程启动命令名称  

查看进程的所有线程情况
```shell
# -H： 设置线程模式
# -p: 显示指定PID的进程
top -H -p <进程id>
# 结果里的
# VIRT 表示 Virtual Memory 虚拟内存
# RES 表示 Resident Memory 驻留内存
# SHR 表示 shared memory 共享内存
```

调用历史命令
> 注意：此markdown语法，两个空格即可换行
> history [n]  
> [root@jb51 Desktop]#history [-c]  
> [root@jb51 Desktop]#history [-raw] histfiles

### 根据进程名字模糊查询进程情况
```shell
ps -ef | grep java-demo
```
### 根据PID确定进程相关的端口号
```shell
netstat -antup | grep PID
```

## 文件篇
### 进入目录
> cd [目录名]
> 目录名有几个bai符号有特殊的含义du，“..”代表上一级目录、“~”代表HOME目录、“-”代表前一目录
### 移动
>格式：mv [选项(option)] 源文件或目录 目标文件或目录

>使用命令：mv webdata /bin/usr/

>mv /usr/lib/* /zone 
>是将/usr/lib/下所有的东西移到/zone/中。

>mv /usr/lib/*.txt /zone
>是将lib下以txt结尾的所有文件移到/zone中。 其他类型，以此类推。
### 拷贝
>cp dir1/a.doc dir2 表示将dir1下的a.doc文件复制到dir2目录下

>cp -r dir1 dir2 表示将dir1及其dir1下所包含的文件复制到dir2下

>cp -r dir1/. dir2 表示将dir1下的文件复制到dir2,不包括dir1目录

说明：cp参数 -i：询问，如果目标文件已经存在，则会询问是否覆盖；
### 查询
某目录下搜索某个文件夹
>find /目录 -name 'hs_err_pid6.log' -ls

### 查看
判断-n lineNo logFileName后面行号的作用，
lineNo前面带+号表示正着数第LineNo行；
lineNo前面带-号表示倒着数第LineNo行；
如果前面不带（+/-）符号的，则tail修饰的话表示倒着数第LineNo行；head修饰的话表示正着数第LineNo行

#### tail
**tail: -n  是显示行号；相当于nl命令；例子如下：**
> tail -100f test.log      实时监控100行日志

>> Ctrl + c 终止 tail 命令

>> Ctrl + s 暂停 tail 命令

>>Ctrl + q 继续 tail 命令

> tail -n +10 test.log    查询10行之后的所有日志;

#### head
**跟tail是相反的，tail是看后多少行日志；例子如下：**
> head -n 10  test.log   查询日志文件中的头10行日志;

> head -n -10  test.log   查询日志文件除了最后10行的其他所有日志;

#### cat
**tac是倒序查看，是cat单词反写；例子如下：**
> cat -n test.log |grep "debug"   查询关键字的日志

> cat filename 一次显示整个文件

## 网络及应用
### ss命令 – 显示活动套接字信息
- 可用于查看哪些端口接受哪些连接。

ss是Socket Statistics的缩写。ss命令用来显示处于活动状态的套接字信息。它可以显示和netstat类似的内容。
但ss的优势在于它能够显示更多更详细的有关TCP和连接状态的信息，而且比netstat更快速更高效。
```shell
# -a 显示所有套接字
# -n 不解析服务名称，以数字方式显示
# -p 显示使用套接字的进程
ss -anp | grep 10000 | grep 192.168.100.148
```
### lsof命令 – 查看文件的进程信息
- 可用于确定端口对应的进程

lsof命令来自于英文词组“list opened files”的缩写，其功能是用于查看文件的进程信息。
既然Linux系统中的一切都是文件，那么使用lsof命令查看进程打开的文件，又或是查看文件的进程信息，都能够很好的帮助用户了解相关服务的运行状态，是一个不错的系统监视工具。
```shell
# 列出所有tcp 网络连接信息
lsof  -i tcp
# 列出所有udp网络连接信息
lsof  -i udp
# 列出谁在使用某个端口
lsof -i:3306
# 列出谁在使用某个特定的udp端口
lsof -i udp:55
# 特定的tcp端口
lsof -i tcp:80
```
### ps命令 – 显示进程状态
- 可用于查看指定的进程状态
ps命令来自于英文词组”process status“的缩写，其功能是用于显示当前系统的进程状态。
使用ps命令可以查看到进程的所有信息，例如进程的号码、发起者、系统资源使用占比（处理器与内存）、运行状态等等。
帮助我们及时的发现哪些进程出现”僵死“或”不可中断“等异常情况。

经常会与kill命令搭配使用来中断和删除不必要的服务进程，避免服务器的资源浪费。
```shell
# 显示系统中全部的进程信息，含详细信息
ps aux
# 结合管道操作符，将当前系统运行状态中指定进程信息过滤出来（示例中1887是进程号）
# -e 显示所有进程 
# -f 全格式（显示UID,PPIP,C与STIME栏位）
ps -ef | grep 1887
```

## 操作

## Mysql类

## 表格示例
表头|表头|表头
---|:--:|---:
内容|内容很多很多很多|内容
内容|内容|内容