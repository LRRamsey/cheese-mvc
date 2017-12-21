package org.launchcode.controllers;


import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "My Menus:");
        return "menu/index";
    }

    @RequestMapping(value="add", method = RequestMethod.GET)
    public String displayAdd(Model model){
        model.addAttribute("title", "Add Menu");
        model.addAttribute(new Menu());
        return "menu/add";
    }

    @RequestMapping(value="add", method = RequestMethod.POST)
    public String processAdd(Model model, @ModelAttribute @Valid Menu menu, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }

        menuDao.save(menu);
        return "redirect:view/" + menu.getId();
    }

    //TODO not exactly sure how to implement this/use @PathVariable in Add Menu Item
    //also unsure of how to implement view/(specific menu id)
    @RequestMapping(value="view/{menuId}", method = RequestMethod.GET)
    public String view( Model model, @PathVariable int menuId){

        model.addAttribute(menuDao.findOne(menuId));
        return "menu/view";
    }

    @RequestMapping(value="add-item/{menuId}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int menuId) {

        Menu menu = menuDao.findOne(menuId);
        AddMenuItemForm form = new AddMenuItemForm(menu, cheeseDao.findAll());

        model.addAttribute("form", form);
        model.addAttribute("title", "Add item to menu" + menu.getName());



        return "menu/add-item";
    }


    @RequestMapping(value="add-item", method = RequestMethod.POST)
    public String addItem(Model model, @ModelAttribute @Valid AddMenuItemForm theMenu, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Menu");
            return "menu/add-item";
        }


        Menu form = menuDao.findOne(theMenu.getMenuId());
        Cheese menuCheeses = cheeseDao.findOne(theMenu.getCheeseId());
        form.addItem(menuCheeses);

        menuDao.save(form);

        return "redirect:/menu/view/" + form.getId();
    }


}
