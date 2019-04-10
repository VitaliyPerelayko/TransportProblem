package by.vit.controller;

import by.vit.model.Point;
import by.vit.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PointController {

    PointService pointService;

    public PointService getPointService() {
        return pointService;
    }

    @Autowired
    public void setPointService(PointService pointService) {
        this.pointService = pointService;
    }

    @RequestMapping(value = "/addPoint", method = RequestMethod.GET)
    public String addPoint(@ModelAttribute("name") String name, ModelMap model) {
        Point point1 = getPointService().addPoint(name);
        List<String> point = new ArrayList<>();
        point.add(point1.getId().toString());
        point.add(point1.getName());
        model.addAttribute("point",point);
        return "successPoint";
    }
}
