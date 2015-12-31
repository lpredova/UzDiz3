/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileIterator.InitialStructure;

/**
 *
 * @author lovro
 */
public class FileRepository implements Container {

    @Override
    public Iterator getIterator() {
        return new InitialFileIterator();
    }
    
    private class InitialFileIterator implements Iterator{

        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Object next() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
       
        private boolean fileExists(String path){
            File f = new File(filePathString);
                if(f.exists() && !f.isDirectory()) { 
    // do something
}
        } 
        
    }
    
}
