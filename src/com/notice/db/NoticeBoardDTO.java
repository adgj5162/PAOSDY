package com.notice.db;

import java.util.Date;

public class NoticeBoardDTO {
	
	private int noticeboard_num;
	private String noticeboard_title;
	private String noticeboard_content;
	private Date noticeboard_date;
	private int noticeboard_readcount;
	
	public int getNoticeboard_num() {
		return noticeboard_num;
	}
	public void setNoticeboard_num(int noticeboard_num) {
		this.noticeboard_num = noticeboard_num;
	}
	public Date getNoticeboard_date() {
		return noticeboard_date;
	}
	public void setNoticeboard_date(Date noticeboard_date) {
		this.noticeboard_date = noticeboard_date;
	}
	public int getNoticeboard_readcount() {
		return noticeboard_readcount;
	}
	public void setNoticeboard_readcount(int noticeboard_readcount) {
		this.noticeboard_readcount = noticeboard_readcount;
	}
	public String getNoticeboard_title() {
		return noticeboard_title;
	}
	public void setNoticeboard_title(String noticeboard_title) {
		this.noticeboard_title = noticeboard_title;
	}
	public String getNoticeboard_content() {
		return noticeboard_content;
	}
	public void setNoticeboard_content(String noticeboard_content) {
		this.noticeboard_content = noticeboard_content;
	}
	
}
