package by.vit.repository;

import by.vit.model.Transporter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for Transporters
 */
public interface TransporterRepository extends JpaRepository<Transporter, Long> {
    /**
     * Return true, if transporter with this license exist in database,
     * and false otherwise.
     *
     * @param license
     * @return true or false
     */
    boolean existsByLicense(String license);

    /**
     * Find transporter by license from DataBase
     *
     * @param license of transporter
     * @return transporter
     */
    Transporter findByLicense(String license);
}
