/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileStructureMemento;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Toni
 */
public class Caretaker {

    private List<Object> savedStates = new ArrayList<>();

    public void addMemento(Object m) {
        savedStates.add(m);
    }

    public Object getMemento(int index) {
        return savedStates.get(index);
    }

    public int getNumberOfPossibleStates(){
        return savedStates.size();
    }
    
    public void clearAllStates(){
        savedStates.clear();
    }
}
