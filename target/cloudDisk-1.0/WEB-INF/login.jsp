<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>阿洋云盘运营管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="keywords"
          content="Techno Login Form Responsive, Login Form Web Template, Flat Pricing Tables, Flat Drop-Downs, Sign-Up Web Templates, Flat Web Templates, Login Sign-up Responsive Web Template, Smartphone Compatible Web Template, Free Web Designs for Nokia, Samsung, LG, Sony Ericsson, Motorola Web Design">
    <script type="application/x-javascript"> addEventListener("load", function () {
        setTimeout(hideURLbar, 0);
    }, false);

    function hideURLbar() {
        window.scrollTo(0, 1);
    } </script>
    <link rel="stylesheet" href="loginUI/css/style.css" type="text/css" media="all">
    <link rel="icon" href="loginUI/images/icon.png" type="image/x-icon">
</head>
<!-- Body -->
<body>

<h1>阿洋云盘运营管理系统</h1>

<div class="containerw3layouts-agileits">

    <div class="w3imageaits">
        <img src="loginUI/images/web.jpg" alt="">
    </div>

    <div class="aitsloginwthree w3layouts agileits">
        <h2>登录</h2>
        <font style="color: red">
            ${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}
        </font>
        <form action="login" method="post">
            <input type="text" Name="username" placeholder="请输入用户名" required="" id="username">
            <input type="password" Name="password" placeholder="请输入密码" required="" id="password">
            <div>
                <input type="text" name="kaptcha" id="yanzhengma" placeholder="请输入验证码" required="" style="width: 55%"/>
                <img src="VerificationCode" style="height: 50px;width:30%;float: left;margin-left: 5%;"
                     id="get-vercode">
            </div>
            <ul class="tick w3layouts agileinfo">
                <li>
                    <input type="checkbox" id="brand1" value="true" name="remember-me">
                    <label for="brand1"><span></span>Remember me</label>
                </li>
            </ul>
            <div class="send-button wthree agileits">
                <input type="submit" value="登录" id="btn">
            </div>
        </form>
    </div>

</div>

<div class="w3lsfooteragileits" style="margin-top:33%">
    <p> &copy; 2021 AY Cloud Disk Login Form. All Rights Reserved | Design by 宁志洋</p>
</div>

</body>
<script src="layuiadmin/js/jquery-3.4.1.min.js"></script>
<script src="layuiadmin/layer/layer.js"></script>
<script>
    $("#get-vercode").click(function () {
        $("#get-vercode").attr("src", "VerificationCode")
    });
</script>
</html>