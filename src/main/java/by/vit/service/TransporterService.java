package by.vit.service;

import by.vit.model.Transporter;

import java.util.List;

public interface TransporterService {
    List<Transporter> findAll();

    Transporter save(Transporter transporter);

    Transporter update(Transporter transporter);

    Transporter findById(Long id);

    Transporter getById(Long id);

    void delete(Transporter transporter);

    void deleteById(Long id);
}
