import env from './env'

const DEV_URL = 'http://localhost:8080'
const PRO_URL = 'http://106.75.114.46/'

export default env === 'development' ? DEV_URL : PRO_URL
