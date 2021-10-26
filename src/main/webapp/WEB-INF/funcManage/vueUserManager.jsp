<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>系统用户管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/layuiadmin/style/admin.css" media="all">
</head>
<style>
    .layui-table-cell {
        height: 60px;
        line-height: 60px;
    }
    th .layui-table-cell {
        height: 30px;
        line-height: 30px;
    }
    th .laytable-cell-1-0-7 {
        height: 30px;
        line-height: 30px;
    }
    .layui-table img {
        width: 100px;
        height: 60px;
    }
</style>
<body>
<div class="layui-card layadmin-header" style="display:block;">
    <span class="layui-breadcrumb">
         <a>功能管理</a>
         <a><cite>vue用户管理</cite></a>
   </span>
</div>

<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item demoTable">
                			  <div class="layui-inline">
                				<div class="layui-input-inline">
                				  <input type="text" name="name" id="demoReload" placeholder="请输入用户账号" autocomplete="off" class="layui-input">
                				</div>
                			  </div>
                			  <div class="layui-inline">
                				<button class="layui-btn layuiadmin-btn-comm" data-type="reload">
                				  <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
                				</button>
            </div>
        </div>
    </div>
    <div class="layui-card">
        &nbsp;&nbsp;
<%--        <div class="demoTable">--%>
<%--                    记账人：--%>
<%--                    <div class="layui-inline">--%>
<%--                           <input class="layui-input" name="name" id="demoReload" autocomplete="off">--%>
<%--                        </div>--%>
<%--                   <button class="layui-btn" data-type="reload">查询</button>--%>
<%--        </div>--%>

        <div class="layui-card-body" style="margin-top: 15px">
            <table class="layui-hide" id="LAY_table_user" lay-filter="LAY_table_user"></table>

            <script type="text/html" id="tools">
                <div class="layui-btn-container">
                    <button class="layui-btn layui-btn-sm" lay-event="add">
                        <i class="layui-icon layui-icon-add-1" style="margin-left: 3px;"></i>
                    </button>
                </div>
            </script>
        </div>
    </div>
</div>

<script src="<%=request.getContextPath()%>/layuiadmin/layui/layui.js" charset="utf-8"></script>

<script type="text/html" id="barDemo">
    <a class="layui-btn  layui-btn-sm" lay-event="edit"><i class="layui-icon layui-icon-edit"></i></a>
    <a class="layui-btn layui-btn-danger  layui-btn-sm" lay-event="del"><i class="layui-icon layui-icon-delete"></i></a>
</script>
<script src="<%=request.getContextPath()%>/layuiadmin/layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script src="<%=request.getContextPath()%>/layuiadmin/js/jquery-3.4.1.min.js"></script>
<script>
    layui.use(['table', 'element', 'form', 'layer'], function () {
        var table = layui.table;
        var form = layui.form;
        var $ = layui.$;
        // 参数d是当前行数据
        var statusTpl = function (d) {
            if (d.vstatus === 1) {
                return '<input type="checkbox" id="status" value="' + d.vid + '" lay-filter="stat" lay-skin="switch" lay-text="开启|禁用" checked> ';
            } else {
                return '<input type="checkbox" id="status" value="' + d.vid + '" lay-filter="stat" lay-skin="switch" lay-text="开启|禁用"> ';
            }
        };

        table.render({
            elem: '#LAY_table_user'
            , url: '<%=request.getContextPath()%>/bg/vue_user_manager/vue_user_manager_json'
            , toolbar: '#tools'
            , title: '用户数据表'
            , totalRow: true
            , cols: [[
                {
                    field: 'vid',
                    title: 'ID',
                    fixed: 'left',
                    unresize: true,
                    sort: true,
                    // totalRowText: '合计'
                }
                , {field: 'vphone', title: '用户手机号'}
                , {field: 'vaccount', title: '用户账号'}
                , {field: 'vpwd', title: '密码'}
                , {
                    field: 'vimg', title: '用户图片', templet: function (res) {
                        return '<div onclick="show_img(this)" ><img src="' + res.vimg + '" ' + 'alt="" style="width: 30px;height: 30px"/></div>'
                    }
                }
                , {field: 'vloginIp', title: '登录IP'}
                , {field: 'vloginTime', title: '登录时间'}
                , {field: 'vstatus', title: '账号状态', align: 'center', fixed: 'right', templet: statusTpl}
                , {fixed: 'right', title: '操作', fixed: 'right', toolbar: '#barDemo'}
            ]]
            , page: true
            , id: 'testReload'
        });

        //监听开关事件
        form.on('switch(stat)', function (data) {
            var contexts;
            var x = data.elem.checked;//判断开关状态
            var data1 = {};
            data1['id'] = this.value;
            if (x === true) {
                contexts = "开启";
                u_status = 1;
                data1['u_status'] = u_status
                $.ajax({
                    url: "<%=request.getContextPath()%>/bg/vue_user_manager/switch",
                    data: data1,
                    type: "Post",
                    dataType: "json",
                    success: function (data) {
                        layer.msg(contexts)
                    },
                    error: function (data) {
                        layer.msg(contexts)
                    }
                });
            } else {
                contexts = "禁用";
                u_status = 0;
                data1['u_status'] = u_status
                $.ajax({
                    url: "<%=request.getContextPath()%>/bg/vue_user_manager/switch",
                    data: data1,
                    type: "Post",
                    dataType: "json",
                    success: function (data) {
                        layer.msg(contexts)
                    },
                    error: function (data) {
                        layer.msg(contexts)
                    }
                });
            }
        });

        //监听行工具事件
        table.on('tool(LAY_table_user)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {
                var data1 = {};
                data1['id'] = data.vid;
                layer.confirm('真的删除行么', function (index) {
                    $.ajax({
                        url: "<%=request.getContextPath()%>/bg/vue_user_manager/delete",
                        data: data1,
                        type: "Post",
                        dataType: "json",
                        success: function (data) {
                            console.log(data)
                            if (data.status === "success") {
                                layer.msg('删除成功！！！', {icon: 1});
                                obj.del();
                            }
                        },
                        error: function (data) {
                            console.log(data)
                            if (data.status === "fail") {
                                layer.msg('删除失败！！！', {icon: 5});
                            }
                        }
                    });
                    layer.close(index);
                });
            } else if (obj.event === 'edit') {
                layer.open({
                    type: 2,
                    title: '修改',
                    shadeClose: true,
                    shade: 0.6,
                    area: ['780px', '500px'],
                    content: '<%=request.getContextPath()%>/bg/vue_user_manager/update?id='+obj.data.vid
                });
                <%--parent.layui.index.openTabsPage("<%=request.getContextPath()%>/bg/user_manager/update?id=" + obj.data.uid, "修改");--%>
            }
        });

        //点击头部按钮操作
        table.on('toolbar(LAY_table_user)', function (obj) {
            if (obj.event === "add") {
                layer.open({
                    type: 2,
                    title: '新建',
                    shadeClose: true,
                    shade: 0.6,
                    area: ['780px', '500px'],
                    content: '<%=request.getContextPath()%>/bg/vue_user_manager/insert'
                });
                <%--parent.layui.index.openTabsPage("<%=request.getContextPath()%>/bg/user_manager/insert", "新建");--%>
            }
        });

        var $ = layui.$, active = {
            reload: function () {
                var demoReload = $('#demoReload');

                //执行重载
                table.reload('testReload', {
                    page: {
                        curr: 1,//重新从第 1 页开始
                        limit: 10
                    }
                    , where: {
                        name: demoReload.val()
                    }
                    , url: "<%=request.getContextPath()%>/bg/vue_user_manager/findListInfo"
                    , method: "post"
                }, 'data');
            }
        };

        $('.demoTable .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
</script>
<script src="<%=request.getContextPath()%>/layuiadmin/js/jquery-3.4.1.min.js"></script>
<script>
    function show_img(t){
        var $ = layui.$;
        var t = $(t).find("img");
        //页面层
        layer.open({
            type: 1,
            title: '图片',
            skin: 'layui-layer-rim', //加上边框
            area: ['780px', '500px'], //宽高 t.width() t.height()
            shadeClose: false, //开启遮罩关闭
            end: function (index, layero) {
                return false;
            },
            content: '<div style="text-align:center"><img src="' + $(t).attr('src') + '" /></div>'
        });
    }
</script>

</body>
</html>