package com.couragehe.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.admin.cluster.snapshots.status.TransportNodesSnapshotsStatus.Request;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

/**
 * JAVA api实现ES增删改查
 * @author 52423
 *
 */
public class ESDemo1 {
	private TransportClient client;
	/**
	 * 获取ElasticSearch
	 * @throws UnknownHostException 
	 */
	@Before
	public void init() throws UnknownHostException {
		//指定Es集群
		Settings settings = Settings.builder().
				put("client.transport.sniff", true)
				.build();//自动嗅探其他集群的ip 如果有则加入
//		Settings settings = Settings.builder().put("cluster.name","my-application").build();
		
		
	    InetSocketTransportAddress master = new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300);
	    client = new PreBuiltTransportClient(settings)
									.addTransportAddress(master);
	}
	
	//查询文档
	@Test
	public void Test(){
		//数据查询
		GetResponse response = client.prepareGet("lib", "user", "1").execute().actionGet();
		//得到查询出的数据
		System.out.println(response.getSourceAsString());
		//关闭数据类型
		client.close();
	}
	/**
	 * 添加文档
	 * @throws IOException 
	 */
	@Test
	public void Test2() throws IOException{
		 XContentBuilder doc = XContentFactory.jsonBuilder()
				 				.startObject()
				 				.field("id", "1")
				 				.field("title", "ElasticSearch高级进阶")
				 				.field("postdate", "2019-11-22")
				 				.field("url", "www.baidu.com")
				 				.endObject();
		 
	IndexResponse response = client.prepareIndex("index1", "blog", "10")
									.setSource(doc).get();
	System.out.println(response.status());
	}
	/**
	 * 删除文档
	 */
	@Test
	public void Test3() {
		DeleteResponse response = client.prepareDelete("index1","blog","10").get();
		System.out.println(response.status());
	}

	/**
	 * 更新文档
	 * @throws IOException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@Test
	public void Test4() throws IOException, InterruptedException, ExecutionException {
		 XContentBuilder doc = XContentFactory.jsonBuilder()
	 				.startObject()
	 				.field("title", "ElasticSearch低级进阶")
	 				.endObject();
		 
		UpdateRequest request = new UpdateRequest();
		request.index("index1")
			   .type("blog")
			   .id("10")
			   .doc(doc);
		UpdateResponse response = client.update(request).get();
		
		System.out.println(response.status());
			   
	}
	/**
	 * upsert模式更新
	 * 若存在则修改，不存在则创建
	 * @throws IOException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@Test
	public void Test5() throws IOException, InterruptedException, ExecutionException {
		 XContentBuilder doc = XContentFactory.jsonBuilder()
	 				.startObject()
	 				.field("id", "2")
	 				.field("title", "ElasticSearch测试")
	 				.field("postdate", "2019-11-22")
	 				.field("url", "www.baidu.com")
	 				.endObject();
		
		IndexRequest request = new IndexRequest("index1","blog","8")
								.source(doc);
		
		//upsert 如果更新请求不成功则执行新建请求
		UpdateRequest request2 = new UpdateRequest("index1","blog","8")
								.doc(doc).upsert(request);
		
		UpdateResponse response = client.update(request2).get();
		
		System.out.println(response.status());
		
		
	}
}
