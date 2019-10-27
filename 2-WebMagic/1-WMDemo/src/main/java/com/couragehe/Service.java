package com.couragehe;

import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 提供共有方法服务
 * Created by bekey on 2017/6/6.
 */
public class Service {
    /**
     * 公有方法,将body解析为markdown文本
     * @param article #content内容
     * @return markdown文本
     */
    public static String markdown(Element article){
        StringBuilder markdown = new StringBuilder("");
        article.children().forEach(it ->parseEle(markdown, it, 0));
        return markdown.toString();
    }

    /**
     * 保存为本地文件
     * @param direct 文件夹
     * @param fileName 文件名
     * @param content 保存的内容
     */
    public static void saveFile(String content,String fileName,String direct) throws IOException{
        File dir = new File(direct);
        if(!dir.exists() || dir.isFile()) {
            if(!dir.mkdirs())
                throw new RuntimeException("创建文件夹失败");
        }
        File file = new File(dir,fileName);
        if(!file.exists() || file.isDirectory()){
            if(!file.createNewFile())
                throw new RuntimeException("创建文件失败");
        }
        PrintWriter pw = new PrintWriter(file);
        pw.write(content);
        pw.close();
        System.out.println("文件创建成功"+file.getName());
    }

    /**
     * 私有方法,解析单个元素并向StringBuilder添加
     */
    private static void parseEle(StringBuilder markdown, Element ele, int level){
        //处理相对地址为绝对地址
        ele.getElementsByTag("a").forEach(it -> it.attr("href",it.absUrl("href")));
        ele.getElementsByTag("img").forEach(it -> it.attr("src",it.absUrl("src")));
        //先判断class,再判定nodeName
        String className = ele.className();
        if(className.contains("example_code")){
            String code = ele.html().replace("&nbsp;"," ").replace("<br>","");
            markdown.append("```\n").append(code).append("\n```\n");
            return;
        }
        String nodeName = ele.nodeName();
        //获取到每个nodes,根据class和标签进行分类处理,转化为markdown文档
        if(nodeName.startsWith("h") && !nodeName.equals("hr")){
            int repeat = Integer.parseInt(nodeName.substring(1)) + level;
            markdown.append(repeat("#", repeat)).append(' ').append(ele.text());
        }else if(nodeName.equals("p")){
            markdown.append(ele.html()).append("  ");
        }else if(nodeName.equals("div")){
            ele.children().forEach(it -> parseEle(markdown, it, level + 1));
        }else if(nodeName.equals("img")) {
            ele.removeAttr("class").removeAttr("alt");
            markdown.append(ele.toString()).append("  ");
        }else if(nodeName.equals("pre")){
            markdown.append("```").append("\n").append(ele.html()).append("\n```");
        }else if(nodeName.equals("ul")) {
            markdown.append("\n");
            ele.children().forEach(it -> parseEle(markdown, it, level + 1));
        }else if(nodeName.equals("li")) {
            markdown.append("* ").append(ele.html());
        }
        markdown.append("\n");
    }

    private static String repeat(String chars,int repeat){
        String a = "";
        if(repeat > 6) repeat = 6;
        for(int i = 0;i<=repeat;i++){
            a += chars;
        }
        return a;
    }
}
