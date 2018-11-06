<template lang="html">
    <div class="" v-show="listShow">
        <div class="comboCart">
            <div class="content">
                <div class="add-to-cart"> 加入购物车 </div>
            </div>
            <transition name="transHeight" v-if="combo.length > 0">
                <div class="combocart-list" v-show="listShow">
                    <div class="food-info food-item">
                        <div class="food-info-head">
                            <div class="img">
                                <img :src="combo[0].sku_img">
                            </div>
                            <div>
                                <p class="name">{{combo[1].goods_name}}</p>
                                <p class="price">{{combo[1].goods_price / 100}}元</p>
                            </div>
                        </div>
                    </div>
                    <div class="food-sku food-item">
                        <div class="title">商品规格</div>
                        <ul>
                            <li v-for="(m,k,i) in combo[0].skus" :key="i" :class="i === skuIndex ? 'active' : ''" @click="skuIndex=i">{{m}}</li>
                        </ul>
                    </div>
                    <div class="food-sku food-item">
                        <div class="title">特殊要求</div>
                        <ul>
                            <li v-for="(m,i) in remark_list" :key="i" :class="i === remarkIndex ? 'active' : ''" @click="remarkIndex=i">{{m}}</li>
                        </ul>
                    </div>
                </div>
            </transition>
        </div>
        <transition name="fade-backdrop">
            <div class="backdrop" v-show="listShow" @click="hideBackdrop"></div>
        </transition>
    </div>
</template>

<script>
    import cartControl from '_c/cartControl/cartControl.vue'

    export default {
        name: 'comboCart',
        props: {
            combo: {
                type: Array,
                default: []
            },
        },
        data() {
            return {
                skuIndex: 0,
                remarkIndex: 0,
                remark_list: ['标准糖', '少糖', '多糖', '去冰', '少冰', '##']
            }
        },
        computed: {
            listShow() {
                return this.$store.state.comboShow;
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
            hideBackdrop() {
                this.$store.dispatch('ToggleComboShow');
            },
        },
        components: {
            cartControl,
        }
    }
</script>

<style lang="less" scoped>
    .comboCart {
        position: fixed;
        left: 0;
        bottom: 0;
        width: 100%;
        height: 48px;
        z-index: 50;

        .combocart-list {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            background: #fff;
            transform: translate3d(0, -100%, 0);
            z-index: -1;

            &.transHeight-enter-active {
                transition: all 0.5s;
            }

            &.transHeight-leave-active {
                transition: all 0.5s;
                transform: translate3d(0, 0, 0);
            }

            &.transHeight-enter {
                transform: translate3d(0, 0, 0);
            }

            .food {
                &-item {
                    position: relative;
                    padding: 10px 0;
                    &::after {
                        content: '';
                        display: block;
                        position: absolute;
                        width: 100%;
                        height: 1px;
                        bottom: 0;
                        left: 0;
                        background: RGBA(0, 0, 0, 0.2);
                        transform: scaleY(.5);
                    }
                }

                &-info {
                    &-head {
                        width: 100%;
                        display: flex;

                        .img {
                            width: 1rem;
                            height: 1rem;
                            margin-right: 10px;

                            img {
                                height: 100%;
                            }
                        }

                        .name {
                            font-weight: bold;
                        }

                        .price {
                            color: #FF6231;
                            text-align: start;
                            font-size: 10px;
                        }
                    }
                }
                &-sku {
                    .title {
                        width: 5em;
                        margin-bottom: 10px;
                        text-align: start;
                        transform: scale(.8);
                        margin-left: -.5em;
                    }
                    ul {
                        display: flex;
                        li {
                            margin-right: 10px;
                            background:#DFE3E4;
                            border-radius: 3px;
                            padding: 0 5px;
                            font-size: 10px;
                            &.active {
                                background:#00b43c;
                                color: #fff;
                            }
                        }
                    }
                }
            }



        }

        .add-to-cart {
            width: 100%;
            height: 48px;
            background: #00b43c;
            color: #fff;
            font-size: 16px;
            line-height: 48px;
            text-align: center;
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

        &.fade-backdrop-enter-active {
            transition: opacity 0.5s;
        }

        &.fade-backdrop-leave-active {
            transition: opacity 0.5s;
            opacity: 0;
        }

        &.fade-backdrop-enter {
            opacity: 0;
        }
    }
</style>