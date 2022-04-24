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


## 操作

## Mysql类

## 表格示例
表头|表头|表头
---|:--:|---:
内容|内容很多很多很多|内容
内容|内容|内容