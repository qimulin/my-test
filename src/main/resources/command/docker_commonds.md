# Docker常用命令个人整理
```shell
# systemctl命令来自于英文词组”system control“的缩写，其功能是用于管理系统服务
# start 启动服务
systemctl start docker
# enable 使某服务开机自启
systemctl enable docker

# 通过ID删除镜像
docker rmi <container_id_or_name>
# 删除repository:tag的镜像
docker rmi allen_mysql:5.7
# 通过ID删除镜像
docker rmi <container_id_or_name>

# 查看正在运行的容器信息，显示2分钟前启动运行
docker ps
# 查看所有镜像
docker images

# 进入容器：docker attach 容器ID
docker exec -it 容器ID /bin/bash 
# 退出
exit # 或者 Ctrl+P+Q

# 拷贝容器日志到服务器某个文件夹
docker cp <container_id_or_name>:/container/path /host/path
# 例如：假设你有一个名为 web 的服务，并且要从其中的容器中复制 /app/data/file.txt 文件到宿主机的 /host/path/ 目录下，你可以运行以下命令：
docker cp web:/app/data/file.txt /host/path/


# 运行某个镜像，参数自己添加（运行容器：docker run -it 镜像名 /bin/bash）
docker run -p 8761:8761 -t eureka-server
# 直接关闭容器
docker kill <container_id_or_name>
# 重启容器
docker restart <container_id_or_name>
# 移除容器
docker rm <container_id_or_name>

# 查询运行容器的配置信息
docker inspect <container_id_or_name>
# 查看运行容器在宿主机的日志存储位置
docker inspect <container_id_or_name> | grep -i logpath
# 查看容器的环境变量
docker exec <container_id_or_name> env
# 不进入容器，查看容器内文件内容，可使用
docker exec <container_id_or_name> cat /log/file.log

# 查看容器进程在宿主机的 PID(docker 中运行的进程, 本质上是运行在 host 上的)
docker container top <container_id_or_name>

# 下载centos镜像,运行一个名为mycentos的容器,并在容器里运行/bin/bash
docker run -ti --name mycentos centos /bin/bash
```
