package by.vit.service.impl;

import by.vit.component.LocalizedMessageSource;
import by.vit.model.Point;
import by.vit.repository.PointRepository;
import by.vit.service.PointService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of service layer for Point entity.
 */
@Service
@Transactional
public class PointServiceImpl implements PointService {

    private final LocalizedMessageSource localizedMessageSource;

    private final PointRepository pointRepository;

    public PointServiceImpl(LocalizedMessageSource localizedMessageSource, PointRepository pointRepository) {
        this.localizedMessageSource = localizedMessageSource;
        this.pointRepository = pointRepository;
    }

    /**
     * Save new entity Point.
     *
     * @param point entity
     * @return saved entity from database
     */
    @Override
    public Point save(Point point) {
        validate(point.getId() != null, localizedMessageSource.getMessage("error.point.notHaveId", new Object[]{}));
        return pointRepository.saveAndFlush(point);
    }

    /**
     * Update entity Point.
     *
     * @param point entity
     * @return updated entity from database
     */
    @Override
    public Point update(Point point) {
        final Long id = point.getId();
        validate(id == null, localizedMessageSource.getMessage("error.point.haveId", new Object[]{}));
        final Point duplicatePoint = pointRepository.findByName(point.getName());
        final boolean isDuplicateExists = duplicatePoint != null && !Objects.equals(duplicatePoint.getId(), id);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.point.name.notUnique", new Object[]{}));
        return pointRepository.saveAndFlush(point);
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
        Optional<Point> point = pointRepository.findById(id);
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
        Point point = pointRepository.getOne(id);
        return point;
    }

    /**
     * Deletes a given entity.
     *
     * @param point
     */
    @Override
    public void deletePoint(Point point) {
        pointRepository.delete(point);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
