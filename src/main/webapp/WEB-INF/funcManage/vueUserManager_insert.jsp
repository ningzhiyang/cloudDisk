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
                    <label class="layui-form-label">用户图片</label>
                    <div class="layui-input-inline">
                        <input name="vImg" lay-verify="required" id="activity_pic" placeholder="图片地址"
                               class="layui-input">
                    </div>
                    <div class="layui-input-inline layui-btn-container" style="width: auto;">
                        <button type="button" class="layui-btn layui-btn-primary" id="test1">
                            <i class="layui-icon">&#xe67c;</i>上传图片
                        </button>
                        <button class="layui-btn layui-btn-primary" type="button" onclick="show_img()">查看图片</button>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">用户手机号</label>
                    <div class="layui-input-block">
                        <input type="text" name="vPhone" lay-verify="required" autocomplete="off" placeholder="请输入用户手机号"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">用户账号</label>
                    <div class="layui-input-block">
                        <input type="text" name="vAccount" lay-verify="required" autocomplete="off" placeholder="请输入用户名"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-block">
                        <input type="text" name="vPwd" lay-verify="required" autocomplete="off" placeholder="请输入密码"
                               class="layui-input">
                    </div>
                </div>
                <!--<div class="layui-form-item">
                    <label class="layui-form-label">摄像头状态</label>
                    <div class="layui-input-inline">
                        <select name="status" lay-verify="required" lay-search="" id="status">
                            <option value="">直接选择摄像头状态</option>
                            <option value="1">1-运行中</option>
                            <option value="2">2-停止运行</option>
                        </select>
                    </div>
                </div>-->
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
    }).use(['index', 'form', 'laydate'], function () {
        var $ = layui.$
            , admin = layui.admin
            , element = layui.element
            , layer = layui.layer
            , laydate = layui.laydate
            , form = layui.form;

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
            $.post("<%=request.getContextPath()%>/bg/vue_user_manager/to_insert", data.field, function (data) {
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
<script>
    layui.use('upload', function () {
        var $ = layui.jquery
            , upload = layui.upload;

        //普通图片上传
        var uploadInst = upload.render({
            elem: '#test1'
            , url: '<%=request.getContextPath()%>/upload' //改成您自己的上传接口
            , field: 'image'
            , async: false
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    $('#demo1').attr('src', result); //图片链接（base64）
                });
            }
            , done: function (res) {
                //如果上传失败
                if (res.code > 0) {
                    return layer.msg('上传失败');
                }
                $('#activity_pic').attr('value', '/cloud/pic/' + res.name);
                //上传成功
                layer.msg('上传成功');
            }
            , error: function (res) {
                layer.msg('上传成功');
            }
            , success: function (res) {
                layer.msg('上传成功');
            }
        });
    });
</script>
<script src="<%=request.getContextPath()%>/layuiadmin/js/jquery-3.4.1.min.js"></script>
<script>
    function show_img() {
        var $ = layui.$;
        var t = $("#activity_pic").val();
        console.log(t)
        //页面层
        layer.open({
            type: 1,
            title: '头像',
            skin: 'layui-layer-rim', //加上边框
            area: ['80%', '85%'], //宽高 t.width() t.height()
            shadeClose: false, //开启遮罩关闭
            end: function (index, layero) {
                return false;
            },
            content: '<div style="text-align:center"><img src="' + t + '" /></div>'
        });
    }
</script>
</body>
</html>


