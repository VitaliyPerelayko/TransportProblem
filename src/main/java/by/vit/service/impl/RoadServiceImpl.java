package by.vit.service.impl;

import by.vit.model.Road;
import by.vit.model.RoadId;
import by.vit.repository.RoadRepository;
import by.vit.service.RoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of service layer for Road entity.
 */
@Service
public class RoadServiceImpl implements RoadService {
    private RoadRepository roadRepository;

    public RoadRepository getRoadRepository() {
        return roadRepository;
    }

    @Autowired
    public void setRoadRepository(RoadRepository roadRepository) {
        this.roadRepository = roadRepository;
    }

    /**
     * Save new entity and update persist entity Road.
     *
     * @param road entity
     * @return saved or updated entity from database
     */
    @Override
    public Road saveRoad(Road road) {
        Road savedEntity = getRoadRepository().save(road);
        return savedEntity;
    }

    /**
     * Retrieves a Road by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws Exception if Road none found
     */
    @Override
    public Road findRoad(RoadId id) throws Exception {
        Optional<Road> road = getRoadRepository().findById(id);
        if (road.isPresent()) {
            return road.get();
        }
        throw new Exception("massage");
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
    public Road getRoad(RoadId id) {
        Road road = getRoadRepository().getOne(id);
        return road;
    }

    /**
     * Deletes a given entity.
     *
     * @param road
     */
    @Override
    public void deleteRoad(Road road) {
        getRoadRepository().delete(road);
    }
}