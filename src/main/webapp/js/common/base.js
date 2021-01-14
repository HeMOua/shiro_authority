//换种方式获取，之前的方式在不同的环境下，有问题
var baseUrl = $("script[baseUrl]").attr('baseUrl');

function logout(){
    $.operate.get(baseUrl + '/u/logout.shtml',function(result){
        if(result && result.status === resp_status.SUCCESS){
            $(".qqlogin").html('').next('ul').remove();
            setTimeout(function () {
                window.location.reload(true);
            }, 1000);
        }
    });
}

;(function ($, w) {
    if(!w.util)w.util = {}
    return (function (util) {
        util.initCheckBox = function (parent, checkAll = '#checkAll', otherCheck = 'input:checkbox:not([id])') {
            $(checkAll).on('change', function () {
                $(otherCheck).prop('checked', $(this).is(':checked'))
            })

            $(otherCheck).on('change', function () {
                let total = $(parent).find('input:checkbox').length - 1
                let select = $(parent).find(otherCheck + ':checked').length
                $(checkAll).prop('checked', total === select)
            })
        }

        util.checkedIdList = function (checked) {
            let ids = []
            for(let i = 0; i < checked.length; i++){
                ids.push(checked.eq(i).val())
            }
            return ids
        }

        util.initCheckStatus = function (ids) {
            if($.common.isEmpty(ids)) return
            let idList = ids.toString().split(',')
            $('input:checkbox').each(function () {
                let id = $(this).val()
                if(idList.indexOf(id + '') !== -1) $(this).prop('checked', true)
            })
        }
    })(util)
})($, window)