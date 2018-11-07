exports.install = function (Vue, options) {
    Vue.prototype.logo = function (){//全局函数1
        let url = '/general_api/api/project_by_id?ts='+Date.parse(new Date());
        fetch(url).then(r => r.json()).then(d => {
            console.log('global varibal');
            console.log(d);
        })
     };
 };