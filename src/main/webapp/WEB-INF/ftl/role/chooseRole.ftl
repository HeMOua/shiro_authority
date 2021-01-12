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
<div class="container">
    <#list roles! as role>
        <div class="checkbox">
            <label>
                <input type="checkbox" data-id="${role.id}"> ${role.name}
            </label>
        </div>
    </#list>
</div>
<script>
    $(function () {
        util.initCheckStatus(${rids})
    })

    function submitHandler() {

    }
</script>

</body>
</html>