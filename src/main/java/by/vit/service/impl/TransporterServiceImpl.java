package by.vit.service.impl;

import by.vit.model.Transporter;
import by.vit.repository.TransporterRepository;
import by.vit.service.TransporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of service layer for Transporter entity.
 */
@Service
public class TransporterServiceImpl implements TransporterService {
    private TransporterRepository transporterRepository;

    public TransporterRepository getTransporterRepository() {
        return transporterRepository;
    }

    @Autowired
    public void setTransporterRepository(TransporterRepository transporterRepository) {
        this.transporterRepository = transporterRepository;
    }

    /**
     * Save new entity and update persist entity Transporter.
     *
     * @param transporter entity
     * @return saved or updated entity from database
     */
    @Override
    public Transporter saveTransporter(Transporter transporter) {
        Transporter savedEntity = getTransporterRepository().save(transporter);
        return savedEntity;
    }

    /**
     * Retrieves a Transporter by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws Exception if Transporter none found
     */
    @Override
    public Transporter findTransporter(Long id) throws Exception {
        Optional<Transporter> transporter = getTransporterRepository().findById(id);
        if (transporter.isPresent()) {
            return transporter.get();
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
    public Transporter getTransporter(Long id) {
        Transporter transporter = getTransporterRepository().getOne(id);
        return transporter;
    }

    /**
     * Deletes a given entity.
     *
     * @param transporter
     */
    @Override
    public void deleteTransporter(Transporter transporter) {
        getTransporterRepository().delete(transporter);
    }
}
