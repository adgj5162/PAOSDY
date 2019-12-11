package admin.order.action;

import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.order.db.OrderDAO;
import com.order.db.OrderDTO;




public class ListExcellSaveAction implements Action{
	
	

       
    	 @Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {


    			// 세션 값 제어 (로그인 상태이면서 관리자 일때만 처리가능)
    			
    			HttpSession session = request.getSession();
    			String member_id = (String) session.getAttribute("member_id");
    			
    			ActionForward forward = new ActionForward();
    			
    			if(member_id == null || !member_id.equals("admin")){
    				
    				forward.setPath("./MemberLogin.me");
    				forward.setRedirect(true);
    				
    				return forward;
    			} 
    		 
    			
    			// DB 처리객체
    			
    			OrderDAO odao = new OrderDAO();
    			// 한페이지에서 보여줄 글 개수
    			
    			int stat = (Integer.parseInt(request.getParameter("stat")));
    			// getOrderList(); => 주문목록
    			int ordercount = odao.getOrderCount(stat);
    			System.out.println("오더카운트"+ordercount);
    			
    			List<OrderDTO> orderList = null;
    			
    			if(ordercount != 0){
    				orderList = odao.getOrderList(stat);
    			}
    		 
    		 String status = "";
    		 switch (stat) {
			case 2:
				status = "주문완료";
				break;
			case 3:
				status = "상품준비";
				break;
			case 4:
				status = "배송중";
				break;
			case 5:
				status = "배송완료";
				break;
			case 6:
				status = "구매확정";
				break;
			case 15:
				status = "취소/반품";
				break;
			}
    		 
    	try{
    		
            //String filename = "C:/NewExcelFile.xlsx" ;
    		String filename = "C:/Upload/"+status+"list.xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(status);  
            XSSFRow rowhead = sheet.createRow((short)1);
            XSSFRow titleName = sheet.createRow((short)0);
            CellStyle styleHead = workbook.createCellStyle(); 
            
            titleName.createCell(0).setCellValue(status);

            // orderDAO 객체 생성
            
            CellStyle cellStyle = workbook.createCellStyle();
            CellStyle styleBody = workbook.createCellStyle();
            styleBody.setBorderTop(BorderStyle.THIN); //셀 테두리
            styleBody.setBorderBottom(BorderStyle.THIN);
            styleBody.setBorderLeft(BorderStyle.THIN);
            styleBody.setBorderRight(BorderStyle.THIN);

            rowhead.createCell(0).setCellValue("주문번호");
            rowhead.createCell(1).setCellValue("주문자ID");
            rowhead.createCell(2).setCellValue("전화번호");
            rowhead.createCell(3).setCellValue("상품이름");
            rowhead.createCell(4).setCellValue("Size");
            rowhead.createCell(5).setCellValue("색상");
            rowhead.createCell(6).setCellValue("수량");
            rowhead.createCell(7).setCellValue("받는사람");
            rowhead.createCell(8).setCellValue("전화번호");
            rowhead.createCell(9).setCellValue("배송지");
            rowhead.createCell(10).setCellValue("MEMO");
            rowhead.createCell(11).setCellValue("주문날짜");
            
            
    		if(orderList == null){
    			System.out.println("널이다");
    		}else{

    	     for(int i=0; i < orderList.size(); i++){ 
    	    	 OrderDTO odto = orderList.get(i);
    	    	 int Startrow = 2;
    		
            XSSFRow row = sheet.createRow((short)Startrow+i);
            row.createCell(0).setCellValue(odto.getOrder_trans_num());
            row.createCell(1).setCellValue(odto.getOrder_member_id());
            row.createCell(2).setCellValue(odto.getOrder_name());
            row.createCell(3).setCellValue(odto.getOrder_member_phone().substring(0,3)+"-"
            							   +odto.getOrder_member_phone().substring(3,7)+"-"
            							   +odto.getOrder_member_phone().substring(7,11));
            row.createCell(4).setCellValue(odto.getOrder_item_name());
            row.createCell(5).setCellValue(odto.getOrder_size());
            row.createCell(6).setCellValue(odto.getOrder_color());
            row.createCell(7).setCellValue(odto.getOrder_take_name());
            row.createCell(8).setCellValue(odto.getOrder_phone().substring(0,3)+"-"
   	      		 						   +odto.getOrder_phone().substring(3,7)+"-"
   	      		 						   +odto.getOrder_phone().substring(7,11));
            row.createCell(9).setCellValue(odto.getOrder_addr1()+","+odto.getOrder_addr2());
            row.createCell(10).setCellValue(odto.getOrder_memo());
            row.createCell(11).setCellValue(odto.getOrder_date()+"");
    	     
    	     	}
    		
    		}
    		
            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("엑셀 파일 생성 완료!");

        } catch ( Exception ex ) {
            System.out.println(ex);
        }
		
    	
		forward.setPath("./AdminOrderList.ador");
		forward.setRedirect(true);

		return forward;
    	 }
}
