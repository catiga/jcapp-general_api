import * as types from './mutation-types'

const mutations = {
    //分组列表
    [types.SET_GROUP_LIST](state, data) {
        state.groupList = data
    },
    //商品列表
    [types.SET_GOODS_LIST](state, data) {
        state.goodsList = data
    },
    //默认第一个分组id
    [types.SET_FIR_CAT_ID](state, data) {
        state.firCatId = data
    },
    //加入购物车
    [types.ADD_TO_CART](state, data) {
        state.cartList = data
    },
    //从购物车删除
    [types.RELEASE_TO_CART](state, data) {
        state.cartList = data
    },
    //清空购物车
    [types.CLEAR_CART](state, data) {
        state.cartList = data
    },
    //监听购物车
    [types.LISTEN_CART](state, data) {
        state.goodsList = data
    },
    //套餐弹窗展示
    [types.TOGGLE_COMBO_SHOW](state, data) {
        state.comboShow = data
    },
    //获取logo
    [types.GET_LOGO](state, data) {
        state.logo = data
    }
}

export default mutations
