package by.vit.service.impl;

import by.vit.component.LocalizedMessageSource;
import by.vit.model.Transporter;
import by.vit.repository.TransporterRepository;
import by.vit.service.RoleService;
import by.vit.service.TransporterService;
import by.vit.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of service layer for Transporter entity.
 */
@Service
@Transactional
public class TransporterServiceImpl implements TransporterService {
    private final TransporterRepository transporterRepository;
    private final LocalizedMessageSource localizedMessageSource;
    private final UserService userService;

    public TransporterServiceImpl(TransporterRepository transporterRepository, LocalizedMessageSource localizedMessageSource,
                                  UserService userService) {
        this.transporterRepository = transporterRepository;
        this.localizedMessageSource = localizedMessageSource;
        this.userService = userService;
    }

    /**
     * Find all transporters from database.
     *
     * @return List<Transporter>
     */
    @Override
    public List<Transporter> findAll() {
        return transporterRepository.findAll();
    }

    /**
     * Save new entity Transporter.
     *
     * @param transporter entity
     * @return saved entity from database
     */
    @Override
    public Transporter save(Transporter transporter) {
        validate(transporter.getId() != null,
                localizedMessageSource.getMessage("error.transporter.haveId", new Object[]{}));
        validate(transporterRepository.existsByLicense(transporter.getLicense()),
                localizedMessageSource.getMessage("error.transporter.license.notUnique", new Object[]{}));
        return transporterRepository.saveAndFlush(transporter);
    }

    /**
     * Update entity Transporter.
     *
     * @param transporter entity
     * @return updated entity from database
     */
    @Override
    public Transporter update(Transporter transporter) {
        final Long id = transporter.getId();
        validate(id == null,
                localizedMessageSource.getMessage("error.transporter.haveNoId", new Object[]{}));
        final Transporter duplicateTransporter = transporterRepository.findByLicense(transporter.getName());
        findById(id);
        final boolean isDuplicateExists = duplicateTransporter != null && !Objects.equals(duplicateTransporter.getId(), id);
        validate(isDuplicateExists,
                localizedMessageSource.getMessage("error.transporter.license.notUnique", new Object[]{}));
        return transporterRepository.saveAndFlush(transporter);
    }

    /**
     * Retrieves a Transporter by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws RuntimeException if Transporter none found
     */
    @Override
    public Transporter findById(Long id) {
        Optional<Transporter> transporter = transporterRepository.findById(id);
        validate(!(transporter.isPresent()),
                localizedMessageSource.getMessage("error.transporter.notExist", new Object[]{}));
        return transporter.get();
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
    public Transporter getById(Long id) {
        Transporter transporter = transporterRepository.getOne(id);
        return transporter;
    }

    /**
     * Deletes a given entity.
     *
     * @param transporter
     */
    @Override
    public void delete(Transporter transporter) {
        final Long id = transporter.getId();
        validate(id == null,
                localizedMessageSource.getMessage("error.transporter.haveId", new Object[]{}));
        findById(id);
        transporterRepository.delete(transporter);
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
        transporterRepository.deleteById(id);
    }


    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
