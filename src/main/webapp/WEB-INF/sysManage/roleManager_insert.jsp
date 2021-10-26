<%--
  Created by IntelliJ IDEA.
  User: 宁志洋
  Date: 2021/7/27
  Time: 9:14
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/layuiadmin/style/admin.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-header">系统功能模块</div>
        <div class="layui-card-body" style="padding: 15px;">
            <form class="layui-form" action="" lay-filter="component-form-group" type="">

                <div class="layui-form-item">
                    <label class="layui-form-label">角色名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="rName" lay-verify="required" autocomplete="off" placeholder="请输入角色名称"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">角色备注</label>
                    <div class="layui-input-block">
                        <input type="text" name="rDetail" lay-verify="required" autocomplete="off" placeholder="请输入角色备注"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-layout-admin">
                    <div class="layui-input-block">
                        <div class="layui-footer" style="left: 0;">
                            <button class="layui-btn" lay-submit="" lay-filter="component-form-demo">立即提交</button>
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>


<script src="<%=request.getContextPath()%>/layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: '/cloud/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'form', 'laydate', 'tree'], function () {
        var $ = layui.$
            , admin = layui.admin
            , element = layui.element
            , layer = layui.layer
            , laydate = layui.laydate
            , form = layui.form
            , tree = layui.tree;

        form.render(null, 'component-form-group');

        laydate.render({
            elem: '#LAY-component-form-group-date1'
            , type: 'datetime'
        });
        laydate.render({
            elem: '#LAY-component-form-group-date2'
            , type: 'datetime'
        });

        /* 自定义验证规则 */
        form.verify({
        });

        /* 监听提交 */
        var form =layui.form;
        form.on('submit(component-form-demo)', function (data) {
            $.post("<%=request.getContextPath()%>/bg/role_manager/to_insert", data.field, function (data) {
                // 获取 session
                if (data.status === "success") {
                    layer.msg("success!!!", {icon: 1}, function () {
                        parent.location.reload(); // 父页面刷新
                        layer.close(layer.index);
                    });
                }else{
                    layer.msg("fail!!!", {icon: 5}, function () {
                        parent.layui.admin.events.closeThisTabs();
                    });
                }
            });
            return false;
        });
    });
</script>
</body>
</html>


