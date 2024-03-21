# Docker Compose常用命令个人整理
```shell
# 启动docker compose管理的所有服务。
# -d 参数在 Docker Compose 中表示以后台（detached）模式运行服务。
# 如果你不加 -d 参数，Docker Compose 将以前台模式运行，也就是说服务的输出会直接输出到当前终端，而你无法使用终端进行其他操作，直到你手动停止服务
docker-compose up -d
# 启动 Docker Compose 文件中定义的特定服务。其中，服务名 是你要启动的服务在 docker-compose.yml 文件中定义的名称
# docker-compose up 命令会重新启动服务，并且会检测到镜像的变化，如果有更新则会使用最新的镜像重新启动服务
docker-compose up -d 服务名

# 重启服务。使用 restart 命令可以重新启动 Docker Compose 项目中的服务，但是它不会检测镜像的变化
docker-compose restart 服务名

# 停止 Docker Compose 中定义的所有服务
docker-compose down
# 停止 Docker Compose 中定义的特定服务
docker-compose stop 服务名

# 使用 docker-compose exec 命令来进入正在运行的 Docker 容器内部
# /bin/bash 和 /bin/sh 都是用于在 Docker Compose 中执行命令的方式，它们的区别在于所使用的命令行解释器不同。
# /bin/bash： 使用 /bin/bash 命令执行时，会启动 Bash 解释器进入交互式终端。Bash 是一个流行的 Unix/Linux 命令解释器，提供了丰富的功能和命令。通过这种方式进入容器，你将获得更多的功能和更强大的命令支持。
# /bin/sh： 使用 /bin/sh 命令执行时，会启动默认的 Shell 解释器进入交互式终端。在大多数 Unix/Linux 系统中，/bin/sh 通常链接到 Bash 或其他 Shell 解释器，例如 Bourne Shell。不过，一些基于 Alpine Linux 等的轻量级容器可能默认使用的是 BusyBox 的 Shell 解释器。因此，通过这种方式进入容器时，可能会受限于较少的功能和命令。
docker-compose exec 服务名 /bin/sh # docker-compose exec 服务名 sh
# 或者 docker-compose exec 服务名 /bin/bash
# 退出
exit

# 显示出 Docker Compose 中定义的所有服务的状态，包括它们的容器 ID、名称、状态等信息。
docker-compose ps
# 如果您只想查看特定服务的信息，可以使用：
docker-compose ps 服务名

# 查看 Docker Compose 容器的日志输出
docker-compose logs 服务名
# 实时跟踪日志输出，可以使用 -f 参数
docker-compose logs -f 服务名
```
