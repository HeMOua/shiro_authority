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

        util.checkedIdList = function (checked = 'input:checked:checkbox:not([id])', parent = 'table.table') {
            let select = $(parent).find(checked);
            let ids = []
            for(let i = 0; i < select.length; i++){
                let element = $(select[i]);
                ids.push(element.val())
            }
            return ids
        }

        util.initCheckStatus = function (ids) {
            if($.common.isEmpty(ids)) return
            let idList = ids.split(',')
            $('input:checkbox').each(function () {
                let id = $(this).data('id')
                if(idList.contains(id)) $(this).prop('checked', true)
            })
        }
    })(util)
})($, window)