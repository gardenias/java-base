# MapReduce 的类型与格式

```
map:(K1,V1) -> list(K2,V2)
combine:(K2,list(V2)) -> list(K2,V2)
reduce:(K2,list(V2)) -> list(K3,V3)
```

> partition 函数：分区单独由键来决定.

```
partition:(K2,V2) -> integer
```
> or

```java
public interface Partition(K2,V2) extends JobConfigurable{
    int getPartition(K2,key,V2 value,int numPartitions);
}
```
