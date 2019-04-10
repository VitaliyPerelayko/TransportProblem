package by.vit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FindBestSolve {

    @RequestMapping(value = "/modelAndView", method = RequestMethod.GET)
    public String viewAnswer(@ModelAttribute("value") String value, ModelMap model) {
        model.addAttribute("message", "Hello World RequestParam:" + value);
        return "main";
    }
}
