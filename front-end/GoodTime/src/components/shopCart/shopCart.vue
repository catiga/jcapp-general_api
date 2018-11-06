<template lang="html">
    <div class="">
        <div class="shopCart" :style="bottom">
            <div class="content">
                <div class="content-left" @click="listToggle">
                    <div class="logo-wrapper">
                        <div class="badge" v-show="totalCount">
                            {{totalCount}}
                        </div>
                        <div class="logo" :class="{'active':totalPrice}">
                            <i class="iconfont icon-ai66"></i>
                        </div>
                    </div>
                    <div class="price" :class="{'active':totalPrice}">
                        ￥{{totalPrice | toFixed}}
                    </div>
                </div>
                <div class="content-right" :class="{'enough':totalPrice}" @click="createOrder"> 去结算 </div>
            </div>
            

            <transition name="transHeight">
                <div class="shopcart-list" v-show="listShow">
                    <div class="list-header">
                        <p class="title">购物车</p>
                        <span class="empty" @click="setEmpty">
                            <i class="iconfont icon-lajitong" style="font-size:12px;"></i>
                            清空购物车
                        </span>
                    </div>
                    <div class="list-content" ref="foodlist">
                        <ul>
                            <li class="food" v-for="item in cartList">
                                <div class="name">
                                    <p>{{item[1].goods_name}}</p>
                                    <div style="display:flex;">
                                        <p v-for="(item,key,index) in item[0].skus" :key="index" style="font-size:12px;color:#ccc;">
                                        <span>{{key}}:</span>
                                        <span>{{item}}</span>
                                    </p>
                                    </div>
                                </div>
                                <div class="price">
                                    <span>￥{{(Number(item[1].goods_price) / 100) * Number(item[0].count) | toFixed}}</span>
                                </div>
                                <div class="cartcontrol-wrapper">
                                    <cartControl :foods="item" :stock="true"></cartControl>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </transition>
        </div>
        <transition name="fade-backdrop">
            <div class="backdrop" v-show="showBackdrop" @click="hideBackdrop"></div>
        </transition>
    </div>
</template>

<script>
    import BScroll from 'better-scroll'
    import cartControl from '_c/cartControl/cartControl.vue'

    export default {
        name: 'shopCart',
        props: {
            cartList: {
                type: Array,
                default: []
            },
            bottom: {
                type: String,
                default: 'bottom:0'
            }
        },
        data() {
            return {
                listShow: false
            }
        },
        mounted() {},
        computed: {
            showBackdrop() {
                if (this.listShow && this.totalPrice) {
                    return true
                }
                this.listShow = false
                return false
            },
            totalCount() {
                let count = 0
                this.cartList.forEach(item => {
                    count += item[0].count
                })
                return count
            },
            totalPrice() {
                let total = 0
                this.cartList.forEach(item => {
                    if (item[0].count) {
                        total += (Number(item[1].goods_price) / 100) * Number(item[0].count)
                    }
                })
                return total
            },
        },
        methods: {
            _initScroll() {
                this.foodlistScroll = new BScroll(this.$refs.foodlist, {
                    click: true
                });
            },

            hideBackdrop() {
                this.listShow = false
            },
            listToggle() {
                if (!this.cartList.length) {
                    return
                }
                this.listShow = !this.listShow
                if (this.listShow) {
                    this.$nextTick(() => {
                        if (!this.foodlistScroll) {
                            this._initScroll()
                        } else {
                            this.foodlistScroll.refresh()
                        }
                    })
                }
            },
            createOrder() {
                if(this.totalPrice) {
                    this.$emit('create-order');
                }                 
            },
            setEmpty() {
                this.$store.dispatch('ClearCart');
             }

        },
        components: {
            cartControl,
        }
    }
</script>

<style lang="less" scoped>
    .shopCart {
        position: fixed;
        left: 0;
        width: 100%;
        height: 60px;
        z-index: 50;

        .content {
            display: flex;
            background: #fff;
            box-shadow: 1px 10px 10px 10px RGBA(179, 179, 179, 0.34);

            .content-left {
                flex: 1;
                height: 60px;
                text-align: start;

                .logo-wrapper {
                    display: inline-block;
                    vertical-align: top;
                    position: relative;
                    height: 60px;
                    line-height: 60px;
                    border-radius: 50%;
                    width: 56px;
                    margin-left: 6px;
                    margin-right: 8px;
                    box-sizing: border-box;
                    text-align: center;

                    .badge {
                        position: absolute;
                        top: 8px;
                        right: 5px;
                        background: #f01414;
                        color: #fff;
                        width: 16px;
                        height: 16px;
                        line-height: 16px;
                        font-size: 10px;
                        // box-shadow: 0px 4px 8px 0px rgba(0, 0, 0, 0.4);
                        font-weight: 700;
                        border-radius: 50%;
                        text-align: center;
                    }

                    .logo {
                        width: 100%;
                        height: 100%;
                        border-radius: 50%;
                        font-size: 24px;
                        line-height: 60px;
                        font-weight: 700;
                        i {
                            font-size:34px;
                            color:RGBA(184, 185, 186, 1);
                        }
                    }

                    .logo.active {
                        color: #fff;
                        i {
                            color:#1aad19;
                        }
                    }
                }

                .price {
                    display: inline-block;
                    font-size: 22px;
                    // margin-top: 12px;
                    padding-right: 12px;
                    box-sizing: border-box;
                    font-weight: 700;
                    line-height: 60px;
                    border-right: 1px solid rgba(255, 255, 255, 0.1);
                }

                .price.active {
                    color: #000;
                }

                .desc {
                    position: relative;
                    display: inline-block;
                    vertical-align: top;
                    margin: 12px 0 0 12px;
                    font-size: 10px;
                    color: rgba(255, 255, 255, 0.4);
                    font-weight: 700;
                    line-height: 24px;
                }
            }

            .content-right {
                flex: 0 0 120px;
                font-size: 20px;
                font-weight: 700;
                background: RGBA(184, 185, 186, 1);
                color: #fff;
                line-height: 60px;
                letter-spacing: 1px;
                text-align: center;
            }

            .content-right.enough {
                background: #00b43c;
                color: #fff;
            }
        }



        .shopcart-list {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            background: #fff;
            transform: translate3d(0, -100%, 0);
            z-index: -1;

            .list-header {
                position: relative;
                height: 40px;
                line-height: 40px;
                background: #f3f5f7;
                text-align: start;
                // border-bottom: 1px solid rgba(7, 17, 27, 0.1);

                &::after {
                    content: '';
                    display: block;
                    position: absolute;
                    left: 0;
                    top: 0;
                    width: 3px;
                    height: 100%;
                    background: #00b43c;
                }

                .title {
                    display: inline-block;
                    font-size: 14px;
                    font-weight: 200;
                    color: #07111b;
                    padding-left: 18px;
                }

                .empty {
                    position: absolute;
                    right: 8px;
                    font-size: 12px;
                    color: RGBA(185, 192, 195, 1);
                    padding: 0 10px;
                }
            }

            .list-content {
                max-height: 217px;
                overflow: hidden;

                .food {
                    position: relative;
                    display: flex;
                    height: 54px;
                    margin: 0 18px;
                    // border-bottom: 1px solid rgba(7, 17, 27, 0.1);

                    .name {
                        flex: 1;
                        display: flex;
                        flex-direction: column;
                        justify-content: center;
                        font-size: 14px;
                        color: #07111b;
                        font-weight: 700;

                        p {
                            text-align: start;
                        }
                    }

                    .price {
                        font-size: 14px;
                        font-weight: 700;
                        padding: 0 12px 0 18px;
                        line-height: 54px;
                    }

                    .cartcontrol-wrapper {
                        font-size: 14px;
                        margin-top: 6px;
                    }
                }
            }
        }

        .shopcart-list.transHeight-enter-active {
            transition: all 0.5s;
        }

        .shopcart-list.transHeight-leave-active {
            transition: all 0.5s;
            transform: translate3d(0, 0, 0);
        }

        .shopcart-list.transHeight-enter {
            transform: translate3d(0, 0, 0);
        }
    }

    .backdrop {
        position: fixed;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(7, 17, 27, 0.6);
        backdrop-filter: blur(10px);
        z-index: 40;
    }

    .backdrop.fade-backdrop-enter-active {
        transition: opacity 0.5s;
    }

    .backdrop.fade-backdrop-leave-active {
        transition: opacity 0.5s;
        opacity: 0;
    }

    .backdrop.fade-backdrop-enter {
        opacity: 0;
    }
</style>