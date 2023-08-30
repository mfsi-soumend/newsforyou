import axios from "../../config/axiosConfig";
import { categoryUrl } from "../../config/constants";

const CategoryService = {
  getAllCategory: () => {
    return axios.get(categoryUrl + "/all-category");
  },
};

export default CategoryService;
