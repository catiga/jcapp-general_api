package com.lengjiabao.general_api.entry.ypcall.ad.home

import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def retobj = [];

def ad_map = [:];
ad_map['disType'] = '100001';
ad_map['title'] = '活动';
//huacai/220021539661108_.jpg
//补充前缀
ad_map['imgUrl'] = 'http://pe1s.static.pdr365.com/huacai/220021539661108_.jpg';
ad_map['clickUrl'] = '';
ad_map['id'] = 5;

retobj.add(ad_map);

ad_map = [:];
ad_map['disType'] = '100001';
ad_map['title'] = '活动';
//huacai/220021539661108_.jpg
//补充前缀
ad_map['imgUrl'] = 'http://pe1d.static.pdr365.com/o_1c7ivqnuus2kckrq3lavl18lqe.jpg';
ad_map['clickUrl'] = '';
ad_map['id'] = 6;

retobj.add(ad_map);



return GeneralPub.success(retobj);