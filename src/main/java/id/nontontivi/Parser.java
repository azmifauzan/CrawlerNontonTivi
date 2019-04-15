package id.nontontivi;

public class Parser {
    public static void main(String args[]){
        switch (args[0]){
            case  "0":
                Viva viva2 = new Viva();
                viva2.getFeed();
                viva2.getBerita();
                Kompas kompas2 = new Kompas();
                kompas2.getFeed();
                kompas2.getBerita();
                Merdeka merdeka2 = new Merdeka();
                merdeka2.getFeed();
                merdeka2.getBerita();
                break;
            case "1":
                Detikhot dh = new Detikhot();
                dh.getFeed();
                dh.getBerita();
                break;
            case "2":
                Viva viva = new Viva();
                viva.getFeed();
                viva.getBerita();
                break;
            case "3":
                Kompas kompas = new Kompas();
                kompas.getFeed();
                kompas.getBerita();
                break;
            case "4":
                Merdeka merdeka = new Merdeka();
                merdeka.getFeed();
                merdeka.getBerita();
                break;
        }

    }
}
