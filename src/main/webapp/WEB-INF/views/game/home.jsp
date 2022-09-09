<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ page import="cybersoft.java18.gamedoanso.utils.UrlUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
    <title>Đoán Số</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
    <div class ="text-center mt-5">
        <h1>Chào mừng bạn đến với game đoán số</h1>

    </div>

    <div class="mt-5">
         <a href="<%=request.getContextPath() + UrlUtils.GAME%>" class="btn btn-outline-primary btn-lg btn-block" role="button" aria-pressed="true">Chơi ngay</a>
         <a href="<%=request.getContextPath() + UrlUtils.XEP_HANG%>" class="btn btn-outline-secondary btn-lg btn-block" role="button" aria-pressed="true">Xem bảng xếp hạng</a>
    </div>

     <div class="text-center mt-5 clearfix">
        <h3 class ="success"> Đừng sợ thất bại,</br>
        thất bại không đáng sợ </br>
        mà thứ đáng sợ chính là kẻ không dám hành động </h3>
        <p></br>Chúc bạn 1 ngày mới tốt lành</p>
    </div>
</body>
</html>