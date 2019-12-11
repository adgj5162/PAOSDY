package com.goods.review.db;

import java.util.Date;

public class ReviewDTO {
	private int review_num;
	private String review_member_id;
	private String review_image;
	private String review_title;
	private String review_content;
	private Date review_date;
	private int review_starpoint;
	private int review_item_num;
	
	public int getReview_num() {
		return review_num;
	}
	public void setReview_num(int review_num) {
		this.review_num = review_num;
	}
	public String getReview_member_id() {
		return review_member_id;
	}
	public void setReview_member_id(String review_member_id) {
		this.review_member_id = review_member_id;
	}
	public String getReview_image() {
		return review_image;
	}
	public void setReview_image(String review_image) {
		this.review_image = review_image;
	}
	public String getReview_title() {
		return review_title;
	}
	public void setReview_title(String review_title) {
		this.review_title = review_title;
	}
	public String getReview_content() {
		return review_content;
	}
	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}
	public Date getReview_date() {
		return review_date;
	}
	public void setReview_date(Date review_date) {
		this.review_date = review_date;
	}
	public int getReview_starpoint() {
		return review_starpoint;
	}
	public void setReview_starpoint(int review_starpoint) {
		this.review_starpoint = review_starpoint;
	}
	public int getReview_item_num() {
		return review_item_num;
	}
	public void setReview_item_num(int review_item_num) {
		this.review_item_num = review_item_num;
	}
	
}
