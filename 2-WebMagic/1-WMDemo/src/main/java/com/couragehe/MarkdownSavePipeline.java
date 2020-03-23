package com.couragehe;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.IOException;

/**
 * 保存文件功能
 * Created by bekey on 2017/6/6.
 */
public class MarkdownSavePipeline implements Pipeline {
    public void process(ResultItems resultItems, Task task) {
        try {

            String fileName = resultItems.get("fileName");
            String document = resultItems.get("content");
            String dir = resultItems.get("dir");
            Service.saveFile(document,fileName,dir);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
