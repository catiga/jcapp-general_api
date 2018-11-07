import axios from 'axios'

export const userData = () => {
    let url = '/general_api/api/project_by_id?ts='+Date.parse(new Date());
    let obj = {};
    return axios.get(url).then(res => {
        obj = res.data;
        console.log(obj);
        return obj;
    })
}