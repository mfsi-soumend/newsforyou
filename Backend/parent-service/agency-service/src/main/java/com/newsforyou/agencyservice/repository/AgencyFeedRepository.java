package com.newsforyou.agencyservice.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.newsforyou.agencyservice.model.AgencyFeed;

public interface AgencyFeedRepository extends MongoRepository<AgencyFeed, String> {

	List<AgencyFeed> findByAgencyIdAndCategoryId(String agencyId, String categoryId);

}
