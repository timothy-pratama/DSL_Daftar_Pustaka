package Model;

import sun.net.www.protocol.http.AuthenticationHeader;

import java.util.ArrayList;

/**
 * Created by timothy.pratama on 22-Nov-15.
 */
public class Website {
    private ArrayList<Author> authors = new ArrayList<>();
    private String websiteTitle;
    private String articleTitle;
    private String publisher;
    private Date publishedDate;
    private Date dateAccesed;

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public String getWebsiteTitle() {
        return websiteTitle;
    }

    public void setWebsiteTitle(String websiteTitle) {
        this.websiteTitle = websiteTitle;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Date getDateAccesed() {
        return dateAccesed;
    }

    public void setDateAccesed(Date dateAccesed) {
        this.dateAccesed = dateAccesed;
    }
}
