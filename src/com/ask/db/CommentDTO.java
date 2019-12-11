package com.ask.db;

import java.sql.Date;

public class CommentDTO {
	private int comment_num;
	private String comment_category;
	private String comment_member_id;
	private String comment_content;
	private int comment_reported;
	private String comment_report_content;
	private int comment_board_num;
	private Date comment_date;
	
	public int getComment_num() {
		return comment_num;
	}
	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}
	public String getComment_category() {
		return comment_category;
	}
	public void setComment_category(String comment_category) {
		this.comment_category = comment_category;
	}
	public String getComment_member_id() {
		return comment_member_id;
	}
	public void setComment_member_id(String comment_member_id) {
		this.comment_member_id = comment_member_id;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public int getComment_reported() {
		return comment_reported;
	}
	public void setComment_reported(int comment_reported) {
		this.comment_reported = comment_reported;
	}
	public String getComment_report_content() {
		return comment_report_content;
	}
	public void setComment_report_content(String comment_report_content) {
		this.comment_report_content = comment_report_content;
	}
	public int getComment_board_num() {
		return comment_board_num;
	}
	public void setComment_board_num(int comment_board_num) {
		this.comment_board_num = comment_board_num;
	}
	public Date getComment_date() {
		return comment_date;
	}
	public void setComment_date(Date comment_date) {
		this.comment_date = comment_date;
	}
	@Override
	public String toString() {
		return "CommentDTO [comment_num=" + comment_num + ", comment_category=" + comment_category
				+ ", comment_member_id=" + comment_member_id + ", comment_content=" + comment_content
				+ ", comment_reported=" + comment_reported + ", comment_report_content=" + comment_report_content
				+ ", comment_board_num=" + comment_board_num + ", comment_date=" + comment_date + "]";
	}
	
	
}
