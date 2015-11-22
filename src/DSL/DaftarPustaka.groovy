package DSL

import Model.Author
import Model.Book
import Model.Journal
import Model.Magazine
import Model.Newspaper
import Model.Website

/**
 * Created by timothy.pratama on 22-Nov-15.
 */
class DaftarPustaka {
    private Author author = new Author();
    private Book book = new Book();
    private Journal journal = new Journal();
    private Magazine magazine = new Magazine();
    private Newspaper newspaper = new Newspaper();
    private Website website = new Website();

    Author getAuthor() {
        return author
    }

    void setAuthor(Author author) {
        this.author = author
    }

    Book getBook() {
        return book
    }

    void setBook(Book book) {
        this.book = book
    }

    Journal getJournal() {
        return journal
    }

    void setJournal(Journal journal) {
        this.journal = journal
    }

    Magazine getMagazine() {
        return magazine
    }

    void setMagazine(Magazine magazine) {
        this.magazine = magazine
    }

    Newspaper getNewspaper() {
        return newspaper
    }

    void setNewspaper(Newspaper newspaper) {
        this.newspaper = newspaper
    }

    Website getWebsite() {
        return website
    }

    void setWebsite(Website website) {
        this.website = website
    }

    String a;
    String b;
    String c;
    String nested;

    def static make(closure)
    {
        DaftarPustaka daftarPustaka = new DaftarPustaka();
        closure.delegate = daftarPustaka;
        closure();
    }

    def a(String a)
    {
        this.a = a;
    }

    def b(String b)
    {
        this.b = b;
    }

    def c(String c)
    {
        this.c = c;
    }

    def nested(nested_closure)
    {

    }

    def getprint()
    {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "DaftarPustaka{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c='" + c + '\'' +
                '}';
    }

    public static void main(String[] args)
    {
        DaftarPustaka.make{
            a "ini a"
            b "ini b"
            c "ini c"
            print
        }
    }
}
