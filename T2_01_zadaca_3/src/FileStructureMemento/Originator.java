/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileStructureMemento;

import FileStructureComposite.AppFile;


/**
 *
 * @author Toni
 */
public class Originator {

    private AppFile state;

    public void set(AppFile state) {
        //System.out.println("Originator: Setting state to " + state);
        this.state = state;
    }

    public AppFile getState() {
        return state.clone();
    }

    public Object saveToMemento() {
        //System.out.println("Originator: Saving to Memento.");

        return new Memento(state);
    }

    public void restoreFromMemento(Object m) {
        if (m instanceof Memento) {
            Memento memento = (Memento) m;
            state = memento.getSavedState();
            //System.out.println("Originator: State after restoring from Memento:" + state);
        }
    }

    private static class Memento {

        private AppFile state;

        public Memento(AppFile stateToSave) {
            state = stateToSave;
        }

        public AppFile getSavedState() {
            return state;
        }

    }

}
