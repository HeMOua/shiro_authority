<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <title>用户列表 —个人中心</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
    <@common.css/>
    <@common.js/>
</head>
<body>
<@common.top/>
<div class="container">
    <div class="row">
        <@common.left 'member' 1 />
        <div class="col-md-10">
            <h2>用户列表</h2>
            <hr>
            <form id="searchForm" action="${basePath}/member/list.shtml" class="form-inline">
                <div class="form-group">
                    <input type="text" class="form-control" value="${search!}" name="search" placeholder="输入昵称 / 帐号">
                </div>
                <button class="btn btn-primary">查询</button>
                <input type="button" id="btnDeleteAll" class="btn btn-danger" value="删除">
            </form>

            <hr>
            <table class="table table-bordered">
                <tr>
                    <th><input type="checkbox" id="checkAll"/></th>
                    <th>昵称</th>
                    <th>Email/帐号</th>
                    <th>登录状态</th>
                    <th>创建时间</th>
                    <th>最后登录时间</th>
                    <th>操作</th>
                </tr>
                <#list users! as it>
                    <tr>
                        <td><input value="${it.id}" type="checkbox"/></td>
                        <td>${it.nickname!('未设置')}</td>
                        <td>${it.email!('未设置')}</td>
                        <td>${(it.status==1)?string('有效','禁止')}</td>
                        <td>${it.createTime?string('yyyy-MM-dd HH:mm')}</td>
                        <td>${it.lastLoginTime?string('yyyy-MM-dd HH:mm')}</td>
                        <td>
                            ${(it.status==1)?string('<i class="glyphicon glyphicon-eye-close"></i>&nbsp;','<i class="glyphicon glyphicon-eye-open"></i>&nbsp;')}
                            <a href="javascript:forbidUserById(${(it.status==1)?string('0','1')},${it.id})">
                                ${(it.status==1)?string('禁止登录','激活登录')}
                            </a>
                            <a href="javascript:deleteUserById(${it.id});">删除</a>
                        </td>
                    </tr>
                </#list>
                <#if !users??>
                    <tr>
                        <td class="text-center danger" colspan="6">没有找到用户</td>
                    </tr>
                </#if>
            </table>
            <#include '../common/config/page.ftl'/>
        </div>
    </div><#--/row-->
</div>
<script>
    function forbidUserById(status, id){
        $.operate.post('${basePath}/member/forbidUserById.shtml', {status, id}, function (result) {
            if(result.status === resp_status.SUCCESS){
                $.modal.delayReload()
            }
        })
    }
    function deleteUserById(id){
        $.operate.post('${basePath}/member/deleteUserById.shtml', {id}, function (result) {
            if(result.status === resp_status.SUCCESS){
                $.modal.delayReload()
            }
        })
    }

    $(function () {
        $('#searchForm').submit(function () {
            let search = $.common.trim($('input[name="search"]').val())
            if($.common.isEmpty(search)){
                $.modal.msgError('搜索内容不可为空！')
                return false
            }
        })
    })
</script>
</body>
</html>