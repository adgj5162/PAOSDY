package com.goods.basket.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BasketDAO {
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
	
	public void insertBasket(BasketDTO bdto){
		
		try {
			con = getCon();
			
			sql = "insert into basket(basket_member_id,basket_item_num,"
					+ "basket_amount,basket_price,basket_size,basket_color,"
					+ "basket_date,basket_thumbnail,basket_item_name) "
					+ "values(?,?,?,?,?,?,now(),?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bdto.getBasket_member_id());
			pstmt.setInt(2, bdto.getBasket_item_num());
			pstmt.setInt(3, bdto.getBasket_amount());
			pstmt.setInt(4, bdto.getBasket_price());
			pstmt.setString(5, bdto.getBasket_size());
			pstmt.setString(6, bdto.getBasket_color());
			pstmt.setString(7, bdto.getBasket_thumbnail());
			pstmt.setString(8, bdto.getBasket_item_name());
			
			pstmt.executeUpdate();
			
			System.out.println("장바구니 등록 완료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	
	//getbasketCount();
		public int getbasketCount(String basket_member_id){
			int count = 0;
			
			try {
				con = getCon();
				
				sql = "select count(*) from basket where basket_member_id = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, basket_member_id);
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					count = rs.getInt(1);
				}
				
				System.out.println("장바구니 상품개수 : "+count);
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				closeDB();
			}
			return count;
		}
		//getbasketCount();
		
		//getBasketList();
		public List<BasketDTO> getBasketList(String basket_member_id){
			List<BasketDTO> basketList = new ArrayList<BasketDTO>();
			
			try {
				con = getCon();
				
				sql = "select * from basket where basket_member_id = ? order by basket_num desc";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, basket_member_id);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					BasketDTO basdto = new BasketDTO();
					
					basdto.setBasket_num(rs.getInt("basket_num"));
					basdto.setBasket_member_id(rs.getString("basket_member_id"));
					basdto.setBasket_item_num(rs.getInt("basket_item_num"));
					basdto.setBasket_amount(rs.getInt("basket_amount"));
					basdto.setBasket_price(rs.getInt("basket_price"));
					basdto.setBasket_size(rs.getString("basket_size"));
					basdto.setBasket_color(rs.getString("basket_color"));
					basdto.setBasket_date(rs.getDate("basket_date"));
					basdto.setBasket_thumbnail(rs.getString("basket_thumbnail"));
					basdto.setBasket_item_name(rs.getString("basket_item_name"));
					
					basketList.add(basdto);
				}

				System.out.println("장바구니 출력 완료!");
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				closeDB();
			}
			return basketList;
		}
		//getBasketList();
		
		//deleteBasket(basket_num);
		public void deleteBasket(int basket_num){
			
			try {
				con = getCon();
				
				sql = "delete from basket where basket_num = ?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, basket_num);
				pstmt.executeUpdate();
				
				System.out.println("장바구니 상품 삭제 완료!");
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				closeDB();
			}
		}
		//deleteBasket(basket_num);
		
		//deleteCheckedBasket(checkArray)
		public void deleteCheckedBasket(String checkArray){
			try {
				con = getCon();
				System.out.println("선택한 상품의 번호 : "+checkArray);
				
				sql = "delete from basket where basket_num in (?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, checkArray);
				pstmt.executeUpdate();
				
				System.out.println("선택한 상품 삭제 완료!");
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				closeDB();
			}
			
		}
		//deleteCheckedBasket(checkArray)
		
		//updateBasket(basket_num);
		public void updateBasket(int basket_num, int basket_amount){
			
			try {
				con = getCon();
				System.out.println(basket_amount);
				
				sql = "update basket set basket_amount = ? where basket_num = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, basket_amount);
				pstmt.setInt(2, basket_num);
				pstmt.executeUpdate();
				
				System.out.println(basket_num+"번째 상품의 변경된 수량 : "+basket_amount);
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				closeDB();
			}
			
		}
		//updateBasket(basket_num);
		
		//deleteBasketAll(id)
		public void deleteBasketAll(String id){
			
			try {
				con = getCon();
				
				sql = "delete from basket where basket_member_id = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.executeUpdate();
				
				System.out.println(id+"님의 장바구니 모든상품 삭제완료!");
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				closeDB();
			}
			
		}
		//deleteBasketAll(id)
		
}
