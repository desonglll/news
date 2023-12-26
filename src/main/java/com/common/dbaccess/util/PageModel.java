package com.common.dbaccess.util;

import java.util.List;

public class PageModel<E> {
	private int total; // 总条数
	private int pages; // 总页数
	private int curpage;//当前页
	private int pagesize;
	private List<E> retList;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public int getCurpage() {
		return curpage;
	}
	public void setCurpage(int curpage) {
		this.curpage = curpage;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public List<E> getRetList() {
		return retList;
	}
	public void setRetList(List<E> retList) {
		this.retList = retList;
	}

}
