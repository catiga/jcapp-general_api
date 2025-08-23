package com.lengjiabao.general_api.entry.api.gate

import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.lengjiabao.general_api.ready.util.DataUtils

import java.text.SimpleDateFormat
import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

// qk=44017401so-341371,358802,717  说明 影城编码 so- 取票码1，取票码2 随机数
def pid = GlobalHolder.pid;
def qk = JC.request.param('qk');
def modify_status = JC.request.param('modify_status');

def originalQk = qk;

JCLogger logger = JCLoggerFactory.getLogger("query ticket:");

//if (!qk.startsWith("so-")) {
if (!qk.matches("^([0-9]{8})?so-.*")) {
    //return new RespModel(errorCode: 901001, errorMessage: DataUtils.toUnicode("无效的自营快捷入场码"), status: 0)

    RespModel errMod = new RespModel(errorCode: 901001, errorMessage: DataUtils.toUnicode("无效的自营快捷入场码"), status: 0, data: null);

    return new RespWrapper(res: errMod);
}

//qk = qk.substring("so-".length())
def soIndex = qk.indexOf("so-")
if (soIndex == -1) {
    // 说明格式不合法
    RespModel errMod = new RespModel(
            errorCode: 901001,
            errorMessage: DataUtils.toUnicode("无效的自营快捷入场码"),
            status: 0,
            data: null
    )
    return new RespWrapper(res: errMod)
}
qk = qk.substring(soIndex + "so-".length())
try {
    qk = qk.split(",")
    qk = qk[0] + "," + qk[1]
} catch (e) {
    RespModel errMod = new RespModel(errorCode: 512404, errorMessage: DataUtils.toUnicode("电子票无效"), status: 0, data: null);
    return new RespWrapper(res: errMod);
}

//以取票码查询
SimpleAjax query_result = JC.internal.call(SimpleAjax, 'ticketingsys', '/ticketing/take_by_code_for_direct_check_split', [get_code:qk, pid:pid, modify_status:modify_status]);

logger.info("query ticket by flag, qk: {}, result: {}", qk, JackSonBeanMapper.toJson(query_result));

def ret_data = null;

if(query_result && query_result.available) {
    ret_data = query_result.data;
}

if (ret_data == null) {
    RespModel errMod = new RespModel(errorCode: 512404, errorMessage: DataUtils.toUnicode(query_result.messages[1]), status: 0, data: null);
    return new RespWrapper(res: errMod);
}

//增加是否过场标志

SimpleDateFormat _sdf_ymdhms_ = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss');
for (x in ret_data[1]) {
    def pass_flag = 0;    //默认未过场
    def play_time = x['plan_date'] + ' ' + x['plan_time'];
    try {
        pass_flag = _sdf_ymdhms_.parse(play_time).before(Calendar.getInstance().getTime());
    } catch (any) {
    }
    x['pass_flag'] = pass_flag;
}

try {
    def orderData = ret_data[0];
    def orderSeats = ret_data[1];
    def cinemaHall = ret_data[2];
    def screen_code = orderData["hall_id"];
    if (cinemaHall != null) {
        screen_code = cinemaHall['screen_code'];
    }


    RespTicket respData = new RespTicket();
    respData.ticketNumber = orderData["order_no"];
    respData.ticketNo = '440127110QzE6U8';//orderData["order_no"];
    respData.seatCode = orderData["order_no"] + DataUtils.generateThreeDigitRandomNumber();
    respData.cinemaName = DataUtils.toUnicode(orderData["store_name"]);
    respData.movieName = DataUtils.toUnicode(orderData["film_name"]);
    respData.hallName = DataUtils.toUnicode(orderData["hall_name"]);
    respData.playDate = orderData["plan_date"];
    respData.playStartTime =  orderData["plan_date"] + " " + orderData["plan_time"]
    respData.playEndTime =  orderData["plan_date"] + " " + orderData["plan_time"]
    respData.price = orderData["pay_amount"];


    respData.movieCode = orderData["film_no"];
    respData.row = "4";
    respData.column = "5";
    respData.printed = "0";
    respData.printTime = "";
    respData.printChannel = "self";
    respData.screenCode = screen_code;//orderData["hall_id"];
    respData.cinePlayId = orderData["plan_id"];
    respData.according = "1";
    respData.sellTime = "";
    respData.ticketType = DataUtils.toUnicode("成人票")
    respData.seatId = "123456" + DataUtils.generateThreeDigitRandomNumber();
    respData.handleFee = "0.00";
    respData.qrCode = originalQk;
    respData.serviceName = "";
    respData.ticketStatus = "2";
    respData.serviceFee = "0.00";

    RespModel respModel = new RespModel(status: 1, errorMessage: "", errorCode: 0, data: new RespData(ticket: respData));
    return new RespWrapper(res: respModel);
} catch (e) {
    logger.error("闸机检票服务异常", e)
    RespModel errMod = new RespModel(errorCode: 909999, errorMessage: DataUtils.toUnicode("检票服务调试中"), status: 0, data: null);
    return new RespWrapper(res: errMod);
}

return GeneralPub.success(query_result);
