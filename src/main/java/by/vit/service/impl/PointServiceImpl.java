package by.vit.service.impl;

import by.vit.component.LocalizedMessageSource;
import by.vit.model.Point;
import by.vit.repository.PointRepository;
import by.vit.service.PointService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
     * Find all points from database.
     *
     * @return List<Point>
     */
    @Override
    public List<Point> findAll() {
        return pointRepository.findAll();
    }

    /**
     * Save new entity Point.
     *
     * @param point entity
     * @return saved entity from database
     */
    @Override
    public Point save(Point point) {
        validate(point.getId() != null,
                localizedMessageSource.getMessage("error.point.haveId", new Object[]{}));
        validate(pointRepository.existsByName(point.getName()),
                localizedMessageSource.getMessage("error.point.name.notUnique", new Object[]{}));
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
        validate(id == null,
                localizedMessageSource.getMessage("error.point.haveNoId", new Object[]{}));
        final Point duplicatePoint = pointRepository.findByName(point.getName());
        findById(id);
        final boolean isDuplicateExists = duplicatePoint != null && !Objects.equals(duplicatePoint.getId(), id);
        validate(isDuplicateExists,
                localizedMessageSource.getMessage("error.point.name.notUnique", new Object[]{}));
        return pointRepository.saveAndFlush(point);
    }


    /**
     * Retrieves a Point by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws RuntimeException if Point none found
     */
    @Override
    public Point findById(Long id) {
        Optional<Point> point = pointRepository.findById(id);
        validate(!point.isPresent(),
                localizedMessageSource.getMessage("error.point.id.notExist", new Object[]{}));
        return point.get();
    }

    /**
     * Retrieves a Point by its name.
     *
     * @param name must not be {@literal null}.
     * @return the entity with the given name
     * @throws RuntimeException if Point none found
     */
    @Override
    public Point findByName(String name) {
        validate(!pointRepository.existsByName(name),
                localizedMessageSource.getMessage("error.point.name.notExist", new Object[]{}));
        return pointRepository.findByName(name);
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
    public Point getById(Long id) {
        Point point = pointRepository.getOne(id);
        return point;
    }

    /**
     * Deletes a given entity.
     *
     * @param point
     */
    @Override
    public void delete(Point point) {
        final Long id = point.getId();
        validate(id == null,
                localizedMessageSource.getMessage("error.point.haveId", new Object[]{}));
        findById(id);
        pointRepository.delete(point);
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    @Override
    public void deleteById(Long id) {
        findById(id);
        pointRepository.deleteById(id);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
