package Img;

public class FileOps {

    /*public static void writeAll(File f, Vector<Contatto> rubrica) throws IOException {

        FileOutputStream fos = new FileOutputStream(f);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        for(Contatto p : rubrica){
            oos.writeObject(p);
            oos.reset();
        }
    }
    public static Vector<Contatto> readAll(File f) throws ClassNotFoundException, IOException{

        Vector<Contatto> rubrica = new Vector<Contatto>();

        FileInputStream fis = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(fis);

        try{
            while(fis.available() != -1){
                Contatto p = (Contatto) ois.readObject();
                rubrica.add(p);
            }
        }
        catch(EOFException e){}

        return rubrica;
    }*/
}