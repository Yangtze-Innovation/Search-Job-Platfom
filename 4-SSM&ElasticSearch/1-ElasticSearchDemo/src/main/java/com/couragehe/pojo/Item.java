package com.couragehe.pojo;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 实体类添加注解
 * indexName: 索引名称(必须小写)
 * type: 文档的类型
 * @Id: 索引库的id
 * index: 是否进行分词	analyzed:分词	 not_analyzed:不分词  no:不根据此字段进行检索
 * store: 是否存储
 * analyzer: 指定使用的分词器
 * type: 存储数据的类型
 * searchAnalyzer: queryStringQuery就是查询条件进行分词的分词器.
 * @author 52423
 *
 */
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
	

	
	
}
