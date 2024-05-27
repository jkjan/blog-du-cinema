import axios, {AxiosRequestConfig} from "axios";
const base = 'http://localhost:8080/info'

export type Config = AxiosRequestConfig<any> | undefined
export type Id = string | number

const baseApiService = <T>(resource: string) => {
    return {
        // Get a list of resources
        list: (config?: Config) => axios.get(`${base}/${resource}`, config),

        // Get a single resource by its id
        get: (id: Id, config?: Config) => axios.get(`${base}/${resource}/${id}`, config),

        // Create a new resource
        create: (payload: T) => axios.post(`${base}/${resource}`, payload),

        // Replace an existing resource with payload
        update: (id: Id, payload: T) => axios.put(`${base}/${resource}/${id}`, payload),

        // Merge new payload into a resource
        patch: (id: Id, payload: T) => axios.patch(`${base}/${resource}/${id}`, payload),

        // Remove a resource by its id
        remove: (id: Id) => axios.delete(`${base}/${resource}/${id}`),
    };
};

export { baseApiService, base };