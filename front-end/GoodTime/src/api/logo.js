import axios from 'axios'

export const userData = function() {
    let url = '/general_api/api/project_by_id?ts='+Date.parse(new Date());
    let obj = {};
    return axios.get(url).then(res => {
        return obj = res.data;
    })
}