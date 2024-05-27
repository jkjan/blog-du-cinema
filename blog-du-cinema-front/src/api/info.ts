import { inject} from 'vue'
const baseURL = "http://localhost:8080"

export default {
    name: "info",
    setup() {
        const axios: any = inject('axios')

        const getCategory = () => {
            return axios.get(baseURL + "/info/category")
        }

        return {getCategory}
    }
}