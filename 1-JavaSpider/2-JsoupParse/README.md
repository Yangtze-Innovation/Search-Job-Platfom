# Jsoup解析HTML

&emsp;&emsp;**Jsoup**是一款Java的HTML解析器，可以直接解析某个URL地址，也可以解析HTML内容。其主要的功能包括解析HTML页面，通过DOM或者CSS选择器来查找、提取数据，可以更改HTML内容,HTML文档过滤清理。

### 一、文档清理

#### 1、字符串转化

```java
String html = "<html><div id=\"blog_list\"><div class=\"blog_title\"> <a href=\"url1\">第一篇博客</a></div><div class=\"blog_title\"><a href=\"url2\">第二篇博客</a></div><div class=\"blog_title\"><a href=\"url3\">第三篇博客</a></div></div></html>";
	    Document doc = Jsoup.parse(html);
```



#### 2、Jsoup的简单请求URL

```java
Document doc1 = Jsoup.connect("http://www.oschina.net/").get(); 
	    //2.2 post请求
	    Document doc2 = Jsoup.connect("http://www.oschina.net/") 
	    		  .data("query", "Java")   // 请求参数
	    		  .userAgent("I ’ m jsoup") // 设置 User-Agent 
	    		  .cookie("auth", "token") // 设置 cookie 
	    		  .timeout(3000)           // 设置连接超时时间
	    		  .post();                 
```

#### 3、解析本地html文件

&emsp;&emsp;Jsoup.parse 的第三个参数,为html中的相对路径加上前缀。因为很多超链接、图片、外部脚本、CSS文件等用到相对路径，而jsoup会自动为这些URL加上一个前缀。

` <a href=/project> 开源软件 </a> `

`<a href=http://www.oschina.net/project> 开源软件 </a>。`

```java
File input = new File("D:/test.html"); 
	    Document doc3 = Jsoup.parse(input,"UTF-8","http://www.oschina.net/"); 
```



### 二、解析并提取HTML元素

+ 获取元素：
  + getElementById () 
  + getElementsByTag ()
  + getElementsByClass()
  + select() 与jquery的$选择器类似

+ 获取属性： attr(String attrname)
+ 获取文本：text()

```java
	    String html = "<html><div id=\"blog_list\"><div class=\"blog_title\"> <a href=\"url1\">第一篇博客</a></div><div class=\"blog_title\"><a href=\"url2\">第二篇博客</a></div><div class=\"blog_title\"><a href=\"url3\">第三篇博客</a></div></div></html>";
	    Document doc = Jsoup.parse(html);
	    //类JavaScript的选择器
	    Elements elements = doc.getElementById("blog_list")
            .getElementsByClass("blog_title");
	    for( Element element : elements ){
	    	System.out.print(element.getElementsByTag("a").text()+" ");
	     	System.out.println(element.select("a").attr("href"));
	    }
```



### 三、修改数据

+ 增加class：addClass("mylinkclass")
+ 删除class: removeClas("mylinkclass")
+ 修改属性：attr("rel", "nofollow"); 
+ 删除属性：removeAttr("onclick"); 
+ 清空值：val(""); 
+ 清空文本：text("")

```java
 String html = "<html><div id=\"blog_list\"><div class=\"blog_title\"> <a href=\"url1\">第一篇博客</a></div><div class=\"blog_title\"><a href=\"url2\">第二篇博客</a></div><div class=\"blog_title\"><a href=\"url3\">第三篇博客</a></div></div></html>";
	    Document doc = Jsoup.parse(html);
	    Elements elements = doc.select("div[id=blog_list] a");
	    for(Element element : elements) {
	    	element.addClass("myclass");
	    	element.attr("href", "123");
	    	element.parent().removeAttr("class");
	    	element.text("");
	    }
	    System.out.println(doc.toString());
```

### 四、文档过滤清理

&emsp;&emsp;**Whitelist**类定义了一些可以被保留的标签和属性，不属于这个范围的标签和属性的都要被删除。

#### 1、Whitelist默认配置

|       方法        |                             属性                             |
| :---------------: | :----------------------------------------------------------: |
|      none()       |                      只允许包含文本信息                      |
|      basic()      | 允许的标签包括：a, b, blockquote, br, cite, code, dd, dl, dt, em, i, li, ol, p, pre, q, small, strike, strong, sub, sup, u, ul, 以及合适的属性 |
|   simpleText()    |             只允许 b, em, i, strong, u 这些标签              |
| basicWithImages() |                在 basic() 的基础上增加了图片                 |
|     relaxed()     | 这个过滤器允许的标签最多，包括：a, b, blockquote, br, caption, cite, code, col, colgroup, dd, dl, dt, em, h1, h2, h3, h4, h5, h6, i, img, li, ol, p, pre, q, small, strike, strong, sub, sup, table, tbody, td, tfoot, th, thead, tr, u, ul |

#### 2、Whitelis添加自定义配置

|                  方法                   |                             属性                             |
| :-------------------------------------: | :----------------------------------------------------------: |
|           **addTags(tag...)**           |                     向Whitelist添加标签                      |
|     **addAttributes(tag,keys...)**      | 给标签添加属性。Tag是属性名，keys对应的是一个个属性值。例如：addAttributes("a", "href", "class") 表示：给标签a添加href和class属性，即允许标签a包含href和class属性。如果想给每一个标签添加一组属性，使用:all。例如： addAttributes(":all", "class").即给每个标签添加class属性。 |
| **addEnforcedAttribute(tag,key,value)** | 给标签添加强制性属性，如果标签已经存在了要添加的属性，则覆盖原有值。tag:标签；key：标签的键；value：标签的键对应的值。例如： addEnforcedAttribute("a", "rel", "nofollow") 表示 <a href="..." rel="nofollow"> |
| **addProtocols(tag,key, protocols...)** | 给URL属性添加协议。例如： addProtocols("a", "href", "ftp", "http", "https")标签a的href键可以指向的协议有ftp、http、https |

**示例**

```java
	    String html = "<html><div id=\"blog_list\"><div class=\"blog_title\"> <a href=\"http://www.baidu.com\">第一篇博客</a></div><div class=\"blog_title\"><a href=\"url2\">第二篇博客</a></div><div class=\"blog_title\"><a href=\"url3\">第三篇博客</a></div></div></html>";
	    String safe = Jsoup.clean(html, Whitelist.basic().
                                  addProtocols("a", "href", "http"));
	    System.out.println(safe);
```

**输出**

```html
<div>
 <div class="blog_title"> 
  <a href="http://src" rel="nofollow">第一篇博客</a>
 </div>
 <div class="blog_title">
  <a rel="nofollow">第二篇博客</a>
 </div>
 <div class="blog_title">
  <a rel="nofollow">第三篇博客</a>
 </div>
</div>
```



