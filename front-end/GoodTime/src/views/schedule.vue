
<template>
    <div style="background:#EFEEF4;">
        <!-- 影城信息 开始 -->
        <div class="cinema-header-wrapper">
            <div class="cinema-header-info">
                <p class="cinema-name">
                    <span>{{storeName}}</span>
                </p>
                <p class="cinema-header-address">{{storeAdd}}</p>
            </div>
            <!-- 影城地图 开始 -->
            <div class="map-icon" style="text-align: center;" @click="goMap">
                    <img style="width: .4rem;display: inline-block;" src="http://pe1s.static.pdr365.com/location_icon.png">
                    <p style="color: #a9a9a9;">地图</p>
            </div>
            <!-- 影城地图 结束 -->
        </div>
        <!-- 影城信息 结束 -->
		
        <div class="swiper-bg">
            <!-- 电影轮播 开始 -->
            <swiper :options="swiperOption" ref="mySwiper" class="swiper" style="margin-top: .25rem;">
                <swiper-slide v-for="(item, index) in movie_list" :key="index" :data-index="index">
                    <input type="hidden" :value="item.id"> 
                    <img style="display:block;width:100%;height:100%;border-radius:.1rem;" :src="item.img == '' || item.img == null  ? 'http://pe1s.static.pdr365.com/default_movie_pic.jpg' : item.img ">
                </swiper-slide>
            </swiper>
            <!-- 电影轮播 结束 -->

            <!-- 电影名字 开始 -->
            <div class="choose-movie-name-wrapper">
                <p class="choose-movie-name">
                    <span>{{properties.film_name}}</span>
                    <span v-if="current_movie.film_score" class="current-movie-score">{{properties.film_score}}</span>
                </p>
            </div>
            <!-- 电影名字 结束 -->
            
            <!-- 演员表 开始 -->
            <div class="actor-list-wrapper">
                <p><span>{{properties.time_diff}}分钟</span> | <span>{{properties.film_type}}</span> | <span> <span v-for="(item,j) in actorList" v-if="j < 3"> {{item.cele_name}} </span> </span></p>
            </div>
            <!-- 演员表 结束 -->
        </div>
       


        <!-- 影片日期 开始 -->
        <div class="wrapper" ref="dateListWrapper">
            <ul style="position: absolute; left: 0; top: 0;height: 100%; white-space: nowrap; z-index: 2;">
                <li :class="date_index === index ? 'choose-current-date' : ''" v-for="(item, index) in date_list" :key="index" @click="clickDate(index)"  style="display: inline-block; line-height: .91rem; width: 1.87rem; height: 100%; text-align: center; font-size: .26rem; color: #828282; padding: 0 .25rem; box-sizing: border-box;">{{item.date}}</li>
            </ul>
        </div>
        <!-- 影片日期 结束 -->

        <!-- 影片场次 开始 -->
        <div class="choose-movie-plan-wrapper" style="padding-bottom: 50px;">
            <ul>
                <li v-for="item in current_plan_list" :key="item.planId" :data-planid="item.planId" :data-hallId="item.hallId">
                   <!-- <router-link :to="{name: 'seats', params: { pid: item.planId }}"> -->
                       <div class="choose-item-wrapper">
                            <div class="choose-time-logo">
                                <img v-if="item.time_flag === 1" src="http://pe1s.static.pdr365.com/sun.png">
                                <img v-if="item.time_flag === 2" src="http://pe1s.static.pdr365.com/moon.png">
                            </div>
                            <div class="choose-time">
                                <p>{{item.startClock}}</p>
                                <p style="font-size: .2rem;">{{item.endClock}} 散场</p>
                            </div>
                            <div class="choose-hall">
                                <p>{{item.language}}</p>
                                <p>{{item.hall}}</p>
                            </div>
                            <div class="choose-price">
                                <p>¥{{item.current_price/100}}</p>
                                <p><s>¥{{item.standard_price/100}}</s></p>
                            </div>
                       </div> 
                       <div class="choose-btn-wrapper" v-on:click="godo_seats(item.planId,item.lastUpdateTime,item.hallId,item.planDate)">
                            <div class="choose-btn">购票</div>
                       </div>
                   <!-- </router-link>  -->
                </li>
            </ul>
        </div>
        <!-- 影片场次 结束 -->

        <!-- 电影详情弹窗 开始-->
        <!-- <div class="dialog"> -->
            <!--外层的遮罩 点击事件用来关闭弹窗，isShow控制弹窗显示 隐藏的props-->
            <!-- <div class="dialog-cover back" v-if="isShow" @click="closeDialog"></div> -->
            <!-- transition 这里可以加一些简单的动画效果 -->
            <!-- <transition name="drop"> -->
                <!--style 通过props 控制内容的样式 -->
                <!-- <div class="dialog-content" v-if="isShow">
                    <div class="movie-details-popup-post" :style="'background-image:url( http://pe1d.static.pdr365.com/' + movie_details.pic_small + ')'">
                        <p>#{{movie_details.film_alias_name}}#</p>
                    </div>
                    <div class="movie-detail-popup-content">
                        <div class="movie-detail-popup-content-box">
                            <div class="movie-detail-popup-content-wrap">
                                <p class="movie-name">{{movie_details.film_name}}</p>
                                <p class="movie-info">
                                    <span>{{movie_details.release_date}}</span>
                                    <span class="border-left">{{movie_details.film_type}}</span>
                                </p>
                                <p class="movie-desc">{{movie_details.film_content}}</p>
                            </div>
                        </div>
                    </div>
                    <div class="see-more">
                        <a @click="goMovieDetails"> 查看更多影片资料 </a>
                    </div>
                    <div class="movie-detail-popup-close" @click="closeDialog">
                        <span>X</span>
                    </div>
                </div>
            </transition>
        </div> -->
        <!-- 电影详情弹窗 结束-->

        <!-- 底部导航栏-->
        <!--
        <div class="bottom-tab">    
            <a href="javascript:void(0)" class="movie" data-status="active" v-on:click="godo_index">        
                <div class="icon"></div>    
            </a>   
            <a href="javascript:void(0)" class="store" v-on:click="godo_goods">
                <div class="icon"></div>    
            </a>      
            <a href="javascript:void(0)" class="cinema" v-on:click="godo_cinemas">
                <div class="icon"></div>    
            </a>    
            <a href="javascript:void(0)" class="mine" v-on:click="godo_mine">        
                <div class="icon"></div>    
            </a>
        </div>
        -->
        <tabBottom :active="'cinema'"></tabBottom>
        
        <div id="loadingToast" v-if="loading">
            <div class="weui-mask_transparent"></div>
            <div class="weui-toast">
                <i class="weui-loading weui-icon_toast"></i>
                <p class="weui-toast__content">加载中</p>
            </div>
        </div>
	</div>
</template>
<script>
    import BScroll from 'better-scroll'
    import Cookies from 'js-cookie'

    export default {
        data() {
            var self = this;
            return {
                isShow: false,
                click_flag: false,
                cinemaId:'',
                movie_id: "",
                cinema_id: "",
                store_city: "",
                store_zone: "",
                cinema_info: {},
                movie_list: [],
                current_plan_list: [],
                date_index: 0,
                date_list_length: 0,
                current_movie_index: 0,
                current_movie: {},
                date_list:'',
                properties:'',
                loading: true,
                actorList:'',//演员列表
                storeAdd:'',
                storeName:'',
                index:'',
                dateListScroll: {},
                swiperOption: {
                    slidesPerView: 4,
                    spaceBetween: 10,
                    centeredSlides: true,
                    on: {
                        slideChangeTransitionEnd: function () {
                            //获取影片排期
                            var mid = $('.swiper-slide-active input').val();
                            if (mid != undefined) {
                                self.current_plan_list = self.date_list[0].plans;
                                self.current_movie_index = this.activeIndex;
                                self.date_index = 0;
                            }
                        },
                        click: function (event) {
                            var page = this;
                            self.current_plan_list = self.movie_list[page.clickedIndex].dates[0].plans;
                            self.properties = self.movie_list[page.clickedIndex].properties;
                            self.actorList = self.movie_list[page.clickedIndex].celebritys;
                            page.slideTo(page.clickedIndex);
                            self.dateListScroll.scrollTo(0,0,700,'bounce');
                        }
                    }
                }
            };
        },
        computed: {
            swiper: function () {
                return this.$refs.mySwiper.swiper
            }
        },
        beforeRouteEnter: function (to, from, next) {
            document.title = "影片排期";
            next();
        },
        created: function () {
            this.movie_id = this.$route.params.mid;
            this.index = this.$route.params.index;
            this.cinemaId = this.$route.params.cinemaId;
            var storeName = this.getCookie("storeName");
            this.decodeUnicodeNm(storeName);
            var storeAdd = this.getCookie("storeAdd");
            this.decodeUnicodeAdd(storeAdd)
        },
        mounted: function () {
            this.fetchMovieData();
            this.dateListScroll = new BScroll(this.$refs.dateListWrapper, {click: true, eventPassthrough: true, scrollX: true, scrollY: false, preventDefault: false})
        },
        methods: {
            //门店名称转码
            decodeUnicodeAdd:function (str) {
                str = str.replace(/\\/g, "%");
                let inner_name = unescape(str);
                inner_name = eval("'" + inner_name + "'");
                this.storeAdd = unescape(inner_name.replace(/\u/g, "%u"));
                
                //this.storeAdd = unescape(str);
            },
            //门店名称转码
            decodeUnicodeNm:function (str) {
                str = str.replace(/\\/g, "%");
                let inner_name = unescape(str);
                inner_name = eval("'" + inner_name + "'");
                this.storeName = unescape(inner_name.replace(/\u/g, "%u"));
                
                //this.storeName = unescape(str);
            },
            //取cookie
            getCookie:function(name){
                var strcookie = document.cookie;//获取cookie字符串
                var arrcookie = strcookie.split("; ");//分割
                //遍历匹配
                for ( var i = 0; i < arrcookie.length; i++) {
                var arr = arrcookie[i].split("=");
                if (arr[0] == name){
                return arr[1];
                }
                }
                return "";
            },
            //获取电影信息
            fetchMovieData: function () {
                var _this = this;
                var url = '/general_api/api/CityMovieList?cinema_id='+_this.cinemaId+'&ts='+Date.parse(new Date());
                fetch(url).then(r => r.json()).then(d => {
                    if (d.code == 0) {
                        var movie_id = [];
                        _this.current_swiper_index = _this.index;
                        movie_id.push(d.data[_this.index].properties.id)
                        _this.movie_list = d.data;//影片列表
                        _this.current_plan_list = d.data[_this.index].dates[0].plans;//plan
                        // if (movie_id != _this.movie_id ) {
                        //     _this.current_swiper_index = 0;
                        //     _this.movieId = d.data[0].properties.id;
                        //     _this.current_movie = d.data[0];
                        //     _this.properties = d.data[0].properties;
                        //     _this.actorList = d.data[0].celebritys;
                        //     _this.swiper.slideTo(2);
                        //     _this.fetchPlanData();
                        //     return
                        // }
                        _this.current_movie = d.data[_this.current_swiper_index];
                        _this.properties = d.data[_this.current_swiper_index].properties;
                        _this.actorList = d.data[_this.current_swiper_index].celebritys;
                        _this.swiper.slideTo(_this.current_swiper_index);
                        _this.fetchPlanData();
                        _this.loading = false;
                    }else{
                        _this.loading = false;
                        
                    }
                })
            },
            //获取影片排期
            fetchPlanData: function () {
                var _this = this;
                var mid = _this.movie_id;
                _this.date_list = _this.movie_list[_this.index].dates
            },
            //点击当前日期的排期
            clickDate: function (index) {
                this.date_index = index;
                this.current_plan_list = this.date_list[index].plans;
            },
            //电影详情弹窗
            closeDialog: function () {
                $('body').removeClass('body-no-scroll');
                this.isShow = false;
            },
            godo_seats:function(planId,lastUpdateTime,hallId,plan_date){
            	if(!lastUpdateTime) {
            		lastUpdateTime = '0';
            	}
                this.$router.push({name: 'seats', params: { planId: planId, lastUpdateTime: lastUpdateTime, hallId: hallId,cinemaId: this.cinemaId, plan_date : plan_date}})
                Cookies.set('s_tip', '1');
            },
            godo_cinemas:function(){
                this.$router.push({name: 'cinemaList', params: { mid: '2' }})
            },goMap:function(){
                // location.href="https://3gimg.qq.com/lightmap/v1/marker/index.html?marker=coord:21.21055,110.4016;title:%E6%B9%9B%E6%B1%9F%E4%B8%AD%E5%BD%B1%E6%98%9F%E7%BE%8EIMAX%E5%9B%BD%E9%99%85%E5%BD%B1%E5%9F%8E;addr:%E6%B9%9B%E6%B1%9F%E5%B8%82%E6%B9%9B%E6%B1%9F%E5%B8%82%E9%9C%9E%E5%B1%B1%E5%8C%BA%E4%BA%BA%E6%B0%91%E5%A4%A7%E9%81%93%E5%8D%97116%E5%8F%B7%E9%BC%8E%E7%9B%9B%E5%B9%BF%E5%9C%BA%E5%9B%9B%E6%A5%BCfrom=canary&key=OEIBZ-NBO3J-JL2FM-KYSH3-NP5O3-MDBYO&referer=wepiao&ch=uri-api&ADTAG=uri-api.wepiao#"
                location.href="https://3gimg.qq.com/lightmap/v1/marker/index.html?marker=coord:40.680960,109.842720;title:包头MZC全景影城;addr:内蒙古自治区包头市青山区阿尔丁北大街泊景湾商务楼3楼;from=canary&key=OEIBZ-NBO3J-JL2FM-KYSH3-NP5O3-MDBYO&referer=wepiao&ch=uri-api&ADTAG=uri-api.wepiao#"
            },
            godo_goods:function(){
                location.href="/general_api/tcss/index#/storeList"
            },godo_index:function(){
                this.$router.push({name: 'index'})
            },godo_mine:function(){
                location.href="/general_api/tcss/index#/personalCenter"
            }
        },
        watch: {
            //监听当前电影的变化
            current_movie_index: function () {
                // this.fetchPlanData();
                this.date_list = this.movie_list[this.current_movie_index].dates;
                this.current_plan_list = this.movie_list[this.current_movie_index].dates[0].plans;
                this.current_movie = this.movie_list[this.current_movie_index];
                this.properties = this.movie_list[this.current_movie_index].properties;
                this.actorList = this.movie_list[this.current_movie_index].celebritys;
                var url = 'url(' + this.current_movie.img + ')';
            },
            //监听当前排期变化
            current_plan_list: function () {
                if (this.current_plan_list != null) {
                    for (var i = 0; i < this.current_plan_list.length; i++) {
                        var start_time = parseInt(this.current_plan_list[i].startClock.slice(0, 3));
                        if (start_time > 5 && start_time < 19) {
                            this.current_plan_list[i].time_flag = 1;
                        } else {
                            this.current_plan_list[i].time_flag = 2;
                        }
                    }
                }
            }
        }
    }
</script>
<style>

/******** 影城信息 ********/

.cinema-header-wrapper {
    position: relative;
    padding: .2rem .3rem;
    background: #fff;
    margin-bottom: .2rem;
}

.cinema-header-info {
    margin-right: 1.4rem;
    font-size: .34rem;
    padding-right: 1rem;
}

.cinema-name {
    font-size: .34rem;
    margin-bottom: .26rem;
}

.cinema-header-score {
    font-size: 20px;
    color: #fd2c32;
}

.cinema-header-address {
    margin-bottom: 5px;
    font-size: .26rem;
    color: #A9A9A9;
}

/******** 电影轮播图 ********/

.swiper-slide-active {
    -webkit-transform: scale(1.2);
    transform: scale(1.2);
}

.swiper-bg {
    width: 100%;
    height: 4.4rem;
    background-color: #fff;
    overflow: hidden;
}

.swiper {
    padding-top: .45rem;
    width: 100%;
    height: 2.7rem;
    overflow: initial;
}

.swiper-slide {
    border-radius: .3rem;
    padding: 0.1rem;
    box-sizing: border-box;
}

.sanjiao-block {
    position: absolute;
    left: 0;
    right: 0;
    bottom: 0;
    margin: 0 auto;
    z-index: 10;
    width: 0;
    height: 0;
    border-left: 8px solid transparent;
    border-right: 8px solid transparent;
    border-bottom: 10px solid #fff;
}

/********* 影片名字 *********/

.choose-movie-name-wrapper {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    justify-content: space-between;
    padding-top: .3rem;
}

.choose-movie-name {
    width: 100%;
    text-align: center;
    font-size: .34rem;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
}

.current-movie-score {
    margin-left: .15rem;
    color: #00c203;
}
.actor-list-wrapper {
    margin-top: .08rem;
    text-align: center;
    font-size: .26rem;
    color: #a9a9a9;
}

/************ 影片排期 ************/

.wrapper {
    position: relative;
    width: 100%;
    height: .88rem;
    overflow: hidden;
    margin-top: .2rem;
    background: #fff;
}

.scroller {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    white-space: nowrap;
    z-index: 2;
}

.scroller li {
    display: inline-block;
    line-height: .91rem;
    width: 25%;
    height: 100%;
    text-align: center;
    font-size: .26rem;
    /* margin: 0 10px; */
    /* border-bottom: 1px solid #ccc; */
    color: #828282;
    padding: 0 .25rem;
    box-sizing: border-box;
}

.choose-current-date {
    border-bottom: 2px solid #1aad19;
    color: #1aad19;
}

.wrapper::after {
    position: absolute;
    left: 0;
    right: 0;
    bottom: 0;
    display: block;
    content: '';
    height: 1px;
    margin: 0 auto;
    background: #ccc;
}
.choose-movie-plan-wrapper {
    background: #fff;
}
.choose-movie-plan-wrapper ul {
    width: 100%;
    padding: 0 4%;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    padding-bottom: .5rem;
}

.choose-movie-plan-wrapper ul li {
    display: flex;
    height: 1.82rem;
    border-bottom: 1px solid #ccc;
    padding: .4rem 0;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
}

.choose-movie-plan-wrapper ul li a {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    justify-content: space-between;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    width: 100%;
    height: 100%;
}

.choose-item-wrapper {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    justify-content: space-between;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    width: 86%;
}

.choose-time-logo {
    width: .45rem;
    height: .4rem;
    margin-right: .5rem;
}

.choose-time-logo img {
    display: block;
    width: 100%;
    height: 100%;
}

.choose-time,
.choose-hall,
.choose-price {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-orient: vertical;
    -webkit-box-direction: normal;
    -ms-flex-direction: column;
    flex-direction: column;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: space-between;
    width: 33%;
    height: 100%;
    text-align: center;
}

.choose-time p:first-child {
    font-size: .34rem;
    color: #000;
    text-align: left;
}

.choose-time p:last-child {
    font-size: .26rem;
    color: #a9a9a9;
    text-align: left;
}

.choose-hall p:first-child {
    font-size: .26rem;
    color: #000;
}

.choose-hall p:last-child {
    font-size: .26rem;
    color: #a9a9a9;
}

.choose-price p:first-child {
    font-size: .34rem;
    color: #1aad19;
}

.choose-price p:last-child {
    font-size: .26rem;
    color: #a9a9a9;
}

.choose-btn-wrapper {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    width: 18%;
}

.choose-btn {
    width: 1.13rem;
    height: .6rem;
    font-size: .26rem;
    color: #1aad19;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    border: 1px solid #1aad19;
    border-radius: .06rem;
    line-height: .6rem;
    text-align: center;
}

.map-icon {
    position: absolute;
    top: 0;
    bottom: 0;
    margin: auto 0;
    right: .4rem;
    width: .9rem;
    height: .9rem;
}

.map-icon::after {
    content: '';
    display: block;
    position: absolute;
    left: -.41rem;
    top: 0;
    bottom: 0;
    margin: auto;
    width: 1px;
    height: .72rem;
    background: #ccc;
}

.map-icon a {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-orient: vertical;
    -webkit-box-direction: normal;
    -ms-flex-direction: column;
    flex-direction: column;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    width: 100%;
    height: 100%;
}

.map-icon a svg {
    display: block;
    width: .5rem;
    height: .5rem;
    margin-bottom: .14rem;
}

.map-icon a p {
    text-align: center;
    font-size: .26rem;
    color: #A9A9A9;
}

/******* 电影弹窗 *******/

/*  最外层 设置position定位  */

.dialog {
    position: relative;
    color: #2e2c2d;
    font-size: 16px;
}

/*  遮罩 设置背景层，z-index值要足够大确保能覆盖，高度 宽度设置满 做到全屏遮罩 */

.dialog-cover {
    background: rgba(0, 0, 0, 0.5);
    position: fixed;
    z-index: 200;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
}

/*  内容层 z-index要比遮罩大，否则会被遮盖， */

.dialog-content {
    position: fixed;
    top: 45%;
    left: 50%;
    -webkit-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
    /*  移动端使用felx布局  */
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-orient: vertical;
    -webkit-box-direction: normal;
    -ms-flex-direction: column;
    flex-direction: column;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    z-index: 300;
    width: 100%;
    padding: 0 2.375rem;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
}

.movie-details-popup-post {
    position: relative;
    width: 100%;
    height: 0;
    padding-bottom: 57%;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    background-repeat: no-repeat;
    background-size: cover;
    background-position-y: 30%;
    border-top-left-radius: .25rem;
    border-top-right-radius: .25rem;
    overflow: hidden;
}

.movie-details-popup-post:before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .36);
}

.movie-details-popup-post p {
    position: absolute;
    width: 78.5%;
    top: 50%;
    left: 50%;
    -webkit-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
    text-align: center;
    color: #fff;
    line-height: 1.9375rem;
    font-size: 1.3125rem;
}

.movie-detail-popup-content {
    position: relative;
    width: 100%;
    font-size: .75rem;
    background-color: #fff;
}

.movie-detail-popup-content-box {
    position: relative;
    height: 0;
    padding-bottom: 65.6%;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    overflow: hidden;
}

.movie-detail-popup-content-wrap {
    position: absolute;
    top: 0;
    bottom: 0;
    width: 100%;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    padding: 0 .9375rem;
    padding-top: .875rem;
    overflow: scroll;
    -webkit-overflow-scrolling: touch;
}

.movie-name {
    padding-right: 4.25rem;
    margin-bottom: .3125rem;
    line-height: 1.5rem;
    font-size: 1.125rem;
}

.movie-info {
    margin-bottom: .875rem;
    font-size: .6875rem;
    color: #333;
}

.movie-info span {
    display: inline-block;
    padding: 0 .375rem;
}

.border-left::after {
    border-left-width: 1px;
}

.movie-desc {
    color: #666;
    font-size: .75rem;
    line-height: 1.25rem;
}

.see-more {
    width: 100%;
    background: #fff;
    text-align: center;
    font-size: .875rem;
    height: 2.5rem;
    line-height: 2.5rem;
    border-bottom-left-radius: .25rem;
    border-bottom-right-radius: .25rem;
}

.see-more a {
    display: block;
    color: #fd2c32;
}

.body-no-scroll {
    overflow: hidden;
    position: fixed;
    width: 100%;
    height: 100%;
}

.movie-detail-popup-close {
    position: absolute;
    bottom: -5rem;
    left: 50%;
    -webkit-transform: translateX(-50%);
    transform: translateX(-50%);
    border-radius: 50%;
    width: 2.8125rem;
    height: 2.8125rem;
    text-align: center;
    line-height: 2.8125rem;
    border: 1px solid #fff;
}

.movie-detail-popup-close span {
    color: #fff;
    font-size: 1.25rem;
}

.itemFlag-B {
    position: absolute;
    top: 0.8rem;
    left: 30%;
    display: inline-block;
    width: auto;
    height: 16px;
    line-height: 16px;
    margin-left: 5px;
    padding: 2px;
    background: #ff5252;
    border-radius: 3px;
    color: #fff;
    font-size: 15px;
    margin-bottom: 3px;
}

.itemFlag-C {
    position: absolute;
    top: 0.8rem;
    left: 30%;
    display: inline-block;
    width: auto;
    height: 16px;
    line-height: 16px;
    margin-left: 5px;
    padding: 2px;
    background: #660066;
    border-radius: 3px;
    color: #fff;
    font-size: 15px;
    margin-bottom: 3px;
}
</style>