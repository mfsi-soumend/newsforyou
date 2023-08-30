import axios from "../../config/axiosConfig";
import { newsUrl } from "../../config/constants";

const NewsService = {
  getAllNews: (categoryIds) => {
    let payload = {
      categoryIds: categoryIds,
    };
    return axios.put(newsUrl + "/all-news", payload);
  },
  getNews: (newsId) => {
    return axios.get(newsUrl + "/" + newsId);
  },
};

export default NewsService;
