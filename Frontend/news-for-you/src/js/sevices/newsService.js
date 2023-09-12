import axios from "../../config/axiosConfig";
import { newsUrl } from "../../config/constants";

const NewsService = {
  getAllNewsForTable: () => {
    return axios.get(newsUrl + "/all-news");
  },
  getAllNews: (categoryIds) => {
    let payload = {
      categoryIds: categoryIds,
    };
    return axios.put(newsUrl + "/all-news", payload);
  },
  getNews: (newsId) => {
    return axios.get(newsUrl + "/" + newsId);
  },
  getAgencyFeedNews: (payload) => {
    return axios.put(newsUrl + "/agency-feed", payload);
  },
  fetchNews: (payload) => {
    return axios.post(newsUrl + "/rss-feed", payload);
  },
  deleteNews: (newsId) => {
    return axios.delete(newsUrl + "/" + newsId);
  },
  increaseClick: (newsId) => {
    return axios.get(newsUrl + "/increase/" + newsId);
  },
};

export default NewsService;
