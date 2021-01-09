<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <title>资料修改 个人中心</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
    <@common.css/>
</head>
<body>
<@common.top/>
<div class="container">
    <div class="row">
        <@common.left 'user' 2/>
        <div class="col-md-10">
            <h2>资料修改</h2><hr>
            <form>
                <input type="hidden" id="id" value="${token.id}">
                <div class="form-group">
                    <label for="nickname">昵称</label>
                    <input type="text" class="form-control" autocomplete="off" id="nickname" maxlength="8" name="nickname"
                           value="${token.nickname?default('未设置')}" placeholder="请输入昵称">
                </div>
                <div class="form-group">
                    <label for="email">Email（不准修改）</label>
                    <input type="text" class="form-control " disabled autocomplete="off" id="email" maxlength="64"
                           name="email" value="${token.email?default('未设置')}" placeholder="请输入帐号">
                </div>
                <div class="form-group">
                    <input class="btn btn-success" type="button" value="确定修改">
                </div>
            </form>
        </div>
    </div>
</div>

<@common.js/>
<script>
    $(function () {
        $('input[type=button]').click(function () {
            let id = $('#id').val()
            let nickname = $('#nickname').val()
            if($.common.isEmpty(nickname)){
                $.modal.msgError('昵称不能为空！')
                $("#nickname").parent().removeClass('has-success').addClass('has-error')
            }else{
                $.operate.post('${basePath}/user/update.shtml', {id, nickname}, function (result) {
                    setTimeout(function () {
                        $.modal.reload()
                    }, 1000)
                })
            }
        })
    });
</script>

</body>
</html>