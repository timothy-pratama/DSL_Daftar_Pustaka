package SDL

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
    private String DatabaseName = "referensi";

    /* user input */
    private String action = "none";
    private String format = "none";
    private String source = "none";
    private String month = "none";
    private int year = 0;
    private String city = "none";
    private String state = "none";
    private String publisher = "none";
    private String periodical_title = "none";
    private String volume_number = "none";
    private String issue_number = "none";
    private String inclusive_page = "none";
    private Date published_date = new Date();
    private Date accessed_date = new Date();
    private String book_title = "none";
    private String website_title = "none";
    private String article_title = "none";
    private String journal_title = "none";
    private String url = "none";

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
            errorMessages.add("Action has already been defined");
        }
    }

    def format(String format) {
        if (action.equalsIgnoreCase("get")) {
            if (!this.format.equalsIgnoreCase("none")) {
                errorMessages.add("Format has already been defined");
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
            errorMessages.add("Source has already been defined")
        }
    }

    def methodMissing(String argumentName, args){
        errorMessages.add("Unknown syntax " + argumentName + " " + args);
    }

    def author(String name)
    {
        authors.add(new Author(name));
    }

    def month(String month) {
        if(this.month.equalsIgnoreCase("none")) {
            this.month = month;
        } else {
            errorMessages.add("Month has already been defined");
        }
    }

    def year(int year) {
        if(this.year == 0) {
            this.year = year;
        } else {
            errorMessages.add("Year has already been defined");
        }
    }

    def book_title(String title){
        if(this.book_title.equalsIgnoreCase("none")){
            this.book_title = title;
        } else {
            errorMessages.add("Title has already been defined");
        }
    }

    def city(String city){
        if(this.city.equalsIgnoreCase("none")){
            this.city = city;
        } else {
            errorMessages.add("City has already been defined");
        }
    }


    def state(String state_publication){
        if(this.state.equalsIgnoreCase("none")){
            this.state = state_publication;
        } else {
            errorMessages.add("State has already been defined");
        }
    }

    def publisher(String publisher){
        if(this.publisher.equalsIgnoreCase("none")){
            this.publisher = publisher;
        } else {
            errorMessages.add("Publisher has already been defined");
        }
    }

    def periodical_title(String title){
        if(this.periodical_title.equalsIgnoreCase("none")){
            periodical_title = title;
        } else {
            errorMessages.add("Periodical Title has already been defined");
        }
    }

    def volume_number(String number){
        if(volume_number.equalsIgnoreCase("none")){
            volume_number = number;
        } else {
            errorMessages.add("Volume number has already been defined");
        }
    }

    def inclusive_page(String inclusive_page){
        if(this.inclusive_page.equalsIgnoreCase("none")){
            this.inclusive_page = inclusive_page;
        } else {
            errorMessages.add("Inclusive page has already been defined");
        }
    }

    def published_date(String tanggals){
        String[] tanggal = tanggals.split(" ");
        int day = Integer.valueOf(tanggal[0]);
        int month = Integer.valueOf(tanggal[1]);
        int year = Integer.valueOf(tanggal[2]);
        if(!this.published_date.isSet()){
            this.published_date = new Date(day, month, year);
        } else {
            errorMessages.add("Published Date has already been defined");
        }
    }

    def accessed_date(String tanggals){
        String[] tanggal = tanggals.split(" ");
        int day = Integer.valueOf(tanggal[0]);
        int month = Integer.valueOf(tanggal[1]);
        int year = Integer.valueOf(tanggal[2]);
        if(!this.accessed_date.isSet()){
            this.accessed_date = new Date(day, month, year);
        } else {
            errorMessages.add("Accessed Date has already been defined");
        }
    }

    def website_title(String title){
        if(website_title.equalsIgnoreCase("none")){
            this.website_title = title;
        } else {
            errorMessages.add("Web Title has already been defined");
        }
    }

    def article_title(String title){
        if(article_title.equalsIgnoreCase("none")){
            this.article_title = title;
        } else {
            errorMessages.add("Article Title has already been defined");
        }
    }

    def journal_title(String title){
        if(journal_title.equalsIgnoreCase("none")){
            this.journal_title = title;
        } else {
            errorMessages.add("Journal Title has already been defined");
        }
    }

    def issue_number(String issueNumber){
        if(issue_number.equalsIgnoreCase("none")){
            this.issue_number = issueNumber;
        } else {
            errorMessages.add("Issue number has already been defined");
        }
    }

    def url(String weburl){
        if(url.equalsIgnoreCase("none")){
            this.url = weburl;
        } else {
            errorMessages.add("URL has already been defined");
        }
    }

    private void validateSource()
    {
        if(source.equalsIgnoreCase("book")){
            validateBookSource();
        } else if(source.equalsIgnoreCase("magazine")){
            validateMagazineSource();
        } else if(source.equalsIgnoreCase("newspaper")){
            validateNewspaperSource();
        } else if(source.equalsIgnoreCase("website")){
            validateWebsiteSource();
        } else if(source.equalsIgnoreCase("journal")) {
            validateJournalSource();
        } else {
            errorMessages.add("Source has not been defined");
        }
    }

    private void validateAction()
    {
        if(action.equalsIgnoreCase("none")) {
            errorMessages.add("Action has not been defined");
        }
    }

    private void validateFormat() {
        if(format.equalsIgnoreCase("none") && action.equalsIgnoreCase("get")) {
            errorMessages.add("Format has not been defined");
        }
    }

    def getgetSQL() {
        validateSource();
        validateAction();
        validateFormat();

        if (errorMessages.size() > 0) {
            System.out.println("Fail generating SQL Query. Found " + errorMessages.size() + " syntax error(s): ");
            int i = 1;
            for (String error : errorMessages) {
                System.out.printf("%d. %s\n", i, error);
                i++;
            }
        } else {
            processSQL();
        }
    }

    private boolean isBookMissingAttributes()
    {
        boolean isMissing = true;

        /*
         * Field to be checked:
         * private ArrayList<Author> authors = new ArrayList<>();
         * private int year;
         * private String book_title;
         * private String city;
         * private String publisher;
        */

        if(authors.size() > 0) {
            isMissing = false;
        }
        if(year > 0) {
            isMissing = false;
        }
        if(!book_title.equalsIgnoreCase("none")) {
            isMissing = false;
        }
        if(!city.equalsIgnoreCase("none")) {
            isMissing = false;
        }
        if(!publisher.equalsIgnoreCase("none")) {
            isMissing = false;
        }

        return isMissing;
    }

    private void validateBookAttributes() {
        /*
         * Field to be checked:
         * private ArrayList<Author> authors = new ArrayList<>();
         * private int year;
         * private String book_title;
         * private String city;
         * private String publisher;
        */

        if (authors.size() == 0 && !source.equalsIgnoreCase("book")) {
            errorMessages.add("Author has not been defined");
        }
        if(year == 0) {
            errorMessages.add("Year has not been defined");
        }
        if(book_title.equalsIgnoreCase("none")) {
            errorMessages.add("book_title has not been defined");
        }
        if(city.equalsIgnoreCase("none")) {
            errorMessages.add("City has not been defined");
        }
        if(publisher.equalsIgnoreCase("none")) {
            errorMessages.add("Publisher has not been defined");
        }
    }

    private boolean isJournalMissingAttributes(){

        boolean isMissing = true;

        /* Fields to be checked:
         * private ArrayList<Author> authors = new ArrayList<>();
         * private String articleTitle;
         * private String journalTitle;
         * private String volumeNumber;
         * private String issueNumber;
         * private int year;
         * private String inclusivePage;
        */

        if(authors.size()>0) {
            isMissing = false;
        }
        if(!article_title.equalsIgnoreCase("none")) {
            isMissing = false;
        }
        if(!journal_title.equalsIgnoreCase("none")) {
            isMissing = false;
        }
        if(!volume_number.equalsIgnoreCase("none")) {
            isMissing = false;
        }
        if(!issue_number.equalsIgnoreCase("none")) {
            isMissing = false;
        }
        if(year > 0) {
            isMissing = false;
        }
        if(!inclusive_page.equalsIgnoreCase("none")) {
            isMissing = false;
        }

        return isMissing;
    }

    private void validateJournalAttributes(){
        /* Fields to be checked:
         * private ArrayList<Author> authors = new ArrayList<>();
         * private String articleTitle;
         * private String journalTitle;
         * private String volumeNumber;
         * private String issueNumber;
         * private int year;
         * private String inclusivePage;
        */

        if(authors.size() == 0) {
            errorMessages.add("Author has not been defined");
        }
        if(article_title.equalsIgnoreCase("none")) {
            errorMessages.add("Article title has not been defined");
        }
        if(journal_title.equalsIgnoreCase("none")) {
            errorMessages.add("Journal title has not been defined");
        }
        if(volume_number.equalsIgnoreCase("none")) {
            errorMessages.add("Volume number has not been defined");
        }
        if(issue_number.equalsIgnoreCase("none")) {
            errorMessages.add("Issue number has not been defined");
        }
        if(year == 0) {
            errorMessages.add("Year has not been defined");
        }
        if(inclusive_page.equalsIgnoreCase("none")) {
            errorMessages.add("Inclusive page has not been defined");
        }
    }

    private boolean isMagazineMissingAttributes(){
        boolean isMissing = true;

        /*Fields to be checked:
        private ArrayList<Author> authors = new ArrayList<>();
        private String articleTitle;
        private String journalTitle;
        private String volumeNumber;
        private String issueNumber;
        private int year;
        private String inclusivePage;*/

        if(authors.size() > 0) {
            isMissing = false;
        }
        if(!article_title.equalsIgnoreCase("none")) {
            isMissing = false;
        }
        if(!journal_title.equalsIgnoreCase("none")) {
            isMissing = false;
        }
        if(!volume_number.equalsIgnoreCase("none")) {
            isMissing = false;
        }
        if(!issue_number.equalsIgnoreCase("none")) {
            isMissing = false;
        }
        if(year > 0) {
            isMissing = false;
        }
        if(!inclusive_page.equalsIgnoreCase("none")) {
            isMissing = false;
        }

        return isMissing;
    }

    private void validateMagazineAttributes(){
        /*Fields to be checked:
        private ArrayList<Author> authors = new ArrayList<>();
        private String articleTitle;
        private String journalTitle;
        private String volumeNumber;
        private String issueNumber;
        private int year;
        private String inclusivePage;*/

        if(authors.size() == 0) {
            errorMessages.add("Author has not been defined")
        }
        if(article_title.equalsIgnoreCase("none")) {
            errorMessages.add("Article title has not been defined");
        }
        if(journal_title.equalsIgnoreCase("none")) {
            errorMessages.add("Journal title has not been defined");
        }
        if(volume_number.equalsIgnoreCase("none")) {
            errorMessages.add("Volume number has not been defined");
        }
        if(issue_number.equalsIgnoreCase("none")) {
            errorMessages.add("Issue number has not been defined");
        }
        if(year == 0) {
            errorMessages.add("Year has not been defined");
        }
        if(inclusive_page.equalsIgnoreCase("none")) {
            errorMessages.add("Inclusive page has not been defined");
        }
    }

    private boolean isNewspaperMissingAttributes(){

        boolean isMissing = true;

        /*
        Fields to be checked:
        private ArrayList<Author> authors = new ArrayList<>();
        private int year;
        private String book_title;
        private String periodicalTitle;
        private String volumeNumber;
        private String inclusivePage;
         */

        if(authors.size() > 0) {
            isMissing = false;
        }
        if(year > 0) {
            isMissing = false;
        }
        if(!book_title.equalsIgnoreCase("none")) {
            isMissing = false;
        }
        if(!periodical_title.equalsIgnoreCase("none")) {
            isMissing = false;
        }
        if(!volume_number.equalsIgnoreCase("none")) {
            isMissing = false;
        }
        if(!inclusive_page.equalsIgnoreCase("none")) {
            isMissing = false;
        }

        return isMissing;
    }

    private void validateNewspaperAttributes(){
        /*
        Fields to be checked:
        private ArrayList<Author> authors = new ArrayList<>();
        private int year;
        private String book_title;
        private String periodicalTitle;
        private String volumeNumber;
        private String inclusivePage;
         */

        if(authors.size() == 0) {
            errorMessages.add("Author has not been defined");
        }
        if(year == 0) {
            errorMessages.add("Year has not been defined");
        }
        if(book_title.equalsIgnoreCase("none")) {
            errorMessages.add("Title has not been defined");
        }
        if(periodical_title.equalsIgnoreCase("none")) {
            errorMessages.add("Periodical title has not been defined");
        }
        if(volume_number.equalsIgnoreCase("none")) {
            errorMessages.add("Volume number has not been defined");
        }
        if(inclusive_page.equalsIgnoreCase("none")) {
            errorMessages.add("Inclusive page has not been defined");
        }
    }

    private boolean isWebsiteMissingAttributes(){

        boolean isMissing = true;

        /*
        Fields to be checked:
        private ArrayList<Author> authors = new ArrayList<>();
        private String websiteTitle;
        private String articleTitle;
        private String publisher;
        private Date publishedDate;
        private Date dateAccesed;
         */

        if(authors.size() > 0) {
            isMissing = false;
        }
        if(!website_title.equalsIgnoreCase("none")) {
            isMissing = false;
        }
        if(!article_title.equalsIgnoreCase("none")) {
            isMissing = false;
        }
        if(!publisher.equalsIgnoreCase("none")) {
            isMissing = false;
        }
        if(published_date.isSet()) {
            isMissing = false;
        }
        if(accessed_date.isSet()) {
            isMissing = false;
        }

        return isMissing;
    }

    private void validateWebsiteAttributes(){
        /*
        Fields to be checked:
        private ArrayList<Author> authors = new ArrayList<>();
        private String websiteTitle;
        private String articleTitle;
        private String publisher;
        private Date publishedDate;
        private Date dateAccesed;
         */

        if(authors.size() == 0) {
            errorMessages.add("Author has not been defined");
        }
        if(website_title.equalsIgnoreCase("none")) {
            errorMessages.add("Website title has not been defined");
        }
        if(article_title.equalsIgnoreCase("none")) {
            errorMessages.add("Article title has not been defined");
        }
        if(publisher.equalsIgnoreCase("none")) {
            errorMessages.add("Publisher has not been defined");
        }
        if(!published_date.isSet()) {
            errorMessages.add("Published date has not been defined");
        }
        if(!accessed_date.isSet()) {
            errorMessages.add("Accessed date has not been defined");
        }
    }

    private void validateBookSource()
    {
        if(action.equalsIgnoreCase("add")) {
            validateBookAttributes();
        } else {
            if (isBookMissingAttributes()) {
                errorMessages.add("Book data is missing")
            }
        }
    }

    private void validateMagazineSource()
    {
        if(action.equalsIgnoreCase("get")) {
            validateMagazineAttributes();
        } else {
            if(isMagazineMissingAttributes()) {
                errorMessages.add("Magazine data is missing");
            }
        }
    }

    private void validateNewspaperSource()
    {
        if(action.equalsIgnoreCase("get")) {
            validateNewspaperAttributes();
        } else {
            if(isNewspaperMissingAttributes()) {
                errorMessages.add("Newspaper data is missing");
            }
        }
    }

    private void validateWebsiteSource()
    {
        if(action.equalsIgnoreCase("get")) {
            validateWebsiteAttributes();
        } else {
            if(isWebsiteMissingAttributes()) {
                errorMessages.add("Website data is missing");
            }
        }
    }

    private void validateJournalSource()
    {
        if(action.equalsIgnoreCase("get")) {
            validateJournalAttributes();
        } else {
            if(isJournalMissingAttributes()) {
                errorMessages.add("Journal data is missing");
            }
        }
    }

    private void processSQL() {
        String MLARef = "";
        String APARef = "";
        String SQLString = "";
        if(action.equalsIgnoreCase("add")){
            if(source.equalsIgnoreCase("book")){

                MLARef =  GenerateMLACitationBook();
                APARef = GenerateAPACitationBook();
            }
            else if(source.equalsIgnoreCase("magazine") || source.equalsIgnoreCase("newspaper")){
                MLARef =  GenerateMLACitationMagazineNNewspaper();
                APARef =  GenerateAPACitationMagazineNNewspaper();
            }
            else if(source.equalsIgnoreCase("journal")){
                MLARef =  GenerateMLACitationJournal();
                APARef = GenerateAPACitationJournal();
            }
            else if(source.equalsIgnoreCase("website")){
                MLARef =  GenerateMLACitationWebsite();
                APARef =  GenerateAPACitationWebsite();
            }
            else{
                errorMessages.add("Source not recognized")
            }
            SQLString = String.format("INSERT INTO %s (Source,Year,Book_Title,City,Publisher,Periodical_Title,Volume_Number,Issue_Number,Inclusive_Page,Published_Date,Accessed_Date,Website_Title,Article_Title,Journal_Title,APA,MLA" +
                    ") VALUES ('%s','%d','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');",DatabaseName,source,year,book_title,city,publisher,periodical_title,volume_number,issue_number,inclusive_page,published_date.toString(),accessed_date.toString(),website_title,article_title,journal_title,APARef,MLARef);
            System.out.println("SQL : "+SQLString);
        }
    }

    public static void main(String[] args) {
        DaftarPustaka.make {
            action "add"
            source "newspaper"
            author "Alvin Natawiguna"
            article_title "Programming ABC"
            periodical_title "Newyork Times"
            volume_number "90"
            inclusive_page "90-100"
            month "April"
            year 2015
            published_date "4 04 2015"
            getSQL
        }
    }

    public String GenerateMLACitationBook(){
        String MLACitationBook = ""
        String authorCitation = GenerateAuthorFormat();
        if(!authorCitation.equalsIgnoreCase("")){
            MLACitationBook += authorCitation+" ";
        }
        MLACitationBook += '*'+book_title+"*. "+city+": "+publisher+", "+year+". Print."
        return MLACitationBook;
    }

    public String GenerateMLACitationMagazineNNewspaper(){
        String MLACitationMagazineNewspaper = "";
        String authorCitation = GenerateAuthorFormat();
        if(!authorCitation.equalsIgnoreCase("")){
            MLACitationMagazineNewspaper += authorCitation+" ";
        }
        MLACitationMagazineNewspaper += '"' + article_title + '."'+" *" + periodical_title+ "* " + published_date.day + " " + published_date.getMonthString() +". " + published_date.year + ": "+inclusive_page +". Print.";
        return MLACitationMagazineNewspaper;
    }

    public String GenerateMLACitationJournal() {
        String MLACitationJournal = "";
        String authorCitation = GenerateAuthorFormat();
        MLACitationJournal += authorCitation+" " + '"' + article_title + '." *'+ journal_title+"* "+volume_number+"."+issue_number+" ("+year+"): "+inclusive_page+". Print.";
        return MLACitationJournal;
    }

    public String GenerateMLACitationWebsite(){
        String MLACitationWebsite = "";
        String authorCitation = GenerateAuthorFormat();
        MLACitationWebsite += authorCitation + ' "' + article_title +'." ' + "*" + website_title +"*. " + publisher + ", " + published_date.day + " " + published_date.getMonthString() + " " + published_date.year + ". Web. " + accessed_date.day + " " + accessed_date.getMonthString() +". " + accessed_date.year +". " + "<" + url +">."
        return MLACitationWebsite;
    }

    public String GenerateAuthorFormat(){
        String authorCitation = "";
        if(authors.size() > 0){
            //One Author

            if(!authors.get(0).lastName.equalsIgnoreCase("")){
                authorCitation += authors.get(0).lastName + ", ";
            }
            authorCitation += authors.get(0).firstName;
            if(authors.size() > 1 && authors.size() <= 3){
                //More than one author
                for(int i =1;i<authors.size()-1;i++){
                    authorCitation += ", " + authors.get(i).firstName;
                    if(!authors.get(i).lastName.equalsIgnoreCase("")){
                        authorCitation += " " +authors.get(i).lastName;
                    }
                }
                authorCitation += ", and " + authors.get(authors.size()-1).firstName;
                if(!authors.get(authors.size()-1).lastName.equalsIgnoreCase("")){
                    authorCitation += " " + authors.get(authors.size()-1).lastName;
                }
            }else if(authors.size() > 3){
                authorCitation += ", et al";
            }
            authorCitation += ".";
        }
        return authorCitation;
    }

    public String GenerateAPACitationBook(){
        String MLACitationJournal = "";
        String authorCitation = GenerateAuthorFormat();
        if(!authorCitation.equalsIgnoreCase("")){
            MLACitationJournal += authorCitation+" ";
        }
        MLACitationJournal += '(' + year + '). *' + book_title + "*. "+ city + ", " + state +": " + publisher + ".";
        return MLACitationJournal;
    }

    public String GenerateAPACitationMagazineNNewspaper(){
        String MLACitationJournal = "";
        String authorCitation = GenerateAuthorFormat();
        if(!authorCitation.equalsIgnoreCase("")){
            MLACitationJournal += authorCitation+" ";
        }
        MLACitationJournal += "(" + year +", " + month + "). "  + article_title + ". *"+ periodical_title + "*, ";
        if(!volume_number.equalsIgnoreCase("none") && !issue_number.equalsIgnoreCase("none")){
            MLACitationJournal += volume_number + "(" + issue_number +") ";
        }
        MLACitationJournal += inclusive_page + ".";
        return MLACitationJournal;
    }

    public String GenerateAPACitationJournal() {
        String MLACitationJournal = "";
        String authorCitation = GenerateAuthorFormat();
        if(!authorCitation.equalsIgnoreCase("")){
            MLACitationJournal += authorCitation+" ";
        }
        MLACitationJournal += '(' + year + '). ' + article_title + ". *"+ journal_title+"*, *"+volume_number+"*("+issue_number+"), "+inclusive_page+".";
        return MLACitationJournal;
    }

    public String GenerateAPACitationWebsite() {
        String MLACitationJournal = "";
        String authorCitation = GenerateAuthorFormat();
        if(!authorCitation.equalsIgnoreCase("")){
            MLACitationJournal += authorCitation+" ";
        }
        MLACitationJournal += '(' + published_date.year + ". " + published_date.month + " " + published_date.day +  "). *" + article_title + "*. Retrieved from " + url;
        return MLACitationJournal;
    }
    
}