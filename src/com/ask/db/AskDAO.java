package com.ask.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class AskDAO {
	//DB작업 및 처리 객체
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql=""; 

	//디비 연결 메서드
	private Connection getCon() throws Exception{
		Context init = new InitialContext();
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/binu");
		con = ds.getConnection();
		System.out.println("DB 접속 완료 : "+con);
		return con;
	}

	//디비 자원해제 메서드
	public void closeDB(){
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//insertAsk(DTO)
	public void insertAsk(AskDTO askdto){
		int ask_num = 0; //문의게시판 글번호 초기화
		try {
			con = getCon();

			sql = "SELECT MAX(ask_num) FROM ask";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if(rs.next()){
				ask_num = rs.getInt(1)+1;
			}
			System.out.println("ask_num = "+ask_num);

			//글작성SQL쿼리
			sql = "INSERT INTO ask(ask_num,ask_member_id,ask_item_num,ask_title,"
					+ "ask_content,ask_open,ask_category,ask_answer,ask_file,ask_date) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,now())";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ask_num);
			pstmt.setString(2, askdto.getAsk_member_id());
			pstmt.setInt(3, askdto.getAsk_item_num());
			pstmt.setString(4, askdto.getAsk_title());
			pstmt.setString(5, askdto.getAsk_content());
			pstmt.setInt(6, askdto.getAsk_open());
			pstmt.setString(7, askdto.getAsk_category());
			pstmt.setInt(8, askdto.getAsk_answer());
			pstmt.setString(9, askdto.getAsk_file());


			int value = pstmt.executeUpdate();
			System.out.println("게시판 글 저장 성공! "+value+"개"); //글이 몇개 들어갔는지 확인
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	//insertAsk(DTO)

	//getAskCount()
	public int getAskCount(int askItemNum){
		int count = 0;
		try {
			con = getCon();
					
			sql = "SELECT COUNT(*) FROM ask WHERE ask_item_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, askItemNum);

			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getInt(1); //1: sql 쿼리구문의 실행 결과
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return count;
	}
	//getAskCount()
	
	
	//getAskCount() admin
	public int getAskCount(){
		int count = 0;
		try {
			con = getCon();
			

			sql = "select count(*) from ask";
			pstmt = con.prepareStatement(sql);
	
	
			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getInt(1); //1: sql 쿼리구문의 실행 결과
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return count;
	}
	//getAskCount() admin
	
	
	//getAskList(askStartRow, askPageSize, askItemNum)
	public List<AskDTO> getAskList(int askStartRow, int askPageSize, int askItemNum){
		List<AskDTO> askList = new ArrayList<AskDTO>();

		try {

			con = getCon();

				sql = "SELECT * FROM ask WHERE ask_item_num=? ORDER BY ask_num DESC limit ?,?"; 
					
				pstmt = con.prepareStatement(sql);

				pstmt.setInt(1, askItemNum);
				pstmt.setInt(2, askStartRow-1); //시작행-1
				pstmt.setInt(3, askPageSize);
			
				//해당 아이템에 대한 문의글리스트만 가져오겠다
				rs = pstmt.executeQuery();

				while(rs.next()){
					AskDTO askdto = new AskDTO();
					askdto.setAsk_num(rs.getInt("ask_num"));
					askdto.setAsk_member_id(rs.getString("ask_member_id"));
					askdto.setAsk_item_num(askItemNum);
					askdto.setAsk_title(rs.getString("ask_title"));
					askdto.setAsk_content(rs.getString("ask_content"));
					askdto.setAsk_open(rs.getInt("ask_open"));
					askdto.setAsk_category(rs.getString("ask_category"));
					askdto.setAsk_answer(rs.getInt("ask_answer"));
					askdto.setAsk_date(rs.getDate("ask_date"));
					askdto.setAsk_file(rs.getString("ask_file"));

					askList.add(askdto);
				}
				System.out.println("글 목록 가져오기 성공! "+askList);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return askList;
	}
	//getAskList(startRow, pageSize, askItemNum)
	
	//getAskList(askStartRow, askPageSize) admin
	public Vector getAskList(int askStartRow, int askPageSize){
		Vector vec = new Vector();
		
		List askList = new ArrayList();
		List item_name = new ArrayList();
		
		try {

			con = getCon();

	
				sql = "SELECT a.*,i.item_name FROM ask as a left join item as i "
						+ "on a.ask_item_num=i.item_num "
						+ "ORDER BY ask_num DESC limit ?,?";
			
				pstmt = con.prepareStatement(sql);
	

				pstmt.setInt(1, askStartRow-1);
				pstmt.setInt(2, askPageSize);

				//해당 아이템에 대한 문의글리스트만 가져오겠다
				rs = pstmt.executeQuery();

				while(rs.next()){
					AskDTO askdto = new AskDTO();
					askdto.setAsk_num(rs.getInt("a.ask_num"));
					askdto.setAsk_member_id(rs.getString("a.ask_member_id"));
					askdto.setAsk_item_num(rs.getInt("a.ask_item_num"));
					askdto.setAsk_title(rs.getString("a.ask_title"));
					askdto.setAsk_content(rs.getString("a.ask_content"));
					askdto.setAsk_open(rs.getInt("a.ask_open"));
					askdto.setAsk_category(rs.getString("a.ask_category"));
					askdto.setAsk_answer(rs.getInt("a.ask_answer"));
					askdto.setAsk_date(rs.getDate("a.ask_date"));
					askdto.setAsk_file(rs.getString("a.ask_file"));
										
					askList.add(askdto);					
					
					
					item_name.add(rs.getString("i.item_name"));

				}
				
				vec.add(askList);
				vec.add(item_name);
				
				System.out.println("글 목록 가져오기 성공! "+askList);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return vec;
	}
	
	
	
	
	
	
	
	

	//closeAsk(DTO)
	public void closeAsk(AskDTO askdto){
		try {
			con = getCon();
			sql = "UPDATE ask SET ask_open=? WHERE ask_num=?";
			//관리자계정으로 접근
			//별도의 비밀번호 체크 없이 빠르게 작업

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, askdto.getAsk_open());
			pstmt.setInt(2, askdto.getAsk_num());
			pstmt.executeUpdate();
			//실행
			int ask_secret = pstmt.executeUpdate();
			System.out.println("문의글 비밀글로 변경완료 : " + ask_secret);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	//closeBoard(DTO)


	//deleteAsk(num)
	public void deleteAsk(int num){
		try {
			con = getCon();
			sql = "DELETE from ask where ask_num=?";
			//관리자계정으로 접근
			//별도의 비밀번호 체크 없이 빠르게 작업

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
			sql = "delete from comment where comment_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
			System.out.println("문의사항 삭제완료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	//deleteAsk(num)
	
	
	
	/********************************************/
	//Comment

	//InsertAskComment(DTO)
		public void InsertAskComment(CommentDTO codto, AskDTO askdto){
			
			try {

				con = getCon();

				//답변 작성
				sql = "INSERT INTO comment(comment_num,comment_content,comment_date) "
						+ "VALUES(?,?,now())";

				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, codto.getComment_num());
				pstmt.setString(2, codto.getComment_content());

				pstmt.executeUpdate();
				System.out.println("답변쓰기 완료!");

				//답변작성 여부 체크
				sql = "UPDATE ask SET ask_answer=? WHERE ask_num=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, askdto.getAsk_answer()+1);
				pstmt.setInt(2, codto.getComment_num());

				pstmt.executeUpdate();
				System.out.println("답변예정 --> 답변완료 수정 완료");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
		}
		//InsertAskComment(DTO)
	
		//getComment()
		public CommentDTO getComment(int ask_num){
			CommentDTO codto = new CommentDTO();
			
			try {
				con = getCon();

				sql ="SELECT * FROM comment WHERE comment_num=?";

				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, ask_num);

				rs = pstmt.executeQuery();

				if(rs.next()){
					codto = new CommentDTO();
					codto.setComment_content(rs.getString("comment_content"));
					codto.setComment_date(rs.getDate("comment_date"));
					codto.setComment_num(rs.getInt("comment_num"));
				}

				System.out.println("답변 글 가져오기 : "+codto);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			return codto;
		}
		//getComment()

	//getComment()
	public List getComment(){
		List coList = new ArrayList();
		CommentDTO codto = new CommentDTO();
		
		try {
			con = getCon();

			sql ="SELECT * FROM comment";

			pstmt = con.prepareStatement(sql);


			rs = pstmt.executeQuery();

			while(rs.next()){
				codto = new CommentDTO();
				codto.setComment_content(rs.getString("comment_content"));
				codto.setComment_date(rs.getDate("comment_date"));
				codto.setComment_num(rs.getInt("comment_num"));
				
				coList.add(codto);
			}

			System.out.println("답변 글 가져오기 : "+coList.size());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return coList;
	}
	//getComment()
	

	// updateComment(codto)
	public void updateComment(CommentDTO codto){
		
		try {
			con = getCon();

			sql = "UPDATE comment SET comment_content=? WHERE comment_num =?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, codto.getComment_content());
			pstmt.setInt(2, codto.getComment_num());

			pstmt.executeUpdate();
			System.out.println("답변수정 : ");
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	// updateComment(codto)



	//deleteComment(num)
	public void deleteComment(int ask_num){
		try {
			con = getCon();
			sql = "delete from comment where comment_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ask_num);
			pstmt.executeUpdate();
			System.out.println(ask_num+"답변 삭제");
			
			//답변작성 여부 체크
			sql = "UPDATE ask SET ask_answer=? WHERE ask_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, 0);
			pstmt.setInt(2, ask_num);

			pstmt.executeUpdate();
			System.out.println("답변완료 --> 답변예정 수정 완료");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
	}
	//deleteComment(num)

}
