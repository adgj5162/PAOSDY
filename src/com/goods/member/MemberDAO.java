package com.goods.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	
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
	
	//updatePoint(review_member_id);
	public void updatePoint(String review_member_id,MemberDTO mdto){
		
		try {
			con = getCon();
			
			sql = "update binu.member1 set member_point=member_point+? where member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, mdto.getMember_point());
			pstmt.setString(2, review_member_id);
			pstmt.executeUpdate();
			
			System.out.println("포인트 적립 성공!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
	}
	//updatePoint(review_member_id);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
