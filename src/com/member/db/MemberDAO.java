package com.member.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.member.action.Shautills;


public class MemberDAO {

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

	 // mdao.insertMember(mdto);
	   public void insertMember(MemberDTO mdto) {
	      try {
	         con = getCon();

	         sql = "insert into member1(member_id,member_password,member_name,member_birth, "
	               + "member_gender,member_email,member_phone,member_postcode, "
	               + "member_addr1,member_addr2,member_regdate,member_point) "
	               + "values(?,?,?,?,?,?,?,?,?,?,now(),?)";

	         pstmt = con.prepareStatement(sql);

	         pstmt.setString(1, mdto.getMember_id());
	         pstmt.setString(2, mdto.getMemeber_password());
	         pstmt.setString(3, mdto.getMember_name());
	         pstmt.setString(4, mdto.getMember_birth());
	         pstmt.setString(5, mdto.getMember_gender());
	         pstmt.setString(6, mdto.getMember_email());
	         pstmt.setString(7, mdto.getMember_phone());
	         pstmt.setString(8, mdto.getMember_postcode());
	         pstmt.setString(9, mdto.getMember_addr1());
	         pstmt.setString(10, mdto.getMember_addr2());
	         pstmt.setInt(11, 0);
	         
	         
	         pstmt.executeUpdate();

	         System.out.println("회원가입 성공!");

	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         // 자원해제
	         closeDB();
	      }

	   }

	// mdao.insertMember(mdto);

	// idCheck(id,pass)
	public int idCheck(MemberDTO mdto) {
		int check = -1;
		
		try {
			con = getCon();

			sql = "select member_password from member1 where member_id=?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, mdto.getMember_id());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (mdto.getMemeber_password().equals(rs.getString("member_password"))) {
					// 아이디O, 비밀번호O
					check = 1;
				} else {
					// 아이디O, 비밀번호X
					check = 0;
				}
			} else {
				// 아이디없음
				check = -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}

		return check;
	}
	// idCheck(id,pass)

	// idCheck(id,pass)
	public int idCheck(String member_id) {
		int check = 0;

		try {
			con = getCon();
			sql = "select * from member1 where member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			System.out.println("member_id: " + member_id);
			if(rs.next()){
				check =0;
			} else {
				check=1;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeDB();
		}



		return check;
	}
	
	// idCheck(id,pass)
	public int idCheck(String member_id, String member_password){
		int check=-1;
		
		try {
			con = getCon();
			
			sql="select member_password from member1 where member_id=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, member_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				if(member_password.equals(rs.getString("member_password"))){
					// 아이디O, 비밀번호O
					check = 1;
				}else{
					// 아이디O, 비밀번호X
					check = 0;
				}
			}else{
				// 아이디없음
				check = -1;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return check;
	}
	// idCheck(id,pass)
	
	// getMember(id)
	public MemberDTO getMember(String member_id){
		MemberDTO mdto = null;
		
		try {
			con = getCon();
			
			//id에 해당하는 회원의 모든정보 가져오기
			sql="select * from member1 where member_id=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, member_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				mdto = new MemberDTO();
				
				mdto.setMember_id(rs.getString("member_id"));
				mdto.setMember_name(rs.getString("member_name"));
				mdto.setMember_phone(rs.getString("member_phone"));
				mdto.setMember_postcode(rs.getString("member_postcode"));
				mdto.setMember_addr1(rs.getString("member_addr1"));
				mdto.setMember_addr2(rs.getString("member_addr2"));
				mdto.setMember_email(rs.getString("member_email"));
				mdto.setMember_gender(rs.getString("member_gender"));
				mdto.setMember_regdate(rs.getDate("member_regdate"));
				mdto.setMember_birth(rs.getString("member_birth"));
				mdto.setMember_point(rs.getInt("member_point"));
				

			}
			System.out.println("회원정보 저장 성공 : "+mdto);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return mdto;
	}
	// getMember(id)
	
	// updateMember(mdto)
	public int updateMember(MemberDTO mdto){
		int check = -1;
		
		try {
			con = getCon();
			sql= "select member_password from member1 where member_id=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mdto.getMember_id());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				System.out.println(mdto.getMemeber_password());
				System.out.println(rs.getString("member_password"));
				if(mdto.getMemeber_password().equals(rs.getString("member_password"))){
					sql="update member1 set member_name=?, member_email=?, member_phone=?,"
							+ "member_postcode=?, member_addr1=?, member_addr2=? "
							+ " where member_id=?";
					
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, mdto.getMember_name());
					pstmt.setString(2, mdto.getMember_email());
					pstmt.setString(3, mdto.getMember_phone());
					pstmt.setString(4, mdto.getMember_postcode());
					pstmt.setString(5, mdto.getMember_addr1());
					pstmt.setString(6, mdto.getMember_addr2());
					pstmt.setString(7, mdto.getMember_id());
					
					pstmt.executeUpdate();
					
					check = 1;
				}else{
					// 비밀번호 오류
					check = 0;
				}
			}else{
				// 아이디 없음
				check = -1;
			}
			
			System.out.println("회원정보 수정 완료 : "+check);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}		
		
		return check;
	}
	// updateMember(mdto)
	
	// passwordUpdate(member_password, new_member_password)
	public int passUpdate(MemberDTO mdto){
		
		int check = -1;
		System.out.println(mdto.getMemeber_password());
		System.out.println(mdto.getMember_id());
		System.out.println(mdto.getNew_member_password());
		
		try {
			con = getCon();
			
			sql= "select member_password from member1 where member_id=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mdto.getMember_id());
			
			rs = pstmt.executeQuery();
	
			if(rs.next()){
	
				if(mdto.getMemeber_password().equals(rs.getString("member_password"))){
					
					sql= "update member1 set member_password=? "
							+ " where member_id=? ";
				
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, mdto.getNew_member_password());
					pstmt.setString(2, mdto.getMember_id());
	
					pstmt.executeUpdate();

					check = 1;
				}else{
				// 비밀번호 오류
					check = 0;
					}
			}else{
			// 아이디 없음
			check = -1;
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		
		return check;
	}

	// passwordUpdate(member_password, new_member_password)


	// deleteMember(member_id, member_password)
	
	public int deleteMember(String member_id, String member_password){
		int check = -1;
		
		try {
			con = getCon();
			sql =" select member_password from member1 where member_id=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				if(member_password.equals(rs.getString("member_password"))){
					sql = "delete from member1 where member_id=?";
					
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, member_id);
					
					pstmt.executeUpdate();
					
					check = 1;				
					
				}else{
					check = 0;
				}
			}else{
				check =-1;
			}
			
			System.out.println("회원 탈퇴 처리 성공  :"+check);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return check;
	}
	
	// deleteMember(member_id, member_password)
	
	
	// getMemberCount()
	
		public int getMemberCount(){

			int count = 0;
			
			try {
				con = getCon();
				
				sql="select count(*) from member1";
				
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
		// getMemberCount()
		
		
	// getMemberList()
	public List<MemberDTO> getMemberList(int startRow, int pageSize){
		List<MemberDTO> memberList = new ArrayList<MemberDTO>();
		
		try {
			con = getCon();
			
			sql="select * from member1 order by member_regdate asc limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow-1); // 시작행 -1
			pstmt.setInt(2, pageSize);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				// 회원 1명의 정보를 저장 -> ArrayList 한칸
				MemberDTO mdto = new MemberDTO();
				mdto.setMember_id(rs.getString("member_id"));
				mdto.setMember_point(rs.getInt("member_point"));
				mdto.setMember_name(rs.getString("member_name"));
				mdto.setMember_gender(rs.getString("member_gender"));
				mdto.setMember_phone(rs.getString("member_phone"));
				mdto.setMember_birth(rs.getString("member_birth"));
				mdto.setMember_postcode(rs.getString("member_postcode"));
				mdto.setMember_addr1(rs.getString("member_addr1"));
				mdto.setMember_addr2(rs.getString("member_addr2"));
				mdto.setMember_email(rs.getString("member_email"));
				mdto.setMember_regdate(rs.getDate("member_regdate"));
				
				
				
				// arrayList 한칸에 정보 저장
				memberList.add(mdto);		
				
			}
			
			System.out.println("회원 목록 저장 완료 ");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return memberList;
	}
	// getMemberList()
	
	//MemberSeach(search)
	public List<MemberDTO> MemberSearch(String search_column, String search, int startRow, int pageSize){
		List<MemberDTO> membersearchList = new ArrayList<MemberDTO>();
		
		try {
			
			System.out.println(search);
			
			con = getCon();
			
			if(search_column.equals("member_id")){
				
				sql = "select * from member1 where member_id like ? limit ?,? ";
			
				System.out.println(search_column+"@@@@");
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, "%"+search+"%");
				pstmt.setInt(2, startRow-1);
				pstmt.setInt(3, pageSize);

				rs = pstmt.executeQuery();
				System.out.println(rs);
				}else if(search_column.equals("member_name")){
				
				sql = "select * from member1 where member_name like ? limit ?,? ";
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, "%"+search+"%");
				pstmt.setInt(2, startRow-1);
				pstmt.setInt(3, pageSize);
			
				rs = pstmt.executeQuery();
				
				}else if(search_column.equals("member_addr1")){
				
				sql = "select * from member1 where member_addr1 like ? limit ?,? ";
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, "%"+search+"%");
				pstmt.setInt(2, startRow-1);
				pstmt.setInt(3, pageSize);

				rs = pstmt.executeQuery();
			}
			
//			pstmt = con.prepareStatement(sql);
//			
//			pstmt.setString(1,"'%"+ search+"%'");
//			pstmt.setInt(2, startRow-1);
//			pstmt.setInt(3, pageSize);
//
//			rs = pstmt.executeQuery();
			
			while(rs.next()){
				// 회원 1명의 정보를 저장 -> ArrayList 한칸
				MemberDTO mdto = new MemberDTO();
				mdto.setMember_id(rs.getString("member_id"));
				mdto.setMember_point(rs.getInt("member_point"));
				mdto.setMember_name(rs.getString("member_name"));
				mdto.setMember_gender(rs.getString("member_gender"));
				mdto.setMember_phone(rs.getString("member_phone"));
				mdto.setMember_birth(rs.getString("member_birth"));
				mdto.setMember_postcode(rs.getString("member_postcode"));
				mdto.setMember_addr1(rs.getString("member_addr1"));
				mdto.setMember_addr2(rs.getString("member_addr2"));
				mdto.setMember_email(rs.getString("member_email"));
				mdto.setMember_regdate(rs.getDate("member_regdate"));
				
				
				
				// arrayList 한칸에 정보 저장
				membersearchList.add(mdto);		
				
			}
		} catch (Exception e) {

			e.printStackTrace();
		}finally {
			closeDB();
		}
		
		
		return membersearchList;
	}
	//MemberSeach(search)
	
	//admindeletemember(deletemeber)
	public int admindeletemember(String deletemember){
		
		int result = 0;
		
		try {
			con = getCon();
			
			
			sql = "delete from member1 where member_id=?";
			
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, deletemember);
			pstmt.executeUpdate();
			
			System.out.println(deletemember+"회원 삭제완료");
		} catch (Exception e) {
	
			e.printStackTrace();
		}finally{
			closeDB();
		}
		
		
		return result;
	}
	//admindeletemember(deletemeber)
	
	
	//findMemberPassword
	public void findMemberPassword(String member_id,String member_pass){
		try {
			con =getCon();
			sql = "update member1 set member_password=? where member_id=?";
			pstmt = con.prepareStatement(sql);
			Shautills shautills = new Shautills();
			pstmt.setString(1, shautills.encryptSha(member_pass));
			pstmt.setString(2, member_id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	//findMemberPassword
	
	
	//matchIdtoEmail
		public int matchIdtoEmail(String member_id,String member_email){
			int result = 0;
			//result = 0 아이디 이메일 불일치
			//       = 1 아이디 이메일 일치
			try{
				con = getCon();
				sql="select * from member1 where member_id=? and member_email=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, member_id);
				pstmt.setString(2, member_email);
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					result=1;
				}
				
				
			}catch (Exception e) {
				
			} finally {
				closeDB();
			}
			System.out.println("result=" + result);
			return result;
		}
		//matchIdtoEmail

		
		
		////////////////////////////////////20191030
		public int isJoinedNaverEmail(String member_email){
			int check=0;
			try {
				con = getCon();
				sql = "select * from member1 where member_email=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, member_email);
				rs = pstmt.executeQuery();
				if(rs.next()){
					check = 1;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
			return check;
		}
		public String naverEmailLogin(String member_email){
			String member_id="";
			try {
				con = getCon();
				sql = "select member_id from member1 where member_email=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, member_email);
				rs= pstmt.executeQuery();
				if(rs.next()){
					member_id = rs.getString("member_id");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				
			} finally {
				closeDB();
			}
			
			return member_id;
		}


	//mdao.updatePoint(id,point)
	public void updatePoint(String id,int point){
		try {
			con = getCon();
			sql = "update member1 set member_point = ? where member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, point);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
			
			System.out.println("member_point 수정 완료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	//mdao.updatePoint(id,point)
	public String matchNameToEmail(String name, String email){
		String member_id = "";
		try {
			con = getCon();
			sql = "select * from member1 where member_name=? and member_email=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			rs = pstmt.executeQuery();
			if(rs.next()){
				member_id = rs.getString("member_id");
			}else{
				member_id = "";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			closeDB();
		}
		return member_id;
	}		
}
		
		
		



//