package by.vit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView modelAndView() {
        return new ModelAndView("main", "message", "Hello World");
    }

    @RequestMapping(value = "/modelAttribute", method = RequestMethod.GET)
    public String modelAttribute(@ModelAttribute("input") String input, ModelMap model) {
        model.addAttribute("message", "Hello World ModelAttribute:" + input);
        return "main";
    }

    @RequestMapping(value = "/viewName", method = RequestMethod.GET)
    public String viewName(@RequestParam("value") String value, ModelMap model) {
        model.addAttribute("message", "Hello World RequestParam:" + value);
        return "main";
    }
}
