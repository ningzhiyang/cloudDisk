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
         <a><cite>vue文件管理</cite></a>
   </span>
</div>

<div class="layui-fluid">
        <div class="layui-card">

            <div class="layui-card-body" style="margin-top: 15px">
                <table class="layui-hide" id="LAY_table_user" lay-filter="LAY_table_user"></table>

                <script type="text/html" id="tools">
                    <div class="layui-btn-container">
                        <button class="layui-btn layui-btn-sm" lay-event="add">
                            <i class="layui-icon layui-icon-slider" style="margin-left: 3px;"></i>
                        </button>
                    </div>
                </script>
            </div>
        </div>
    </div>

    <script src="<%=request.getContextPath()%>/layuiadmin/layui/layui.js" charset="utf-8"></script>

    <script type="text/html" id="barDemo">
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
                if (d.isDelete === true) {
                    return '删除';
                } else {
                    return '未删除';
                }
            };

            table.render({
                elem: '#LAY_table_user'
                , url: '<%=request.getContextPath()%>/bg/vue_file_manager/vue_file_manager_json'
                , toolbar: '#tools'
                , title: '用户数据表'
                , totalRow: true
                , cols: [[
                    {
                        field: 'fid',
                        title: 'ID',
                        fixed: 'left',
                        unresize: true,
                        sort: true,
                        // totalRowText: '合计'
                    }
                    , {field: 'fname', title: '文件名称'}
                    , {field: 'fext', title: '文件类型'}
                    , {field: 'fpath', title: '文件路径'}
                    , {field: 'fsize', title: '文件大小'}
                    , {field: 'fuploadTime', title: '文件上传时间'}
                    , {field: 'isDelete', title: '文件状态', align: 'center', fixed: 'right', templet: statusTpl}
                    , {fixed: 'right',width:70, title: '操作', fixed: 'right', toolbar: '#barDemo'}
                ]]
                , page: true
                , id: 'testReload'
            });

            //监听行工具事件
            table.on('tool(LAY_table_user)', function (obj) {
                var data = obj.data;
                if (obj.event === 'del') {
                    var data1 = {};
                    data1['id'] = data.fid;
                    layer.confirm('真的删除行么', function (index) {
                        $.ajax({
                            url: "<%=request.getContextPath()%>/bg/vue_file_manager/delete",
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
                }
            });

            //点击头部按钮操作
            table.on('toolbar(LAY_table_user)', function (obj) {
                if (obj.event === "add") {
                    $.post('<%=request.getContextPath()%>/bg/vue_file_manager/filtera',function (data) {
                        if (data.status === 'success'){
                            layer.alert("一键过滤成功！！！")
                        }
                    })
                    <%--parent.layui.index.openTabsPage("<%=request.getContextPath()%>/bg/user_manager/insert", "新建");--%>
                }
            });

            $('.demoTable .layui-btn').on('click', function () {
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });
        });
    </script>
</body>
</html>