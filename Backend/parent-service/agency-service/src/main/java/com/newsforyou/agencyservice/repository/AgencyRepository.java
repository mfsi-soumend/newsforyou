package com.newsforyou.agencyservice.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.newsforyou.agencyservice.model.Agency;

public interface AgencyRepository extends MongoRepository<Agency, String> {

	List<Agency> findByAgencyName(String agencyName);

}
