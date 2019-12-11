package com.goods.review.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ReviewDAO {

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	// 디비 연결
	private Connection getCon() throws Exception {

		Context init = new InitialContext();
		DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/binu");
		con = ds.getConnection();
		return con;
	}

	// 디비 자원 해제
	public void closeDB() {
		try {
			if (rs != null)
				rs.close();
			if (con != null)
				con.close();
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//insertReview(rdto);
	public void insertReview(ReviewDTO rdto){
		int review_num = 0;
		
		try {
			con = getCon();
			System.out.println("디비 연결 성공 : "+con);
			
			sql = "select max(review_num) from review";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				review_num = rs.getInt(1)+1;
			}
			System.out.println("review_num : "+review_num);
			
			sql = "insert into "
					+ "review(review_num,review_member_id,review_image,review_title,"
					+ "review_content,review_date,review_starpoint,review_item_num) "
					+ "values(?,?,?,?,?,now(),?,?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, review_num);
			pstmt.setString(2, rdto.getReview_member_id());
			pstmt.setString(3, rdto.getReview_image());
			pstmt.setString(4, rdto.getReview_title());
			pstmt.setString(5, rdto.getReview_content());
			pstmt.setInt(6, rdto.getReview_starpoint());
			pstmt.setInt(7, rdto.getReview_item_num());
			
			pstmt.executeUpdate();
			
			System.out.println("리뷰작성 완료!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
	}
	//insertReview(rdto);
	
	//getReviewCount();
	public int getReviewCount(int item_num){
		int count = 0;
		
		try {
			con = getCon();
			if(item_num > 0){
			sql = "select count(*) from review where review_item_num=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, item_num);
			}else{
				sql= "select count(*) from review";
				pstmt = con.prepareStatement(sql);
			}
			
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}else{
				count = 0;
			}
			
			System.out.println("count글의 개수 : "+count);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		
		return count;
	}
	//getReviewCount();
	
	//getReviewList(startRow, pageSize, int item_num);
	public List<ReviewDTO> getReviewList(int startRow, int pageSize, int item_num){
		List<ReviewDTO> reviewList = new ArrayList<ReviewDTO>();
		
		try {
			con = getCon();
			
			if(item_num == 0){
			sql = "select * from review order by review_num desc limit ?,?";
			}else{
				sql = "select * from review where review_item_num=? order by review_num desc limit ?,?";	
			}
			pstmt = con.prepareStatement(sql);
			if(item_num == 0){
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, pageSize);
			}else{
				pstmt.setInt(1, item_num);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, pageSize);
			}
			rs = pstmt.executeQuery(); 
			
			while(rs.next()){
				ReviewDTO rdto = new ReviewDTO();
				
				rdto.setReview_num(rs.getInt("review_num"));
				rdto.setReview_member_id(rs.getString("review_member_id"));
				rdto.setReview_image(rs.getString("review_image"));
				rdto.setReview_title(rs.getString("review_title"));
				rdto.setReview_content(rs.getString("review_content"));
				rdto.setReview_date(rs.getDate("review_date"));
				rdto.setReview_starpoint(rs.getInt("review_starpoint"));
				rdto.setReview_item_num(rs.getInt("review_item_num"));
				
				reviewList.add(rdto);
			}
			
			System.out.println("후기 출력 완료!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		return reviewList;
	}
	//getReviewList(startRow, pageSize, int item_num);
	
	//getReviewList(startRow, pageSize);
	public Vector getReviewList(int startRow, int pageSize){
		
		Vector vec = new Vector();
		
		List<ReviewDTO> reviewList = new ArrayList<ReviewDTO>();
		List item_name = new ArrayList();
		
		try {
			con = getCon();
			

			sql = "select r.*,i.item_name from review as r join item as i "
					+ "on r.review_item_num=i.item_num "
					+ "order by review_num desc limit ?,?";
			
			pstmt = con.prepareStatement(sql);

				pstmt.setInt(1, startRow-1);
				pstmt.setInt(2, pageSize);
			
			rs = pstmt.executeQuery(); 
			
			while(rs.next()){
				ReviewDTO rdto = new ReviewDTO();
				
				rdto.setReview_num(rs.getInt("r.review_num"));
				rdto.setReview_member_id(rs.getString("r.review_member_id"));
				rdto.setReview_image(rs.getString("r.review_image"));
				rdto.setReview_title(rs.getString("r.review_title"));
				rdto.setReview_content(rs.getString("r.review_content"));
				rdto.setReview_date(rs.getDate("r.review_date"));
				rdto.setReview_starpoint(rs.getInt("r.review_starpoint"));
				rdto.setReview_item_num(rs.getInt("r.review_item_num"));
				
				reviewList.add(rdto);
				
				item_name.add(rs.getString("i.item_name"));
				
				
			}
			
			vec.add(reviewList);
			vec.add(item_name);
			
			System.out.println("후기 출력 완료!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		return vec;
	}
	//getReviewList(startRow, pageSize);
	
	
	//getReview(review_num)
	public ReviewDTO getReview(int review_num){
		ReviewDTO rdto = null;
		
		try {
			con = getCon();
			
			sql = "select * from review where review_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, review_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				rdto = new ReviewDTO();
				
				rdto.setReview_num(rs.getInt("review_num"));
				rdto.setReview_member_id(rs.getString("review_member_id"));
				rdto.setReview_image(rs.getString("review_image"));
				rdto.setReview_title(rs.getString("review_title"));
				rdto.setReview_content(rs.getString("review_content"));
				rdto.setReview_date(rs.getDate("review_date"));
				rdto.setReview_starpoint(rs.getInt("review_starpoint"));
				rdto.setReview_item_num(rs.getInt("review_item_num"));
			}
			System.out.println("글정보 저장완료!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		return rdto;
	}
	//getReview(review_num)
	
	//deleteReview(review_num,member_pass);
	public void deleteReview(int review_num){
		
		try {
			con = getCon();
			System.out.println("DB 연결");
			
			sql = "delete from review "
					+ "where review_num = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, review_num);
			
			pstmt.executeUpdate();
			
			System.out.println("후기글 삭제 완료!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		
	}
	//deleteReview(review_num,member_pass);
	
	//updateReview(rdto);
	public int updateReview(ReviewDTO rdto){
		int check = 0;
		
		try {
			con = getCon();
			
			sql = "select * from review where review_num = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, rdto.getReview_num());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				
				sql = "update review set "
						+ "review_starpoint=?, review_title=?, review_content=?, review_image=? "
						+ "where review_num = ?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, rdto.getReview_starpoint());
				pstmt.setString(2, rdto.getReview_title());
				pstmt.setString(3, rdto.getReview_content());
				pstmt.setString(4, rdto.getReview_image());
				pstmt.setInt(5, rdto.getReview_num());
				
				check = pstmt.executeUpdate();
				
			}else{
				check = -1;
			}
			
			System.out.println("후기 수정 완료!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		return check;
	}
	//updateReview(rdto);
	
	
	
	
	
	
	
	
	
	
}
