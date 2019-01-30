<template>
	<div>
		<div class="details-bg"></div>
		<div class="bindCard-box">
			<form>
				<div class="bindCard-top">
					<label class="kahao">门店/影城</label>
					<select v-model="card_store" id="select" class="kahao_d" style="width: 60%;">
						<option value="400">测试影城</option>
					</select>
				</div>
				<div class="bindCard-bot">
					<label class="kahao">卡号</label> 
					<input class="bindCard-bot-input" v-model="card_num" id="Kpassword" type="text" name="Kpassword" placeholder="请填写会员卡号">
				</div>
				<div class="bindCard-bot">
					<label class="kahao">密码</label> 
					<input class="bindCard-bot-input" v-model="card_pwd" id="Kpassword" type="text" name="Kpassword" placeholder="请填写正确的密码">
				</div>
			</form>
		</div>
		
		
		<div class="bindCard-bind">
			<div v-on:click="do_bind"><p class="bind-bd">绑定</p></div>
			<p class="bind-hint">注：测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试</p>
		</div>
			
	</div>
</template>
<script>
	import Cookies from 'js-cookie'
	import {mapState,mapActions} from 'vuex'
	
	export default {
		data(){
			return {
				loading:true,
				synopsis:'synopsis-none',
				showBtn:'showBtn',
				card_num: '',
				card_pwd: '',
				card_store: ''
			};
		},beforeRouteEnter:function(to, from, next){
			//当组件加载时自动调用此函数 函数结尾必须next();
			document.title="绑定会员卡";
			next();
		},created:function(){
			//组件加载完成会自动调用此方法
		},methods:{
			back:function(){
				//自定义返回上一页函数 可以copy到每一个组件中方便使用
				router.go(-1);
			},godo_paycard:function(){
				location.href="/general_api/tcss/index#/purchasingCard/purchasing"
				// this.$router.push({name: 'purchasingCard'})
			},do_bind:function() {
				let page = this;
				let token = Cookies.get('_lac_k_');
				let card_num = this.card_num;
				let card_pwd = this.card_pwd;
				let card_store = this.card_store;
				if(!card_num || !card_pwd) {
					weui.dialog({
    					content: '请输入会员卡号和密码',
					    buttons: [{
					        label: '确定',
					        type: 'primary',
					        onClick: function () { }
					    }]
					});
					return;
				}
				
				let loading = weui.loading('加载中');
				
				var url = '/general_api/api/auth/bind_my_card?token=' + token + '&mc_num='+card_num+'&mc_pwd='+card_pwd+'&s_id='+card_store+'&'+new Date().getTime();
		        fetch(url).then(r => r.json()).then(d => {
		        	loading.hide();
		        	if (d.available) {
		        		weui.dialog({
	    					content: '会员卡绑定成功',
						    buttons: [{
						        label: '确定',
						        type: 'primary',
						        onClick: function () {page.$router.go(-1); }
						    }]
						});
		        		
		        	}else{
		        		weui.dialog({
	    					content: d.messages[0],
						    buttons: [{
						        label: '确定',
						        type: 'primary',
						        onClick: function () { }
						    }]
						});
		        	}
		        });
			}
		}
	}
</script>
<style>
.bindCard-box{
	width: 100%;
	height: 2.6rem;
	background: #FFFFFF;
	margin-top: .3rem;
}
.bindCard-top{
	width: auto;
	height: .88rem;
	line-height: .88rem;
	margin-left: .3rem;
	font-size: .34rem;
	color: #000000;
}
.kahao_d{
	font-size: .34rem;
}
.bindCard-bot-input{
	font-size: .32rem;
    border: none!important;
    outline: none!important;
    display: inline-block!important;
}
.kahao{
	display: inline-block;
	width: 1.5rem;
}
.bindCard-bot{
	width: auto;
	height: .88rem;
	line-height: .88rem;
	margin-left: .3rem;
	font-size: .34rem;
	color: #000000;
	border-top: 1px solid #efeff4;
}
.bindCard-bind{
	width: auto;
	padding: 0 .3rem;
}
.bindCard-bind div{
	margin-top: .6rem;
	width: 100%;
	text-align: center;
    height: .88rem;
    line-height: .88rem;
    border: 1px solid #029400;
    border-radius: .1rem;
    background: #09BB07;
    color: #FFFFFF;
    font-size: .36rem;	
}
.bind-bd{
    height: .88rem;
    line-height: .88rem;
	font-size: .36rem;
	color: #FFFFFF;
}
.bind-hint{
	margin-top: .2rem;
	font-size: .24rem;
	color: #999999;
	letter-spacing: 1px;
}

</style>

