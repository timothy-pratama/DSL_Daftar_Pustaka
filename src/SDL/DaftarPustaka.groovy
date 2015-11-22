package DSL

import Model.Author
import Model.Book
import Model.Journal
import Model.Magazine
import Model.Newspaper
import Model.Website
import org.codehaus.groovy.ant.Groovy

import java.nio.Buffer

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

    /* user preference */
    private String action = "none";
    private String format = "none";

    /* list of terminals */
    private ArrayList<String> allowedActions = new ArrayList<>();
    private ArrayList<String> allowedFormat = new ArrayList<>();

    /* list for syntax errors */
    Set<String> errorMessages = new HashSet<>();

    public DaftarPustaka() {
        addAllowedActions();
        addAllowedFormats();
    }

    private void addAllowedActions() {
        allowedActions.add("add");
        allowedActions.add("get");
        allowedActions.add("delete");
    }

    private void addAllowedFormats() {
        allowedFormat.add("apa");
        allowedFormat.add("mla");
    }

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

    /* DSL */

    def static make(closure) {
        DaftarPustaka daftarPustaka = new DaftarPustaka();
        closure.delegate = daftarPustaka;
        closure();
    }

    def action(String action) {
        if (this.action.equalsIgnoreCase("none")) {
            if (allowedActions.contains(action)) {
                this.action = action;
            } else {
                errorMessages.add("Undefined action");
            }
        } else {
            errorMessages.add("Action already set");
        }
    }

    def format(String format) {
        if (action.equalsIgnoreCase("get")) {
            if (!this.format.equalsIgnoreCase("none")) {
                errorMessages.add("Format already set");
            } else {
                if (allowedFormat.contains(format)) {
                    this.format = format;
                } else {
                    errorMessages.add("Unknown bibliography format");
                }
            }
        } else {
            errorMessages.add("Incompatible action (must use action get)")
        }
    }

    def getgetSQL() {
        if (errorMessages.size() > 0) {
            System.out.println("Fail generating SQL Query. Found syntax error(s): ");
            int i = 1;
            for (String error : errorMessages) {
                System.out.printf("%d. %s\n", i, error);
                i++;
            }
        } else {
            System.out.println("No Error Found! Generating SQL Query...");
        }
    }

    public static void main(String[] args) {
        DaftarPustaka.make {
            action "geet"
            action "noo"
            format "apa"
            format "mla"
            getSQL
        }
    }
}