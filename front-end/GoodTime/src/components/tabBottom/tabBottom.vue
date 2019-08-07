<template>
    <div class="bottom-tab">
    	<a href="javascript:void(0)" class="store" :data-status="active === 'store' ? 'active' : ''" @click="store">
            <div class="icon"></div>
        </a>
        <a href="javascript:void(0)" class="movie" :data-status="active === 'movie' ? 'active' : ''" @click="movie">
            <div class="icon"></div>
        </a>
        <a href="javascript:void(0)" class="cinema" :data-status="active === 'cinema' ? 'active' : ''" @click="cinema">
            <div class="icon"></div>
        </a>
        <a href="javascript:void(0)" class="mine" :data-status="active === 'mine' ? 'active' : ''" @click="mine">
            <div class="icon"></div>
        </a>
    </div>
</template>

<script>
	import Cookies from 'js-cookie'
	
    export default {
    	props: {
    		active: {
    			type: String,
    			default: ''
    		}
    	},
        methods: {
            movie: function () {
                this.$router.push({
                    name: 'index',
                    params: {
                        mid: '2'
                    }
                })
            },
            store: function () {
                this.$router.push({
                    name: 'storeList'
                })
            },
            cinema: function () {
                this.$router.push({
                    name: 'cinemaList',
                    params: {
                        mid: '2'
                    }
                })
            },
            mine: function () {
            	//this.$router.push({name:'personalCenter'});
            	let token = Cookies.get('_lac_k_');
                if(!token) {
                	let apid = Cookies.get('_lac_ap_');
                    if(!apid) {
                    	location.href = '/general_api/h5/wx/auth?type=base&' + new Date().getTime();	//online should be
                    } else {
                    	this.$router.push({name:'login', params:{apid:apid}});
                    }
                } else {
                	this.$router.push({name:'personalCenter'});
                }
            },
        }
    }
</script>

<style lang="less" scoped>
    .bottom-tab {
        position: fixed;
        left: 0;
        right: 0;
        bottom: 0;
        height: 50px;
        z-index: 50;
        box-sizing: border-box;
        width: 100%;
        display: -webkit-box;
        display: -webkit-flex;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-orient: horizontal;
        -webkit-box-direction: normal;
        -webkit-flex-direction: row;
        -ms-flex-direction: row;
        flex-direction: row;
        -webkit-flex-wrap: nowrap;
        -ms-flex-wrap: nowrap;
        flex-wrap: nowrap;
        -webkit-box-align: center;
        -webkit-align-items: center;
        -ms-flex-align: center;
        align-items: center;
        padding-top: 6px;
        background: #f7f7fa;
        border-top: 1px solid #f7f7f8;

        a {
            display: block;
            text-align: center;
            -webkit-box-flex: 1;
            -webkit-flex: 1 1 33%;
            -ms-flex: 1 1 33%;
            flex: 1 1 33%;
            color: #8a869e;

            .icon {
                width: 22px;
                height: 22px;
                margin: 0 auto;
            }

            &[data-status=active] {
                color: #1aad19;
            }
        }


        .movie {
             &.icon {
                background-image: url(https://ex.piaodaren.com/img_server/_common/icon/movie_f.png);
                background-position: center center;
                background-size: cover;
            }

            &[data-status=active] .icon {
                background-image: url(https://ex.piaodaren.com/img_server/_common/icon/movie_b.png);
                    background-position: center center;
                background-size: cover;
            }

            &:after {
                content: '电影';
                display: block;
                font-size: 10px;
                padding-top: .125rem;
                padding-top: .6vmin;
            }
        }


        .store {
            &.icon {
                background-image: url(https://ex.piaodaren.com/img_server/_common/icon/goods_f.png);
                    background-position: center center;
                background-size: cover;
            }

            &[data-status=active] .icon {
                background-image: url(https://ex.piaodaren.com/img_server/_common/icon/goods_b.png);
                    background-position: center center;
                background-size: cover;
            }

            &:after {
                content: '商品';
                display: block;
                font-size: 10px;
                padding-top: .125rem;
                padding-top: .6vmin;
            }
        }
        .cinema {
            .icon {
                background-image: url(https://ex.piaodaren.com/img_server/_common/icon/cinema_f.png);
                    background-position: center center;
                background-size: cover;
            }

            &[data-status=active] .icon {
                background-image: url(https://ex.piaodaren.com/img_server/_common/icon/cinema_b.png);
                    background-position: center center;
                background-size: cover;
            }

            &:after {
                content: '门店';
                display: block;
                font-size: 10px;
                padding-top: .125rem;
                padding-top: .6vmin;
            }
        }



        .mine {
            .icon {
                background-image: url(https://ex.piaodaren.com/img_server/_common/icon/user_f.png);
                    background-position: center center;
                background-size: cover;
            }

            &[data-status=active] .icon {
                background-image: url(https://ex.piaodaren.com/img_server/_common/icon/user_b.png);
                    background-position: center center;
                background-size: cover;
            }

            &:after {
                content: '我的';
                display: block;
                font-size: 10px;
                padding-top: .125rem;
                padding-top: .6vmin;
            }
        }


    }
</style>