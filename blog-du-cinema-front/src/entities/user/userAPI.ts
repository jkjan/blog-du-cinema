import { baseURL, Config } from "../../shared/base_api_service.ts";
import axios from "axios";

export const userAPI = {
  "sign-up": (
    signUpForm: { username: string; password: string; nickname: string },
    config?: Config,
  ) =>
    axios.post(`${baseURL}/sign-up`, signUpForm, {
      ...config,
      withCredentials: true,
    }),

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

  "nickname": (username: string) =>
      axios.get(
          `${baseURL}/nickname/${username}`
      ),
};
