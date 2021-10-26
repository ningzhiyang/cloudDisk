<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <base href="<%=basePath %>" />
    <base lay-href="<%=basePath %>" />
    <title>阿洋云盘运营管理系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="icon" href="loginUI/images/icon.png" type="image/x-icon">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/layuiadmin/style/admin.css" media="all">
</head>
<body class="layui-layout-body">

<div id="LAY_app">
    <div class="layui-layout layui-layout-admin">
        <div class="layui-header">
            <!-- 头部区域 -->
            <ul class="layui-nav layui-layout-left">
                <li class="layui-nav-item layadmin-flexible" lay-unselect>
                    <a href="javascript:;" layadmin-event="flexible" title="侧边伸缩">
                        <i class="layui-icon layui-icon-shrink-right" id="LAY_app_flexible"></i>
                    </a>
                </li>
                <li class="layui-nav-item" lay-unselect>
                    <a href="javascript:;" layadmin-event="refresh" title="刷新">
                        <i class="layui-icon layui-icon-refresh-3"></i>
                    </a>
                </li>
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <input type="text" placeholder="搜索..." autocomplete="off" class="layui-input layui-input-search" layadmin-event="serach" lay-action="template/search.html?keywords=">
                </li>
            </ul>
            <ul class="layui-nav layui-layout-right" lay-filter="layadmin-layout-right">

<%--                <li class="layui-nav-item" lay-unselect>--%>
<%--                    <a lay-href="app/message/index.html" layadmin-event="message" lay-text="消息中心">--%>
<%--                        <i class="layui-icon layui-icon-notice"></i>--%>

<%--                        <!-- 如果有新消息，则显示小圆点 -->--%>
<%--&lt;%&ndash;                        <span class="layui-badge-dot"></span>&ndash;%&gt;--%>
<%--                    </a>--%>
<%--                </li>--%>
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <a href="javascript:;" layadmin-event="theme">
                        <i class="layui-icon layui-icon-theme"></i>
                    </a>
                </li>
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <a href="javascript:;" layadmin-event="note">
                        <i class="layui-icon layui-icon-note"></i>
                    </a>
                </li>
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <a href="javascript:;" layadmin-event="fullscreen">
                        <i class="layui-icon layui-icon-screen-full"></i>
                    </a>
                </li>
                <li class="layui-nav-item" lay-unselect>
                    <a href="javascript:;">
                        <cite>${sessionScope.sysUser.username}</cite>
                    </a>
                    <dl class="layui-nav-child">
<%--                        <dd><a lay-href="set/user/info.html">基本资料</a></dd>--%>
<%--                        <dd><a lay-href="set/user/password.html">修改密码</a></dd>--%>
<%--                        <hr>--%>
                        <dd layadmin-event="logout" style="text-align: center;"><a href="<%=request.getContextPath()%>/logout">退出</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item layui-show-xs-inline-block layui-hide-sm" lay-unselect>
                    <a href="javascript:;" layadmin-event="more"><i class="layui-icon layui-icon-more-vertical"></i></a>
                </li>
            </ul>
        </div>

        <!-- 侧边菜单 -->
        <div class="layui-side layui-side-menu">
            <div class="layui-side-scroll">
                <div class="layui-logo" lay-href="/index">
                    <span>阿洋云盘运营管理系统</span>
                </div>

                <ul class="layui-nav layui-nav-tree"  id="LAY-system-side-menu" lay-filter="layadmin-system-side-menu">
                    <c:forEach items="${one }" var="parent">
                        <c:if test="${parent.MStatus == true }">
                            <li class="layui-nav-item">
                                <a href="javascript:;" class="nav-link tpl-left-nav-link-list active">
                                    <i class="${parent.MIcon}"></i>
                                    <cite>${parent.MName}</cite>
                                    <i class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
                                </a>
                                <dl class="layui-nav-child">
                                        <c:forEach items="${two}" var="children">
                                            <c:if test="${children.MStatus==true && children.MThirdMenuId == parent.MId}">
                                                <dd data-name="console" >
                                                    <a
                                                            <c:choose>
                                                                <c:when test="${children.MUrl == 'http://www.shiningstars.cn:50070'}">
                                                                    href="${children.MUrl}" target="_blank"
                                                                </c:when>
                                                                <c:otherwise>
                                                                    lay-href="/cloud${children.MUrl}"
                                                                </c:otherwise>
                                                            </c:choose>
                                                    > <i class="am-icon-angle-right"></i>
                                                        <span>${children.MName}</span>
                                                        <i class="${children.MIcon} tpl-left-nav-content-ico am-fr am-margin-right"></i>
                                                    </a>
                                                </dd>
                                            </c:if>
                                        </c:forEach>
                                </dl>
                            </li>
                        </c:if>
                    </c:forEach>
                </ul>
            </div>
        </div>

        <!-- 页面标签 -->
        <div class="layadmin-pagetabs" id="LAY_app_tabs">
            <div class="layui-icon layadmin-tabs-control layui-icon-prev" layadmin-event="leftPage"></div>
            <div class="layui-icon layadmin-tabs-control layui-icon-next" layadmin-event="rightPage"></div>
            <div class="layui-icon layadmin-tabs-control layui-icon-down">
                <ul class="layui-nav layadmin-tabs-select" lay-filter="layadmin-pagetabs-nav">
                    <li class="layui-nav-item" lay-unselect>
                        <a href="javascript:;"></a>
                        <dl class="layui-nav-child layui-anim-fadein">
                            <dd layadmin-event="closeThisTabs"><a href="javascript:;">关闭当前标签页</a></dd>
                            <dd layadmin-event="closeOtherTabs"><a href="javascript:;">关闭其它标签页</a></dd>
                            <dd layadmin-event="closeAllTabs"><a href="javascript:;">关闭全部标签页</a></dd>
                        </dl>
                    </li>
                </ul>
            </div>
            <div class="layui-tab" lay-unauto lay-allowClose="true" lay-filter="layadmin-layout-tabs">
                <ul class="layui-tab-title" id="LAY_app_tabsheader">
                    <li lay-id="/cloud/bg/vue_user_manager/vue_user_manager" lay-attr="/cloud/bg/vue_user_manager/vue_user_manager" class="layui-this"><i class="layui-icon layui-icon-home">首页</i></li>
	            </ul>
            </div>
        </div>


        <!-- 主体内容 -->
        <div class="layui-body" id="LAY_app_body">
            <div class="layadmin-tabsbody-item layui-show">
               <iframe src="/cloud/bg/vue_user_manager/vue_user_manager" frameborder="0" class="layadmin-iframe"></iframe>
            </div>
        </div>

        <!-- 辅助元素，一般用于移动设备下遮罩 -->
        <div class="layadmin-body-shade" layadmin-event="shade"></div>
    </div>
</div>

<script src="layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: 'layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use('index');
</script>
</body>
</html>



