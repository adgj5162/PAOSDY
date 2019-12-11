package com.ask.db;

import java.sql.Date;

/**
 * ask_num 문의사항 게시물 번호
 * ask_member_id 회원ID
 * ask_item_num 문의사항 해당 제품번호
 * ask_title 문의사항 제목
 * ask_content 문의사항 본문내용
 * ask_open 문의사항 비밀글여부
 * ask_category 문의사항 구분
 * ask_answer 문의사항 답변여부
 * ask_file 문의사항 업로드파일
 */
public class AskDTO {
	private int ask_num ;
	private String ask_member_id;
	private int ask_item_num;
	private String ask_title;
	private String ask_content;
	private int ask_open;
	private String ask_category;
	private int ask_answer;
	private String ask_file;
	private Date ask_date;
	public int getAsk_num() {
		return ask_num;
	}
	public void setAsk_num(int ask_num) {
		this.ask_num = ask_num;
	}
	public String getAsk_member_id() {
		return ask_member_id;
	}
	public void setAsk_member_id(String ask_member_id) {
		this.ask_member_id = ask_member_id;
	}
	public int getAsk_item_num() {
		return ask_item_num;
	}
	public void setAsk_item_num(int ask_item_num) {
		this.ask_item_num = ask_item_num;
	}
	public String getAsk_title() {
		return ask_title;
	}
	public void setAsk_title(String ask_title) {
		this.ask_title = ask_title;
	}
	public String getAsk_content() {
		return ask_content;
	}
	public void setAsk_content(String ask_content) {
		this.ask_content = ask_content;
	}
	public int getAsk_open() {
		return ask_open;
	}
	public void setAsk_open(int ask_open) {
		this.ask_open = ask_open;
	}
	public String getAsk_category() {
		return ask_category;
	}
	public void setAsk_category(String ask_category) {
		this.ask_category = ask_category;
	}
	public int getAsk_answer() {
		return ask_answer;
	}
	public void setAsk_answer(int ask_answer) {
		this.ask_answer = ask_answer;
	}
	public String getAsk_file() {
		return ask_file;
	}
	public void setAsk_file(String ask_file) {
		this.ask_file = ask_file;
	}
	public Date getAsk_date() {
		return ask_date;
	}
	public void setAsk_date(Date ask_date) {
		this.ask_date = ask_date;
	}
}
