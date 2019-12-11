package net.email;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SendEmail
 */
@WebServlet("/SendEmail")
public class SendEmail extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         String host = "smtp.naver.com";
         String port = "587";
         String mailFrom = "maienkr1";
         String password = "2772197z";

         // outgoing message information
         String mailTo = request.getParameter("member_email");
         String subject = "PAOSDY E-Mail 인증코드 발송";
         String code = "";
         for(int i =0; i<4; i++){
            int ranNum =(int)(Math.random()*9);
            code += ranNum;
         }
         
         String message = "<h1>인증코드</h1><br>";
         message += "인증번호 : " + code+"<br>";
         message += "안녕하세요!<br>";
         message += "PAOSDY 인증코드 확인 메일입니다.<br> 아래 인증번호를 입력하여 주십시오 <br>";
         message += "<font color=red>인증코드를 입력창에 입력해주세요 <br> code : "+code+"</font>";

         HtmlEmailSender mailer = new HtmlEmailSender();

         try {
            mailer.sendHtmlEmail(host, port, mailFrom, password, mailTo, subject, message);
            System.out.println("Email sent.");
         } catch (Exception ex) {
            System.out.println("Failed to sent email.");
            ex.printStackTrace();
         }
         
         PrintWriter script = response.getWriter();
         script.print(code);
      
   }

}