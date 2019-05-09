package by.vit.service.impl;

import by.vit.component.LocalizedMessageSource;
import by.vit.model.CarModel;
import by.vit.repository.CarModelRepository;
import by.vit.service.CarModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of service layer for CarModel entity.
 */
@Service
@Transactional
public class CarModelServiceImpl implements CarModelService {

    private final LocalizedMessageSource localizedMessageSource;

    private final CarModelRepository carModelRepository;

    public CarModelServiceImpl(LocalizedMessageSource localizedMessageSource, CarModelRepository carModelRepository) {
        this.localizedMessageSource = localizedMessageSource;
        this.carModelRepository = carModelRepository;
    }

    /**
     * Find all carModels from database.
     *
     * @return List<CarModel>
     */
    @Override
    public List<CarModel> findAll() {
        return carModelRepository.findAll();
    }

    /**
     * Save new entity CarModel.
     *
     * @param carModel entity
     * @return saved entity from database
     */
    @Override
    public CarModel save(CarModel carModel) {
        validate(carModel.getId() != null,
                localizedMessageSource.getMessage("error.carModel.haveId", new Object[]{}));
        validate(carModelRepository.existsByName(carModel.getName()),
                localizedMessageSource.getMessage("error.carModel.name.notUnique", new Object[]{}));
        return carModelRepository.saveAndFlush(carModel);
    }

    /**
     * Update entity CarModel.
     *
     * @param carModel entity
     * @return updated entity from database
     */
    @Override
    public CarModel update(CarModel carModel) {
        final Long id = carModel.getId();
        validate(id == null,
                localizedMessageSource.getMessage("error.carModel.haveNoId", new Object[]{}));
        final CarModel duplicatePoint = carModelRepository.findByName(carModel.getName());
        findById(id);
        final boolean isDuplicateExists = duplicatePoint != null && !Objects.equals(duplicatePoint.getId(), id);
        validate(isDuplicateExists,
                localizedMessageSource.getMessage("error.carModel.name.notUnique", new Object[]{}));
        return carModelRepository.saveAndFlush(carModel);
    }


    /**
     * Retrieves a CarModel by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws RuntimeException if CarModel none found
     */
    @Override
    public CarModel findById(Long id) {
        Optional<CarModel> carModel = carModelRepository.findById(id);
        validate(!(carModel.isPresent()),
                localizedMessageSource.getMessage("error.carModel.id.notExist", new Object[]{}));
        return carModel.get();
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
    public CarModel getById(Long id) {
        CarModel carModel = carModelRepository.getOne(id);
        return carModel;
    }

    /**
     * Deletes a given entity.
     *
     * @param carModel
     */
    @Override
    public void delete(CarModel carModel) {
        final Long id = carModel.getId();
        validate(id == null,
                localizedMessageSource.getMessage("error.carModel.haveNoId", new Object[]{}));
        findById(id);
        carModelRepository.delete(carModel);
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
        carModelRepository.deleteById(id);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
