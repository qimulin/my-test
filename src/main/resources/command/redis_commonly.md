# Redis常用命令个人整理
## 登录

客户端连接服务器命令
```shell
redis-cli -h [IP地址] -p [PORT端口号] -a [密码password]
```

进入本机部署在docker内的redis客户端
```shell
# 有密码就加上-a进行指定
docker exec -it <容器名称或容器ID> redis-cli -a <密码>
```

## 对库操作

查询对应index的数据库
```shell
SELECT [index]
```

清除整个数据库的数据
```shell
flushdb
```

清除所有库的所有数据
```shell
flushall
```

## 查询数据

## 删除数据



