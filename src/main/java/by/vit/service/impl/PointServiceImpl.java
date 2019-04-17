package by.vit.service.impl;

import by.vit.model.Point;
import by.vit.repository.PointRepository;
import by.vit.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of service layer for Point entity.
 */
@Service
public class PointServiceImpl implements PointService {
    private PointRepository pointRepository;

    public PointRepository getPointRepository() {
        return pointRepository;
    }

    @Autowired
    public void setPointRepository(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    /**
     * Save new entity and update persist entity Point.
     *
     * @param point entity
     * @return saved or updated entity from database
     */
    @Override
    public Point savePoint(Point point) {
        Point savedEntity = getPointRepository().save(point);
        return savedEntity;
    }

    /**
     * Retrieves a Point by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws Exception if Point none found
     */
    @Override
    public Point findPoint(Long id) throws Exception {
        Optional<Point> point = getPointRepository().findById(id);
        if (point.isPresent()) {
            return point.get();
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
    public Point getPoint(Long id) {
        Point point = getPointRepository().getOne(id);
        return point;
    }

    /**
     * Deletes a given entity.
     *
     * @param point
     */
    @Override
    public void deletePoint(Point point) {
        getPointRepository().delete(point);
    }
}
