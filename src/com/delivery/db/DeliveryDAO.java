package com.delivery.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class DeliveryDAO {

	// 데이터 처리객체 (DB-mysql과 직접연결)
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

		System.out.println("디비 접속 완료 :" + con);

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
	
	//insertAdressBook(ddto); 주소록 저장
	public void insertAdressBook(DeliveryDTO ddto){
		
		try {
			con = getCon();
			
			sql = "insert into delivery "
					+ "(delivery_member_id,delivery_name,delivery_place_name, "
					+ "delivery_phone,delivery_postcode,delivery_addr1,delivery_addr2) "
					+ "values(?,?,?,?,?,?,?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, ddto.getDelivery_member_id());
			pstmt.setString(2, ddto.getDelivery_name());
			pstmt.setString(3, ddto.getDelivery_place_name());
			pstmt.setString(4, ddto.getDelivery_phone());
			pstmt.setString(5, ddto.getDelivery_postcode());
			pstmt.setString(6, ddto.getDelivery_addr1());
			pstmt.setString(7, ddto.getDelivery_addr2());
			
			pstmt.executeUpdate();
			
			System.out.println("주소록 저장 완료");
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 자원해제
			closeDB();
		}

	}
	
	//insertAdressBook(ddto); 주소록 저장

	// getAddrCount()
	
	public int getAddrCount(){

		int count = 0;
		
		try {
			con = getCon();
			
			sql="select count(*) from delivery";
			
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
	// getAddrCount()
	
	
	// deliveryList() 주소록 목록 리스트
	public List<DeliveryDTO> getDeliveryList(String member_id){
		List<DeliveryDTO> deliveryList = new ArrayList<DeliveryDTO>();
		
		try {
			con = getCon();
			
			sql="select * from delivery where delivery_member_id=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				// 회원 1명의 정보를 저장 -> ArrayList 한칸
				DeliveryDTO ddto = new DeliveryDTO();
				
				ddto.setDelivery_place_name(rs.getString("delivery_place_name"));
				ddto.setDelivery_name(rs.getString("delivery_name"));
				ddto.setDelivery_phone(rs.getString("delivery_phone"));
				ddto.setDelivery_postcode(rs.getString("delivery_postcode"));
				ddto.setDelivery_addr1(rs.getString("delivery_addr1"));
				ddto.setDelivery_addr2(rs.getString("delivery_addr2"));
				ddto.setDelivery_index(rs.getInt("delivery_index"));
				
				// arrayList 한칸에 정보 저장
				deliveryList.add(ddto);		
				
			}
			
			System.out.println("주소록 목록 저장 완료 ");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return deliveryList;
	}
	// gdeliveryList()
	
	
	//DeliveryDelete(delete_addr) 주소록 삭제
	public int DeliveryDelete(int delete_addr){
		
		int result = 0;
		
		try {
			con = getCon();
			
			
			sql = "delete from delivery where delivery_index=?";
			
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, delete_addr);
			pstmt.executeUpdate();
			
			System.out.println("주소록 삭제 완료");
		} catch (Exception e) {
	
			e.printStackTrace();
		}finally{
			closeDB();
		}
		
		
		return result;
	}
	
	//DeliveryDelete(delete_addr)
	
	
	//GetDelivery(index)
	public DeliveryDTO GetDelivey(int index){
		
		DeliveryDTO ddto = new DeliveryDTO();
		
		try {
			con = getCon();
			sql = "select * from delivery where delivery_index=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, index);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				
				ddto.setDelivery_place_name(rs.getString("delivery_place_name"));
				ddto.setDelivery_name(rs.getString("delivery_name"));
				ddto.setDelivery_phone(rs.getString("delivery_phone"));
				ddto.setDelivery_postcode(rs.getString("delivery_postcode"));
				ddto.setDelivery_addr1(rs.getString("delivery_addr1"));
				ddto.setDelivery_addr2(rs.getString("delivery_addr2"));
				ddto.setDelivery_index(rs.getInt("delivery_index"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return ddto;
	}
	//GetDelivery(index)
	
	
	//updateDelivery(DeliveryDTO ddto)
	public void updateDelivery(DeliveryDTO ddto){
		try {
			con = getCon();
			sql = "update delivery set delivery_name=?, delivery_place_name=?,"
					+ "delivery_phone=?, delivery_postcode=?, delivery_addr1=?,"
					+ "delivery_addr2=? where delivery_index=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ddto.getDelivery_name());
			pstmt.setString(2, ddto.getDelivery_place_name());
			pstmt.setString(3, ddto.getDelivery_phone());
			pstmt.setString(4, ddto.getDelivery_postcode());
			pstmt.setString(5, ddto.getDelivery_addr1());
			pstmt.setString(6, ddto.getDelivery_addr2());
			pstmt.setInt(7, ddto.getDelivery_index());
			pstmt.executeUpdate();
			
			System.out.println("주소록 수정 완료");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	//updateDelivery(DeliveryDTO ddto)
	
}
