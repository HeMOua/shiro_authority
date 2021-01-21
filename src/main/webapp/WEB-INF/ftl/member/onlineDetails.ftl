<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <title>Session详情 — SSM + Shiro Demo</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
    <@common.css/>
    <@common.js/>
</head>
<body>
<@common.top/>
<div class="container">
    <div class="row">
        <@common.left 'member' 2/>
        <div class="col-md-10">
            <h2>Session详情</h2>
            <hr>
            <table class="table table-bordered">
                <tr>
                    <th>Session Id</th>
                    <td>${bo.sessionId?default('—')}</td>
                </tr>
                <tr>
                    <th>Session创建时间</th>
                    <td>${bo.startTime?string('yyyy年MM月dd日 HH:mm:ss')}</td>
                </tr>
                <tr>
                    <th>Session最后交互时间</th>
                    <td>${bo.lastAccess?string('yyyy年MM月dd日 HH:mm:ss')}</td>
                </tr>
                <tr>
                    <th>Session 状态</th>
                    <td>${(bo.sessionStatus)?string('有效','已踢出')}</td>
                </tr>
                <tr>
                    <th>Session Host</th>
                    <td>${bo.host!('—')}</td>
                </tr>
                <tr>
                    <th>Session timeout</th>
                    <td>${bo.timeout}&nbsp;(毫秒) = ${bo.timeout/1000}(秒) = ${bo.timeout/1000/60}(分钟)</td>
                </tr>
                <tr>
                    <th>昵称</th>
                    <td>${bo.nickname!('—')}</td>
                </tr>
                <tr>
                    <th>Email/帐号</th>
                    <td>${bo.email!('—')}</td>
                </tr>
                <tr>
                    <th>创建时间</th>
                    <td>${bo.createTime?string('yyyy-MM-dd HH:mm')}</td>
                </tr>
                <tr>
                    <th>最后登录时间</th>
                    <td>${bo.lastLoginTime?string('yyyy-MM-dd HH:mm')}</td>
                </tr>
            </table>
        </div>
    </div><#--/row-->
</div>

</body>
</html>