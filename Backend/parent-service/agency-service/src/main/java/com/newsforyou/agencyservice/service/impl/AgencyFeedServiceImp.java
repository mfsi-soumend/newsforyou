package com.newsforyou.agencyservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.newsforyou.agencyservice.configurations.Constants;
import com.newsforyou.agencyservice.dto.AgencyFeedRequest;
import com.newsforyou.agencyservice.dto.AgencyFeedResponse;
import com.newsforyou.agencyservice.dto.AgencyFeedResponseList;
import com.newsforyou.agencyservice.exception.InvalidRequestException;
import com.newsforyou.agencyservice.model.AgencyFeed;
import com.newsforyou.agencyservice.repository.AgencyFeedRepository;
import com.newsforyou.agencyservice.service.AgencyFeedService;
import com.newsforyou.agencyservice.service.AgencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgencyFeedServiceImp implements AgencyFeedService {
	
	private final AgencyFeedRepository agencyFeedRepo;
	private final AgencyService agencyService;
	private final WebClient webClient;
	@Value("${category_service_url}")
	private String categoryServiceUrl;
	
	@Override
	public void createAgencyFeed(AgencyFeedRequest agencyFeedRequest) {
		if(agencyFeedRequest.getAgencyId() == null || agencyFeedRequest.getAgencyId().isBlank()
				|| agencyFeedRequest.getCategoryId() == null || agencyFeedRequest.getCategoryId().isBlank()) {
			throw new InvalidRequestException(Constants.EMPTY_DATA_ERROR);
		}
		if(!agencyFeedRepo.findByAgencyIdAndCategoryId(agencyFeedRequest.getAgencyId(), agencyFeedRequest.getCategoryId()).isEmpty()) {
			throw new InvalidRequestException(Constants.AGENCY_FEED_ALREADY_EXSISTS);
		}
		if (!agencyService.checkAgencyAvailable(agencyFeedRequest.getAgencyId())) {
			throw new InvalidRequestException(Constants.NO_AGENCY_FOUND);
		}
		boolean checkCategory = false;
 		try {
 			checkCategory = webClient.get()
				.uri(categoryServiceUrl + "available/" + agencyFeedRequest.getCategoryId())
				.retrieve()
				.bodyToMono(boolean.class)
				.block();
		}
		catch (Exception e) {
			throw new InvalidRequestException(Constants.CATEGORY_SERVER_DOWN);
		}
		if(!checkCategory) {
			throw new InvalidRequestException(Constants.NO_CATEGORY_FOUND);
		}
		try {
			AgencyFeed newAgencyFeed = AgencyFeed.builder()
					.agencyId(agencyFeedRequest.getAgencyId())
					.categoryId(agencyFeedRequest.getCategoryId())
					.build();
			AgencyFeed savedAgencyFeed = agencyFeedRepo.save(newAgencyFeed);
			savedAgencyFeed.setAgencyFeedUrl(Constants.AGENCY_FEED_LINK_STARTER + savedAgencyFeed.getAgencyFeedId());
			savedAgencyFeed = agencyFeedRepo.save(savedAgencyFeed);
			log.info("Agency Feed created!! - "+savedAgencyFeed.getAgencyId());
		}
		catch (Exception e) {
			throw new InvalidRequestException(e.getMessage());
		}
	}

	@Override
	public AgencyFeedResponseList getAllAgencyFeed() {
		List<AgencyFeed> allAgencyFeed = agencyFeedRepo.findAll();
		return AgencyFeedResponseList.builder()
				.totalAgencyFeedCount(allAgencyFeed.size())
				.agencyFeedResponseList(allAgencyFeed.stream().map(this::mapToAgencyFeedList).toList())
				.build();
	}

	@Override
	public AgencyFeedResponse getAgencyFeed(String agencyFeedId) {
		if(agencyFeedId == null || agencyFeedId.isBlank()) {
			throw new InvalidRequestException(Constants.EMPTY_DATA_ERROR);
		}
		Optional<AgencyFeed> findById = agencyFeedRepo.findById(agencyFeedId);
		if(findById.isPresent()) {
			AgencyFeed res = findById.get();
			return AgencyFeedResponse.builder()
					.agencyFeedId(agencyFeedId)
					.agencyFeedUrl(res.getAgencyFeedUrl())
					.agencyId(res.getAgencyId())
					.categoryId(res.getCategoryId())
					.build();
		}
		else {
			throw new InvalidRequestException(Constants.NO_AGENCY_FEED_FOUND);
		}
	}
	
	private AgencyFeedResponse mapToAgencyFeedList(AgencyFeed agencyFeed) {
		return AgencyFeedResponse.builder()
				.agencyFeedId(agencyFeed.getAgencyFeedId())
				.agencyId(agencyFeed.getAgencyId())
				.agencyFeedUrl(agencyFeed.getAgencyFeedUrl())
				.categoryId(agencyFeed.getCategoryId())
				.build();
	}

}
