package com.lengjiabao.general_api.entry.api.auth


import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.QiniuSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.power.support.QiniuUploadResult
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.AvailabilityStatus
import com.lengjiabao.general_api.ready.dto.AccountInfo
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.util.MD5Util
import com.lengjiabao.general_api.ready.util.NativeUtil
import com.lengjiabao.general_api.ready.util.RemoteUtil

import org.apache.commons.fileupload.FileItem;


JCLogger  Logger = JCLoggerFactory.getLogger(this.getClass())
try { 
	JCRequest req = JC.request.get();
	def name = req.getParameter("name");
	def head = req.getParameter("head");
	if (name == null || name.equals("")) {
		return AvailabilityStatus.notAvailable("昵称不能为空");
	}
	def  accountInfo = req.getAttribute("_user_");//获取用户信息
	def msg = NativeUtil.connect("crm", "h5/user/update_account", ['ap_id':accountInfo['ap_id'].toString(),'head':head,'name':name])
	println "update____" + msg;
	return msg;
} catch (e) {
	Logger.error("修改资料失败", e);
	return new Result().setData(AvailabilityStatus.notAvailable("修改资料失败"))
}




def nextInt(final int min, final int max){
	Random rand= new Random();
	int tmp = Math.abs(rand.nextInt());
	return tmp % (max - min + 1) + min;
}
