## java keyword _`transient`_  
```
/**
*
* Java的serialization提供了一种持久化对象实例的机制。  
* 当持久化对象时，可能有一个特殊的对象数据成员，我们不想用serialization机制来保存它。  
* 为了在一个特定对象的一个域上关闭serialization，可以在这个域前加上关键字transient。  
* transient是Java语言的关键字，用来表示一个域不是该对象串行化的一部分。  
* 当一个对象被串行化的时候，transient型变量的值不包括在串行化的表示中，然而非transient型的变量是被包括进去的。
*
*/
```
1. **通过构造方法或者 setter 设置 transient filed的值，不会加入序列化过程**
2. **transient filed 默认值也同样在序列化过程中忽略**
