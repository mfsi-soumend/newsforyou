import axios from "axios";
import { loginUrl } from "./constants";

/**
 * Creates an instance of Axios with a base URL and an interceptor that adds an
 * authorization header to requests if a token is present in local storage.
 * @returns {AxiosInstance} - An instance of Axios with the interceptor.
 */
const instance = axios.create({
  baseURL: process.env.REACT_APP_BASE_URL,
});
instance.interceptors.request.use(function (config) {
  if (config.url.includes(loginUrl)) {
    return config;
  }
  // const token = localStorage.getItem("adminUserToken");
  // if (token) {
  //   config.headers.Authorization = token ? `Bearer ${token}` : "";
  // }
  return config;
});
instance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (
      (error.code === "ERR_NETWORK" || error.response.status === 403) &&
      !window.location.href.endsWith("/login")
    ) {
      localStorage.removeItem("adminUserToken");
      window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

export default instance;
