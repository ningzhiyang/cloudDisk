// 初始化插件

// 全局保存当前选中窗口
var g_iWndIndex = 0; //可以不用设置这个变量，有窗口参数的接口中，不用传值，开发包会默认使用当前选择窗口
$(function () {
	// 检查插件是否已经安装过
	if (-1 == WebVideoCtrl.I_CheckPluginInstall()) {
		alert("您还未安装过插件，双击开发包目录里的WebComponents.exe安装！");
		return;
	}

	// 初始化插件参数及插入插件
	WebVideoCtrl.I_InitPlugin(1450, 800, {
		iWndowType: 3,
		cbSelWnd: function (xmlDoc) {
			g_iWndIndex = $(xmlDoc).find("SelectWnd").eq(0).text();
			var szInfo = "当前选择的窗口编号：" + g_iWndIndex;
			alert(szInfo);
		}
	});
	WebVideoCtrl.I_InsertOBJECTPlugin("divPlugin");

	// 检查插件是否最新
	if (-1 == WebVideoCtrl.I_CheckPluginVersion()) {
		alert("检测到新的插件版本，双击开发包目录里的WebComponents.exe升级！");
		return;
	}
});
// 登录
function clickLogin() {
	var szIP = $("#loginip").val(),
		szPort = $("#port").val(),
		szUsername = $("#username").val(),
		szPassword = $("#password").val();

	if ("" == szIP || "" == szPort) {
		return;
	}

	var iRet = WebVideoCtrl.I_Login(szIP, 1, szPort, szUsername, szPassword, {
		success: function (xmlDoc) {
			alert(szIP + " 登录成功！");
			$("#ip").prepend("<option value='" + szIP + "'>" + szIP + "</option>");
			setTimeout(function () {
				$("#ip").val(szIP);
				//getChannelInfo();
			}, 10);
		},
		error: function () {
			alert(szIP + " 登录失败！");
		}
	});

	if (-1 == iRet) {
		alert(szIP + " 已登录过！");
	}
}

// 开始预览
function clickStartRealPlay() {
	var oWndInfo = WebVideoCtrl.I_GetWindowStatus(g_iWndIndex),
		szIP = $("#ip").val();

	if ("" == szIP) {
		return;
	}

	if (oWndInfo != null) {// 已经在播放了，先停止
		WebVideoCtrl.I_Stop();
	}

	var iRet = WebVideoCtrl.I_StartRealPlay(szIP,{iWndIndex:8});
	if (0 == iRet) {
		szInfo = "开始预览成功！";
	} else {
		szInfo = "开始预览失败！";
	}

	alert(szIP + " " + szInfo);
}

// 获取通道
function getChannelInfo() {
	var szIP = $("#ip").val(),
		oSel = $("#channels").empty(),
		nAnalogChannel = 0;

	if ("" == szIP) {
		return;
	}

	// 模拟通道
	WebVideoCtrl.I_GetAnalogChannelInfo(szIP, {
		async: false,
		success: function (xmlDoc) {
			var oChannels = $(xmlDoc).find("VideoInputChannel");
			nAnalogChannel = oChannels.length;

			$.each(oChannels, function (i) {
				var id = parseInt($(this).find("id").eq(0).text(), 10),
					name = $(this).find("name").eq(0).text();
				if ("" == name) {
					name = "Camera " + (id < 9 ? "0" + id : id);
				}
				oSel.append("<option value='" + id + "' bZero='false'>" + name + "</option>");
			});
			alert(szIP + " 获取模拟通道成功！");
		},
		error: function () {
			alert(szIP + " 获取模拟通道失败！");
		}
	});
	// 数字通道
	WebVideoCtrl.I_GetDigitalChannelInfo(szIP, {
		async: false,
		success: function (xmlDoc) {
			var oChannels = $(xmlDoc).find("InputProxyChannelStatus");

			$.each(oChannels, function (i) {
				var id = parseInt($(this).find("id").eq(0).text(), 10),
					name = $(this).find("name").eq(0).text(),
					online = $(this).find("online").eq(0).text();
				if ("false" == online) {// 过滤禁用的数字通道
					return true;
				}
				if ("" == name) {
					name = "IPCamera " + ((id - nAnalogChannel) < 9 ? "0" + (id - nAnalogChannel) : (id - nAnalogChannel));
				}
				oSel.append("<option value='" + id + "' bZero='false'>" + name + "</option>");
			});
			alert(szIP + " 获取数字通道成功！");
		},
		error: function () {
			alert(szIP + " 获取数字通道失败！");
		}
	});
	// 零通道
	WebVideoCtrl.I_GetZeroChannelInfo(szIP, {
		async: false,
		success: function (xmlDoc) {
			var oChannels = $(xmlDoc).find("ZeroVideoChannel");

			$.each(oChannels, function (i) {
				var id = parseInt($(this).find("id").eq(0).text(), 10),
					name = $(this).find("name").eq(0).text();
				if ("" == name) {
					name = "Zero Channel " + (id < 9 ? "0" + id : id);
				}
				if ("true" == $(this).find("enabled").eq(0).text()) {// 过滤禁用的零通道
					oSel.append("<option value='" + id + "' bZero='true'>" + name + "</option>");
				}
			});
			alert(szIP + " 获取零通道成功！");
		},
		error: function () {
			alert(szIP + " 获取零通道失败！");
		}
	});
}


// 全屏
function clickFullScreen() {
	WebVideoCtrl.I_FullScreen(true);
}