package com.couragehe.entity;

import java.util.List;

public class JobResult {
	private List<JobInfoField>rows;
	//总页数
	private Integer pageTotal;
	public List<JobInfoField> getRows() {
		return rows;
	}
	public void setRows(List<JobInfoField> rows) {
		this.rows = rows;
	}
	public Integer getPageTotal() {
		return pageTotal;
	}
	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}
	
}
