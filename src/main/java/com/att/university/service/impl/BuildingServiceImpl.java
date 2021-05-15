package com.att.university.service.impl;

import com.att.university.dao.BuildingDao;
import com.att.university.entity.Building;
import com.att.university.exception.dao.BuildingNotFoundException;
import com.att.university.mapper.building.BuildingAddRequestMapper;
import com.att.university.mapper.building.BuildingUpdateRequestMapper;
import com.att.university.request.building.BuildingAddRequest;
import com.att.university.request.building.BuildingUpdateRequest;
import com.att.university.service.BuildingService;
import com.att.university.validator.building.BuildingAddValidator;
import com.att.university.validator.building.BuildingUpdateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BuildingServiceImpl implements BuildingService {
    private static final String BUILDING_RANK_NOT_FOUND = "BUILDING with Id %d is not found";

    private final BuildingDao buildingDao;
    private final BuildingUpdateValidator updateValidator;
    private final BuildingAddValidator addValidator;
    private final BuildingAddRequestMapper addRequestMapper;
    private final BuildingUpdateRequestMapper updateRequestMapper;

    @Override
    public List<Building> findAll() {
        log.debug("Find all Buildings...");

        return buildingDao.findAll(1, buildingDao.count());
    }

    @Override
    public Building findById(Integer id) {
        return buildingDao.findById(id)
                .orElseThrow(() -> new BuildingNotFoundException(String.format(BUILDING_RANK_NOT_FOUND, id)));
    }

    @Override
    @Transactional
    public void create(BuildingAddRequest addRequest) {
        log.debug("Building creating with request {}", addRequest);

        addValidator.validate(addRequest);

        buildingDao.save(addRequestMapper.convertToEntity(addRequest));
    }

    @Override
    @Transactional
    public void update(BuildingUpdateRequest updateRequest) {
        updateValidator.validate(updateRequest);

        if (!buildingDao.findById(updateRequest.getId()).isPresent()) {
            throw new BuildingNotFoundException(String.format(BUILDING_RANK_NOT_FOUND, updateRequest.getId()));
        }

        buildingDao.update(updateRequestMapper.convertToEntity(updateRequest));
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (!buildingDao.findById(id).isPresent()) {
            throw new BuildingNotFoundException(String.format(BUILDING_RANK_NOT_FOUND, id));
        }

        log.debug("Building deleting with id {}", id);

        buildingDao.deleteById(id);
    }
}
