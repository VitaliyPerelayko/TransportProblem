package by.vit.service;

import by.vit.model.Transporter;

public interface TransporterService {
    Transporter saveTransporter(Transporter transporter);

    Transporter findTransporter(Long id) throws Exception;

    Transporter getTransporter(Long id);

    void deleteTransporter(Transporter transporter);
}
