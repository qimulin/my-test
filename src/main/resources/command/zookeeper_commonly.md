# Zookeeper常用命令个人整理
首先需要进入zk客户端，连接zk服务
```shell
]$ zkCli.sh -server {ip:port}
```

## 节点操作
### 创建一个znode
```text
命令语法：create [-s] [-e] [-c] [-t ttl] path [data] [acl]
-s：创建的是带序列号的节点，序列号用0填充节点路径。
-e：创建的是临时节点。
-c：创建的是容器节点
path：znode的路径，ZooKeeper中没有相对路径，所有路径都必须以’/'开头。
data：znode携带的数据。
acl：这个节点的ACL。
```
```shell
# 非顺序节点，重复创建会提示“Node already exists”
# 创建一个持久化节点
[zk: 192.168.0.143:2181(CONNECTED) 19] create /zk_test
Created /zk_test
# 创建一个临时节点
[zk: 192.168.0.143:2181(CONNECTED) 25] create -e /ephemeral_node
Created /ephemeral_node
```
### 删除znode节点
```shell
# delete命令删除节点前要求节点目录为空，不存在子节点
[zk: 192.168.0.143:2181(CONNECTED) 34] delete /config
Node not empty: /config
[zk: 192.168.0.143:2181(CONNECTED) 35] delete /config/topics/test
[zk: 192.168.0.143:2181(CONNECTED) 27] delete /ephemeral_node
# 如果要删除整个节点及子节点可以使用deleteall
[zk: 192.168.0.143:2181(CONNECTED) 36] deleteall /config
```
