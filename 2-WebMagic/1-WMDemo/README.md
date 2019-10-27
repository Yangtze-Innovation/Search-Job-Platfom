## WebMagic Demo

### 一、总体架构

&emsp;&emsp;WebMagic的结构分为`Downloader`、`PageProcessor`、`Scheduler`、`Pipeline`四大组件，并由Spider将它们彼此组织起来。这四大组件对应爬虫生命周期中的下载、处理、管理和持久化等功能。WebMagic的设计参考了Scapy，但是实现方式更Java化一些。

&emsp;&emsp;Spider则将这几个组件组织起来，让它们可以互相交互，流程化的执行，可以认为Spider是一个大的容器，它也是WebMagic逻辑的核心。

![WebMagic ](../../images/2.1WebMagic.png)

#### WebMagic的四个组件

#### 1、Downloader 负责下载页面

&emsp;&emsp;Downloader负责从互联网上下载页面，以便后续处理。WebMagic默认使用了[Apache HttpClient](http://hc.apache.org/index.html)作为下载工具。

#### 2、PageProcessor 负责解析页面

&emsp;&emsp;PageProcessor负责解析页面，抽取有用信息，以及发现新的链接。WebMagic使用[Jsoup](http://jsoup.org/)作为HTML解析工具，并基于其开发了解析XPath的工具[Xsoup](https://github.com/code4craft/xsoup)。

#### 3、Scheduler 调度URL

&emsp;&emsp;Scheduler负责管理待抓取的URL，以及一些去重的工作。WebMagic默认提供了JDK的内存队列来管理URL，并用集合来进行去重。

#### 4、Pipeline 持久化到文件/数据库等

&emsp;&emsp;Pipeline负责抽取结果的处理，包括计算、持久化到文件、数据库等。WebMagic默认提供了“输出到控制台”和“保存到文件”两种结果处理方案。

一般Downloader和Scheduler不需要定制

 流程核心控制引擎 -- Spider ,用来自由配置爬虫,创建/启动/停止/多线程等



#### 用于数据流转的对象

##### 1. Request

`Request`是对URL地址的一层封装，一个Request对应一个URL地址。

它是PageProcessor与Downloader交互的载体，也是PageProcessor控制Downloader唯一方式。

##### 2. Page

`Page`代表了从Downloader下载到的一个页面——可能是HTML，也可能是JSON或者其他文本格式的内容。

##### 3. ResultItems

`ResultItems`相当于一个Map，它保存PageProcessor处理的结果，供Pipeline使用。它的API与Map很类似，值得注意的是它有一个字段`skip`，若设置为true，则不应被Pipeline处理。

参考：https://my.oschina.net/u/3491123/blog/917836