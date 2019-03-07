package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.lengjiabao.general_api.ready.common.AvailabilityStatus
import com.lengjiabao.general_api.ready.common.McAvailabilityStatus
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.util.StringUtil
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.app.sdk.source.RequestSource
// println '1211'

JCLogger logger = LoggerSource.getLogger();
def sid = JC.request.param("sid");
if (StringUtil.isEmpty(sid)) {
	return AvailabilityStatus.notAvailable("请选择门店");
}


def token = null;
JCCookie[] cookies = JC.request.get().getCookies();
logger.info("cookie :" + JackSonBeanMapper.toJson(cookies))
if(cookies!=null&&cookies.length>0) {
	for(JCCookie c : cookies) {
		if(c.getName().equals("_lac_k_")) {
			try {
				token = URLDecoder.decode(c.getValue(), "utf-8");
			}catch(Exception e){
			}
			break;
		}
	}
}

if (!token) {
	token = JC.request.param('token');
	if(!token) {
		return AvailabilityStatus.notAvailable("user_no_login");
	}
}

SimpleAjax simpleAjax = JC.internal.call(SimpleAjax, 'crm', '/h5/p/info', [token:token, pid:GlobalHolder.pid]);

if (simpleAjax == null || !simpleAjax.available || simpleAjax.data == null ) {
	return AvailabilityStatus.notAvailable("user_no_login");
}
def ap_id = simpleAjax.data['ap_id'];//获取用户信息
def mobile = simpleAjax.data['mobile'];//获取用户信息
McAvailabilityStatus status = JC.internal.call(McAvailabilityStatus,'crm', '/h5/user/get_account_mcs', [apid:ap_id.toString(),pid:GlobalHolder.pid.toString(),mobile:mobile]);
if (status.obj != null && status.obj.size() != 0 && !"0".equals(status.obj.get(0).outer_type)) {
	// 如果是外部会员卡
	try { 
		def mc = status.obj.get(0);
		logger.info("CardGradeList___mch_id" + mc.mch_id + " sid=" + mobile+ " sid=" + mobile)
		def  avai = JC.internal.call(McAvailabilityStatus, 'crm', '/h5/mc/get_hierarchy', [mch_id:mc.mch_id,sid:sid,pid:GlobalHolder.pid.toString()]);
		return avai;
	} catch (any) {
		logger.info("",any);
	}
}
String point = "crm";
String address = "/h5/mc/get_hierarchy_list";
List<CommunicationParam> params = new ArrayList<CommunicationParam>();
//CommunicationParam param = new CommunicationParam("key", "value");
//params.add(param);
CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
String ret = systemCaller.doworkAsString(address, params);
// println ret //输入ret到日志
return new Result().setData(ret);

