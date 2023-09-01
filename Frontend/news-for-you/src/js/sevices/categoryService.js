import axios from "../../config/axiosConfig";
import { categoryUrl } from "../../config/constants";

const CategoryService = {
  getAllCategory: () => {
    return axios.get(categoryUrl + "/all-category");
  },
  saveCategory: (payload) => {
    return axios.post(categoryUrl, payload);
  },
  updateCategory: (payload) => {
    return axios.put(categoryUrl, payload);
  },
};

export default CategoryService;
