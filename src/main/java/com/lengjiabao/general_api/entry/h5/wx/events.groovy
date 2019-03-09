package com.lengjiabao.general_api.entry.h5.wx

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.util.XMLUtil

JCLogger logger = LoggerSource.getLogger();
// 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
String signature = JC.request.param("signature");
// 时间戳
String timestamp = JC.request.param("timestamp");
// 随机数
String nonce = JC.request.param("nonce");
// 随机字符串
String echostr = JC.request.param("echostr");

if(signature && timestamp && nonce && echostr) {
	logger.info('return success');
	return echostr;
}

//处理post请求

def inputStream = JC.request.get().getInputStream()

StringBuilder sb = new StringBuilder();
String line;
 
BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
while ((line = br.readLine()) != null) {
    sb.append(line);
}
String str = sb.toString();
logger.info('wx_notify_msg:' + str);
if(!str) {
	return 'empty';
}
XMLUtil xml_util = new XMLUtil();
Map<String, String> requestMap = xml_util.to_map(str);

// 发送方帐号（open_id）
String fromUserName = requestMap.get("FromUserName");
// 公众帐号
String toUserName = requestMap.get("ToUserName");
// 消息类型
String msgType = requestMap.get("MsgType");
//消息内容
String content = requestMap.get("Content");

if (msgType.equals('event')) {
	//事件类型
	String eventType = requestMap.get("Event");
	logger.info("msg_type=======" + msgType + "--" + eventType);
	if (eventType.equals('subscribe')) {
		// 订阅消息
		// 处理新用户关注消息
	} else if (eventType.equals('unsubscribe')) {
		// 取消订阅
		// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
	} else if (eventType.equals('CLICK')) {
		// 自定义菜单点击事件
		// TODO 暂时不处理菜单点击消息
	} else if (eventType.equals('LOCATION')) {
		//上报地理位置事件
	} else if(eventType.equals('user_get_card')){
		//用户领取券，服务器收到的通知
		logger.info("wx:user_get_card:"+JackSonBeanMapper.toJson(requestMap));
		try{
			String openid = requestMap.get("FromUserName");				//领用人的openid
			String card_id = requestMap.get("CardId");					//wx_card_id
			String userCardCode = requestMap.get("UserCardCode");		//coupon_code
			String IsGiveByFriend = requestMap.get('IsGiveByFriend');	//是否为转赠领取，1代表是，0代表否。
			String FriendUserName = requestMap.get('FriendUserName');	//当IsGiveByFriend为1时填入的字段，表示发起转赠用户的openid
			
			logger.info('card_id=' + card_id + ', card_code=' + userCardCode);
			
			//通知market重置卡券归属
			JC.internal.call('market', '/wx/trans_cb', [wx_card_id:card_id, card_code:userCardCode, get_partid:openid, from_partid:FriendUserName, pid:GlobalHolder.pid]);
		}catch(Exception e){
			logger.error("update wx card code info error",e);
		}
	} else if(eventType.equals('user_gifting_card')) {
		//用户产生赠送券给朋友，服务器收到的通知
		logger.info("user_gifting_card:"+JackSonBeanMapper.toJson(requestMap));
		//转增行为不需要处理
		
	}
}


