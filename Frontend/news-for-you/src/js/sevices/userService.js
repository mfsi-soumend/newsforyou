import axios from "../../config/axiosConfig";
import { loginUrl } from "../../config/constants";

const UserService = {
  login: (payload) => {
    return axios.post(loginUrl, payload);
  },
};

export default UserService;
