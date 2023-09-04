import axios from "../../config/axiosConfig";
import { agencyFeedUrl, agencyUrl } from "../../config/constants";

const AgencyService = {
  getAgency: (agencyId) => {
    return axios.get(agencyUrl + "/" + agencyId);
  },
  getAllAgency: () => {
    return axios.get(agencyUrl + "/all-agency");
  },
  createAgency: (payload) => {
    return axios.post(agencyUrl, payload);
  },
  updateAgency: (payload) => {
    return axios.put(agencyUrl, payload);
  },
  getAllAgencyFeed: () => {
    return axios.get(agencyFeedUrl + "/all-agency-feed");
  },
  createAgencyFeed: (payload) => {
    return axios.post(agencyFeedUrl, payload);
  },
  getAgencyFeed: (id) => {
    return axios.get(agencyFeedUrl + "/" + id);
  },
};

export default AgencyService;
