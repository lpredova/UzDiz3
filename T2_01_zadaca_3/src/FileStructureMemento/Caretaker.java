/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileStructureMemento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author Toni
 */
public class Caretaker {

    private HashMap<Object, String> savedStates = new HashMap<>();
    //private List<Object> savedStates = new ArrayList<>();

    public void addMemento(Object m) {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("E dd.MM.yyyy 'at' hh:mm:ss a zzz");
        
        savedStates.put(m, ft.format(dNow));
    }

    public Entry getMemento(int index) {
        return (Entry) savedStates.entrySet().toArray()[index];
        //return savedStates.keySet().toArray()[index];
    }

    public int getNumberOfPossibleStates(){
        return savedStates.size();
    }
    
    public void clearAllStates(){
        savedStates.clear();
    }
    
    public HashMap<Object, String> getSavedStates(){
        return savedStates;
    }
}
