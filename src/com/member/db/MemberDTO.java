package com.member.db;

import java.sql.Date;

import com.member.action.Shautills;

public class MemberDTO {
   
   
   private int member_num;
   private String member_id;
   private String memeber_password;
   private String member_name;
   private String member_gender;
   private String member_email;
   private String member_postcode;
   private String member_addr1;
   private String member_addr2;
   private String member_phone;
   private int member_totalprice;
   private Date member_regdate;
   private int member_point;
   private int member_coupon_num;
   private String member_birth;
   private String new_member_password;
   
   
   
   public int getMember_num() {
      return member_num;
   }
   public void setMember_num(int member_num) {
      this.member_num = member_num;
   }
   public String getMember_id() {
      return member_id;
   }
   public void setMember_id(String member_id) {
      this.member_id = member_id;
   }
   public String getMemeber_password() {
      return memeber_password;
   }
   public void setMemeber_password(String memeber_password) {
      Shautills shautills = new Shautills();
      this.memeber_password = shautills.encryptSha(memeber_password);
   }
   public String getNew_member_password() {
      return new_member_password;
   }
   public void setNew_member_password(String new_member_password) {
      Shautills shautills = new Shautills();
      this.new_member_password = shautills.encryptSha(new_member_password);
   }
   public String getMember_name() {
      return member_name;
   }
   public void setMember_name(String member_name) {
      this.member_name = member_name;
   }
   public String getMember_gender() {
      return member_gender;
   }
   public void setMember_gender(String member_gender) {
      this.member_gender = member_gender;
   }
   public String getMember_email() {
      return member_email;
   }
   public void setMember_email(String member_email) {
      this.member_email = member_email;
   }
   public String getMember_postcode() {
      return member_postcode;
   }
   public void setMember_postcode(String member_postcode) {
      this.member_postcode = member_postcode;
   }
   public String getMember_addr1() {
      return member_addr1;
   }
   public void setMember_addr1(String member_addr1) {
      this.member_addr1 = member_addr1;
   }
   public String getMember_addr2() {
      return member_addr2;
   }
   public void setMember_addr2(String member_addr2) {
      this.member_addr2 = member_addr2;
   }
   public String getMember_phone() {
      return member_phone;
   }
   public void setMember_phone(String member_phone) {
      this.member_phone = member_phone;
   }
   public int getMember_totalprice() {
      return member_totalprice;
   }
   public void setMember_totalprice(int member_totalprice) {
      this.member_totalprice = member_totalprice;
   }
   public Date getMember_regdate() {
      return member_regdate;
   }
   public void setMember_regdate(Date member_regdate) {
      this.member_regdate = member_regdate;
   }
   public int getMember_point() {
      return member_point;
   }
   public void setMember_point(int member_point) {
      this.member_point = member_point;
   }
   public int getMember_coupon_num() {
      return member_coupon_num;
   }
   public void setMember_coupon_num(int member_coupon_num) {
      this.member_coupon_num = member_coupon_num;
   }
   public String getMember_birth() {
      return member_birth;
   }
   public void setMember_birth(String member_birth) {
      this.member_birth = member_birth;
   }
   
   
   
}