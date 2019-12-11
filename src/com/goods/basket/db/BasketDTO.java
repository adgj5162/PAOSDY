package com.goods.basket.db;

import java.util.Date;

public class BasketDTO {
   private int basket_num;
   private String basket_member_id;
   private int basket_item_num;
   private int basket_amount;
   private int basket_price;
   private String basket_size;
   private String basket_color;
   private Date basket_date;
   private String basket_thumbnail;
   private String basket_item_name;
   
   public int getBasket_num() {
      return basket_num;
   }
   public void setBasket_num(int basket_num) {
      this.basket_num = basket_num;
   }
   public String getBasket_member_id() {
      return basket_member_id;
   }
   public void setBasket_member_id(String basket_member_id) {
      this.basket_member_id = basket_member_id;
   }
   public int getBasket_item_num() {
      return basket_item_num;
   }
   public void setBasket_item_num(int basket_item_num) {
      this.basket_item_num = basket_item_num;
   }
   public int getBasket_amount() {
      return basket_amount;
   }
   public void setBasket_amount(int basket_amount) {
      this.basket_amount = basket_amount;
   }
   public int getBasket_price() {
      return basket_price;
   }
   public void setBasket_price(int basket_price) {
      this.basket_price = basket_price;
   }
   public String getBasket_size() {
      return basket_size;
   }
   public void setBasket_size(String basket_size) {
      this.basket_size = basket_size;
   }
   public String getBasket_color() {
      return basket_color;
   }
   public void setBasket_color(String basket_color) {
      this.basket_color = basket_color;
   }
   public Date getBasket_date() {
      return basket_date;
   }
   public void setBasket_date(Date basket_date) {
      this.basket_date = basket_date;
   }
   public String getBasket_thumbnail() {
      return basket_thumbnail;
   }
   public void setBasket_thumbnail(String basket_thumbnail) {
      this.basket_thumbnail = basket_thumbnail;
   }
   public String getBasket_item_name() {
      return basket_item_name;
   }
   public void setBasket_item_name(String basket_item_name) {
      this.basket_item_name = basket_item_name;
   }
@Override
public String toString() {
	return "BasketDTO [basket_num=" + basket_num + ", basket_member_id=" + basket_member_id + ", basket_item_num="
			+ basket_item_num + ", basket_amount=" + basket_amount + ", basket_price=" + basket_price + ", basket_size="
			+ basket_size + ", basket_color=" + basket_color + ", basket_date=" + basket_date + ", basket_thumbnail="
			+ basket_thumbnail + ", basket_item_name=" + basket_item_name + "]";
}
   
   
}
