/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuMVC;

/**
 *
 * @author tonovosel
 */
public class MenuController {
    
    private MenuModel model;
    private MenuView view;
    
    public MenuController(MenuModel model, MenuView view) {
        this.model = model;
        this.view = view;
    }
    
    public MenuModel getModel() {
        return model;
    }
    
    public void setModel(MenuModel model) {
        this.model = model;
    }
    
    public MenuView getView() {
        return view;
    }
    
    public void setView(MenuView view) {
        this.view = view;
    }
    
    public void updateMenuView() {
        for (String menu : model.getMenuOptions()) {
            view.printMenu(menu);
        }
    }
    
    public void updateOptioniew(int index) {
        view.printOption(model.getMenuOptions().get(index));
    }
    
}
