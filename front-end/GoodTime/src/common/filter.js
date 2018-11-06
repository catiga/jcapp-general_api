let toFixed = val => Number(val).toFixed(2)

let toSlice = val => Number(val.slice(0, val.indexOf('/'))) / 10;

export default {toFixed, toSlice}