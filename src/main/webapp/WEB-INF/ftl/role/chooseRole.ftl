<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <@common.css/>
    <@common.js/>
</head>
<body>
<div class="container" data-uid="${uid}">
    <#list roles! as role>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="${role.id}"> ${role.name}
            </label>
        </div>
    </#list>
</div>
<script>
    $(function () {
        util.initCheckStatus('${rids}')
    })

    function submitHandler() {
        let uid = $('.container').data('uid')
        if($.common.isEmpty(uid)) {
            $.modal.msgError('系统错误，请刷新后尝试！')
            return
        }
        $.operate.save('${basePath}/role/allocRole.shtml', {
            rids: util.checkedIdList($('input:checkbox:checked')).join(','), uid
        })
    }
</script>

</body>
</html>