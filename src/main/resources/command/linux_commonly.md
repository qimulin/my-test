  # Linux常用命令个人整理
```shell
# 查看CentOS内核版本
uname -r
# 使用root权限登录系统，更新yum包更新到最新版本
sudo yum update -y
# 清屏命令
clear
```
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
### 磁盘占用排查
df命令来自英文词组“report file system disk space usage”的缩写，其功能是用于显示系统上磁盘空间的使用量情况。
df命令显示的磁盘使用量情况含可用、已有及使用率等信息，默认单位为Kb，建议使用-h参数进行单位换算，毕竟135M比138240Kb更利于阅读对吧~
> 语法格式：df 参数 [对象磁盘/分区]
```
# 常用参数
# -a 显示所有文件系统 
# -h 以更易读的方式显示 
# -H 以1KB=1000Byte为换算单位（跟-h的换算1024不一样）
# -i 显示索引字节信息 
# -k 设置显示时的块大小 
# -l 只显示本地文件系统 
# -t 只显示指定类型文件系统 
# -T 显示文件系统的类型 
# --sync 在获取磁盘使用信息前先执行sync同步命令
```
带容量单位的显示系统全部磁盘使用量情况：
```
[root@linuxcool ~]# df -h 
Filesystem Size Used Avail Use% Mounted on 
devtmpfs 969M 0 969M 0% /dev 
tmpfs 984M 0 984M 0% /dev/shm 
tmpfs 984M 9.6M 974M 1% /run 
tmpfs 984M 0 984M 0% /sys/fs/cgroup 
/dev/mapper/rhel-root 17G 3.9G 14G 23% / 
/dev/sr0 6.7G 6.7G 0 100% /media/cdrom 
/dev/sda1 1014M 152M 863M 15% /boot 
tmpfs 197M 16K 197M 1% /run/user/42 
tmpfs 197M 3.5M 194M 2% /run/user/0
```
带容量单位的显示**指定磁盘分区**使用量情况，可用以确定文件夹是属于哪个分区的：
```
[root@linuxcool ~]# df -h /boot 
Filesystem Size Used Avail Use% Mounted on 
/dev/sda1 1014M 152M 863M 15% /boot
```
（不甚理解）显示系统中所有文件系统格式为XFS的磁盘分区使用量情况：
```
[root@linuxcool ~]# df -t xfs 
Filesystem 1K-blocks Used Available Use% Mounted on 
/dev/mapper/rhel-root 17811456 4041320 13770136 23% /
/dev/sda1 1038336 155556 882780 15% /boot
```
确定文件夹后，可以再更进一步确定占磁盘空间大的目录或文件，通过du命令。\
du命令来自英文词组“Disk Usage”的缩写，其功能是用于查看文件或目录的大小。人们经常会把df和du命令混淆，df是用于查看磁盘或分区使用情况的命令，而du命令则是用于按照指定容量单位来查看文件或目录在磁盘中的占用情况。
> 语法格式：du [参数] 文件名
```
常用参数：
-a 显示目录中所有文件大小 
-b 使用Byte为单位显示文件大小 
-c 显示占用磁盘空间大小总和 
-d 表示迭代深度
-D 显示符号链接对应源文件大小 
-g 使用GB为单位显示文件大小 
-h 使用易读方式显示文件大小 
-k 使用KB为单位显示文件大小 
-m 使用MB为单位显示文件大小 
-P 不显示符号链接对应源文件大小 
-s 显示子目录总大小 
-S 不显示子目录大小 
-X 排除指定文件 
--help 显示帮助信息 
--version 显示版本信息
```
例如想要查看/data/logs目录下日志文件和目录占用情况，则可以的-d参数，或--max-depth（设置查询的目录深度）
```
# 当前目录深度是0 1表示最深迭代到当前目录的下一个深度,等价的命令是du --max-depth 1 
[root@linuxcool ~]# du -h -d 1 /data
272K    /data/zookeeper
140K    /data/kafka
495M    /data/es_data
14G     /data/logs
14G     /data
```
其他示例：\
以易读的容量格式显示指定目录内总文件的大小信息：
```
[root@linuxcool ~]# du -sh /Dir 
29M /Dir
```
显示指定文件的大小信息（默认单位为K）：
```
[root@linuxcool ~]# du File.cfg 
# 4 File.cfg
```

### 根据进程名字模糊查询进程情况
```shell
ps -ef | grep java-demo
```
### 根据PID确定进程相关的端口号
```shell
netstat -antup | grep PID
```

## 文件篇
### 文件权限相关
- chmod命令(”Change mode“) 改变文件或目录权限\
-c	若该文件权限确实已经更改，才显示其更改动作\
-f	若该文件权限无法被更改也不显示错误讯息\
-v	显示权限变更的详细资料\
-R	对目前目录下的所有文件与子目录进行相同的权限变更(即以递回的方式逐个变更)
```shell
# 设定某个文件的权限为775：
chmod 775 anaconda-ks.cfg
# 设定某个文件让任何人都可以读取：
chmod a+r anaconda-ks.cfg
# 设定某个目录及其内子文件任何人都可以读取和读取
chmod -R a+r Documents
```
- chown命令(”Change owner“) 改变文件或目录的用户和用户组\
-R	对目前目录下的所有文件与目录进行相同的变更\
-c	显示所属信息变更信息\
-f	若该文件拥有者无法被更改也不要显示错误\
-h	只对于链接文件进行变更，而非真正指向的文件\
-v	显示拥有者变更的详细资料\
--help	显示辅助说明\
--version	显示版本
```shell
# 改变指定文件的所属主与所属组：
chown root:root /etc/fstab
# 改变指定文件的所属主与所属组，并显示过程：
chown -c linuxprobe:linuxprobe /etc/fstab
# changed ownership of '/etc/fstab' from root:root to linuxprobe:linuxprobe
# 改变指定目录及其内所有子文件的所属主与所属组：
chown -R root:root /etc
```


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
### 删除
rm命令来自英文单词“remove”的缩写，中文译为“消除”，其功能是用于删除文件或目录，一次可以删除多个文件，或递归删除目录及其内的所有子文件。 \

rm也是一个很危险的命令，使用的时候要特别当心，尤其对于新手更要格外注意，如执行rm -rf /*命令则会清空系统中所有的文件，甚至无法恢复回来。
所以我们在执行之前一定要再次确认下在哪个目录中，到底要删除什么文件，考虑好后再敲击回车键，时刻保持清醒的头脑

```shell
# -d 仅删除无子文件的空目录 
# -f 强制删除文件而不询问 
# -i 删除文件前询问用户是否确认 
# -r 递归删除目录及其内全部子文件 
# -v 显示执行过程详细信息 
# --help 显示帮助信息 
# --version 显示版本信息

# 删除文件默认会进行二次确认，敲击y进行确认
[root@linuxcool ~]# rm File.cfg 
rm: remove regular file 'File.cfg'? y
# 强制删除文件而无需二次确认： 
[root@linuxcool ~]# rm -f File.cfg 
# 删除指定目录及其内的全部子文件，一并都强制删除： 
[root@linuxcool ~]# rm -rf Dir 
# 删除指定目录及其内的全部子文件，但不删除指定目录： 
[root@linuxcool ~]# rm -rf Dir/* 
# 强制删除当前工作目录内的所有以.txt为后缀的文件：
[root@linuxcool ~]# rm -f *.txt 
#【离职（坐牢）小妙招，谨慎！！！】强制清空服务器系统内的所有文件：
 [root@linuxcool ~]# rm -rf /*
```
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
## 用户相关
### 用户基本操作
- adduser命令 – 创建用户账户
```shell
# 创建指定名称的用户账户develop
adduser develop
# 创建指定名称的用户账户，并设置账户有效期
adduser -e 18/05/2023 develop
# 创建指定名称的用户账户，并添加扩展组
adduser -G root develop
# 创建指定名称的用户账户，并设置家目录名称
adduser -d /home/linux develop
# passwd命令修改密码信息，回车后会提醒输入密码和确认密码
sudo passwd develop
```
- lslogins命令 – 显示系统中现有用户的相关信息
```shell
# 展示出系统中现有用户的相关信息
lslogins -u
```
- id命令 – 显示用户与用户组信息\
  -g	显示用户所属群组的ID\
  -G	显示用户扩展群组的ID\
  -n	显示用户所属群组或扩展群组的名称\
  -r	显示实际ID\
  -u	显示用户ID\
  --help	显示帮助\
  --version	显示版本信息
```shell
# 显示当前用户的身份信息
id
# 以下为显示内容
uid=0(root) gid=0(root) groups=0(root) context=unconfined_u:unconfined_r:unconfined_t:s0-s0:c0.c1023
# 显示当前用户的所属群组名称
id -gn
```
- usermod命令 – 修改用户账号信息\
  -c<备注>	修改用户账号的备注文字\
  -d<登入目录>	修改用户登入时的家目录\
  -e<有效期限>	修改账号的有效期限\
  -f<缓冲天数>	修改在密码过期后多少天即关闭该账号\
  -g<群组>	修改用户所属的群组\
  -G<群组>	修改用户所属的附加群组\
  -l<账号名称>	修改用户账号名称\
  -L	锁定用户密码，使密码无效\
  -s<shell>	修改用户登入后所使用的shell\
  -u<uid>	修改用户ID\
  -U	解除密码锁定
> 语法格式：usermod [参数] 用户名
```shell
# 修改指定用户的家目录路径
usermod -d /home develop
# 修改指定用户的名称为 develop2
usermod -l develop develop2
# 修改用户组
usermod -g developgroup develop
```

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