import {baseApiService, baseURL, Config, Id} from "../../shared/base_api_service.ts";
import axios from "axios";


export const labelAPI = {
    info: {
        ...baseApiService("/label/info")
    },
    post: {
        get: (id: Id, config?: Config) => axios.get(`${baseURL}/label/${id}/post`, config),
    }
}
