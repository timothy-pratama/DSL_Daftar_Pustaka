package DSL

import Model.Author
import Model.Book
import Model.Date
import Model.Journal
import Model.Magazine
import Model.Newspaper
import Model.Website

/**
 * Created by timothy.pratama on 22-Nov-15.
 */
class DaftarPustaka {
    private ArrayList<Author> authors = new ArrayList<>();
    private Book book = new Book();
    private Journal journal = new Journal();
    private Magazine magazine = new Magazine();
    private Newspaper newspaper = new Newspaper();
    private Website website = new Website();

    /* user input */
    private String action = "none";
    private String format = "none";
    private String source = "none";
    private int year = 0;
    private String title = "none";
    private String city = "none";
    private String publisher = "none";
    private String periodical_title = "none";
    private int volume_number = -1;
    private String inclusive_page = "none";
    private Date published_date = new Date();
    private Date accessed_date = new Date();
    private String web_title = "none";
    private String article_title = "none";
    private String journal_title = "none";

    /* list of terminals */
    private ArrayList<String> allowedActions = new ArrayList<>();
    private ArrayList<String> allowedFormat = new ArrayList<>();
    private ArrayList<String> allowedSource = new ArrayList<>();

    /* list for syntax errors */
    Set<String> errorMessages = new HashSet<>();

    public DaftarPustaka() {
        addAllowedActions();
        addAllowedFormats();
        addAllowedSources()
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

    private void addAllowedSources()
    {
        allowedSource.add("book");
        allowedSource.add("magazine");
        allowedSource.add("newspaper");
        allowedSource.add("website");
        allowedSource.add("journal");
    }

    ArrayList<Author> getAuthors() {
        return authors
    }

    void setAuthors(ArrayList<Author> authors) {
        this.authors = authors
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
                errorMessages.add("Unknown action " + action);
            }
        } else {
            errorMessages.add("Action has already been set");
        }
    }

    def format(String format) {
        if (action.equalsIgnoreCase("get")) {
            if (!this.format.equalsIgnoreCase("none")) {
                errorMessages.add("Format has already been set");
            } else {
                if (allowedFormat.contains(format)) {
                    this.format = format;
                } else {
                    errorMessages.add("Unknown bibliography format " + format);
                }
            }
        } else {
            errorMessages.add("Incompatible action (must use action get)")
        }
    }

    def source(String source)
    {
        if(this.source.equalsIgnoreCase("none")){
            if(allowedSource.contains(source)){
                this.source = source;
            } else {
                errorMessages.add("Unknown source " + source);
            }
        } else {
            errorMessages.add("Source has already been set")
        }
    }

    def methodMissing(String argumentName, args){
        errorMessages.add("Unknown syntax " + argumentName + " " + args);
    }

    def author(String name)
    {
        authors.add(new Author(name));
    }

    def year(int year) {
        if(this.year == 0) {
            this.year = year;
        } else {
            errorMessages.add("Year has already been set");
        }
    }

    def title(String title){
        if(this.title.equalsIgnoreCase("none")){
            this.title = title;
        } else {
            errorMessages.add("Title has already been set");
        }
    }

    def city(String city){
        if(this.city.equalsIgnoreCase("none")){
            this.city = city;
        } else {
            errorMessages.add("City has already been set");
        }
    }

    def publisher(String publisher){
        if(this.publisher.equalsIgnoreCase("none")){
            this.publisher = publisher;
        } else {
            errorMessages.add("Publisher has already been set");
        }
    }

    def periodical_title(String title){
        if(this.periodical_title.equalsIgnoreCase("none")){
            periodical_title = title;
        } else {
            errorMessages.add("Periodical Title has already been set");
        }
    }

    def volume_number(int number){
        if(volume_number == -1){
            volume_number = number;
        } else {
            errorMessages.add("Volume number has already been set");
        }
    }

    def inclusive_page(String inclusive_page){
        if(this.inclusive_page.equalsIgnoreCase("none")){
            this.inclusive_page = inclusive_page;
        } else {
            errorMessages.add("Inclusive page has already been set");
        }
    }

    def published_date(String tanggals){
        String[] tanggal = tanggals.split(" ");
        int day = Integer.valueOf(tanggal[0]);
        int month = Integer.valueOf(tanggal[1]);
        int year = Integer.valueOf(tanggal[2]);
        if(this.published_date.getDay() == 0){
            this.published_date = new Date(day, month, year);
        } else {
            errorMessages.add("Published Date has already been set");
        }
    }

    def accessed_date(String tanggals){
        String[] tanggal = tanggals.split(" ");
        int day = Integer.valueOf(tanggal[0]);
        int month = Integer.valueOf(tanggal[1]);
        int year = Integer.valueOf(tanggal[2]);
        if(this.accessed_date.getDay() == 0){
            this.accessed_date = new Date(day, month, year);
        } else {
            errorMessages.add("Accessed Date has already been set");
        }
    }

    def web_title(String title){
        if(web_title.equalsIgnoreCase("none")){
            this.web_title = title;
        } else {
            errorMessages.add("Web Title has already been set");
        }
    }

    def article_title(String title){
        if(article_title.equalsIgnoreCase("none")){
            this.article_title = title;
        } else {
            errorMessages.add("Article Title has already been set");
        }
    }

    def journal_title(String title){
        if(journal_title.equalsIgnoreCase("none")){
            this.journal_title = title;
        } else {
            errorMessages.add("Journal Title has already been set");
        }
    }

    def getgetSQL() {
        if(source.equalsIgnoreCase("book")){
            validateBookSource();
        } else if(source.equalsIgnoreCase("magazine")){
            validateMagazineSource();
        } else if(source.equalsIgnoreCase("newspaper")){
            validateNewspaperSource();
        } else if(source.equalsIgnoreCase("website")){
            validateWebsiteSource();
        } else {
            validateJournalSource();
        }
        if (errorMessages.size() > 0) {
            System.out.println("Fail generating SQL Query. Found " + errorMessages.size() + " syntax error(s): ");
            int i = 1;
            for (String error : errorMessages) {
                System.out.printf("%d. %s\n", i, error);
                i++;
            }
        } else {
            System.out.println("No Error Found! Generating SQL Query...");
        }
    }

    private void validateBookSource()
    {

    }

    private void validateMagazineSource()
    {

    }

    private void validateNewspaperSource()
    {

    }

    private void validateWebsiteSource()
    {

    }

    private void validateJournalSource()
    {

    }

    public static void main(String[] args) {
        DaftarPustaka.make {
            action "get"
            format "mla"
            source "website"
            author "Bambang Pamungkas"
            year 2005
            title "geje"
            publisher "honda"
            inclusive_page "1"
            periodical_title "gates of babylonia"
            volume_number 10
            web_title "Information Retrieval"
            article_title "The Lost One's Weeping"
            journal_title "Indonesia Pusaka"
            accessed_date "1 1 2012"
            published_date "1 2 2015"
            getSQL
        }
    }
}