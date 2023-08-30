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
  const token = localStorage.getItem("NEWS_FOR_YOU_TOKEN");
  if (token) {
    config.headers.Authorization = token ? `Bearer ${token}` : "";
  }
  return config;
});

export default instance;
