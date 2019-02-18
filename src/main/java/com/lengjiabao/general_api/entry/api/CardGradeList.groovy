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
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.app.sdk.source.RequestSource
// println '1211'

JCLogger logger = LoggerSource.getLogger();
SimpleAjax simpleAjax = JC.internal.call(SimpleAjax, 'crm', '/h5/p/info', [token:GlobalHolder.getToken(), pid:GlobalHolder.pid]);

if (simpleAjax == null || !simpleAjax.available || simpleAjax.data == null ) {
	return AvailabilityStatus.notAvailable("user_no_login");
}
def ap_id = simpleAjax.data['ap_id'];//获取用户信息
def mobile = simpleAjax.data['mobile'];//获取用户信息
McAvailabilityStatus status = JC.internal.call(McAvailabilityStatus,'crm', '/h5/user/get_account_mcs', [apid:ap_id.toString(),pid:GlobalHolder.pid.toString(),mobile:mobile]);
logger.info("CardGradeList__" + JackSonBeanMapper.toJson(status) + "__"+ap_id + "__"+mobile)
if (status.obj != null && status.obj.size() != 0 && !"0".equals(status.obj.get(0).outer_type)) {
	// 如果是外部会员卡
	try {
		def mc = status.obj.get(0);
		AvailabilityStatus  avai = JC.internal.call(AvailabilityStatus, 'crm', '/h5/mc/get_hierarchy', [mch_id:mc.mch_id]);
		if (!savai.available) {
			return avai;
		}
		def item = status.obj.get(0);
		item.getpay =  item.least_recharge;
		def list =  [];
		list.add(item);
		avai = AvailabilityStatus.available(null, list);
		logger.info("CardGradeList__" + JackSonBeanMapper.toJson(avai))
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

