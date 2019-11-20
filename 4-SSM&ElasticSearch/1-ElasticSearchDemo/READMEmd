## ElasticSearch概述、优点、测试

### 一、为什么要用ElasticSearch

#### 1、数据库查询缺点

​	数据越大, 查询效率越低;(数据库的解决方案是建立索引, 但是使用前模糊查询,会导致索引失效)

**查询数据库总量：**35万数据量，用时10秒左右。

![4.1数据库使用模糊查询](..\..\Resource\images\4.1数据库总量查询.png)

**使用模糊查询JAVA：**模糊查询，用时8秒左右。

![4.1数据库总量查询](..\..\Resource\images\4.1数据库使用模糊查询.png)

#### 2、ES使用优势

1. 分布式实时文件存储，可将每一个字段存入索引，使其可以被检索到。
2. 实时分析的分布式搜索引擎。
   分布式：索引分拆成多个分片，每个分片可有零个或多个副本。集群中的每个数据节点都可承载一个或多个分片，并且协调和处理各种操作；负载再平衡和路由在大多数情况下自动完成。
3. 可以扩展到上百台服务器，处理PB级别的结构化或非结构化数据。也可以运行在单台PC上（已测试）
4. 支持插件机制，分词插件、同步插件、Hadoop插件、可视化插件等。

与数据库相较而言: 解决数据库中数据量过大同时模糊查询会导致数据库索引失效,查询效率低的问题.

参考：https://blog.csdn.net/Orangesss_/article/details/82260749

### 二、相关概念理解

#### 1、逻辑层面

+ **Index (索引)**：这里的 Index 是名词，一个 Index 就像是传统关系数据库的 Database，它是 Elasticsearch 用来存储数据的逻辑区域

+ **Document (文档)**：Elasticsearch 使用 JSON 文档来表示一个对象，就像是关系数据库中一个 Table 中的一行数据

+ **Type (类型)**：文档归属于一种 Type，就像是关系数据库中的一个 Table

+ **Field (字段)**：每个文档包含多个字段，类似关系数据库中一个 Table 的列

| Elasticsearch | MySQL    |
| :------------ | :------- |
| Index         | Database |
| Type          | Table    |
| Document      | Row      |
| Field         | Column   |

#### 2、物理层面

+ **Node (节点)**：node 是一个运行着的 Elasticsearch 实例，一个 node 就是一个单独的 server
+ **Cluster (集群)**：cluster 是多个 node 的集合
+ **Shard (分片)**：数据分片，一个 index 可能会存在于多个 shard

### 三、端口的区别及作用

Elasticsearch 启动后，也启动了两个端口 9200 和 9300：

- 9200 端口：HTTP RESTful 接口的通讯端口
- 9300 端口：TCP 通讯端口，用于集群间节点通信和与 Java 客户端通信的端口

### 四、ES相关使用demo

#### 1、elasticsearch.xml配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:elasticsearch="http://www.springframework.org/schema/data/elasticsearch"
       xsi:schemaLocation="
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.2.xsd
		http://www.springframework.org/schema/data/elasticsearch http://www.springframework.org/schema/data/elasticsearch/spring-elasticsearch-1.0.xsd">

    <!-- 搜索DAO 扫描 -->
    <elasticsearch:repositories base-package="com.couragehe.dao" />

    <!-- 配置ES客户端的连接-->
    <elasticsearch:transport-client id="client" cluster-nodes="127.0.0.1:9300"/>

    <!-- 配置ES模板  -->
    <bean id="elasticsearchTemplate"
          class="org.springframework.data.elasticsearch.core.ElasticsearchTemplate">
        <constructor-arg name="client" ref="client" />
    </bean>
    <!-- 配置service扫描 -->
    <context:component-scan base-package="com.couragehe.service"/>
</beans>

```

#### 2、配置maven依赖

```xml
	<!-- transport客户端 -->
		<dependency>
			<groupId>org.elasticsearch.client</groupId>
			<artifactId>transport</artifactId>
			<version>5.6.8</version>
		</dependency>
		<!-- elasticsearch包 -->
		<dependency>
			<groupId>org.elasticsearch</groupId>
			<artifactId>elasticsearch</artifactId>
			<version>5.6.8</version>
		</dependency>
		<!-- Spring data与elasticSearch结合的包 给各种数据访问提供统一的编程接口 -->
		<dependency>
			<groupId>org.springframework.data</groupId>
			<artifactId>spring-data-elasticsearch</artifactId>
			<version>3.0.5.RELEASE</version>
		</dependency>
		<!-- Spring测试包 -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-test</artifactId>
			<version>5.1.0.RELEASE</version>
		</dependency>
```

#### 3、ES实体JavaBean

```java
@Document(indexName ="item",type="item")
public class Item {
	@Id
	@Field(index = true,store = true,type = FieldType.Integer)
	private Integer id; 		
	@Field(index = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart",store = true,type = FieldType.text)
	private String jobname; 					                   		
	@Field(index = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart",store = true,type = FieldType.text)
	private String content;
	public Integer getId() {
		return id;
	}
	public void setId(Integer i) {
		this.id = i;
	}
	public String getJobname() {
		return jobname;
	}
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Item [id=" + id + ", jobname=" + jobname + ", content=" + content + "]";
	}
}

```

#### 4、测试ES的类

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-elasticsearch.xml")
public class TestSearch {
	@Autowired
	private JobService jobService;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Before
	public void init() {
		
	}
	//创建索引和映射
	@Test
	public void createIndex() {
		this.elasticsearchTemplate.createIndex(Item.class);
		this.elasticsearchTemplate.putMapping(Item.class);
	}
	//新增
	@Test
	public void TestSave() {
		Item item = new Item();
		item.setId(1234561);
		item.setJobname("Java工程师1");
		item.setContent("房租减半，水电全免1");
		this.jobService.save(item);
	}
	//修改,和新增的代码一样，如果id存在则是修改，不存在就是新增
	@Test
	public void TestUpdata() {
		Item item = new Item();
		item.setId(1234561);
		item.setJobname("Java工程师111");
		item.setContent("房租减半，水电全免111");
		this.jobService.save(item);
	}
	//删除
	@Test
	public void TestDelete() {
		Item item = new Item();
		item.setId(1234561);
		this.jobService.delete(item);
	}
	//批量保存
	
	@Test
	public void TestSaveAll() {
		//创建集合
		List<Item> list = new ArrayList<Item>();
		//封装数据
		for(int i = 0;i<100;i++) {
			Item item = new Item();
			item.setId(1234561+i);
			item.setJobname("Java工程师111"+i);
			item.setContent("房租减半，水电全免111"+i);
			list.add(item);
		}
		//批量保存
		this.jobService.saveAll(list);
	}
	//查询所有数据
	@Test
	public void testFindAll() {
		Iterable<Item> items = this.jobService.findAll();
		for(Item item:items) {
			System.out.println(item);
		}
	}
	//分页查询数据
	@Test 
	public void testFindByPage() {
		Page<Item> items = this.jobService.findByPage(1,10);
		for(Item item:items) {
			System.out.println(item);
		}
	}
	
	//复杂查询
	//根据title和Content进行查询，交集
	@Test
	public void TestFindByJobnameAndContent() {
		List<Item> list = this.jobService.findByJobnameAndContent("11122","11122");
		for(Item item:list) {
			System.out.println(item);
		}
	}
	//根据title和Content进行查询，并集
	@Test
	public void TestFindByJobnameOrContent() {
		List<Item> list = this.jobService.findByJobnameOrContent("11122","11123");
		for(Item item:list) {
			System.out.println(item);
		}		
	}
	//根据title或者content和id的范围 进行分页查询
	@Test
	public void TestFindByJobnameAndContentAndIdBetween() {
		Page<Item> items = this.jobService.findByJobnameAndContentAndIdBetween("Java工程师111","房租减半，水电全免111",12345610, 12345616,1,3);
		for(Item item:items) {
			System.out.println(item);
		}
	}
}

```



