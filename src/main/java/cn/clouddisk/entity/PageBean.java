package cn.clouddisk.entity;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageBean {
	private List<?> list; // 一页的所有记录
	private int totalrecord; // 总共多少条记录
	private int pagesize=5; // 一页有几条数据
	private int totalpage; // 总共多少页
	private int currentpage=1; // 用户想看的页
	private int previouspage; // 想看的页的前一页
	private int nextpage; // 想看的页的下一页
	private int[] pagebar; // 底下的 1 2 3 ...页码条
	private int startindex=0;
	private String searchcontent;

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public int getTotalrecord() {

		return totalrecord;
	}

	public void setTotalrecord(int totalrecord) {
		this.totalrecord = totalrecord;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		if (pagesize <= 0)
			this.pagesize = 5;
		else
			this.pagesize = pagesize;
	}

	public int getTotalpage() {
		if (this.totalrecord == 0) {
			return 1;
		} else if (this.totalrecord % this.pagesize == 0) {
			this.totalpage = this.totalrecord / this.pagesize;
		} else {
			this.totalpage = this.totalrecord / this.pagesize + 1;
		}
		return totalpage;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		if (currentpage <= 0)
			this.currentpage = 1;
		else
			this.currentpage = currentpage;
	}

	public int getPreviouspage() {
		if (this.currentpage - 1 < 1) {
			this.previouspage = 1;
		} else {
			this.previouspage = this.currentpage - 1;
		}
		return previouspage;
	}

	public int getNextpage() {
		if (this.currentpage + 1 >= this.totalpage) {
			this.nextpage = this.totalpage;
		} else {
			this.nextpage = this.currentpage + 1;
		}
		return nextpage;
	}

	public int[] getPagebar() {
		int startpage;
		int endpage;
		int pagebar[] = null;
		if (this.totalpage <= 10) { // 如果页码总共不超过10页，全部显示出来就好了
			pagebar = new int[this.totalpage];
			startpage = 1;
			endpage = this.totalpage;
		} else { // 总页数大于10，显示邻近的10页
			pagebar = new int[10];
			startpage = this.currentpage - 4;
			endpage = this.currentpage + 5;
			if (startpage < 1) { // 确保不能越界
				startpage = 1;
				endpage = 10;
			}

			if (endpage > this.totalpage) {
				endpage = this.totalpage;
				startpage = this.totalpage - 9;
			}
		}

		int index = 0;
		for (int i = startpage; i <= endpage; i++) {
			pagebar[index++] = i;
		}

		this.pagebar = pagebar;
		return this.pagebar;
		/*
		 * int pagebar[] = new int[this.totalpage]; for(int i=1;i<=this.totalpage;i++){
		 * pagebar[i-1] = i; } this.pagebar = pagebar; return pagebar;
		 */
	}

	public int getStartindex() {
		// 比如用户想看第2页，每页5条数据，那么数据在数据库里的起始位置是 5
		this.startindex = (this.currentpage - 1) * this.pagesize;
		return startindex;
	}

	public void setStartindex(int startindex) {
		this.startindex = startindex;
	}

	public String getSearchcontent() {
		return searchcontent;
	}

	public void setSearchcontent(String searchcontent) {
		this.searchcontent = searchcontent;
	}

}
