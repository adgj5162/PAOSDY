package com.goodlist.db;

import java.sql.Date;

public class GoodListDTO {
	private int goodlist_num;
	private int goodlist_item_num;
	private String goodlist_member_id;
	private Date goodlist_date;
	private String goodlist_item_name;
	private String goodlist_item_thumbnail;
	
	
	public String getGoodlist_item_thumbnail() {
		return goodlist_item_thumbnail;
	}
	public void setGoodlist_item_thumbnail(String goodlist_item_thumbnail) {
		this.goodlist_item_thumbnail = goodlist_item_thumbnail;
	}
	public String getGoodlist_item_name() {
		return goodlist_item_name;
	}
	public void setGoodlist_item_name(String goodlist_item_name) {
		this.goodlist_item_name = goodlist_item_name;
	}
	public int getGoodlist_num() {
		return goodlist_num;
	}
	public void setGoodlist_num(int goodlist_num) {
		this.goodlist_num = goodlist_num;
	}
	public int getGoodlist_item_num() {
		return goodlist_item_num;
	}
	public void setGoodlist_item_num(int goodlist_item_num) {
		this.goodlist_item_num = goodlist_item_num;
	}
	public String getGoodlist_member_id() {
		return goodlist_member_id;
	}
	public void setGoodlist_member_id(String goodlist_member_id) {
		this.goodlist_member_id = goodlist_member_id;
	}
	public Date getGoodlist_date() {
		return goodlist_date;
	}
	public void setGoodlist_date(Date goodlist_date) {
		this.goodlist_date = goodlist_date;
	}
	
	
}
