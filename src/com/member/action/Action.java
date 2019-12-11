package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	// ?��붿긽硫붿꽌��? -> 硫붿꽌�뱶�?�� ��(�삎�떇)吏��젙
	// 紐⑤�? 泥섎?���룞�옉�� 諛섎뱶�?�� �빐�떦 ?��붿긽硫붿꽌�뱶瑜�? ?��?�쁽�빐�꽌 �궗�슜

	public ActionForward execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception;

}
