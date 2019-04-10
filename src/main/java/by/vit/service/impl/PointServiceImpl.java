package by.vit.service.impl;

import by.vit.model.Point;
import by.vit.repository.PointRepository;
import by.vit.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Point addPoint(String name) {
        Point point = new Point(name);
        return getPointRepository().save(point);
    }
}
