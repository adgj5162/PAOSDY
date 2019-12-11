package com.goodlist.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class GoodListDAO {
	// 디비연결
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
	// 디비자원해제
	
	public void closeDB(){
		try {
			if(rs != null){ rs.close(); }
			if(pstmt != null){ pstmt.close(); }
			if(con != null){ con.close(); }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// insertWishList
	public void insertWishList(int page_num, String id, String item_name, String item_thumbnail){
		try {
			con = getCon();
			sql = "insert into goodlist(goodlist_item_num,goodlist_member_id,"
					+ "goodlist_date,goodlist_item_name,goodlist_item_thumbnail) values(?,?,now(),?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, page_num);
			pstmt.setString(2, id);
			pstmt.setString(3, item_name);
			pstmt.setString(4, item_thumbnail);
			
			pstmt.executeUpdate();
			
			System.out.println("위시리스트 추가완료");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			closeDB();
		}
	}
	// insertWishList
	
	
	// checkWishList
	public int checkWishList(int page_num, String id){
		int num = 1;
		try {
			con = getCon();
			sql = "select * from goodlist where goodlist_member_id=? and goodlist_item_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, page_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				num = 2;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			closeDB();
		}
		return num;
	}
	// checkWishList
	
	
	// deleteWishList
	public void deleteWishList(int page_num, String id){
		try {
			con = getCon();
			sql = "delete from goodlist where goodlist_member_id=? and goodlist_item_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, page_num);
			
			pstmt.executeUpdate();
			System.out.println("위시리스트 삭제완료");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	// deleteWishList
	
	
	// gdao.getGoodList(id);
	public List getGoodList(String id){
		List goodList = new ArrayList();
		
		try {
			con = getCon();
			sql = "select * from goodlist where goodlist_member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				GoodListDTO gdto = new GoodListDTO();
				gdto.setGoodlist_item_num(rs.getInt("goodlist_item_num"));
				gdto.setGoodlist_date(rs.getDate("goodlist_date"));
				gdto.setGoodlist_item_name(rs.getString("goodlist_item_name"));
				gdto.setGoodlist_item_thumbnail(rs.getString("goodlist_item_thumbnail"));
				gdto.setGoodlist_num(rs.getInt("goodlist_num"));
				
				
				goodList.add(gdto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return goodList;
	}
	// gdao.getGoodList(id);
	
	
	// gdao.deleteGoodList(goodlist_num);
	public void deleteGoodList(int goodlist_num){
		try {
			con = getCon();
			sql = "delete from goodlist where goodlist_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, goodlist_num);
			pstmt.executeUpdate();
			
			System.out.println("찜목록 삭제 완료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	// gdao.deleteGoodList(goodlist_num);
	
	
}
