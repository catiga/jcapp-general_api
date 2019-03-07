<template>
    <div style="width:100%;height: 100%;padding-bottom:120px;">
        
        <div class="goods">
            <div class="menu-wrapper" ref="menuWrapper"  style="overflow:auto;">
                <ul>
                    <li v-for="(item,index) in groupList" :key="index" @click="menuClick(index,item.id,$event)" :class="index === menuCurIndex ? 'menu-selected' : ''">
                        <p>{{item.cat_name_cn}}</p>
                    </li>
                </ul>
            </div>
            <div class="goods-wrapper" ref="goodsWrapper" style="overflow:auto;">
                <div class="goods-wrapper-title" v-if="groupList.length > 0">{{groupList[menuCurIndex].cat_name_cn}}</div>
                <ul>
                    <!-- <div v-if="goodsList.length < 1" style="margin-top:20px;font-size:20px;width:100%;text-align:center;">暂无商品</div> -->
                    <li v-for="(item, index) in goodsList" :key="index" >
                        <div class="goods-item-box">
                            <div class="goods-img">
                                <span v-if="item[0].stock == null || item[0].stock == '0' " style="padding: 2px 4px;background: #CC6666;position: absolute;color: #fff;z-index: 1;">售罄</span>
                                <img :src="item[0].sku_img !== 'http://pe1s.static.pdr365.com/null' ? item[0].sku_img : ''" alt="" @click="goDetails(item)">
                            </div>
                            <div class="goods-item">
                                <div class="goods-item-title">
                                    <!-- <span>{{item[1].goods_price / 100}}元</span> -->
                                    <span>{{item[1].goods_name}}</span>
                                </div>

                                <div class="goods-item-sku" v-for="(item,key,index) in item[0].skus" :key="index">
                                    <span>{{key}}:</span>
                                    <span>{{item}}</span>
                                </div>
                                <div class="goods-item-price">
                                    <span>¥{{item[1].goods_price / 100}}</span>
                                    <!-- <s>{{item[1].goods_original_price / 100}}元</s> -->
                                </div>
                            </div>
                            <div class="goods-control">
                                <!-- <cartControl v-if="item[0].length === 1" :foods="item"></cartControl> -->
                                <cartControl :foods="item" :stock="item[0].stock"></cartControl>
                                <!-- <div v-else-if="item[0].length > 1" @click="goCombo(item)" class="choose-sku">选规格</div> -->
                            </div>
                        </div>

                    </li>
                </ul>
                <div class="bottom-tip" v-if="goodsList.length > 0">已经是最底了哦～</div>
            </div>

            <comboCart v-show="comboShow" :combo="comboData"></comboCart>
            <shopCart v-show="!comboShow" :cartList="cartList" :bottom="bottom" @create-order="createOrder"></shopCart>
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
        <div id="loadingToast" v-if="loading">
            <div class="weui-mask_transparent"></div>
            <div class="weui-toast">
                <i class="weui-loading weui-icon_toast"></i>
                <p class="weui-toast__content">加载中</p>
            </div>
        </div>
        <tabBottom :active="'store'"></tabBottom>
    </div>
</template>


<script>
    import Cookies from 'js-cookie'
    import cartControl from '_c/cartControl/cartControl.vue'
    import shopCart from '_c/shopCart/shopCart.vue'
    import comboCart from '_c/comboCart/comboCart.vue'
    import tabBottom from '_c/tabBottom/tabBottom.vue'
    
    import {
        mapState,
        mapGetters,
        mapActions,
    } from 'vuex'
    export default {
        data() {
            return {
                tableNum: '', //桌号
                loading: false,
                tip_msg: "",
                choose: false,
                GoPay: false,
                StoreList: '',
                menuCurIndex: 0, //菜单栏当前index
                comboData: [], //选中套餐信息
                sid:'',//选中门店ID
                bottom: 'bottom:50px',
                has_sid: false,
            }
        },beforeRouteEnter:function(to, from, next){
            //当组件加载时自动调用此函数 函数结尾必须next();
            document.title = '商品';
            next();
        },
        computed: {
            ...mapState(['groupList', 'goodsList', 'firCatId', 'cartList', 'comboShow', 'logo']),
        },
        created() {
        	let store_id = this.$route.params.sid;
            if(store_id) {
            	this.sid = store_id;
            	Cookies.set('s_id', store_id);
            } else {
            	this.sid = Cookies.get('s_id');	// load default setts
            	//TODO if still empty，should popup selection box;
            }
        	this.CheckLogin();
        },
        mounted: function () {
        	this.cityList();//获取城市列表
        },
        methods: {
            ...mapActions(['getGoodsList', 'getGroupList', 'ToggleComboShow', 'initFirCatId']),
            _initScroll() {
                // 初始化菜单滚动
            },
            menuClick(index, id, event) {
                
                this.menuCurIndex = index;
                let obj = {};
                obj.id = id;
                obj.sid = this.sid;
                console.log(obj);
                this.getGoodsList(obj);
            },
            CheckLogin:function(){
				var page = this;
                var auth_type = Cookies.get('_lac_ap_');
                if(!auth_type) {
                	location.href = '/general_api/h5/wx/auth?type=base&' + new Date().getTime();
                }
			},
            goCombo(item) {
                this.comboData = item;
                this.ToggleComboShow();
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
            createOrder: function () {
            	let page = this;
            	let token = Cookies.get('_lac_k_');
            	let au_type = Cookies.get('_lac_ated_');
            	let part_id = Cookies.get('_lac_op_');
                if(!token) {
                	let apid = Cookies.get('_lac_ap_');
                    if(!apid) {
                    	location.href = '/general_api/h5/wx/auth?type=base&' + new Date().getTime();	//online should be
                    } else {
                    	this.$router.push({name: 'login', params: { apid:apid }})
                    }
                } else {
                	page.GoGoodsPay();
                }
            },
            //获取城市列表
            cityList:function(){
            	let city_no = Cookies.get('city_no');
            	if(!city_no) {
            		var page = this;
	            	var url = '/general_api/api/cityList?ts='+Date.parse(new Date());
			        fetch(url).then(r => r.json()).then(d => {
			        	console.log(d);
			        	if (d.code == '0' && d.data && d.data.length>0) {
			        		Cookies.set('city_no', d.data[0].city_no);
			        		// this.city = d.data;
			        		page.city_name = d.data[0].city_name;
	        				page.city_no = Number(d.data[0].city_no);
			        		this.GetStore(Number(d.data[0].city_no));
	        				Cookies.set('city_name', page.city_name);
			        	}
			        	page.loading = false;
			        });
            	} else {
            		//判断有没有加载过门店
            		if(!this.sid) {
						//需要加载一次门店列表
						this.GetStore(city_no);
					} else {
						this.initFirCatId();
            			this.getGroupList('storeList');
					}
            	}
			},
            GoGoodsPay:function(){
                var page = this;
                var str = "";
                page.loading = true;
                this.cartList.forEach((d, i) => {
                    var cstr = "";
                    // d.forEach((c, i) => {
                    //     cstr += c.id + ",";
                    // });
                    // str += "100" + "," + cstr.slice(0, cstr.length - 1) + "," + d.amount + "," + encodeURIComponent(d.remark) + ";";
                    cstr += d[1].gt + ',' + d[1].id + ',' + d[0].id + ",";
                    str += cstr.slice(0, cstr.length - 1) + "," + d[0].count + "," + '' + ";";
                });
                var gs = str.slice(0, str.length - 1);
                var token = Cookies.get('_lac_k_');
                var cinemaId = page.sid;
                var url = '/general_api/api/auth/createOrder?dss=190&tbn=&gs=' + gs + '&sid=' + cinemaId + '&token=' + token + '&ts='+Date.parse(new Date());
                fetch(url).then(r => r.json()).then(d => {
                	console.log(d);
                    if (d.available) {
                        page.loading = false;
                        let trade_info = d.data[0];
                        let orders = d.data[1];
                        document.cookie = "goodsOrderNo=" + orders[0].order_no + ";path=/";
                        document.cookie = "goosdOrderTnum=" + trade_info.tnum + ";path=/";
                        location.href="/general_api/tcss/index/?#/ConfirmGoodsOrder/tcssGoods";
                        //this.$router.push({name:'ConfirmGoodsOrder' , params: { Goods : 'tcssGoods'  }});
                    } else {
                        page.loading = false;
                		location.href = '/general_api/h5/wx/auth?type=base&' + new Date().getTime();	//online should be
                    }
                });
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
            godo_my:function(){
                his.$router.push({name:'personalCenter'});
            },
            GetStore:function(city_no){
				var page = this;
            	var url = '/general_api/api/StoreList?city_no='+city_no+'&'+new Date().getTime();
		        fetch(url).then(r => r.json()).then(d => {
		        	if (d.code == 0  && d.data && d.data.length>0) {
		        		let cinema_id = 0;
		        		if(d.data[0].cinema_id) {
		        			cinema_id = d.data[0].cinema_id;
		        		}
		        		page.cinemaId = cinema_id;
		        		page.cinemanm = d.data[0].store_name;
                        page.sid = d.data[0].s_id;
                        
                        
		        		//默认设置cookie
		        		console.log('set cinemaId default cookie======' + page.cinemaId);
		        		Cookies.set('cinemaId', page.cinemaId);
		        		Cookies.set('s_id', d.data[0].store_basic);	//一定有
		        		var add = d.data[0].city + d.data[0].zone + d.data[0].address
						page.encodeUnicodeAdd(add);
						page.encodeUnicodeName(d.data[0].physics_name);
						
                        page.getGroupList('storeList').then(r => {
                            console.log('debug promise: sid >>>>> ' + d.data[0].store_basic);
                            let obj = {};
                            obj.id = page.firCatId;
                            obj.sid = d.data[0].store_basic;
                            page.getGoodsList(obj);
                        });
		        	}
		        	page.loading = false;
		        });
			},
			encodeUnicodeAdd: function(str) {
			    var res = [];
			    for ( var i=0; i<str.length; i++ ) {
				res[i] = ( "00" + str.charCodeAt(i).toString(16) ).slice(-4);
			    }
			    //document.cookie = "storeAdd="+"\\u" + res.join("\\u")
			    Cookies.set('storeAdd', "\\u" + res.join("\\u"));
			},
			encodeUnicodeName: function(str) {
			    var res = [];
			    for ( var i=0; i<str.length; i++ ) {
				res[i] = ( "00" + str.charCodeAt(i).toString(16) ).slice(-4);
			    }
			    //document.cookie = "storeName="+"\\u" + res.join("\\u")
			    Cookies.set('storeName', "\\u" + res.join("\\u"));
			},
            haun_cards:function(sid){
                this.sid = sid;
                //用第选中门店ID掉获取分组接口 然后获取商品
            },
            goDetails(item) {
                this.$router.push({name: 'goodsDetails', params:{gid:item[1].id,skuid:item[0].id}});
            }
        },
        watch: {
            firCatId() {
            	console.log('watch_' + this.firCatId + ',this.sid=' + this.sid);
                if(this.firCatId !== -1 && this.sid) {
                	let obj = {};
                	obj.id = this.firCatId;
                	obj.sid = this.sid;
                    this.getGoodsList(obj);
                }
            }
        },
        components: {
            cartControl,
            shopCart,
            comboCart,
            tabBottom
        }
    }
</script>



<style lang="less">
    html,
    body {
        width: 100%;
        height: 100%;
    }

    #app {
        width: 100%;
        height: 100%;
    }

    .scan-top {
        position: relative;
        width: 100%;
        height: 1.6rem;
        background: #000;
        display: flex;
        align-items: center;
    }

    .scan-logo {
        display: inline-block;
        width: .9rem;
        height: .9rem;
        margin-left: .3rem;
        border-radius: .1rem;
    }

    .scan-content {
        margin-left: .2rem;
    }

    .scan-content-nm {
        font-size: .3rem;
        margin-top: .1rem;
        color: #fff;
    }

    .scan-content-num {
        text-align: left;
        font-size: .24rem;
        margin-top: .2rem;
        color: #09BB07;
    }

    .scan-personal-center {
        position: absolute;
        top: .45rem;
        right: .3rem;
        display: flex;
        align-items: center;
    }

    .scan-personal-center p {
        font-size: .24rem;
        display: inline-block;
        color: #fff;
    }

    .scan-personal-center div {
        display: inline-block;
        width: 15px;
        height: 15px;
        overflow: hidden;
    }

    .scan-personal-center div:before {
        content: "";
        display: block;
        width: 9px;
        height: 11px;
        margin-left: 0;
        border: 2px solid #fff;
        border-top: 0 none;
        border-left: 0 none;
        -webkit-transform: rotate(-45deg);
    }




    .goods {
        display: flex;
        width: 100%;
        height: 100%;

        .menu-wrapper {
            width: 1.8rem;
			
			&::after {
				content: "";
				display: block;
				width: 100%;
				// height:120px;
				background: #fff;
			}
            ul {
                height: 100%;
                // border-right: 1px solid #E7E7E7;
                border-top: 1px solid #E7E7E7;

                li {
                    height: .8rem;
                    line-height: .8rem;
                    font-size: .28rem;
                    text-align: center;
                    padding: 0 .3rem;
                    border-bottom: 1px solid #E7E7E7;
                    color: RGBA(141, 147, 152, 1);
                }
            }

            .menu-selected {
                position: relative;
                font-weight: bold;
                color: #000;

                &::after {
                    content: '';
                    display: block;
                    position: absolute;
                    left: 0;
                    top: 0;
                    width: 3px;
                    height: 100%;
                    background: #00BD00;
                }
            }
        }

        &-wrapper {
            flex: 1;
			&::after {
				content: "";
				display: block;
				width: 100%;
				// height:120px;
				background: #fff;
				
			}
            &-title {
                width: 100%;
                padding: .12rem .2rem;
                background: #F0EEF5;
                text-align: start;
                color: #ADAFB1;
                font-size: .28rem;
            }

            ul {
                li {
                    padding: 0 .2rem;
                    box-sizing: border-box;
                }
            }

            .goods {
                &-img {
                    width: 1.2rem;
                    height: 1.2rem;

                    img {
                        height: 100%;
                    }
                }

                &-item-box {
                    display: flex;
                    padding: .3rem 0;
                    position: relative;

                    &::after {
                        content: '';
                        position: absolute;
                        bottom: 0;
                        left: 0;
                        display: block;
                        width: 100%;
                        height: 1px;
                        background: #EDEDED;
                    }
                }

                &-item {
                    flex: 1;
                    text-align: start;
                    margin-left: 1.2em;

                    &-title {
                        font-size: .32rem;
                        font-weight: bold;
                        // margin-left: .3em;
                    }

                    &-sku {
                        display: inline-block;
                        font-size: .26rem;
                        // transform: scale(.8);
                        color: RGBA(141, 147, 152, 1);
                    }

                    &-price {

                        span {
                            color: RGBA(255, 98, 49, 1);
                            font-size: .3rem;
                            font-weight: bold;
                        }

                        s {
                            display: inline-block;
                            font-size: #E7E7E7;
                            transform: scale(.9);
                        }
                    }
                }

                &-control {
                    display: flex;
                    align-items: flex-end;

                    .choose-sku {
                        background: #00BD00;
                        color: #fff;
                        padding: 0 3px;
                        border-radius: 3px;
                    }
                }
            }
        }
    }
    .bottom-tip {
    	text-align: center;
    	padding: 5px;
    }
</style>