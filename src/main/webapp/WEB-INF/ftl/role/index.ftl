<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <title>角色列表 - 权限管理</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
    <@common.css/>
    <@common.js/>
</head>
<body>
<@common.top/>
<div class="container">
    <div class="row">
        <@common.left 'auth' 1 />
        <div class="col-md-10">
            <h2>角色列表</h2>
            <hr>
            <form id="searchForm" action="${basePath}/role/index.shtml" class="form-inline">
                <div class="form-group">
                    <input type="text" class="form-control" value="${search!}" name="search"
                           placeholder="输入角色类型 / 角色名称">
                </div>
                <button class="btn btn-primary">查询</button>
                <input type="button" class="btn btn-success" id="btnAdd" value="增加角色">
                <input type="button" class="btn btn-danger" id="btnDeleteAll" value="删除">
            </form>
            <hr>

            <form method="post" action="" id="formId" class="form-inline">
                <table class="table table-bordered">
                    <tr>
                        <th><input type="checkbox" id="checkAll"/></th>
                        <th>角色名称</th>
                        <th>角色类型</th>
                        <th>操作</th>
                    </tr>
                    <#list roles as it>
                        <tr>
                            <td><input value="${it.id}" check='box' type="checkbox"/></td>
                            <td>${it.name!('-')}</td>
                            <td>${it.type!('-')}</td>
                            <td>
                                <i class="glyphicon glyphicon-remove"></i>
                                <a href="javascript:deleteById(${it.id});">删除</a>
                            </td>
                        </tr>
                    </#list>
                    <#if  roles?? && roles?size == 0>
                        <tr>
                            <td class="text-center" colspan="6">没有找到角色</td>
                        </tr>
                    </#if>
                </table>
            </form>

            <#include '../common/config/page.ftl'/>
        </div>
    </div><#--/row-->

</div>
<script>
    $(function () {
        util.initCheckBox('table.table')

        $('#btnAdd').on('click', function () {
            $.operate.add('角色', '${basePath}/role/add.shtml', 500)
        })

        $('#btnDeleteAll').on('click', function () {
            let checked = $('table.table').find('input:checkbox:checked:not([id])')
            $.operate.removeAll('${basePath}/role/deleteRole.shtml', util.checkedIdList(checked))
        })
    })

    function deleteById(id) {
        $.operate.remove('${basePath}/role/deleteRole.shtml', id)
    }
</script>

</body>
</html>