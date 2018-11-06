package com.lengjiabao.general_api.entry.api

import org.apache.commons.fileupload.FileItem

import com.alibaba.druid.stat.JdbcStatManager
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.QiniuSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.power.support.QiniuUploadResult
import com.jeancoder.core.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.common.AvailabilityStatus
import com.lengjiabao.general_api.ready.util.MD5Util

JCLogger  Logger = JCLoggerFactory.getLogger(this.getClass())
try {
	JCRequest req = JC.request.get();
	List<FileItem> items = req.getFormItems();
	def head= "";
	if(items) {
		FileItem file = items.get(0);
		println "upload_file__" + file.getSize();
		def define_name = MD5Util.getMD5(file.getName() + nextInt(1000, 9999));
		head = 'hi_'+define_name; 
		println "upload_file__" + head;
		QiniuUploadResult qu = QiniuSource.getQiniuPower().upload(file.getInputStream(), head);
		println "upload_file__" + JackSonBeanMapper.toJson(qu);
	}
	head = "http://pe1s.static.pdr365.com/" + head;
	println "upload_file__" + head;
	AvailabilityStatus  available = AvailabilityStatus.available();
	available.setData( head);
	return available;
} catch (e) {
	Logger.error("上传文件失败", e);
	return AvailabilityStatus.notAvailable("上传文件失败");
}

def nextInt(final int min, final int max){
	Random rand= new Random();
	int tmp = Math.abs(rand.nextInt());
	return tmp % (max - min + 1) + min;
}
