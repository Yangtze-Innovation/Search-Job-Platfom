package com.couragehe.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.admin.cluster.snapshots.status.TransportNodesSnapshotsStatus.Request;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
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
 * JAVA api实现ES 批量查询
 * @author 52423
 *
 */
public class ESDemo2 {
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
	/**
	 * Bulk批量操作
	 * @throws IOException 
	 */
	@Test
	public void Test() throws IOException{
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		
		XContentBuilder doc1 =  XContentFactory.jsonBuilder()
									.startObject()
									.field("title", "Python")
									.field("price", 99)
									.endObject();
		XContentBuilder doc2 = XContentFactory.jsonBuilder()
				.startObject()
				.field("title", "VR")
				.field("price", 98)
				.endObject();
				
		bulkRequest.add(client.prepareIndex("lib2","books","8")
							  .setSource(doc1));
		bulkRequest.add(client.prepareIndex("lib2","books","9")
				  				.setSource(doc2));

		BulkResponse response = bulkRequest.get();
		System.out.println(response.status());
	}

}
