package com.item.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ItemDAO {
	// ?��비연�?
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	String sql="";
	
	private Connection getCon() throws Exception{
		Context init = new InitialContext();
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/binu");
		con = ds.getConnection();
		
		return con;
	}
	// ?��비자?��?��?��
	
	public void closeDB(){
		try {
			if(rs != null){ rs.close(); }
			if(pstmt != null){ pstmt.close(); }
			if(con != null){ con.close(); }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// insertItem(idto)
	public void insertItem(ItemDTO idto){
		try {
			con = getCon();
			sql = "insert into item(item_name,item_price,item_category,item_amount"
					+ ",item_color,item_size,item_saleprice,item_thumbnail,item_contentimage,item_sold)"
					+ " values(?,?,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, idto.getItem_name());
			pstmt.setInt(2, idto.getItem_price());
			pstmt.setString(3, idto.getItem_category());
			pstmt.setInt(4, idto.getItem_amount());
			pstmt.setString(5, idto.getItem_color());
			pstmt.setString(6, idto.getItem_size());
			pstmt.setInt(7, idto.getItem_saleprice());
			pstmt.setString(8, idto.getItem_thumbnail());
			pstmt.setString(9, idto.getItem_contentimage());
			pstmt.setInt(10, 0);
			
			pstmt.executeUpdate();
			
			System.out.println("?��?��?�� ?���? ?���?");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			closeDB();
		}
	}
	// insertItem(idto)
	///////////////////
	
	// getItemCount()
	public int getItemCount(){
		int count = 0;
		
		try {
			con = getCon();
			sql = "select count(item_num) from item";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return count;
	}
	// getItemCount()
	/////////////////
	
	// getItemList()
	public List getItemList(int startRow, int pageSize, int order_type){
		List itemList = new ArrayList();
		
		try {
			con = getCon();
			if(order_type==1){
				sql = "select * from item order by item_num desc limit ?,?";
			}else if(order_type==2){
				sql = "select * from item order by item_sold desc limit ?,?";
			}else if(order_type==3){
				sql = "select * from item order by item_price asc limit ?,?";
			}else if(order_type==4){
				sql = "select * from item order by item_price desc limit ?,?";
			}
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				ItemDTO idto = new ItemDTO();
				idto.setItem_num(rs.getInt("item_num"));
				idto.setItem_name(rs.getString("item_name"));
				idto.setItem_amount(rs.getInt("item_amount"));
				idto.setItem_price(rs.getInt("item_price"));
				idto.setItem_color(rs.getString("item_color"));
				idto.setItem_size(rs.getString("item_size"));
				idto.setItem_saleprice(rs.getInt("item_saleprice"));
				idto.setItem_category(rs.getString("item_category"));
				idto.setItem_thumbnail(rs.getString("item_thumbnail"));
				idto.setItem_contentimage(rs.getString("item_contentimage"));
				idto.setItem_sold(rs.getInt("item_sold"));
				itemList.add(idto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			closeDB();
		}
		
		return itemList;
	}
	// getItemList()
	
	
	// deleteItem()
	public void deleteItem(int item_num){
		
		try {
			con = getCon();
			sql = "delete from item where item_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, item_num);
			
			pstmt.executeUpdate();
			
			System.out.println("?��?��?�� ?��?�� ?���?");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
	}
	// deleteItem()
	
	
	// getItem()
	public ItemDTO getItem(int item_num){
		ItemDTO idto = new ItemDTO();
		
		try {
			con = getCon();
			sql = "select * from item where item_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, item_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				
				idto.setItem_num(rs.getInt("item_num"));
				idto.setItem_name(rs.getString("item_name"));
				idto.setItem_amount(rs.getInt("item_amount"));
				idto.setItem_price(rs.getInt("item_price"));
				idto.setItem_color(rs.getString("item_color"));
				idto.setItem_size(rs.getString("item_size"));
				idto.setItem_saleprice(rs.getInt("item_saleprice"));
				idto.setItem_category(rs.getString("item_category"));
				idto.setItem_thumbnail(rs.getString("item_thumbnail"));
				idto.setItem_contentimage(rs.getString("item_contentimage"));
				idto.setItem_sold(rs.getInt("item_sold"));
			}
			
			System.out.println("?��?��?�� ?���? �??��?���?");
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return idto;
	}
	// getItem()
	
	
	
	// updateItem()
	public void updateItem(ItemDTO idto){
		
		try {
			con = getCon();
			sql = "update item set item_name=?,item_price=?"
					+ ",item_category=?,item_amount=?,item_color=?"
					+ ",item_size=?,item_saleprice=?"
					+ ",item_thumbnail=?,item_contentimage=? where item_num=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, idto.getItem_name());
			pstmt.setInt(2, idto.getItem_price());
			pstmt.setString(3, idto.getItem_category());
			pstmt.setInt(4, idto.getItem_amount());
			pstmt.setString(5, idto.getItem_color());
			pstmt.setString(6, idto.getItem_size());
			pstmt.setInt(7, idto.getItem_saleprice());
			pstmt.setString(8, idto.getItem_thumbnail());
			pstmt.setString(9, idto.getItem_contentimage());
			pstmt.setInt(10, idto.getItem_num());
			
			pstmt.executeUpdate();
			
			
			
			System.out.println("?��?��?�� ?���? ?��?��?���?");
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	// updateItem()
	
	
/*	// getItemName()
	public String getItemName(int item_num){
			String item_name = null;
		try {
			con = getCon();
			sql ="select item_name from item where item_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, item_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				item_name = rs.getString(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			closeDB();
		}
		
		return item_name;
	}
	// getItemName()
*/
	
	// getItemList()
	public List getItemList(){
		List itemList = new ArrayList();
		
		try {
			con = getCon();
			sql = "select * from item";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
			while(rs.next()){
				ItemDTO idto = new ItemDTO();
				idto.setItem_num(rs.getInt("item_num"));
				idto.setItem_name(rs.getString("item_name"));
				idto.setItem_amount(rs.getInt("item_amount"));
				idto.setItem_price(rs.getInt("item_price"));
				idto.setItem_color(rs.getString("item_color"));
				idto.setItem_size(rs.getString("item_size"));
				idto.setItem_saleprice(rs.getInt("item_saleprice"));
				idto.setItem_category(rs.getString("item_category"));
				idto.setItem_thumbnail(rs.getString("item_thumbnail"));
				idto.setItem_contentimage(rs.getString("item_contentimage"));
				idto.setItem_sold(rs.getInt("item_sold"));
				
				itemList.add(idto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			closeDB();
		}
		
		return itemList;
	}
	// getItemList()







}
