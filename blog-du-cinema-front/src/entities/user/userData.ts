import { UserData } from "../../app/types.ts";

export const setUserDataInLocalStorage = (userData: UserData) => {
  if (userData === null) localStorage.removeItem("userData");
  else localStorage.setItem("userData", JSON.stringify(userData));
};

export const getUserDataInLocalStorage = (): UserData => {
  return JSON.parse(localStorage.getItem("userData"));
};
