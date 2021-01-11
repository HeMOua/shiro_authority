<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${token.nickname} 个人中心</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <@common.css/>
</head>
<body>
<@common.top/>
<div class="container">
    <div class="row">
        <@common.left 'user' 1 />
        <div class="col-md-10">
            <h2>个人资料</h2><hr>
            <table class="table table-bordered">
                <tr>
                    <th>昵称</th>
                    <td>${token.nickname!('未设置')}</td>
                </tr>
                <tr>
                    <th>Email/帐号</th>
                    <td>${token.email!('未设置')}</td>
                </tr>
                <tr>
                    <th>创建时间</th>
                    <td>${token.createTime?string('yyyy-MM-dd HH:mm')}</td>
                </tr>
                <tr>
                    <th>最后登录时间</th>
                    <td>${token.lastLoginTime?string('yyyy-MM-dd HH:mm')}</td>
                </tr>
            </table>
        </div>
    </div>
</div>
<@common.js/>
</body>
</html>