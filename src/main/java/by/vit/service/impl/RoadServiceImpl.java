package by.vit.service.impl;

import by.vit.component.LocalizedMessageSource;
import by.vit.model.Road;
import by.vit.model.RoadId;
import by.vit.repository.PointRepository;
import by.vit.repository.RoadRepository;
import by.vit.service.RoadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of service layer for Road entity.
 */
@Service
@Transactional
public class RoadServiceImpl implements RoadService {

    private final LocalizedMessageSource localizedMessageSource;

    private final RoadRepository roadRepository;
    private final PointRepository pointRepository;

    public RoadServiceImpl(LocalizedMessageSource localizedMessageSource,
                           RoadRepository roadRepository, PointRepository pointRepository) {
        this.localizedMessageSource = localizedMessageSource;
        this.roadRepository = roadRepository;
        this.pointRepository = pointRepository;
    }

    /**
     * Find all roads from database.
     *
     * @return List<Road>
     */
    @Override
    public List<Road> findAll() {
        return roadRepository.findAll();
    }

    /**
     * Save new entity Road it.
     *
     * @param road entity
     * @return saved entity from database
     */
    @Override
    public Road save(Road road) {
        validate(road.getId() == null,
                localizedMessageSource.getMessage("error.road.haveNoId", new Object[]{}));
        isRoadValid(road);
        return roadRepository.saveAndFlush(road);
    }

    /**
     * Update entity Road.
     *
     * @param road entity
     * @return updated entity from database
     */
    @Override
    public Road update(Road road) {
        validate(road.getId() == null,
                localizedMessageSource.getMessage("error.road.haveNoId", new Object[]{}));
        isRoadValid(road);
        road.setId(isExist(road.getId()));
        //TODO if it work delete code in comment and delete update method from roadRepository
//        RoadId roadId = new RoadId(road.getPoint1id(),road.getPoint2id());
//        roadId = new RoadId(road.getPoint2id(),road.getPoint1id());
//        final Road updatedRoad = roadRepository.update(road.getDistance(), roadId1, roadId2);
//        roadRepository.flush();
        return roadRepository.saveAndFlush(road);
    }


    /**
     * Retrieves a Road by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws RuntimeException if Road none found
     */
    @Override
    public Road findById(RoadId id) {
        Optional<Road> road = roadRepository.findById(isExist(id));
        validate(!(road.isPresent()),
                localizedMessageSource.getMessage("error.road.id.notExist", new Object[]{}));
        return road.get();
    }

    /**
     * Get an instance, whose state may be lazily fetched.
     * If the requested instance does not exist in the database,
     * the <code>EntityNotFoundException</code> is thrown when the instance
     * state is first accessed.
     *
     * @param id must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     */
    @Override
    public Road getById(RoadId id) {
        return roadRepository.getOne(id);
    }

    /**
     * Deletes a given entity.
     *
     * @param road road entity
     */
    @Override
    public void delete(Road road) {
        final RoadId id = road.getId();
        validate(id == null, localizedMessageSource.getMessage("error.road.haveNoId", new Object[]{}));
        road.setId(isExist(id));
        roadRepository.delete(road);
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    @Override
    public void deleteById(RoadId id) {
        roadRepository.deleteById(isExist(id));
    }


    private void isRoadValid(Road road){
        boolean isPointsExists = pointRepository.existsById(road.getPoint1().getId())&&
                pointRepository.existsById(road.getPoint2().getId());
        validate(!isPointsExists,
                localizedMessageSource.getMessage("error.road.pointsNotExist",
                        new Object[]{road.getPoint1id(), road.getPoint2id()}));
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
    private RoadId isExist(RoadId id){
        if (!roadRepository.existsById(id)) {
            RoadId roadId = new RoadId(id.getPoint2Id(), id.getPoint1Id());
            validate(!roadRepository.existsById(roadId),
                    localizedMessageSource.getMessage("error.road.id.notExist", new Object[]{}));
            return roadId;
        }
        return id;
    }
}
