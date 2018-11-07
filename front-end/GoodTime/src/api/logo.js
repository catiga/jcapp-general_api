import axios from 'axios'

export const userData = function() {
    let url = '/general_api/api/ro_code_pay?ts='+Date.parse(new Date());
    let obj = {};
    return axios.get(url).then(res => {
        console.log(res);
        return obj = res.data;
    })
}