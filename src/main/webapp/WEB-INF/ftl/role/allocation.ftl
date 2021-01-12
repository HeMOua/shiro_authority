<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <title>用户角色分配 - 权限管理</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
    <@common.css/>
    <@common.js/>
</head>
<body>
<@common.top/>
<div class="container">
    <div class="row">
        <@common.left 'auth' 2 />
        <div class="col-md-10">
            <h2>用户角色分配</h2>
            <hr>
            <form id="searchForm" action="${basePath}/role/allocation.shtml" class="form-inline">
                <div class="form-group">
                    <input type="text" class="form-control" value="${search!}" name="search"
                           placeholder="输入用户昵称 / 用户帐号">
                </div>
                <button class="btn btn-primary">查询</button>
                <input type="button" class="btn btn-danger" id="btnDeleteAll" value="清空用户角色">
            </form>
            <hr>

            <table class="table table-bordered">
                <input type="hidden" id="selectUserId">
                <tr>
                    <th width="5%"><input type="checkbox" id="checkAll"/></th>
                    <th width="10%">用户昵称</th>
                    <th width="10%">Email/帐号</th>
                    <th width="10%">状态</th>
                    <th>拥有的角色</th>
                    <th width="12%">操作</th>
                </tr>
                <#list users! as it>
                    <tr>
                        <td><input value="${it.id}" check='box' type="checkbox"/></td>
                        <td>${it.nickname}</td>
                        <td>${it.email}</td>
                        <td>${(it.status==1)?string('有效','禁止')}</td>
                        <td data-ids="<@common.toIdsString it.roleList/>" uid="${it.id}">
                            <@common.toNamesString it.roleList/>
                        </td>
                        <td>
                            <i class="glyphicon glyphicon-share-alt"></i>
                            <a href="javascript:selectRole(${it.id});">选择角色</a>
                        </td>
                    </tr>
                </#list>
                <#if users?? && users?size == 0>
                    <tr>
                        <td class="text-center" colspan="6">没有找到用户</td>
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

        })
    })

    function selectRole(uid) {
        let rids = $.common.trim($('td[uid='+ uid +']').data('ids'))
        $.operate.add('角色至用户', '${basePath}/role/chooseRole.shtml?rids=' + rids)
    }
</script>
</body>
</html>