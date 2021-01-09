
//换种方式获取，之前的方式在不同的环境下，有问题
var baseUrl = $("script[baseUrl]").attr('baseUrl');



/**退出*/
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
