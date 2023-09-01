package com.newsforyou.agencyservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.newsforyou.agencyservice.configurations.Constants;
import com.newsforyou.agencyservice.dto.AgencyRequest;
import com.newsforyou.agencyservice.dto.AgencyResponse;
import com.newsforyou.agencyservice.dto.AgencyResponseList;
import com.newsforyou.agencyservice.exception.InvalidRequestException;
import com.newsforyou.agencyservice.model.Agency;
import com.newsforyou.agencyservice.repository.AgencyRepository;
import com.newsforyou.agencyservice.service.AgencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgencyServiceImp implements AgencyService {

	private final AgencyRepository agencyRepo;
	
	@Override
	public void createAgency(AgencyRequest agencyRequest) {
		if(agencyRequest.getAgencyName() == null || agencyRequest.getAgencyName().isBlank()) {
			throw new InvalidRequestException(Constants.EMPTY_DATA_ERROR);
		}
		if(!agencyRepo.findByAgencyName(agencyRequest.getAgencyName()).isEmpty()) {
			throw new InvalidRequestException(Constants.AGENCY_ALREADY_EXSISTS);
		}
		try {
			Agency newAgency = Agency.builder()
					.agencyName(agencyRequest.getAgencyName())
					.agencyLogoPath(agencyRequest.getAgencyLogoPath())
					.build();
			agencyRepo.save(newAgency);
			log.info("Agency created!! - "+agencyRequest.getAgencyName());
		}
		catch (Exception e) {
			throw new InvalidRequestException(e.getMessage());
		}

	}
	
	@Override
	public void updateAgency(Agency agencyRequest) {
		if(agencyRequest.getAgencyName() == null || agencyRequest.getAgencyName().isBlank() ||
				agencyRequest.getAgencyId() == null || agencyRequest.getAgencyId().isBlank()
				) {
			throw new InvalidRequestException(Constants.EMPTY_DATA_ERROR);
		}
		Optional<Agency> optionalAgency = agencyRepo.findById(agencyRequest.getAgencyId());
		if(optionalAgency.isEmpty()) {
			throw new InvalidRequestException(Constants.NO_AGENCY_FOUND);
		}
		if(!agencyRepo.findByAgencyName(agencyRequest.getAgencyName()).isEmpty() && !optionalAgency.get().getAgencyName().equals(agencyRequest.getAgencyName())  ) {
			throw new InvalidRequestException(Constants.AGENCY_ALREADY_EXSISTS);
		}
		try {
			agencyRepo.save(agencyRequest);
			log.info("Agency Updated!! - "+agencyRequest.getAgencyName());
		}
		catch (Exception e) {
			throw new InvalidRequestException(e.getMessage());
		}
		
	}
	
	@Override
	public AgencyResponse getAgency(String agencyId) {
		if(agencyId == null || agencyId.isBlank()) {
			throw new InvalidRequestException(Constants.EMPTY_DATA_ERROR);
		}
		Optional<Agency> findById = agencyRepo.findById(agencyId);
		if(findById.isPresent()) {
			Agency res = findById.get();
			return AgencyResponse.builder()
					.agencyId(agencyId)
					.agencyName(res.getAgencyName())
					.agencyLogoPath(res.getAgencyLogoPath())
					.build();
		}
		else {
			throw new InvalidRequestException(Constants.NO_AGENCY_FOUND);
		}
	}

	@Override
	public AgencyResponseList getAllAgency() {
		List<Agency> allAgency = agencyRepo.findAll();
		return AgencyResponseList.builder()
				.totalAgencyCount(allAgency.size())
				.agencyResponseList(allAgency.stream().map(this::mapToAgencyList).toList())
				.build();
	}
	
	private AgencyResponse mapToAgencyList(Agency agency) {
		return AgencyResponse.builder()
				.agencyName(agency.getAgencyName())
				.agencyId(agency.getAgencyId())
				.agencyLogoPath(agency.getAgencyLogoPath())
				.build();
	}

	@Override
	public boolean checkAgencyAvailable(String agencyId) {
		if(agencyId == null || agencyId.isBlank()) {
			return false;
		}
		Optional<Agency> findById = agencyRepo.findById(agencyId);
		if(findById.isPresent()) {
			return true;
		}
		else {
			return false;
		}
	}

}
