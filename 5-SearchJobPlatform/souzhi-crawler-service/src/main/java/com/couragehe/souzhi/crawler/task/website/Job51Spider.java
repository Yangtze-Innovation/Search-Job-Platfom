package com.couragehe.souzhi.crawler.task.website;

import com.couragehe.souzhi.beans.Company;
import com.couragehe.souzhi.beans.Position;
import com.couragehe.souzhi.beans.PositionDetail;
import com.couragehe.souzhi.crawler.mapper.CompanyMapper;
import com.couragehe.souzhi.crawler.mapper.PositionDetailMapper;
import com.couragehe.souzhi.crawler.mapper.PositionMapper;
import com.couragehe.souzhi.utils.UUIDUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @PackageName:com.couragehe.souzhi.crawler.task.website
 * @ClassName:Job51Spider
 * @Description:
 * @Autor:CourageHe
 * @Date: 2020/4/9 10:39
 */
@Component
public class Job51Spider implements WebsiteSpider {

    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    PositionMapper positionMapper;
    @Autowired
    private PositionDetailMapper positionDetailMapper;


    /**
     * 从page解析职务
     * 模版页面：https://jobs.51job.com/shenzhen-nsq/121270657.html?s=01&t=0
     *
     * @param page
     */
    @Override
    public synchronized void parseJobInfo(Page page) {

        String[] s1 = new String[5];
        String[] s2 = new String[5];
        //解析页面
        Html html = page.getHtml();

        //获取数据，封装到对象中
        //用正则获取学历，经验，城市，发布时间
        String s = html.css("div.cn p.msg", "text").toString();
        Pattern p = Pattern.compile("([\\u4e00-\\u9fa5_a-zA-Z0-9-]+)");
        Matcher m = p.matcher(s);
        //加入线程锁，否则会出现学历为召几人的情况
        synchronized (this) {
            for (int i = 0; i < 5; i++) {
                if (m.find()) {
                    s1[i] = m.group(1);
                    s2[i] = s1[i];

                }
            }
            //如果学历要求的内容没有，则显示学历不限的字样
            if (s1[4] == null) {
                s2[4] = s1[3];
                s2[2] = "学历不限";
            }
        }
        String ss = html.css("div.cn p.msg", "text").toString();
        String messges[]=ss.split("  ");


        String settime = messges[4].substring(0, messges[4].length() - 2);
        //1、招聘详情对象
        Position position = new Position();
        position.setId(UUIDUtils.getUUID64());
        position.setWorkCity(messges[0].substring(0, messges[0].indexOf("-")));
        position.setWorkDistrict(messges[0].substring(messges[0].indexOf("-") + 1));

        position.setDetailUrl(page.getUrl().toString());

        //获取工作薪资
        position.setWorkSalary(html.$("div.cn strong", "text").toString());//问题
//        position.setWorkSalary(Jsoup.clean(html.$("div.cn strong").toString(), Whitelist.none()));

        position.setOriginWebsite("51job");
        position.setPositionName(html.css("div.cn h1", "text").toString());
        position.setCompanyName(html.css("div.cn p.cname a", "text").toString());
        //格式公司地址  去掉“上班地址  地图”的字
        position.setCreateTime(settime);
        position.setFormatCreateTime(settime);
        position.setEducationRequire(messges[2]);
        position.setWorkYear(messges[1]);
        position.setWorkNature("全职");
        position.setIsSchoolJob(0);


        //2、工作详情
        //获取工作详细地址
        String positionAddress = html.xpath("//div[@class=\"bmsg inbox\"]/p[@class=\"fp\"]/text()").toString();//问题
        //获取工作简要内容
        String positionDescMix = Jsoup.parse(html.xpath("//div[@class=\"bmsg job_msg inbox\"]/p").nodes().toString()).text();
        String positionDesc = positionDescMix.substring(1, positionDescMix.length() - 1);
        PositionDetail positionDetail = new PositionDetail(UUIDUtils.getUUID64(), position.getId(), positionDesc, positionAddress);

        //3、公司
        //公司名称
        String companyName = html.xpath("//div[@class=\"com_msg\"]/a/p/text()").toString();
        //公司图标
        String companyLogo = "http://r.soozhi.net/portal/201107/search/logo.gif";
        String imgString = html.xpath("//div[@class=\"com_msg\"]/a/img[@src]").toString();
        Matcher imgMatcher = Pattern.compile("(.*src=\")(.*)(\" w.*)").matcher(imgString);
        if (imgMatcher.find()) {
            companyLogo = imgMatcher.group(2);
            System.out.println(imgMatcher.group(2));
        }
        //公司规模
        String financeStage = Jsoup.parse(html.$("div.com_tag p.at").nodes().get(0).toString()).text();
        String companySize = Jsoup.parse(html.$("div.com_tag p.at").nodes().get(1).toString()).text();
        String industryField = Jsoup.parse(html.$("div.com_tag p.at").nodes().get(2).toString()).text();
        //获取公司标签(有的职位没有写)
        String sc = html.css("div.jtag div.t1 span.sp4", "text").nodes().toString();

        //格式公司标签  去掉“[]”
        String companyLabelList = sc.substring(1, sc.length() - 1);
        Company company = new Company(UUIDUtils.getUUID64(), companyName, companyLogo, companySize, industryField, financeStage, companyLabelList);
        //保存结果
        page.putField("position", position);
        page.putField("positionDetail", positionDetail);
        page.putField("company", company);
    }

    /**
     * 从page中进行深度、广度爬取，即添加url
     *
     * @param page
     */
    @Override
    public void addPageUrl(Page page) {
        String url = page.getRequest().getUrl();
        Html html = page.getHtml();
        List<String> positionUrls = html.xpath("//span[@class=\"title\"]/a").links().all();//xpath选择器
        List<String> positionTypeUrls = html.css("div.filter div.e5 a").links().all();//css选择器
        List<String> positionPagesUrls = html.$("div.p_in li a").links().all();

        if (positionUrls.size() != 0) {
            //该页为51job职务列表页，将职务的详情链接加入队列
            page.addTargetRequests(positionUrls);
        }
        if (positionPagesUrls.size() != 0) {
            //该页为51job职务列表页，将职务的分页链接加入队列
            page.addTargetRequests(positionPagesUrls);
        } else if (positionTypeUrls.size() != 0) {
            //该页为51JOB职务导航页 ，仅获取 全部招聘职位的链接，将其加入待爬队列中
            page.addTargetRequests(positionTypeUrls);
        }
    }

    /**
     * 爬取的信息持久化至数据库
     *
     * @param resultItems
     */
    @Override
    public void jobInfoSave(ResultItems resultItems) {
        //获取封装好的数据
        Position position = resultItems.get("position");
        PositionDetail positionDetail = resultItems.get("positionDetail");
        Company company = resultItems.get("company");

        //先判断数据库中是否有该公司，
        Company company1 = new Company();
        company1.setCompanyName(company.getCompanyName());
        company1 = companyMapper.selectOne(company1);
        if(company1 == null){
            //数据库中没有该公司
            companyMapper.insertSelective(company);
            position.setCompanyId(company.getId());
        }else {
            position.setCompanyId(company1.getId());
        }
        positionMapper.insertSelective(position);
        positionDetailMapper.insertSelective(positionDetail);

    }
}

