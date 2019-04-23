package by.vit.repository;

import by.vit.model.Transporter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for Transporters
 */
public interface TransporterRepository extends JpaRepository<Transporter, Long> {
}
