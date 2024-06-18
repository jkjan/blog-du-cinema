import { baseURL } from "../../shared/base_api_service.ts";
import axios from "axios";

export const postAPI = {
  create: (title: string, contentHtml: string, token: string) =>
    axios.post(
      `${baseURL}/post`,
      { title, contentHtml },
      {
        headers: {
          authorization: token,
        },
      },
    ),
};
