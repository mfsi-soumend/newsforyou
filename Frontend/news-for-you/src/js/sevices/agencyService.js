import axios from "../../config/axiosConfig";
import { agencyUrl } from "../../config/constants";

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
};

export default AgencyService;
