<template lang="html">

    <div class="cart" v-if="stock">
        <transition name="fadeRotate">
            <div class="cart-reduce" v-show="foods[0].count>0" @click="reduce()">
                <i class="iconfont icon-jianhao" style="font-size:22px;"></i>
            </div>
        </transition>
        <transition name="fade">
            <div class="cart-count" v-show="foods[0].count>0" style="font-size: 0.32rem;">
                {{foods[0].count}}
            </div>
        </transition>
        <div class="cart-add" @click="add($event)">
            <i class="iconfont icon-jiahao2" style="font-size:22px"></i>
        </div>
        <div class="ball-container">
            <transition name="drop" v-on:before-enter="beforeEnter" v-on:enter="enter" v-on:after-enter="afterEnter"
                v-for="(ball,index) in balls">
                <div class="ball" v-show="ball.show">
                    <div class="inner inner-hook"></div>
                </div>
            </transition>
        </div>
    </div>
    <div class="cart" v-else>
    	<div class="cart-add">
            <i class="iconfont icon-jiahao2" style="font-size:22px;color:#ccc;"></i>
        </div>
    </div>

</template>

<script>
    import {
        mapActions
    } from 'vuex'

    export default {
        name: "cartControl",
        props: {
            foods: {},
            stock: "",
        },
        data() {
            return {
                count: 0,
                balls: [{
                    show: false
                }, {
                    show: false
                }, {
                    show: false
                }, {
                    show: false
                }, {
                    show: false
                }],
                dropBalls: [],
            }
        },
        methods: {
            ...mapActions(['AddToCart', 'ReleaseToCart', 'ListenCart']),
            reduce() {
                if (this.foods[0].count > 0) {
                    this.foods[0].count--;
                    this.count--;
                } else {
                    this.foods[0].count = 0;
                    this.count = 0;
                }
                this.ReleaseToCart({
                    data: this.foods,
                });
                this.$emit('reduce');
            },
            add(event) {
                this.foods[0].count++;
                this.count++;
                this.AddToCart({
                    data: this.foods,
                });
                this.drop(event.target);
                this.$emit('add');
            },
            drop(el) {
                for (let i = 0, l = this.balls.length; i < l; i++) {
                    let ball = this.balls[i]
                    if (!ball.show) {
                        ball.show = true
                        ball.el = el
                        this.dropBalls.push(ball)
                        return
                    }
                }
            },
            beforeEnter(el) {
                let count = this.balls.length;
                while (count--) {
                    let ball = this.balls[count];
                    if (ball.show) {
                        let rect = ball.el.getBoundingClientRect();
                        let x = rect.left - 32;
                        let y = -(window.innerHeight - rect.bottom + 40);
                        el.style.display = '';
                        el.style.webkitTransform = `translate3d(0,${y}px,0)`;
                        el.style.transform = `translate3d(0,${y}px,0)`;
                        let inner = el.querySelector('.inner-hook');
                        inner.style.webkitTransform = `translate3d(${x}px,0,0)`;
                        inner.style.transform = `translate3d(${x}px,0,0)`;
                    }
                }
            },
            enter(el) {
                el.offsetHeight // 触发浏览器重绘，offsetWidth、offsetTop等方法都可以触发
                this.$nextTick(() => {
                    el.style.webkitTransform = 'translate3d(0,0,0)'
                    el.style.transform = 'translate3d(0,0,0)'
                    let inner = el.querySelector('.inner-hook')
                    inner.style.webkitTransform = 'translate3d(0,0,0)'
                    inner.style.transform = 'translate3d(0,0,0)'
                })
            },
            afterEnter(el) {
                let ball = this.dropBalls.shift()
                if (ball) {
                    ball.show = false
                    el.style.display = 'none'
                }
            }
        },
        watch: {
             count() {
                this.ListenCart();
            }
        }
    }
</script>

<style lang="less">
    .cart {
        display: flex;
        align-items: center;
        &-reduce {
            display: inline-block;
            padding: 6px;
            padding-bottom: 0;
            color: #1aad19;
            transition: all .4s linear;

            .inner {
                color: #1aad19;
                transition: all .4s linear;
            }

            &.fadeRotate-enter-active,
            &.fadeRotate-leave-active {
                transform: translate3d(0, 0, 0);

                .inner {
                    display: inline-block;
                    transform: rotate(0);
                }
            }

            &.fadeRotate-enter,
            &.fadeRotate-leave-active {
                opacity: 0;
                transform: translate3d(24px, 0, 0);

                .inner {
                    transform: rotate(180deg);
                }
            }
        }

        &-count {
            display: inline-block;
            vertical-align: top;
            text-align: center;
            padding: 6px 0;
            padding-bottom: 0;
        }


        &-add {
            display: inline-block;
            vertical-align: top;
            color: #1aad19;
            padding: 6px;
            padding-bottom: 0;
        }
    }

    .ball-container {
        .ball {
            position: fixed;
            left: 32px;
            bottom: 22px;
            z-index: 200;
        }

        .ball.drop-enter {
            transition: all 0.4s cubic-bezier(0.49, -0.29, 0.75, 0.41);

            .inner {
                width: 16px;
                height: 16px;
                border-radius: 50%;
                background: #1aad19;
                transition: all 0.4s linear;
            }
        }

        .ball.drop-enter-active {
            transition: all 0.4s cubic-bezier(0.49, -0.29, 0.75, 0.41);

            .inner {
                width: 16px;
                height: 16px;
                border-radius: 50%;
                background: #1aad19;
                transition: all 0.4s linear;
            }
        }
    }
</style>