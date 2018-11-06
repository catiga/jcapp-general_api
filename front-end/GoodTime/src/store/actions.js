import * as types from './mutation-types'
import {
    groupListData,
    goodsListData
} from '@/api/goods'

//获取卖品分组
export const getGroupList = function ({commit},routeName) {
    return groupListData(routeName).then(res => {
        commit(types.SET_GROUP_LIST, res);
        commit(types.SET_FIR_CAT_ID, res[0].id);
    })
}
//获取商品列表
export const getGoodsList = function ({ commit, state }, data) {
	console.log(data);
    goodsListData(data.id,data.sid).then(res => {
        if (state.cartList.length > 0) {
            for (let i = 0; i < state.cartList.length; i++) {
                if (res.length > 0) {
                    let index = res.findIndex(item => {
                        console.log(item);
                        return item[0].id === state.cartList[i][0].id && item[1].id === state.cartList[i][1].id
                    });
                    if (index !== -1) {
                        res[index][0].count = state.cartList[i][0].count
                    }
                }
            }

        }
        commit(types.SET_GOODS_LIST, res)
    })
}
//监听购物车
export const ListenCart = function ({ commit, state }) {
    for (let i = 0; i < state.cartList.length; i++) {
        if (state.goodsList.length > 0) {
            let index = state.goodsList.findIndex(item => {
                return item[0].id === state.cartList[i][0].id && item[1].id === state.cartList[i][1].id
            });
            if (index !== -1) {
                state.goodsList[index][0].count = state.cartList[i][0].count
            }
        }
    }
}
//加入购物车
export const AddToCart = function ({ commit, state }, { data }) {
    if (state.cartList.length > 0) {
        let flag = false;
        //循环里return进行打断
        for (let i = 0; i < state.cartList.length; i++) {
            if (state.cartList[i][0].id === data[0].id) {
                flag = false;
                state.cartList[i][0].count = data[0].count;
                return;
            } else {
                flag = true;
            }
        }
        if (flag) {
            state.cartList.push(data);
        }
    } else {

        state.cartList.push(data);
    }
    commit(types.ADD_TO_CART, state.cartList)
}
//从购物车删除
export const ReleaseToCart = function ({ commit, state }, { data }) {
    //循环里return进行打断
    for (let i = 0; i < state.cartList.length; i++) {
        if (state.cartList[i][0].id === data[0].id) {
            if (data[0].count === 0) {
                state.cartList.splice(i, 1);
            }
            return;
        }
    }
}

//清空购物车
export const ClearCart = function ({ commit, state }) {
    if(state.goodsList.length > 0) {
        state.goodsList.forEach(item => {
            item[0].count = 0;
        })
    }
    let arr = [];
    commit(types.CLEAR_CART, arr)
}

//套餐弹窗展示
export const ToggleComboShow = function ({ commit, state }) {
    let flag = !state.comboShow;
    commit(types.TOGGLE_COMBO_SHOW, flag)
}


//初始化firCatId
export const initFirCatId = function({commit, state}) {
    commit(types.SET_FIR_CAT_ID, -1);
}





