<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <title>当前在线Session — SSM + Shiro Demo</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
    <@common.css/>
    <@common.js/>
</head>
<body>
<@common.top/>
<div class="container">
    <div class="row">
        <@common.left 'member' 2 />
        <div class="col-md-10">
            <h2>当前在线用户</h2>
            <hr>
            <table class="table table-bordered">
                <tr>
                    <th>SessionID</th>
                    <th>昵称</th>
                    <th>Email/帐号</th>
                    <th>创建回话</th>
                    <th>回话最后活动</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                <#list users! as it>
                    <tr>
                        <td>${it.sessionId!('未设置')}</td>
                        <td>${it.nickname!('未设置')}</td>
                        <td>${it.email!('未设置')}</td>
                        <td>${it.startTime?string('yy-MM-dd HH:mm:ss')}</td>
                        <td>${it.lastAccess?string('yy-MM-dd HH:mm:ss')}</td>
                        <td>${(it.sessionStatus)?string('有效','已踢出')}</td>
                        <td>
                            <a href="${basePath}/member/onlineDetails/${it.sessionId}.shtml">详情</a>

                            <@shiro.hasPermission name="user:kickout">
                                <a href="javascript:" data-status="${it.sessionStatus?string('false','true')}" data-id="${it.sessionId}">
                                    ${(it.sessionStatus)?string('踢出','激活')}
                                </a>
                            </@shiro.hasPermission>
                        </td>
                    </tr>
                </#list>
                <#if users?? && users?size == 0>
                    <tr>
                        <td class="center-block" colspan="4">没有用户</td>
                    </tr>
                </#if>

            </table>
        </div>
    </div><#--/row-->
</div>
<script>
    $(function () {
        $('a[data-status]').on('click', function () {
            let status = $(this).data('status')
            let sessionId = $(this).data('id')
            let msg = '确定'+ $(this).text() +'该用户吗？'
            $.modal.confirm(msg, function () {
                $.operate.post('${basePath}/member/changeSessionStatus.shtml', {status, sessionId})
            })
        })
    })
</script>
</body>
</html>