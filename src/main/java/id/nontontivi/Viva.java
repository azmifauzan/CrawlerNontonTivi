package id.nontontivi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class Viva {
    private String url = "https://www.viva.co.id/showbiz";

    public void getFeed() {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements terbaru = doc.getElementsByClass("title-content");
        Cobamysql db = new Cobamysql();
        for(Element link:terbaru)
        {
            String linkHref = link.attr("href");
            System.out.println("Link: "+linkHref);
            if (!db.ifUrlFeedExist(linkHref) && !db.ifUrlBeritaExist(linkHref)) {
                db.insertFeed(linkHref, "viva");
            }
        }
    }

    public void getBerita()
    {
        Cobamysql db = new Cobamysql();
        List<String> feeds = db.getListFeed("viva");
        for(String feed : feeds){
            if(!db.ifUrlBeritaExist(feed)) {
                Document doc = null;
                try {
                    doc = Jsoup.connect(feed).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String title = doc.select("div.leading-title").text();
                System.out.println("Title: " + title);
                String tgl = doc.select("div.date").text();
                String[] arrtgl = tgl.split("WIB");
                Elements konten = doc.select("div.article-detail > p");
                String textkonten = "";
                String kontenisi = konten.text().trim();
                if(kontenisi != "" && !kontenisi.isEmpty()) {
                    for (Element txt : konten) {
                        String isi = txt.text().trim();
                        if(isi != "" && !isi.isEmpty() && !isi.contains("Baca juga:")) {
                            textkonten += "<p>" + txt.text() + "</p>";
                        }
                    }
                    //System.out.println(textkonten);
                    db.insertBerita(feed, title, textkonten, arrtgl[0]+"WIB", "viva");
                }
                //db.hapusFeed(feed);
            }
            db.hapusFeed(feed);
        }
    }
}
