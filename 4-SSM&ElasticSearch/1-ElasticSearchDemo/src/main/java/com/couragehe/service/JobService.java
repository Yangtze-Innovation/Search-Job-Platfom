package com.couragehe.service;


import java.util.List;

import com.couragehe.pojo.Item;


public interface JobService {
	//新增
	public void save(Item item);
	//删除
	public void delete(Item item);
	//批量保存
	public void saveAll(List<Item> list);
}
