import axios, {AxiosRequestConfig} from "axios";
const baseURL = 'http://localhost:8080'

export type Config = AxiosRequestConfig<any> | undefined
export type Id = string | number

const baseApiService = <T>(resources: string) => {
    let url: string = `${baseURL}${resources}`;

    return {
        // Get a list of resources
        list: (config?: Config) => axios.get(url, config),

        // Get a single resource by its id
        get: (id: Id, config?: Config) => axios.get(`${url}/${id}`, config),

        // Create a new resource
        create: (payload: T) => axios.post(url, payload),

        // Replace an existing resource with payload
        update: (id: Id, payload: T) => axios.put(`${url}/${id}`, payload),

        // Merge new payload into a resource
        patch: (id: Id, payload: T) => axios.patch(`${url}/${id}`, payload),

        // Remove a resource by its id
        remove: (id: Id) => axios.delete(`${url}/${id}`),
    };
};

export { baseURL, baseApiService };