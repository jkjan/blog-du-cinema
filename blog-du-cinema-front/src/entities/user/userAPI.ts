import { baseURL, Config } from "../../shared/base_api_service.ts";
import axios from "axios";

export const userAPI = {
  "sign-up": (username: string, password: string, config?: Config) =>
    axios.post(
      `${baseURL}/sign-up`,
      {
        username,
        password,
      },
      {
        ...config,
        withCredentials: true,
      },
    ),

  "sign-in": (username: string, password: string, config?: Config) =>
    axios.post(
      `${baseURL}/sign-in`,
      {
        username,
        password,
      },
      {
        ...config,
        withCredentials: true,
      },
    ),
};
