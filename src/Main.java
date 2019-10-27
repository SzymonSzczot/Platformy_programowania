import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Main {

    enum Stanowisko{
        DYREKTOR ("Dyrektor", 1, "Osba zarządzająca całą działalnością"),
        KIEROWNIK ("Kierownik", 2, "Osoba zarządzająca pracownikami"),
        PRACOWNIK ("Pracownik", 3, "Osoba pracująca");

        private final String nazwa;
        private final int poziom;
        private final String opis;

        Stanowisko(String nazwa, int poziom, String opis) {
            this.nazwa = nazwa;
            this.poziom = poziom;
            this.opis = opis;
        }

        @Override
        public String toString() {
            return "Nazwa: " + this.nazwa + " Poziom: " + this.poziom + "\nOpis: " + this.opis;
        }
    }

    public static void main(String[] args) {

        class Osoba {

            private String imie;
            private String nazwisko;

            Osoba(){
                imie = "NULL";
                nazwisko = "NULL";
            }

            Osoba(Osoba os){
                this.imie = os.imie;
                this.nazwisko = os.nazwisko;
            }

            Osoba(String im, String naz){
                imie = im;
                nazwisko = naz;
            }

            @Override
            public String toString() {
                StringBuilder result = new StringBuilder();

                result.append("Imię i nazwisko: " + this.imie + " " + this.nazwisko + "\n");

                return result.toString();
            }
        }

        class Pracownik extends Osoba {

            private Stanowisko stanowisko;
            private int pensja;


            Pracownik(Osoba osoba, Stanowisko stanowisko, int pensja){
                super(osoba);
                this.stanowisko = stanowisko;
                this.pensja = pensja;
            }

            @Override
            public String toString() {
                String result = super.toString();

                result = result + stanowisko.toString() + "\n" +
                        "Pensja: " + this.pensja + "\n";

                return result;
            }
        }

        class Firma implements Iterable<Pracownik>{

            private ArrayList<Pracownik> listaPracownikow;

            Firma(){
                listaPracownikow = new ArrayList<>();
            }

            public void add(Pracownik prac){
                this.listaPracownikow.add(prac);
            }

            public int count(){
                return this.listaPracownikow.size();
            }

            public void print(){
                for (Pracownik e : this.listaPracownikow) {
                    System.out.println(e);
                }
            }

            Iterator<Pracownik> iterator(Stanowisko s){
                List<Pracownik> filteredList =
                        this.listaPracownikow.stream()
                                .filter(pracownik -> pracownik.stanowisko == s)
                                .collect(Collectors.toList());

                return new FirmaFilteredIterator(filteredList);
            }

            @Override
            public Iterator iterator() {
                return new FirmaIterator();
            }


            class FirmaIterator implements Iterator {
                int size = listaPracownikow.size();
                int currentPointer = 0;

                public boolean hasNext() {
                    return (currentPointer < size);
                }

                public Pracownik next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }

                    Pracownik val = listaPracownikow.get(currentPointer);
                    currentPointer += 1;

                    return val;
                }

            }

            class FirmaFilteredIterator implements Iterator {

                List<Pracownik> insideList;
                int currentPointer = 0;
                int size;


                public FirmaFilteredIterator(List<Pracownik> lis) {
                    this.insideList = lis;
                    this.size = insideList.size();
                }

                public boolean hasNext() {
                    return (currentPointer < size);
                }

                public Pracownik next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }

                    Pracownik val = insideList.get(currentPointer);
                    currentPointer += 1;

                    return val;
                }

            }

        }

        Osoba jed = new Osoba("Szymon", "Szczot");
        Osoba dwa = new Osoba("Kamil", "Halmis");
        Osoba trz = new Osoba("Magda", "Dawid");
        Osoba czt = new Osoba("Jakis", "Otak");

        Pracownik dyr = new Pracownik(jed, Stanowisko.DYREKTOR, 1000);
        Pracownik dyr2 = new Pracownik(czt, Stanowisko.DYREKTOR, 1000);
        Pracownik kier = new Pracownik(dwa, Stanowisko.KIEROWNIK, 100);
        Pracownik prac = new Pracownik(trz, Stanowisko.PRACOWNIK, 10);

        Firma XD = new Firma();

        XD.add(dyr);
        XD.add(dyr2);
        XD.add(kier);
        XD.add(prac);

//        for(Pracownik e :  XD) {
//            System.out.println(e);
//        }

        Iterator<Pracownik> iter = XD.iterator(Stanowisko.DYREKTOR);

        while (iter.hasNext()){
            System.out.println(iter.next());
        }
    }
}
