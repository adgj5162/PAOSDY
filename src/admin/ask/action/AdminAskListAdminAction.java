package admin.ask.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ask.db.AskDAO;
import com.ask.db.AskDTO;
import com.ask.db.CommentDTO;


public class AdminAskListAdminAction implements Action {
   
   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      System.out.println("AskListAction_execute()**********");
      
      //ID 세션 체크
      HttpSession session = request.getSession();
      String id = (String)session.getAttribute("member_id");
      ActionForward forward = new ActionForward();
      if(id == null){
         forward.setPath("./MemberLogin.me");
         forward.setRedirect(true);
         return forward;
      }

      //객체 생성
      AskDAO askdao = new AskDAO();
      AskDTO askdto = new AskDTO();
      List coList = new ArrayList();
      List<AskDTO> askList =null;
      
      //문의글 개수 확인 메서드
      int askCount = askdao.getAskCount();
         
      
      //페이징처리
      int askPageSize = 5;
      // 현 페이지가 몇페이지 인지를 가져오기
      String askPageNum = request.getParameter("ask_pageNum");
      
      
      if (askPageNum == null || askPageNum.equals("null")) {
         askPageNum = "1"; // pageNum의 값이 없을경우 무조건 1페이지
      }
      
      // 시작행 구하는 작업
      int askCurrentPage = Integer.parseInt(askPageNum);
      int askStartRow = (askCurrentPage - 1) * askPageSize + 1;
      // => 1 11 21 31 ....
      
      // 끝행 구하는 작업
      int askEndRow = askCurrentPage * askPageSize;
      // => 10 20 30 40....
      
      //문의글 리스트 저장
      Vector vec = null;
      
      if (askCount != 0) {
         vec = askdao.getAskList(askStartRow, askPageSize);
         askList=(List)vec.get(0);
         
         for(int i=0; i<askList.size(); i++){
            askdto = askList.get(i);
            
            CommentDTO codto = askdao.getComment(askdto.getAsk_num());
            coList.add(codto);
         }
      }else{
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
                  
            out.println("<script>");
            out.println("   alert('문의글이 존재하지 않습니다');   ");
            out.println("   location.href='./AdminMain.adme'; ");
            out.println("</script>");
                  
            out.close();
            return null;
      }
      
      
      
      // 페이지 출력
      int askPageCount = askCount / askPageSize + (askCount % askPageSize == 0 ? 0 : 1);

      // 한화면에 보여줄 페이지 번호 계산
      int askPageBlock = 5;
      // 시작페이지 1~10 =>1 11~20 => 11 21~30 => 21
      int askStartPage = ((askCurrentPage - 1) / askPageBlock) * askPageBlock + 1;
      // 끝페이지
      int askEndPage = askStartPage + askPageBlock;
      if (askEndPage > askPageCount) {
         askEndPage = askPageCount;
      }
      
      // 정보 저장 (글 , 페이징처리 값)
      // 글 개수, 게시판 정보(DB),페이지
      // 정보(pageNum,pageCount,pageBlock,startPage,endPage)

      request.setAttribute("askCount", askCount);
      request.setAttribute("askList", askList);
      request.setAttribute("item_name", vec.get(1));
      request.setAttribute("askPageNum", askPageNum);
      request.setAttribute("askPageCount", askPageCount);
      request.setAttribute("askPageBlock", askPageBlock);
      request.setAttribute("askStartPage", askStartPage);
      request.setAttribute("askEndPage", askEndPage);
      

      System.out.println(askPageNum);
      
      //request.setAttribute("askdto",askdto);
      request.setAttribute("coList", coList);
      
      //페이지 이동("./ask/ask_list.jsp")
      forward.setPath("./admin/admin_ask_list.jsp");
      forward.setRedirect(false);
      return forward;
   }

}