package com.lengjiabao.general_api.entry.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result

////返回非list数据
//Result result = new Result();
//List<CommunicationParam> params = new ArrayList<CommunicationParam>();
//JCRequest reques = RequestSource.getRequest();
//def movie_id = reques.getParameter("movie_id");
//
//CommunicationParam param1 = new CommunicationParam("movie_id", movie_id);
//
//params.add(param1);
//CommunicationPower caller = CommunicationSource.getCommunicator("ticketingsys");
//def aaaa = caller.doworkAsString("/api/movie_detail", params);
//
//
//return result.setData(aaaa);

def movie_id = JC.request.param('movie_id');
def aaaa = JC.internal.call('ticketingsys', '/api/movie_future_detail', [movie_id:movie_id]);
Result result = new Result();
return result.setData(aaaa);
