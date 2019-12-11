<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
<script src="https://cdn.bootpay.co.kr/js/bootpay-3.0.6.min.js" type="application/javascript"></script>
<script type="text/javascript">
window.history.forward();
function noBack(){window.history.forward();}
 
var today = new Date();
var dd = today.getDate() + 3;
var mm = today.getMonth() + 1;
var yyyy = today.getFullYear();
if (dd < 10) {dd = '0' + dd}
if (mm < 10) {mm = '0' + mm}
today = yyyy+"-"+mm+"-"+dd;

BootPay.request({
	price : $('#total_sum').val(),
	application_id : "5dac674a4f74b40029c5f066",
	name : $('#item_name').val(),
	pg : 'nicepay',
	method : $('#order_payment').val(),
	show_agree_window : 1,
    user_info: {
    	username: $('#order_payer_name').val(),
        email : $('#email').val(),
        addr: $('#addr').val(),
        phone: $('#order_payer_phone').val()
    },
    order_id: "1571616640212", 
    account_expire_at: today,
    extra: {
    	 vbank_result: 1
	}
}).error(function (data) {
    //결제 진행시 에러가 발생하면 수행됩니다.
    alert('고객님의 정보가 일치하지 않거나 유효하지 않은 정보입니다 다시 시도해 주십시오');
    console.log(data);
    location.href='./Order.or';
}).cancel(function (data) {
    //결제가 취소되면 수행됩니다.
    alert('고객님의 요청에 의해 결제가 취소되었습니다');
    location.href='./Order.or';
}).ready(function (data) {
	// 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
	console.log(data);
}).done(function (data) {
	$.ajax({
		url : "./OrderAdd.or",
		type : "POST",
		data : {total_sum : $('#total_sum').val(),
				order_payment : $('#order_payment').val(),
				email : $('#email').val(),
				order_payer_name : $('#order_payer_name').val(),
				order_payer_phone1 : $('#order_payer_phone1').val(),
				order_payer_phone2 : $('#order_payer_phone2').val(),
				order_payer_phone3 : $('#order_payer_phone3').val(),
				order_recipient_phone : $('#order_recipient_phone').val(),
				postcode : $('#postcode').val(),
				addr1 : $('#addr1').val(),
				addr2 : $('#addr2').val(),
				memo : $('#memo').val(),
				rest_point : $('#rest_point').val(),
				new_point : $('#new_point').val(),
				order_recipient_name : $('#order_recipient_name').val()},
		success : function(data){
			$('body').html(data);
		},
	});
});
</script>
</head>
<body>
	<%
	request.setCharacterEncoding("UTF-8");
	
	int total_sum = Integer.parseInt(request.getParameter("total_sum"));
	String order_payment = request.getParameter("order_payment");
	String order_payer_email1 = request.getParameter("order_payer_email1");
	String order_payer_email2 = request.getParameter("order_payer_email2");
	String order_payer_name = request.getParameter("order_payer_name");
	String order_payer_phone1 = request.getParameter("order_payer_phone1");
	String order_payer_phone2 = request.getParameter("order_payer_phone2");
	String order_payer_phone3 = request.getParameter("order_payer_phone3");
	String order_recipient_phone = request.getParameter("order_recipient_phone");
	String postcode = request.getParameter("postcode");
	String addr1 = request.getParameter("addr1");
	String addr2 = request.getParameter("addr2");
	String memo = request.getParameter("memo");
	int rest_point = Integer.parseInt(request.getParameter("rest_point"));
	int new_point = Integer.parseInt(request.getParameter("new_point"));
	String order_recipient_name = request.getParameter("order_recipient_name");
	String item_name = request.getParameter("item_name");
	
	System.out.println(order_recipient_phone);
	System.out.println(order_payer_phone1);
	System.out.println(order_payer_phone2);
	System.out.println(order_payer_phone3);
	%>
	<input type="hidden" value="<%=total_sum%>" id="total_sum">
	<input type="hidden" value="<%=order_payment%>" id="order_payment">
	<input type="hidden" value="<%=order_payer_email1 %>@<%=order_payer_email2 %>" id="email">
	<input type="hidden" value="<%=order_payer_name%>" id="order_payer_name">
	
	<input type="hidden" value="<%=order_payer_phone1%>" id="order_payer_phone1">
	<input type="hidden" value="<%=order_payer_phone2%>" id="order_payer_phone2">
	<input type="hidden" value="<%=order_payer_phone3%>" id="order_payer_phone3">
	<input type="hidden" value="<%=order_payer_phone1%>-<%=order_payer_phone2%>-<%=order_payer_phone3%>" id="order_payer_phone">
	
	<input type="hidden" value="<%=order_recipient_phone %>" id="order_recipient_phone">
	<input type="hidden" value="<%=postcode%>" id="postcode">
	<input type="hidden" value="<%=addr1%>" id="addr1">
	<input type="hidden" value="<%=addr2%>" id="addr2">
	<input type="hidden" value="<%=addr1%><%=addr2%>" id="addr">
	<input type="hidden" value="<%=memo%>" id="memo">
	<input type="hidden" value="<%=rest_point%>" id="rest_point">
	<input type="hidden" value="<%=new_point%>" id="new_point">
	<input type="hidden" value="<%=order_recipient_name %>" id="order_recipient_name">
	<input type="hidden" value="<%=item_name %>" id="item_name">
</body>
</html>