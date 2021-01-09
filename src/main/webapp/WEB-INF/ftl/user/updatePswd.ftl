<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <title>密码修改 个人中心</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
    <@common.css/>
</head>
<body>
<@common.top/>
<div class="container">
    <div class="row">
        <@common.left 'user' 3/>
        <div class="col-md-10">
            <h2>密码修改</h2>
            <hr>
            <form>
                <div class="form-group">
                    <label for="pswd">原密码</label>
                    <input type="password" class="form-control" autocomplete="off" id="pswd" maxlength="20" name="pswd"
                           placeholder="请输入原密码">
                </div>
                <div class="form-group">
                    <label for="newPswd">新密码</label>
                    <input type="password" class="form-control" autocomplete="off" id="newPswd" maxlength="20"
                           name="newPswd" placeholder="请输入新密码">
                </div>
                <div class="form-group">
                    <label for="reNewPswd">新密码（再输入一次）</label>
                    <input type="password" class="form-control" autocomplete="off" id="reNewPswd" maxlength="20"
                           name="reNewPswd" placeholder="请再次输入新密码">
                </div>
                <div class="form-group">
                    <input type="button" class="btn btn-success">确定修改</input>
                </div>
            </form>

        </div>
    </div><#--/row-->
</div>
<script src="/js/common/jquery/jquery.form-2.82.js?${_v}"></script>
<script>
    function validate(msg, elem){
        let value = $.common.trim(elem.val())
        if(value === ''){
            $.modal.msgError(msg)
            elem.parent().removeClass('has-success').addClass('has-error')
            return false
        }else{
            elem.parent().removeClass('has-error').addClass('has-success')
        }
        return true
    }

    $(function () {
        $('input[type=button]').click(function () {
            let url = '${basePath}/user/updatePswd.shtml'
            let ePassword = $("#pswd")
            let eNewPassword = $("#newPswd")
            let eReNewPassword = $("#reNewPswd")
            let password = $.common.trim(ePassword.val())
            let newPassword = $.common.trim(eNewPassword.val())
            //判断参数
            let isValidity = validate('请输入原密码', ePassword)
            let isNewValidity = validate('请输入新密码', eNewPassword)
            let isReNewValidity = validate('请再次输入新密码', eReNewPassword)

            if(!isValidity || !isNewValidity || !isReNewValidity) return
            if(eReNewPassword.val() !== newPassword){
                $.modal.msgError('两次密码不一致！')
                return
            }
            $.operate.post(url, {}, function (result) {
                $("form :password").val('');
            })
        })
    });
</script>

</body>
</html>