package com.couragehe.souzhi.crawler.task.website;

import java.util.ArrayList;

import java.util.List;

import com.couragehe.souzhi.beans.Company;
import com.couragehe.souzhi.beans.Position;
import com.couragehe.souzhi.beans.PositionDetail;
import com.couragehe.souzhi.crawler.mapper.CompanyMapper;
import com.couragehe.souzhi.crawler.mapper.PositionDetailMapper;
import com.couragehe.souzhi.crawler.mapper.PositionMapper;
import com.couragehe.souzhi.utils.UUIDUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.selector.JsonPathSelector;

/**
 * 拉勾网的职业信息封装
 * 拉勾网信息爬取机制繁琐，必须是首先获取搜索页的Cookie才能
 * 向拉勾的职业信息api发起请求
 *  @author CourageHe
 * */
@Component
public class LagouSpider extends WebsiteSpider {
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private PositionMapper positionMapper;
	@Autowired
	private PositionDetailMapper positionDetailMapper;
	//日志输出
	private Logger logger = LoggerFactory.getLogger(getClass());


	/**
	 * 拉勾网下载重写
	 *
	 * @param url 携带keyword、page的链接
	 * @return 发起请求的响应头
	 */
	public static CloseableHttpResponse lagouDownloader(String url) {
		CloseableHttpClient httpClient = getLagouClient();
		//第一次ajax后返回json数据获得总数，对所有页面进行请求
		int num = Integer.parseInt(url.substring(url.indexOf("&num=") + 5));
		//截取关键字
		String keyword = url.substring(url.indexOf("keyword=") + 8, url.indexOf("&num="));
		HttpPost httpPost = new HttpPost(url);

		//添加请求头
		httpPost.addHeader("Host", "www.lagou.com");
		httpPost.addHeader("Accept", "'application/json, text/javascript, */*; q=0.01");
		httpPost.addHeader("Referer", "'https://www.lagou.com/jobs/list_Java/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=");
		httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
		httpPost.addHeader("Origin", "https://www.lagou.com");
		httpPost.addHeader("X-Anit-Forge-Code", "0");
		httpPost.addHeader("X-Anit-Forge-Token", "Nne");
		httpPost.addHeader("User-Agent", "Mozilla/5.0(X11; Linux x86_64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");

		//post添加请求body
		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
		nameValuePairList.add(new BasicNameValuePair("first", "true"));
		nameValuePairList.add(new BasicNameValuePair("pn", num + ""));
		nameValuePairList.add(new BasicNameValuePair("kd", keyword));
		HttpEntity entity;
		try {
			entity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
			httpPost.setEntity(entity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 每次请求都必须请求一次搜索页以获得新的Cookie
	 *
	 * @return 携带新Cookie的httpClient
	 */
	public static CloseableHttpClient getLagouClient() {
		String urlHome = "https://www.lagou.com/jobs/list_Java/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(urlHome);
		httpGet.addHeader("Host", "www.lagou.com");
		httpGet.addHeader("Accept", "'application/json, text/javascript, */*; q=0.01");
		httpGet.addHeader("Referer", "'https://www.lagou.com/jobs/list_Java/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=");
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpClient;
	}

	/**
	 * 拉钩网站具体职务信息从Page中解析出来，并保存至field
	 *
	 * @param page
	 */
	public void parseJobInfo(Page page) {

		//1、职位
		List<String> positionName = new JsonPathSelector("$.content.positionResult.result[*].positionName").selectList(page.getRawText());
		List<String> skillLables = new JsonPathSelector("$.content.positionResult.result[*].skillLables").selectList(page.getRawText());
		List<String> createtime = new JsonPathSelector("$.content.positionResult.result[*].createTime").selectList(page.getRawText());
		List<String> formatCreateTime = new JsonPathSelector("$.content.positionResult.result[*].formatCreateTime").selectList(page.getRawText());
		List<String> city = new JsonPathSelector("$.content.positionResult.result[*].city").selectList(page.getRawText());
		List<String> district = new JsonPathSelector("$.content.positionResult.result[*].district").selectList(page.getRawText());
		List<String> salary = new JsonPathSelector("$.content.positionResult.result[*].salary").selectList(page.getRawText());
		List<String> workYear = new JsonPathSelector("$.content.positionResult.result[*].workYear").selectList(page.getRawText());
		List<String> jobNature = new JsonPathSelector("$.content.positionResult.result[*].jobNature").selectList(page.getRawText());
		List<String> education = new JsonPathSelector("$.content.positionResult.result[*].education").selectList(page.getRawText());
		List<String> positionAdvantage = new JsonPathSelector("$.content.positionResult.result[*].positionAdvantage").selectList(page.getRawText());
		List<String> isSchoolJob = new JsonPathSelector("$.content.positionResult.result[*].isSchoolJob").selectList(page.getRawText());
		//组合URL链接
		List<String> positionId = new JsonPathSelector("$.content.positionResult.result[*].positionId").selectList(page.getRawText());
		String showId = page.getJson().jsonPath("$.content.showId").get();

		page.putField("positionName", positionName);
		page.putField("skillLables", skillLables);
		page.putField("createtime", createtime);
		page.putField("formatCreateTime", formatCreateTime);
		page.putField("city", city);
		page.putField("district", district);
		page.putField("salary", salary);
		page.putField("workYear", workYear);
		page.putField("jobNature", jobNature);
		page.putField("education", education);
		page.putField("positionAdvantage", positionAdvantage);
		page.putField("isSchoolJob", isSchoolJob);
		page.putField("positionId", positionId);
		page.putField("showId", showId);


		//2、职位详情
		//组合职位详情
//		List<String> positionDesc = new JsonPathSelector("$.content.positionResult.result[*].skillLables").selectList(page.getRawText());
		List<String> positionAddress = new JsonPathSelector("$.content.positionResult.result[*].businessZones").selectList(page.getRawText());
//		page.putField("positionDesc",positionDesc);
		page.putField("positionAddress", positionAddress);

		//3、公司
		List<String> companyname = new JsonPathSelector("$.content.positionResult.result[*].companyFullName").selectList(page.getRawText());
		List<String> companySize = new JsonPathSelector("$.content.positionResult.result[*].companySize").selectList(page.getRawText());
		List<String> companyLogo = new JsonPathSelector("$.content.positionResult.result[*].companyLogo").selectList(page.getRawText());
		List<String> industryField = new JsonPathSelector("$.content.positionResult.result[*].industryField").selectList(page.getRawText());
		List<String> financeStage = new JsonPathSelector("$.content.positionResult.result[*].financeStage").selectList(page.getRawText());
		List<String> companylablelist = new JsonPathSelector("$.content.positionResult.result[*].companyLabelList").selectList(page.getRawText());

		page.putField("companyname", companyname);
		page.putField("companySize", companySize);
		page.putField("companyLogo", companyLogo);
		page.putField("industryField", industryField);
		page.putField("financeStage", financeStage);
		page.putField("companylablelist", companylablelist);

	}


	/**
	 * 1、对拉勾网进行广度爬取，获取首页的推荐分类词汇，组合链接爬取
	 * 2、对拉勾网某职务的所有页面进行深度爬取，获取所有职务的列表页。
	 *
	 * @param page
	 */
	public void addPageUrl(Page page) {
		String url = page.getRequest().getUrl();

		//确保是职位ajax请求而且是第一次请求返回的数据
		if ((url.indexOf("positionAjax") != -1) && url.substring(url.indexOf("num=") + 4).equals("1")) {
			//第一次ajax后返回json数据获得总数，对所有页面进行请求
			int num = Integer.parseInt(new JsonPathSelector("$.content.positionResult.totalCount").select(page.getRawText()));
			//截取关键字
			String keyword = url.substring(url.indexOf("keyword=") + 8, url.indexOf("&num="));
			//获取页数
			num = num > 450 ? 300 : num / 15;
			for (int i = 2; i <= num; i++) {
				page.addTargetRequest("https://www.lagou.com/jobs/positionAjax.json?needAddtionalResult=false&keyword=" + keyword + "&num=" + i);
			}
			System.out.println("总个数：" + num);
		} else if ("https://www.lagou.com".equals(page.getRequest().getUrl())) {
			//解析首页关键字、并发起请求
			Document doc = Jsoup.parse(page.getRawText());
			List<String> list = doc.getElementsByClass("menu_sub").select("a").eachText();
//			int num = 0;
//			对每个关键字发起第一个请求
			for (String keyword : list) {
//				if(num++ == 10)return ;
				page.addTargetRequest("https://www.lagou.com/jobs/positionAjax.json?needAddtionalResult=false&keyword=" + keyword + "&num=" + 1);
			}
		}

	}

	/**
	 * 拉勾职业信息持久化存储到数据库
	 *
	 * @param resultItems 处理结果集
	 */
	public void jobInfoSave(ResultItems resultItems) {
		List<String> positionName = resultItems.get("positionName");
		List<String> skillLables = resultItems.get("skillLables");
		List<String> createtime = resultItems.get("createtime");
		List<String> formatCreateTime = resultItems.get("formatCreateTime");
		List<String> city = resultItems.get("city");
		List<String> district = resultItems.get("district");
		List<String> salary = resultItems.get("salary");
		List<String> workYear = resultItems.get("workYear");
		List<String> jobNature = resultItems.get("jobNature");
		List<String> education = resultItems.get("education");
		List<String> positionAdvantage = resultItems.get("positionAdvantage");
		List<String> isSchoolJob = resultItems.get("isSchoolJob");
		List<String> positionId = resultItems.get("positionId");
		String showId = resultItems.get("showId");

//		List<String> positionDesc =resultItems.get("positionDesc");
		List<String> positionAddress = resultItems.get("positionAddress");
		List<String> companyname = resultItems.get("companyname");
		List<String> companySize = resultItems.get("companySize");
		List<String> companyLogo = resultItems.get("companyLogo");
		List<String> industryField = resultItems.get("industryField");
		List<String> financeStage = resultItems.get("financeStage");
		List<String> companylablelist = resultItems.get("companylablelist");


		for (int i = 0; i < positionName.size(); i++) {
			//1、招聘详情对象
			Position position = new Position();
			position.setId(UUIDUtils.getUUID64());
			position.setPositionName(positionName.get(i));
			position.setSkillLables(skillLables.get(i));
			position.setCompanyName(companyname.get(i));
			position.setCreateTime(createtime.get(i));
			position.setWorkCity(city.get(i));
			position.setWorkDistrict(district.get(i));
			position.setWorkSalary(salary.get(i));
			position.setWorkYear(workYear.get(i));
			position.setWorkNature(jobNature.get(i));
			position.setEducationRequire(education.get(i));
			position.setPositionAdvantage(positionAdvantage.get(i));
			position.setIsSchoolJob(Integer.parseInt(isSchoolJob.get(i)));
			position.setFormatCreateTime(formatCreateTime.get(i));
			//组合成职位跳转链接
			String url = "https://www.lagou.com/jobs/" + positionId.get(i) + ".html?show=" + showId;
			position.setDetailUrl(url);
			position.setOriginWebsite("拉勾");

			//2、工作详情
			// 组合成职位详情
			String positionDesc = "职位优势：" + positionAdvantage.get(i) + "技能要求：" + skillLables.get(i);
			PositionDetail positionDetail = new PositionDetail(UUIDUtils.getUUID64(), position.getId(), positionDesc, positionAddress.get(i));

			//3、公司
			Company company = new Company(UUIDUtils.getUUID64(), companyname.get(i), "https://www.lgstatic.com/" + companyLogo.get(i), companySize.get(i), industryField.get(i), financeStage.get(i), companylablelist.get(i));

			//先判断数据库中是否有该公司，
			Company company1 = new Company();
			company1.setCompanyName(company.getCompanyName());
			List<Company>companyList = companyMapper.select(company1);
			if (companyList.size() == 0) {
				//数据库中没有该公司
				companyMapper.insertSelective(company);
				position.setCompanyId(company.getId());
			} else {
				position.setCompanyId(companyList.get(0).getId());
			}
			positionMapper.insertSelective(position);
			positionDetailMapper.insertSelective(positionDetail);

			synchronized (positionMapper){
				//爬取职务的计数器
				logger.info("The number of position saved has {} ",++count);
			}
		}
	}

}
