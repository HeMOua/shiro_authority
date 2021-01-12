<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <title>权限列表 - 权限管理</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
    <@common.css/>
    <@common.js/>
</head>
<body>
<@common.top/>
<div class="container">
    <div class="row">
        <@common.left 'auth' 3 />
        <div class="col-md-10">
            <h2>权限列表</h2>
            <hr>
            <form id="searchForm" action="${basePath}/permission/index.shtml" class="form-inline">
                <div class="form-group">
                    <input type="text" class="form-control" value="${search!}" name="search" placeholder="输入权限名称">
                </div>
                <button class="btn btn-primary">查询</button>
                <input type="button" class="btn btn-success" id="btnAdd" value="增加权限">
                <input type="button" class="btn btn-danger" id="btnDeleteAll" value="删除">
            </form>
            <hr>

            <table class="table table-bordered">
                <tr>
                    <th><input type="checkbox" id="checkAll"/></th>
                    <th>权限名称</th>
                    <th>角色类型</th>
                    <th>操作</th>
                </tr>
                <#list list! as it>
                    <tr>
                        <td><input value="${it.id}" check='box' type="checkbox"/></td>
                        <td>${it.name!('-')}</td>
                        <td>${it.url!('-')}</td>
                        <td>
                            <i class="glyphicon glyphicon-remove"></i>
                            <a href="javascript:deleteById(${it.id});">删除</a>
                        </td>
                    </tr>
                </#list>
                <#if  list?? && list?size == 0>
                    <tr>
                        <td class="text-center" colspan="4">没有找到权限</td>
                    </tr>
                </#if>
            </table>

            <#include '../common/config/page.ftl'/>
        </div>
    </div><#--/row-->
</div>
<script>
    $(function () {
        util.initCheckBox('table.table')

        $('#btnAdd').on('click', function () {
            $.operate.add('权限', '${basePath}/permission/add.shtml', 500)
        })

        $('#btnDeleteAll').on('click', function () {
            $.operate.removeAll('${basePath}/permission/deletePermission.shtml', util.checkedIdList())
        })
    })

    function deleteById(id) {
        $.operate.remove('${basePath}/permission/deletePermission.shtml', id)
    }
</script>
</body>
</html>