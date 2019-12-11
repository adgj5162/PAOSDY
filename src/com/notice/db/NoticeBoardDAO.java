package com.notice.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class NoticeBoardDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	// DB 연결
	private Connection getCon() throws Exception {

		// Context 객체 생성
		Context init = new InitialContext();
		// DB연동 이름으로 DB 호출-> DataSource저장
		DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/binu");
		// 연결정보를 가져와서 리턴
		con = ds.getConnection();

		System.out.println("디비 접속 완료 : " + con);

		return con;
	}

	// DB 자원해제
	public void closeDB() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void writeNotice(NoticeBoardDTO mbdto){
		
		try {
			con = getCon();
			sql = "insert into noticeboard(noticeboard_title, noticeboard_content, noticeboard_readcount) values(?,?,0)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mbdto.getNoticeboard_title());
			pstmt.setString(2, mbdto.getNoticeboard_content());
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		
	}
	
	public int getNoticeCount(){

		int count = 0;
		
		try {
			con = getCon();
			
			sql="select count(*) from noticeboard";
			
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
	
	public List<NoticeBoardDTO> getNoticeList(int startRow, int pageSize){
		List<NoticeBoardDTO> noticeList = new ArrayList<NoticeBoardDTO>();
		
		try {
			con = getCon();
			
			sql="select * from noticeboard order by noticeboard_num desc limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				NoticeBoardDTO nbdto = new NoticeBoardDTO();
				nbdto.setNoticeboard_content(rs.getString("noticeboard_content"));
				nbdto.setNoticeboard_date(rs.getTimestamp("noticeboard_date"));
				nbdto.setNoticeboard_num(rs.getInt("noticeboard_num"));
				nbdto.setNoticeboard_readcount(rs.getInt("noticeboard_readcount"));
				nbdto.setNoticeboard_title(rs.getString("noticeboard_title"));
				
				
				// arrayList 한칸에 정보 저장
				noticeList.add(nbdto);		
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return noticeList;
	}
	
	public void updateReadcount(int num){
		try {
			con = getCon();
			sql="update noticeboard set noticeboard_readcount=noticeboard_readcount+1 where noticeboard_num=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			int value = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
	}
	public NoticeBoardDTO getNoticeContent(int noticeboard_num){
		NoticeBoardDTO nbdto = null;
		try {
			con = getCon();
			sql = "select * from noticeboard where noticeboard_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, noticeboard_num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				nbdto = new NoticeBoardDTO();
				nbdto.setNoticeboard_content(rs.getString("noticeboard_content"));
				nbdto.setNoticeboard_date(rs.getTimestamp("noticeboard_date"));
				nbdto.setNoticeboard_num(rs.getInt("noticeboard_num"));
				nbdto.setNoticeboard_readcount(rs.getInt("noticeboard_readcount"));
				nbdto.setNoticeboard_title(rs.getString("noticeboard_title"));
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		
		return nbdto;
	}
	public void deleteNotice(int noticeboard_num){
		
		try {
			con = getCon();
			sql = "delete from noticeboard where noticeboard_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, noticeboard_num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeDB();
		}
	}
	
	public void noticeBoardUpdate(NoticeBoardDTO nbdto){
		try {
			con = getCon();
			sql = "update noticeboard set noticeboard_title=? ,noticeboard_content=?"
					+ " where noticeboard_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nbdto.getNoticeboard_title());
			pstmt.setString(2, nbdto.getNoticeboard_content());
			pstmt.setInt(3, nbdto.getNoticeboard_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
