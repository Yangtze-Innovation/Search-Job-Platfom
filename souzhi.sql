CREATE DATABASE search_job;
use search_job
CREATE table job_data(
				id  VARCHAR(64)  PRIMARY KEY, 			 #主键 索引
				jobname  VARCHAR(30), 					 #查询的工作名称（相关度）
				lowSalary INT,							#工作最低薪资
				highSalary INT,							 #工作最高薪资
				city  VARCHAR(10),						 #城市名
				experience VARCHAR(15),			 	 	 #工作经验 
				education VARCHAR(5),					 #教育学历筛选。/本科/硕士/博士/应届毕业生
				companylablelist  VARCHAR(60), 	  		 #公司标签列表 五险一金和其他说明信息
				companyname  VARCHAR(30),  			     #公司名
				companyaddress  VARCHAR(80), 		  	 #公司地址信息
				createtime TIMESTAMP,					 #工作发布时间  也可使用datetime
				url       VARCHAR(80),                   #跳转具体招聘信息的链接		
				detail text,							 #招聘详细信息
				website varchar(10)						 #所属网站
)ENGINE=InnoDB DEFAULT CHARSET=utf8;