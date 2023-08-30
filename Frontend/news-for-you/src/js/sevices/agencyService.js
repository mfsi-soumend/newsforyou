import axios from "../../config/axiosConfig";
import { agencyUrl } from "../../config/constants";

const AgencyService = {
  getAgency: (agencyId) => {
    return axios.get(agencyUrl + "/" + agencyId);
  },
};

export default AgencyService;
