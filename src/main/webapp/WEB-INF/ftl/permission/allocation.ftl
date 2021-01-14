<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <title>权限分配 - 权限管理</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
    <@common.css/>
    <@common.js/>
</head>
<body>
<@common.top/>
<div class="container">
    <div class="row">
        <@common.left 'auth' 4 />
        <div class="col-md-10">
            <h2>权限分配</h2>
            <hr>
            <form id="searchForm" action="${basePath}/permission/allocation.shtml" class="form-inline">
                <div class="form-group">
                    <input type="text" class="form-control" value="${search!}" name="search"
                           placeholder="输入角色类型 / 角色名称">
                </div>
                <button class="btn btn-primary">查询</button>
                <input type="button" class="btn btn-danger" id="btnDeleteAll" value="清空角色权限">
            </form>
            <hr>

            <table class="table table-bordered">
                <input type="hidden" id="selectRoleId">
                <tr>
                    <th width="5%"><input type="checkbox" id="checkAll"/></th>
                    <th width="10%">角色名称</th>
                    <th width="10%">角色类型</th>
                    <th>拥有的权限</th>
                    <th width="12%">操作</th>
                </tr>
                <#list roles! as it>
                    <tr>
                        <td><input value="${it.id}" check='box' type="checkbox"/></td>
                        <td>${it.name}</td>
                        <td>${it.type}</td>
                        <td data-ids="<@common.toIdsString it.permissionList/>" rid="${it.id}">
                            <@common.toNamesString it.permissionList/>
                        </td>
                        <td>
                            <i class="glyphicon glyphicon-share-alt"></i>
                            <a href="javascript:selectPermission(${it.id});">选择权限</a>
                        </td>
                    </tr>
                </#list>
                <#if  roles?? && roles?size == 0>
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

        $('#btnDeleteAll').on('click', function () {
            let checked = $('table.table').find('input:checkbox:checked:not([id])')
            $.operate.removeAll('${basePath}/permission/cancelPermission.shtml', util.checkedIdList(checked))
        })
    })

    function selectPermission(rid) {
        let pids = $.common.trim($('td[rid='+ rid +']').data('ids'))
        $.operate.add('权限至角色', '${basePath}/permission/choosePermission.shtml?rid='+ rid +'&pids=' + pids, '', 300)
    }
</script>
</body>
</html>