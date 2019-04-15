package id.nontontivi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class Detikhot {
    private String url = "https://hot.detik.com/movie";
    public Detikhot(){}

    public void getFeed() {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //String title = doc.title();
        //System.out.println("Title:" + title);

        Elements beritautama = doc.select("div.box_hl > article > div.ratio_headline > div.lqd > a");
        //String xx = beritautama.toString();
        //System.out.println(xx);
        Cobamysql db = new Cobamysql();
        for (Element link : beritautama) {
            String linkHref = link.attr("href");
            System.out.println("Link: " + linkHref);
            if (!db.ifUrlFeedExist(linkHref) && !db.ifUrlBeritaExist(linkHref)) {
                db.insertFeed(linkHref, "detikhot");
            }
        }

        beritautama = doc.select("span.berita_terkait > a");
        for (Element link : beritautama) {
            String linkHref = link.attr("href");
            System.out.println("Link: " + linkHref);
            if (!db.ifUrlFeedExist(linkHref) && !db.ifUrlBeritaExist(linkHref)) {
                db.insertFeed(linkHref, "detikhot");
            }
        }

        Elements newsfeed = doc.select("ul.list_feed > li > article > div > div > a");
        for (Element link : newsfeed) {
            String linkHref = link.attr("href");
            System.out.println("Link: " + linkHref);
            if (!db.ifUrlFeedExist(linkHref) && !db.ifUrlBeritaExist(linkHref)) {
                db.insertFeed(linkHref, "detikhot");
            }
        }
    }

    public void getBerita()
    {
        Cobamysql db = new Cobamysql();
        List<String> feeds = db.getListFeed("detikhot");
        for(String feed : feeds){
            if(!db.ifUrlBeritaExist(feed)) {
                Document doc = null;
                try {
                    doc = Jsoup.connect(feed).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String title = doc.title();
                System.out.println("Title: " + title);
                String tgl = doc.select("div.jdl > span.date").text();
                String konten = doc.getElementById("detikdetailtext").text();
                System.out.println("Konten: "+konten);
                //db.insertBerita(feed,title,konten,tgl,"detikhot");
                //db.hapusFeed(feed);
            }
        }
    }
}
