# Kubernetes常用命令个人整理
```shell
# 查看指定命名空间（Namespace）中的所有 Pod
# -n wdt-pro
#   -n 是 --namespace 的缩写，指定要操作的命名空间。
#   demo-namespace 是目标命名空间的名称。
# get pod
#   get：查询资源。
#   pod：资源类型（即 Pod，Kubernetes 中最小的可部署单元）。
kubectl -n demo-namespace get pod

# 将Kubernetes Pod中的日志文件复制到本地机器
kubectl cp demo-namespace/demo-service-6fc75fdb56-spj5m:logs/demo-service.log ./demo-service.log

```
