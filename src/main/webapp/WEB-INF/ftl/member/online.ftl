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
                <#list list! as it>
                    <tr>
                        <td>${it.sessionId!('未设置')}</td>
                        <td>${it.nickname!('未设置')}</td>
                        <td>${it.email!('未设置')}</td>
                        <td>${it.startTime?string('HH:mm:ss yy-MM-dd')}</td>
                        <td>${it.lastAccess?string('HH:mm:ss yy-MM-dd')}</td>
                        <td>${(it.sessionStatus)?string('有效','已踢出')}</td>
                        <td>
                            <a href="${basePath}/member/onlineDetails/${it.sessionId}.shtml">详情</a>

                            <a v="onlineDetails" href="javascript:void(0);" sessionId="${it.sessionId}"
                               status="${(it.sessionStatus)?string(1,0)}">
                                ${(it.sessionStatus)?string('踢出','激活')}
                            </a>

                        </td>
                    </tr>
                </#list>
                <#if !list??>
                    <tr>
                        <td class="center-block" callspan="4">没有用户</td>
                    </tr>
                </#if>

            </table>
        </div>
    </div><#--/row-->
</div>

</body>
</html>