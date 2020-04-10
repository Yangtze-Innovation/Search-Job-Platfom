package com.couragehe.souzhi;

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
    public  static  void  main(String[]args){
        testRegex();
    }

}
