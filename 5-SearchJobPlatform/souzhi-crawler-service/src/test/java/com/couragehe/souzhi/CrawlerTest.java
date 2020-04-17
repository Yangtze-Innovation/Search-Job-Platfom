package com.couragehe.souzhi;

import org.jsoup.Jsoup;
import us.codecraft.webmagic.selector.Html;

import javax.swing.text.html.HTML;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @PackageName:com.couragehe.souzhi
 * @ClassName:CrawlerTest
 * @Description:
 * @Autor:CourageHe
 * @Date: 2020/4/9 18:37
 */
public class CrawlerTest {

    public static void testRegex(){
        String img = "<img src=\"//img01.51jobcdn.com/fansImg/CompLogo/1/124/123056/123056_120.gif?635700459643348092\" width=\"63\" height=\"63\" alt=\"\">";
        Matcher m  = Pattern.compile("(.*src=\")(.*)(\" w.*)").matcher(img);

        if(m.find()){
            int size = m.groupCount();
            for(int i =0; i<=size;i++)
                System.out.println("group"+i+"："+m.group(i));
        }else
            System.out.println("无法匹配");
    }

    public static void testXpath(){
        String htmlStr = "<div class=\"tBorderTop_box\">\n" +
                "        <h2><span class=\"bname\">公司信息</span></h2>\n" +
                "        </div>\n" +
                "        <div class=\"com_tag\">\n" +
                "            <p class=\"at\" title=\"民营公司\"><span class=\"i_flag\"></span>民营公司</p>\n" +
                "            <p class=\"at\" title=\"150-500人\"><span class=\"i_people\"></span>150-500人</p>\n" +
                "            <p class=\"at\" title=\"互联网/电子商务,贸易/进出口\">\n" +
                "                <span class=\"i_trade\"></span>\n" +
                "                                    <a href=\"//company.51job.com/shenzhen/hy32/\">互联网/电子商务</a>\n" +
                "                                    <a href=\"//company.51job.com/shenzhen/hy04/\">贸易/进出口</a>\n" +
                "                            </p>\n" +
                "        </div>\n" +
                "        <a track-type=\"jobsButtonClick\" event-type=\"2\" class=\"icon_b i_house\" href=\"https://jobs.51job.com/all/co4992481.html?#syzw\" target=\"_blank\"><span class=\"icon_det\"></span>查看所有职位</a>\n" +
                "    </div>";
        Html html = new Html(htmlStr);
//        String src = html.xpath("//div[@class=\"com_msg\"]/a/img[@src]").toString();
        String src = html.$("div.com_msg a img","src").toString();
        System.out.println(src);

    }
    public  static  void  main(String[]args){
//        testRegex();
        testXpath();
    }

}
