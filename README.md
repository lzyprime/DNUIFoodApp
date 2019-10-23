# 2018 Android 课，kotlin -> 订餐系统

## 博客地址 ： [lzyprime.top](https://lzyprime.top)

> *十方三世，尽在一念*

---

## 分支说明

- dev1: app 1.0, 中规中矩的原生`kotlin`
- dev2: app 2.0, `kotlin` + `anko` 开发，功能齐全

---

# 我用 $kotlin$ 写 $android$

## 正题之前先扯扯

这是Java劝退系列

这学期要写的app, 早在学长那听过了。听完描述之后，不用看具体长什么样，我就有数怎么实现，还是那点技术。 难不成我在复习一遍？ 没意思

所以，应该搞点新鲜的。比如用 $kotlin$ 写， 找点新的技术，之类的

于是随便学了学 $kotlin$ 和 各种 $bar$ 啊， 网络请求啊，之类的实现。没想到的是，赚便宜了，赚大发了。用这些玩意儿写 $android$ 真省力，又不失逼格

所以我很自信一晚上就能 $肝$ 完这个 $app$。 然后就写完了 : )

## kotlin 下

> 写完这份 $app$ ，其实想写 $kotlin$ 的笔记的，但是最近没那个功夫，有机会再说  
> 先来说说省了哪些麻烦

### 1. 免了 $findviewbyid$

就凭这点，就足够从 $java$ 转到 $kotlin$ 了

Java ： 要想用布局里的某个元件，要先在 $activity$ 前声明对应的变量。 然后挨个 $findviewbyid$，用几个就写几个。  
啪，一大堆这玩意儿,  好处就是 **有代码量**  ，，，，，*呵*

kotlin: 布局文件中的原件 id 本身就是独一无二，直接 写 id 名就能找到原件啊。 find 个锤啊

### 2. $lambda$ 省了具体实现监听器

```java
java : xxx.setxxxlistener(new xxxxxxxxxxxlisener{ 
    
    //略n行
    //n > 10


    if(item == 1) dosomething


    })  
```

就是里边什么都不写，已经 10 行展开了。 然而，可能有用动作就一行

然后 kotlin 写：

```kotlin
xxx.setxxxlistener{ it ->
    if(it == 1) dosomething
}
```

没略，就这么长

这是只有一句的例子，像 $fragment$ 的组织页，实现菜单和多个页面的联动时，优势更明显。

而且，在你还在 $findviewbyid$ 的时候，监听器里的东西我已经写完了。dd

### 3. $Bean$ 类免了 $get$ , $set$ 方法

解析请求回来的 $json$ 转成 $Bean$ , 里边就两个东西，变量声明，挨个写 $get$ , $set$ 。 

有快速生成这也没什么，不过就是类写的又臭又长罢了。但是在调用的时候，`xxx.getXxx();`

.......

```kotlin
// kotlin data class， 所有变量默认有 get, set。 常亮有 get
data class ShopBean(var shop_id: String,
                    var shopname: String,
                    var address: String,
                    var phonenum: String,
                    var intro: String,
                    var pic: String,
                    var comment: String,
                    var level: String)

// 调用 ： xxx.shop_id
```

### 4. Anko , 其实连布局都免了拖框了

kotlin.org 。 用什么原件直接在类里写，免了造前端长什么样。。。看起来比手造的还好看

### 5. 安全机制，判 $null$ ? 各种 $try$ ?

```java
// java
String s;
int l;
if (s != null){
    l = s.length();
}else {
    l = -1;
}
```

```kotlin
// kotlin
var s : String?
val l = s?.length ?: -1
```

try

```java
// java
try{
    xxx = yyy;
        if(xxx != null){
            xxx.do();
        }
}catch(a){
    rrr;
}catch(b){
    ttt;
}
```

```kotlin
//kotlin
xxx!!.do()
```

### 6. 多了去了，说不完

```java
// java
int f(int x){
if (x == 1){
    return 111;
} 
else if (x == 2){
    return 222;
}
else if (x == 5){
    return 555;
}
else {
    return 0;
}
}
```

```kotlin
// kotlin
fun f(x : Int) : Int = when(x){
    1 -> 111
    2 -> 222
    5 -> 555
    else -> 0
}
```

## 官方文档中文的， 一开门就知道这东西多好用。抓紧转吧  [kotlin 中文网](https://www.kotlincn.net/) 。 我写完之后就更到了 1.3，更好用了，没赶上
