import {baseApiService, baseURL, Config, Id} from "../../shared/base_api_service.ts";
import axios from "axios";


export const infoAPI = {
    label: {
        ...baseApiService("/info/label")
    },
    post: {
        get: (id: Id, config?: Config) => axios.get(`${baseURL}/info/post/${id}`, config),
    }
}
