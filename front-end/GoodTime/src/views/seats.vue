<template>
	<div style="overflow:hidden;">
        <div class="dy-info">
            <div class="dy-info-name" v-if="move_data.movieInfo">{{move_data.movieInfo[0].movieName}}</div>
            <div class="dy-info-time" v-if="move_data.movieInfo">{{move_data.startTime}} {{move_data.movieInfo[0].movieLanguage}}{{move_data.movieInfo[0].movieDimensional}}</div>
        </div>
        <section id="seats" class="hidden" style="display: block;">
            <div :class="standard" data-user="">
                <div class="screen-center-name">
                    <div class="tixing"></div>
                    <p>{{move_data.hallName}}</p>
                </div>
                <div id="wrapper">
                    <div id="scroller">
                        <div class="item">
                            <div class="c-tips">
                                <span>银幕中央</span>
                            </div>
                            <p v-for="(row,i) in seat_data.rows" id="seat_scroll">
                                <template v-for="(seat,j) in row.seats">
                                <a href="javascript:" class="seat disabled" v-if="seat.seatState == 0 || seat.seatState == 1 || seat.seatState == 2 || seat.seatState == 6 || seat.seatState == 8">
                                </a>
                                <a href="javascript:" :name="seat.seatRow+'-'+seat.seatCol" :love="seat.loveseats" class="seat active" v-else-if="seat.seatState == 4" v-on:click="seatnum(seat.cineSeatId, seat.seatRow, seat.seatCol, seat.graphCol, seat.loveseats)">
                                </a>
                                <span class="seat" v-else></span>
                                </template>
                            </p>
                        </div>
                    </div>
                    <div class="side">
                        <ol id="float-row">
                            <li class="cs"></li>
                            <li v-for="(row,i) in seat_data.rows">
                                {{row.row}}
                            </li>
                            <li class="cs"></li>
                        </ol>
                    </div>
                </div>
                <div class="tips cf">
                    <ul class="seat-intro" style="width:100%;">
                        <li><a href="javascript:;" class="seat active" ></a>可选</li>
                        <li><a href="javascript:;" class="seat disabled"></a>已售</li>
                        <li><a href="javascript:;" class="seat selected"></a>已选</li>
                    </ul>
                </div>
            </div>
        </section>
        <!-- <div style="height:2rem;line-height:2rem;width:100%;text-align:center;color:#969696;font-size:15px;" v-if="!select_seat.length">点击图标选择座位</div> -->
        <div class="side-box">
            <div class="side-box-list" v-for="item in select_seat">{{item.row}}排{{item.col}}座</div>
        </div>
		<div class="js_dialog" id="iosDialog2" v-if="tip_msg">
            <div class="weui-mask"></div>
            <div class="weui-dialog">
                <div class="weui-dialog__bd">{{tip_msg}}</div>
                <div class="weui-dialog__ft">
                    <a href="javascript:;" v-on:click="tip_msg=''" class="weui-dialog__btn weui-dialog__btn_primary">知道了</a>
                </div>
            </div>
        </div>
        <div class="dy-card-item">
			<div class="dy-btn">
				<button id="J_confirm" type="submit" v-if="select_seat.length" class="dy-button" v-on:click="makeorder">确认选座</button>
                <button id="J_confirm" type="submit" style="border: 1px solid #9ad499; background: #9de59c;" v-if="!select_seat.length" class="dy-button">请先选座</button>
			</div>
		</div>
		<div id="loadingToast" v-if="loading">
	        <div class="weui-mask_transparent"></div>
	        <div class="weui-toast">
	            <i class="weui-loading weui-icon_toast"></i>
	            <p class="weui-toast__content">正在加载</p>
	        </div>
	    </div>
	    <div id="loadingToast" v-if="hint">
	        <div class="weui-mask_transparent"></div>
	        <div class="weui-toast">
	            <i class="weui-loading weui-icon_toast"></i>
	            <p class="weui-toast__content">{{loginhint}}</p>
	        </div>
	    </div>
	    <div class="js_dialog" id="iosDialog1" v-if="city_tip">
	        <div class="weui-mask"></div>
	        <div class="weui-dialog">
	            <div class="weui-dialog__bd">当前场次您有{{order_obj.buy_num}}个座位的未支付订单</div>
	            <div class="weui-dialog__ft">
	                <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary" v-on:click="gopay">立即支付</a>
	                <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_default" v-on:click="nopay">我要重选</a>
	            </div>
	        </div>
	    </div>

	</div>
</template>
<script>
    import Cookies from 'js-cookie'
    export default {
        data: function () {
            return {
                loading: true,
                tip_msg: false,
                city_tip: false,
                hint: false,
                max: 4,
                show_body: "none",
                standard: "main main-small main-big",
                seat_data: {},
                select_seat_meta: {},
                select_seat: '',
                order_obj: {},
                move_data: {},
                apid: '',
                codesrc: '',
                hint: '',
                apid: '',
                loginhint: '',
                identifying_code: '发送验证码',
                num: '',
                rowsLength: '',
                positionLeft: '0',
                zoom: '',
                loadScroll: null,
                tip_msg: '',
                cinemaId:'',
                plan_date:'',
                hallId:'',
                lastUpdateTime:'',
                planId:'',
                myScroll: '',
                code:''
            };
        },
        beforeRouteEnter: function (to, from, next) {
            document.title = "在线选座";
            next();
        },
        created: function () {
            this.planId = this.$route.params.planId;
            this.lastUpdateTime = this.$route.params.lastUpdateTime;
            this.hallId = this.$route.params.hallId;
            this.cinemaId = this.$route.params.cinemaId;
            this.plan_date = this.$route.params.plan_date;
            this.fetchSeatData(this.plan_date,this.planId,this.lastUpdateTime,this.hallId,this.cinemaId);
        },
        mounted: function () {
            this.seatScroll();
        },
        beforeDestroy: function () {
            if (this.loadScroll) {
                clearInterval(this.loadScroll);
            }
        },
        methods: {
            //选座区域放大缩小
            seatScroll: function () {
                var myScroll = new IScroll('#wrapper', {
                    scrollX: true,
                    scrollY: true,
                    preventDefault: false,
                    zoom: true,
                    zoomMax: 1.5,
                    zoomMin: 1,
                    bounce: true,
                    mouseWheel: true,
                    wheelAction: 'zoom',
                });
                this.myScroll = myScroll;
                
            },
            back: function () {
                //返回上一页 如需跳转到其他页面 可使用router.push("路径（例: /index）")
                router.go(-1);
            },
            fetchSeatData: function (plan_date,planId,lastUpdateTime,hallId,cinemaId) {
                var page = this;
                let ap_id = Cookies.get('_lac_ap_');
                var url = '/general_api/api/GetSeats?plan_date='+plan_date+'&plan_id='+planId+'&last_update_time='+lastUpdateTime+'&hall_id='+hallId+'&cinema_id='+cinemaId+'&ap_id='+ap_id+'&'+Date.parse(new Date());
                fetch(url).then(r => r.json()).then(msg => {
                    page.loading = false;
                    page.show_body = "";
                    let recent_order = msg.data.recent_order;
                    if (msg.code == 0) {
                        page.move_data = msg.data.plan;
                        console.log(page.move_data);
                        page.seat_data = msg.data.seat;
                        page.rowsLength = msg.data.seat.rows.length;
                    }
                    if (msg.data.seat.rows.length > 11) {
                        page.standard = "main main-small";
                    }
                    if(recent_order) {
                    	//当前场次的未支付订单
                    	this.city_tip = true;
                    	this.order_obj = recent_order;
                    }

                    var checkScroll = setInterval(function () {
                        
                        if ($("#wrapper")[0]) {
                            var sw = $("#scroller").width();
                            var dw = $(window).width();
                            $("#wrapper").animate({
                                scrollLeft: (sw - dw) / 2 + 22
                            })
                            page.loadScroll = setInterval(function () {
                                if ($('#scroller').css('transform')) {
                                    var arr = $('#scroller').css('transform').split(',');
                                    var y = parseFloat(arr[5]);
                                    var trans = "translateY(" + y + 'px) scale(' + arr[3] + ')';
                                    $('.side').css('transform', trans);
                                }
                                $(".side").css("left", $("#wrapper").scrollLeft());
                            }, 1000 / 60);
                            clearInterval(checkScroll);
                        }
                    }, 300);
                }); 
            },
            gopay: function () {
                let order_obj = this.order_obj;
                //if call this method, then mush exist trunm in cookie
                let trnum = Cookies.get('ticket_trdnum');
                location.href="/general_api/tcss/index/?#/confirmOrder/"+trnum;
            },
            nopay: function () {
                let order_obj = this.order_obj;
                var token = Cookies.get('_lac_k_');
                //close the trade
                let url = '/general_api/api/auth/release_seat_order?order_no=' + order_obj.order_no + '&token=' + token + '&' + new Date().getTime();
                this.loading = true;
                this.city_tip = false;
                fetch(url).then(r => r.json()).then(msg => {
                   console.log(msg);
                   this.loading = false;
                   this.loading = false;
                   this.city_tip = false;
                   this.planId = this.$route.params.planId;
            	   this.lastUpdateTime = this.$route.params.lastUpdateTime;
            	   this.hallId = this.$route.params.hallId;
            	   this.cinemaId = this.$route.params.cinemaId;
            	   this.plan_date = this.$route.params.plan_date;
            	   this.fetchSeatData(this.plan_date,this.planId,this.lastUpdateTime,this.hallId,this.cinemaId);
                });
            },
            showTip: function (msg, ts) {
                var page = this;
                page.tip_msg = msg;
                if (ts) {
                    clearTimeout(window.maxtm);
                    window.maxtm = setTimeout(function () {
                        page.tip_msg = "";
                    }, ts)
                }
            },
            seatnum: function (sid, row, col, gc, loveseats) {
                var page = this;
                var key = row + "-" + col;
                page.seats = key;
                if (page.select_seat_meta[key]) {
                    //反选
                    delete page.select_seat_meta[key];
                    $("a[name=" + key + "]").removeClass("selected").addClass("active");
                    if(loveseats) {
                        $("a[love=" + loveseats + "]").removeClass("active").addClass("selected");
                    }
                } else {
                    //选中
                    if (page.select_seat.length + 1 > page.max) {
                        page.showTip("最多只能购买4个座位", 1300);
                        return;
                    }

                    page.select_seat_meta[key] = {
                        "sid": sid,
                        "row": row,
                        "col": col
                    };
                    $("a[name=" + key + "]").removeClass("active").addClass("selected");
                    if(loveseats) {
                        $("a[love=" + loveseats + "]").removeClass("active").addClass("selected");
                    }
                }

                var select_seat = [];
                for (var sk in page.select_seat_meta) {
                    select_seat.push(page.select_seat_meta[sk]);
                }
                page.select_seat = select_seat;

                var click_index = select_seat.length - 1;
                var el = $('#seat_scroll .selected');
                if (el.offset()) {
                    if (page.rowsLength > 11) {
                        this.myScroll.zoom(1.5, el.offset().left, el.offset().top);
                        this.myScroll.scrollToElement('#seat_scroll .selected', 1200, true, true);
                    } else {
                        this.myScroll.zoom(1.2, el.offset().left, el.offset().top);
                        this.myScroll.scrollToElement('#seat_scroll .selected', 1200, true, true);
                    }
                } else {

                }
            },
            makeorder: function () {
            	let page = this;
            	let token = Cookies.get('_lac_k_');
            	let au_type = Cookies.get('_lac_ated_');
            	let part_id = Cookies.get('_lac_op_');
            	let apid = Cookies.get('_lac_ap_');
            	if(token) {
            		page.GoPayTicket();
            	} else {
            		//跳转到登录页面
            		this.$router.push({name: 'login', params: { apid:apid }})
            	}
            },
            GoPayTicket:function(){
                var page = this;
                page.loading = true;
                if (!page.select_seat.length) {
                    page.showTip("请选择您要购买的座位", 1300);
                    page.loading = false;
                    return;
                }
                var seats = "";
                var ids = "";
                for (var i = 0, ic = page.select_seat.length; i < ic; i++) {
                    if (ids) {
                        ids += ",";
                    }
                    ids += page.select_seat[i].sid;
                    seats = seats + page.select_seat[i].row + "排" + page.select_seat[i].col + "座 "
                }
                //真正发送请求
                var token = Cookies.get('_lac_k_');
                var url = '/general_api/api/auth/CreateSeatsOrder?plan_id='+page.planId+'&last_update_time='+page.lastUpdateTime+'&hall_id='+page.hallId+'&cinema_id='+page.cinemaId+'&seat_ids='+ids+'&plan_date='+page.plan_date+'&token='+token+'&ts='+Date.parse(new Date());
                fetch(url).then(r => r.json()).then(msg => {
                   page.loading = false;
                    if (msg.code == 0) {
                        document.cookie = "orderNo=" + msg.data[1].order_no + ";path=/";
                        Cookies.set('ticket_trdnum', msg.data[0]);
                        location.href="/general_api/tcss/index/?#/confirmOrder/"+msg.data[0];
                    } else {
                        if (msg.available == false && msg.messages[0] == 'user_no_login') {
                            location.href = '/general_api/h5/wx/auth?type=base&' + new Date().getTime();
                            return;
                        }
                        page.showTip("座位锁定失败", 1300);
                        //清理
                        page.seat_data = {};
                        page.select_seat_meta = [];
                        page.select_seat = [];
                        page.fetchSeatData(page.plan_date,page.planId,page.lastUpdateTime,page.hallId,page.cinemaId);
                    }
                });
            }
        }
    }
</script>
<style>
/******* 电影信息开始 *******/

.dy-info {
    display: flex;
    flex-direction: column;
    justify-content: center;
    width: 100%;
    height: 1.2rem;
    background: #fff;
    padding-left: .3rem;
}

.dy-info-name {
    font-size: .34rem;
    color: #000;
}

.dy-info-time {
    font-size: .26rem;
    color: #999999;
}


#seats {
    height: 7.75rem;
}

#seats .tips {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    padding: .1rem 0;
    background: #efeff4;
    z-index: 5;
}


.cf:after {
    clear: both;
    content: "";
    display: block;
    height: 0;
    overflow: hidden;
}


#seats .tips .seat-intro li {
    width: 1.3rem;
    font-size: .22rem;
    color: #999999;
}





#seats .main h3 {
    position: absolute;
    top: 0;
    left: 50%;
    z-index: 5;
    margin-left: -1.63rem;
    width: 3.26rem;
    height: .4rem;
    background: transparent url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAU4AAAAmCAYAAACrg3LqAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEgAACxIB0t1+/AAAABx0RVh0U29mdHdhcmUAQWRvYmUgRmlyZXdvcmtzIENTNui8sowAAAAXdEVYdENyZWF0aW9uIFRpbWUAMjAxNC4yLjE4Ryp/NQAAAylJREFUeJzt3cGKFFcUh/HPziJPkCfwBty4CpmE6APMLgsXLoO7rNznDcaMGoOKiqKBSEJcJAFJ5qobIYh6FQyKIjgYcKEoIgoGYWKmsuiaMDN2dXdVV9u3x+8Hveiqrltn9ecU1XVq0+LszGXgUyRJw7jUAeYmXYUkTZG5DnAWuDfpSiRpCtwCfuuEmJaB+UlXI0lTYM/mhatFp/xyGngyyWokKXN/AT8BdABCTK+Ag5OsSJIytxd4DWVwlo4Cf0+kHEnK22Pg1MqX/4MzxPQUODmJiiQpc9+WV+bA2o4T4ABlKypJAuAFcGT1hjXBGWK6D/z8NiuSpMwdCzE9X71hfccJ/jVJklYs0b0SX+ON4AwxXQcuvoWCJCl334eYHq3f2KvjBLtOSVoG9vfa0TM4i6JYAO6MsyJJytzZEFPPHOwZnB+eu1YAX4+1JEnKW+WVd9WlOsCPwBvX9pL0DrgcYrpUtbMyOENMS8A3YylJkvK2p9/Ofh0nwHHgZXu1SFL27tIdt1mpb3CWf/o82mZFkpS5/eW4zUqDOk7oTk36p516JClrT+mO2exrYHCGmB7QvVEkSRvd4dXDPKoM03FC9yZRMVo9kpS1V8ChYX44VHCGmP4Ezo9SkSRl7nQ5XnOgYTtOgH0Ni5Gk3BX0GOZRZejgDDFdAG40qUiSMne+6vHKXup0nNB954YkbTS1HvapG5xngAc1j5GknN0piqLWPZxawRlieo2PYUraWObLwUZDq9txApwAng/8lSTl7yHwQ92DagdniOkl615cJElTaq4caFRLk44Tuo9hDvx3vSRl7DZwrMmBjYKzfAfH7ibHSlIGloBdTbpNaN5xEmI6AXzV9HhJmpAC+DLEdK3pAo2DEyDENAfswpmdkqbDMrA7xHRqlEVGCk6AENN3wMfAlVHXkqQxegHsCDENNcijn5GDEyDEdLcois+AncDNNtaUpBb9AmwNMf3axmKb2lhkvcXZme3AF8DnwAfjOIckDfAv8DswH2L6o40FF2dn2LxwdTzBueok7wEfAduAT4At5ef9cZ5X0jvrGZCAc8CZENPDNhdfCc7/AAdIwJ2yPD/oAAAAAElFTkSuQmCC") no-repeat center top;
    background-size: 167px 19px;
    font-size: .26rem;
    color: #999999;
}

#seats .wrapper {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: 3;
    overflow: auto;
    -webkit-overflow-scrolling: touch;
}


#seats .scroller {
    position: relative;
    margin-top: 20px;
    padding: 0 23px;
}

#seats .main .item {
    background: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAIAAAAQCAMAAAALdl6GAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyRpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoTWFjaW50b3NoKSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDpCRUZFQjczNjkwOEQxMUUzOURDOUU2QjMwODYxMzg5NiIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDpCRUZFQjczNzkwOEQxMUUzOURDOUU2QjMwODYxMzg5NiI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOkJFRkVCNzM0OTA4RDExRTM5REM5RTZCMzA4NjEzODk2IiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOkJFRkVCNzM1OTA4RDExRTM5REM5RTZCMzA4NjEzODk2Ii8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+hu0OggAAACRQTFRF6Ojo5eXl6enp5ubm3d3d0dHR29vb39/fz8/P3Nzc0NDQ8PDwPbCqXgAAACtJREFUeNpiYGZk4OBgYOFk4OJiYGdn4OaGIgYmkAgnJwMrKwMbG1wcIMAAFuIBBsTiT5gAAAAASUVORK5CYII=") repeat-y center top;
}

#seats .c-tips span {
    padding: 0 4px;
    font-size: 12px;
    background: #e0e0e0;
    border-radius: 2px;
}


#seats .main-big .item p {
    height: 24px;
}



#seats .main .item p {
    height: 24px;
    overflow: hidden;
    white-space: nowrap;
}

#seats .c-tips {
    margin-bottom: 10px;
    height: 24px;
    line-height: 24px;
    background: #f0f0f0;
}

#seats .main .side {
    position: absolute;
    /*left: 4px;*/
    top: 0px;
    z-index: 4;
    pointer-events: none;
    font-size: 10px;
    color: #fff;
    opacity: .6;
    filter: alpha(opacity=60);
}

#seats .main .side ol {
    width: 16px;
    border-radius: 8px;
    background: #7f7f7f;
}

#seats .main .side .cs {
    display: block;
    height: 32px;
}

#seats .main .side li {
    position: relative;
    height: 24px;
    line-height: 24px;
    white-space: nowrap;
}


.bg-login-bg {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: #FAFAFA;
    z-index: -1;
}

.bg-login-box {
    float: left;
    width: 100%;
    height: auto;
    z-index: 99;
}

.bg-login-num {
    float: left;
    width: 94%;
    height: 40px;
    line-height: 40px;
    margin-left: 3%;
    margin-right: 3%;
    margin-bottom: 25px;
}

.bg-login-num input {
    display: inline-block;
    width: 100%;
    height: 40px;
    padding-left: 6px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

.bg-login-password {
    float: left;
    width: 94%;
    height: 40px;
    line-height: 40px;
    margin-left: 3%;
    margin-right: 3%;
    margin-bottom: 25px;
}

.bg-login-password input {
    display: inline-block;
    width: 100%;
    height: 40px;
    padding-left: 6px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

.bg-login-bot {
    float: left;
    width: 94%;
    text-align: center;
    height: 40px;
    line-height: 40px;
    margin-left: 3%;
    margin-right: 3%;
    color: #fff;
    font-size: 16px;
    background: #367cff;
    border-radius: 5px;
}

.form-input-mobile {
    position: relative;
    margin-bottom: 25px;
}

.form-item {
    display: -webkit-box;
    display: -moz-box;
    display: box;
    width: 100%;
    background: #fff;
    margin-bottom: 20px;
}

.form-input-mobile:before {
    background: #fff;
    content: "";
    -webkit-box-sizing: border-box;
    display: block;
    width: 200%;
    height: 200%;
    position: absolute;
    left: 0;
    top: 0;
    z-index: 1;
    -webkit-transform: scale(0.5);
    -webkit-transform-origin: 0 0;
    border: 1px solid #ccc;
    border-radius: 10px;
    -webkit-border-radius: 10px;
    -moz-border-radius: 10px;
    -o-border-radius: 10px;
    -ms-border-radius: 10px;
}

.form-input-mobile .vcode-span {
    position: static;
}

.form-input-mobile span {
    z-index: 2;
    position: relative;
}

.form-input-wrapper {
    display: -webkit-box;
    display: -moz-box;
    display: box;
    box-sizing: border-box;
    -webkit-box-sizing: border-box;
    -o-box-sizing: border-box;
    -ms-box-sizing: border-box;
    -moz-box-sizing: border-box;
    -moz-box-flex: 1;
    -webkit-box-flex: 1;
    box-flex: 1;
    height: 50px;
    padding: 0 14px;
    background: #fff;
    border-radius: 10px;
}

.verifyCode-img {
    display: block;
    height: 36px;
    margin-top: 7px;
    margin-right: 5px;
    box-sizing: border-box;
    -webkit-box-sizing: border-box;
    -o-box-sizing: border-box;
    -ms-box-sizing: border-box;
    -moz-box-sizing: border-box;
    z-index: 2;
    position: relative;
}

.form-input-mobile input {
    z-index: 2;
    position: relative;
    margin: 1px;
}

/*input[type=text],
input[type=password],
input[type=number],
input[type=email],
input[type=tel],
textarea {
    border: 0;
    display: block;
    -moz-box-flex: 1;
    -webkit-box-flex: 1;
    box-flex: 1;
    -webkit-appearance: none;
    -webkit-tap-highlight-color: rgba(255, 0, 0, 0);
    line-height: 20px;
    font-size: 14px;
    height: 20px;
    padding-top: 25px;
    padding-bottom: 14px;
    color: #333;
    line-height: 20px;
}*/

.form-input-mobile span {
    z-index: 2;
    position: relative;
}

.input-clearValue {
    width: 19px;
    /*background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACoAAAAqCAYAAADFw8lbAAAAAXNSR…XczH++Se2cH8QORNAOlbe4NETU4H/8WMHViaO0X6PN2vwBXtDwHrylQ1oAAAAASUVORK5CYII=) no-repeat center center;*/
    -webkit-background-size: 15px 15px;
    cursor: pointer;
    display: none;
}

.form-wrapper .form-item-username {
    margin-bottom: 0;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
}

.form-item-username {
    position: relative;
}

.form-input-mobile {
    position: relative;
    margin-bottom: 25px;
}

.form-input-wrapper {
    display: -webkit-box;
    display: -moz-box;
    display: box;
    box-sizing: border-box;
    -webkit-box-sizing: border-box;
    -o-box-sizing: border-box;
    -ms-box-sizing: border-box;
    -moz-box-sizing: border-box;
    -moz-box-flex: 1;
    -webkit-box-flex: 1;
    box-flex: 1;
    height: 50px;
    padding: 0 14px;
    background: #fff;
    border-radius: 10px;
}

.form-item {
    display: -webkit-box;
    display: -moz-box;
    display: box;
    width: 100%;
    background: #fff;
    margin-bottom: 20px;
}

.form-wrapper .form-item-username:before {
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
    border-bottom: 0;
}

.form-input-mobile:before {
    background: #fff;
    content: "";
    -webkit-box-sizing: border-box;
    display: block;
    width: 200%;
    height: 200%;
    position: absolute;
    left: 0;
    top: 0;
    z-index: 1;
    -webkit-transform: scale(0.5);
    -webkit-transform-origin: 0 0;
    border: 1px solid #ccc;
    border-radius: 10px;
    -webkit-border-radius: 10px;
    -moz-border-radius: 10px;
    -o-border-radius: 10px;
    -ms-border-radius: 10px;
}

.form-input-mobile input {
    z-index: 2;
    position: relative;
    margin: 1px;
}

.form-input-mobile span {
    z-index: 2;
    position: relative;
}

.input-clearValue {
    width: 19px;
    /*background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACoAAAAqCAYAAADFw8lbAAAAAXNSR…XczH++Se2cH8QORNAOlbe4NETU4H/8WMHViaO0X6PN2vwBXtDwHrylQ1oAAAAASUVORK5CYII=) no-repeat center center;*/
    -webkit-background-size: 15px 15px;
    cursor: pointer;
    display: none;
}

.form-input-mobile span {
    z-index: 2;
    position: relative;
}

.pass-input-userlist {
    /*background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAGXRFW…sB93FEmY+SRNQC2lEb5Tt45/oz0gD1xFkon2QFYHJ9BRgA1YdoEYAKKm8AAAAASUVORK5CYII=) no-repeat 50% 50%;*/
    -webkit-background-size: 16px 16px;
    z-index: 12;
    position: relative;
    display: none;
}

.pass-input-aide {
    width: 22px;
    height: 19px;
    display: block;
    cursor: pointer;
    margin-top: 16px;
    margin-left: 13px;
}

.form-wrapper .form-item-password {
    border-top-right-radius: 0;
    border-top-left-radius: 0;
}

.form-input-mobile {
    position: relative;
    margin-bottom: 25px;
}

.form-input-wrapper {
    display: -webkit-box;
    display: -moz-box;
    display: box;
    box-sizing: border-box;
    -webkit-box-sizing: border-box;
    -o-box-sizing: border-box;
    -ms-box-sizing: border-box;
    -moz-box-sizing: border-box;
    -moz-box-flex: 1;
    -webkit-box-flex: 1;
    box-flex: 1;
    height: 50px;
    padding: 0 14px;
    background: #fff;
    border-radius: 10px;
}

.form-item {
    display: -webkit-box;
    display: -moz-box;
    display: box;
    width: 100%;
    background: #fff;
    margin-bottom: 20px;
}

.form-wrapper .form-item-password:before {
    border-top-right-radius: 0;
    border-top-left-radius: 0;
}

.form-input-mobile:before {
    background: #fff;
    content: "";
    -webkit-box-sizing: border-box;
    display: block;
    width: 200%;
    height: 200%;
    position: absolute;
    left: 0;
    top: 0;
    z-index: 1;
    -webkit-transform: scale(0.5);
    -webkit-transform-origin: 0 0;
    border: 1px solid #ccc;
    border-radius: 10px;
    -webkit-border-radius: 10px;
    -moz-border-radius: 10px;
    -o-border-radius: 10px;
    -ms-border-radius: 10px;
}

.form-input-mobile input {
    z-index: 2;
    position: relative;
    margin: 1px;
}

.form-input-mobile a {
    position: relative;
    z-index: 2;
}

a {
    text-decoration: none;
    color: #9ab1d1;
    -webkit-touch-callout: none;
}

.form-input-mobile span {
    z-index: 2;
    position: relative;
}

.input-clearValue {
    width: 19px;
    /*background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACoAAAAqCAYAAADFw8lbAAAAAXNSR…XczH++Se2cH8QORNAOlbe4NETU4H/8WMHViaO0X6PN2vwBXtDwHrylQ1oAAAAASUVORK5CYII=) no-repeat center center;*/
    -webkit-background-size: 15px 15px;
    cursor: pointer;
    display: none;
}

.form-item-btn {
    background: #f1f1f1;
    border: 0;
    width: auto;
}

.form-item {
    display: -webkit-box;
    display: -moz-box;
    display: box;
    width: 100%;
    background: #fff;
    margin-bottom: 20px;
}

input[type=submit],
input[type=button] {
    -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
    -webkit-appearance: none;
    -moz-appearance: none;
}

.pass-button-full-disabled {
    color: #fff;
    opacity: 1;
}

.pass-button-full {
    color: #fff;
    font-size: 12px;
    width: 100%;
    line-height: 48px;
    box-sizing: border-box;
    -webkit-box-sizing: border-box;
    -o-box-sizing: border-box;
    -ms-box-sizing: border-box;
    -moz-box-sizing: border-box;
    cursor: pointer;
    display: block;
    text-align: center;
    -webkit-appearance: none;
    -moz-appearance: none;
    -webkit-tap-highlight-color: rgba(255, 0, 0, 0);
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    -o-border-radius: 5px;
    -ms-border-radius: 5px;
    border: 1px solid #367cff;
    background: #367cff;
}

#pageWrapper,
.wrapper {
    width: 100%;
}

.form-wrapper {
    padding: 0 15px;
    -webkit-box-sizing: border-box;
}

.img-div {
    width: 100%;
    height: 80px;
    margin-top: 10px;
    margin-bottom: 15px;
}

.side-box {
    display: flex;
    margin-top: .24rem;
    width: 100%;
    box-sizing: border-box;
    padding: 0 .3rem;
}
.side-box-list {
    display: block;
    margin-right: .16rem;
    text-align: center;
    height: .6rem;
    line-height: .6rem;
    border: 1px solid #dfdfdf;
    padding: 0 .16rem;
    border-radius: 4px;
    background: #f8f8f8;
    font-size: .26rem;
    color: #444444;
}

#seats .tips .seat-intro {
    display: flex;
    justify-content: center;
    padding-right: .5em;
    text-align: center;
}

.cf:after {
    clear: both;
    content: "";
    display: block;
    height: 0;
    overflow: hidden;
}

#seats .tips .seat-intro .seat {
    margin: 0 3px;
    vertical-align: -2px;
}

#seats .active {
    background-image: url("http://pe1s.static.pdr365.com/seat_no_choose.png");
}

#seats .disabled {
    background-image: url("http://pe1s.static.pdr365.com/seat_locked.png");
}

#seats .selected {
    background-image: url("http://pe1s.static.pdr365.com/seat_select.png");
}

#seats .main {
    position: relative;
    width: 100%;
    height: 100%;
    z-index: 1;
    background: #EFEFF4;
    text-align: center;
    overflow: hidden;
    border-top: 1px solid silver;
}

.screen-center-name {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    z-index: 5;
    width: 3.26rem;
    height: .44rem;
    margin: 0 auto;
}

.tixing {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    z-index: 5;
    width: 3.26rem;
    height: .44rem;
    margin: 0 auto;
    font-size: .26rem;
    color: #999999;
    background: #dcdcdc;
    border-bottom-left-radius: .1rem;
    border-bottom-right-radius: .1rem;
    transform: perspective(5px) rotateX(-2deg);
    transform-origin: top;
}

.screen-center-name p {
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    margin: auto;
    color: #999999;
    font-size: .26rem;
    z-index: 10;
}

#wrapper {
    position: absolute;
    top: .4rem;
    bottom: 0;
    left: 0;
    right: 0;
    overflow: auto;
}

#scroller {
    position: absolute;
    left: 0;
    top: 0;
    width: 140%;
    padding: 0 23px;
}

#seats .main-big .seat {
    width: 20px;
    height: 20px;
    line-height: 20px;
    margin: 0 4px 4px 0;
    background-size: 20px 20px;
}

#seats .main .item {
    background: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAIAAAAQCAMAAAALdl6GAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyRpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoTWFjaW50b3NoKSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDpCRUZFQjczNjkwOEQxMUUzOURDOUU2QjMwODYxMzg5NiIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDpCRUZFQjczNzkwOEQxMUUzOURDOUU2QjMwODYxMzg5NiI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOkJFRkVCNzM0OTA4RDExRTM5REM5RTZCMzA4NjEzODk2IiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOkJFRkVCNzM1OTA4RDExRTM5REM5RTZCMzA4NjEzODk2Ii8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+hu0OggAAACRQTFRF6Ojo5eXl6enp5ubm3d3d0dHR29vb39/fz8/P3Nzc0NDQ8PDwPbCqXgAAACtJREFUeNpiYGZk4OBgYOFk4OJiYGdn4OaGIgYmkAgnJwMrKwMbG1wcIMAAFuIBBsTiT5gAAAAASUVORK5CYII=") repeat-y center top;
}

#seats .c-tips span {
    padding: 0 4px;
    font-size: 12px;
    background: #e0e0e0;
    border-radius: 2px;
}

#seats .main .item p {
    height: 16px;
    overflow: hidden;
    white-space: nowrap;
}

#seats .main-big .item p {
    height: 24px;
}


#seats .seat {
    display: inline-block;
    width: 14px;
    height: 14px;
    line-height: 14px;
    margin: 0 2px 2px 0;
    background-size: 14px 14px;
}

#seats .main .item p {
    height: 24px;
    overflow: hidden;
    white-space: nowrap;
}

#seats .c-tips {
    margin-bottom: 10px;
    height: 24px;
    line-height: 24px;
    background: #f0f0f0;
}

#seats .main .side {
    position: absolute;
    left: 4px;
    top: 0px;
    z-index: 4;
    pointer-events: none;
    font-size: 10px;
    color: #fff;
    opacity: .6;
    filter: alpha(opacity=60);
    transform-origin: 0 0;
}

#seats .main .side ol {
    width: 16px;
    border-radius: 8px;
    background: #7f7f7f;
}

#seats .main .side .cs {
    display: block;
    height: 32px;
}

#seats .main .side li {
    position: relative;
    height: 24px;
    line-height: 24px;
    white-space: nowrap;
}


.dy-card-item {
    position: fixed;
    bottom: 0;
    width: 100%;
    height: .9rem;
    margin: .2rem 0;
}

.dy-btn {
    height: .8rem;
    padding: 0 .3rem;
}
.dy-button {
    width: 100%;
    height: 100%;
    border: 1px solid #019400;
    text-align: center;
    background: #0ABB07;
    color: #fff;
    border-radius: .06rem;
    font-size: .34rem;
}


</style>