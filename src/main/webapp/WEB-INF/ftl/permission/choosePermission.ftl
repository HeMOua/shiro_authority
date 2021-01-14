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
<div class="container" data-rid="${rid}">
    <#list permissions! as permis>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="${permis.id}"> ${permis.name}
            </label>
        </div>
    </#list>
</div>
<script>
    $(function () {
        util.initCheckStatus('${pids}')
    })

    function submitHandler() {
        let rid = $('.container').data('rid')
        if($.common.isEmpty(rid)) {
            $.modal.msgError('系统错误，请刷新后尝试！')
            return
        }
        $.operate.save('${basePath}/permission/allocPermission.shtml', {
            pids: util.checkedIdList($('input:checkbox:checked')).join(','), rid
        })
    }
</script>
</body>
</html>