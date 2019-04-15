package id.nontontivi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class Kompas {
    private String url = "https://entertainment.kompas.com/";

    public void getFeed() {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements terbaru = doc.getElementsByClass("headline__big__link");
        Cobamysql db = new Cobamysql();
        for(Element link:terbaru)
        {
            String linkHref = link.attr("href");
            System.out.println("Link: "+linkHref);
            if (!db.ifUrlFeedExist(linkHref) && !db.ifUrlBeritaExist(linkHref)) {
                System.out.println("Link: "+linkHref);
                db.insertFeed(linkHref, "kompas");
            }
        }

        Elements terbaru2 = doc.getElementsByClass("headline__thumb__link");
        for(Element link:terbaru2)
        {
            String linkHref = link.attr("href");
            System.out.println("Link: "+linkHref);
            if (!db.ifUrlFeedExist(linkHref) && !db.ifUrlBeritaExist(linkHref)) {
                System.out.println("Link: "+linkHref);
                db.insertFeed(linkHref, "kompas");
            }
        }

        Elements terbaru3 = doc.getElementsByClass("article__link");
        for(Element link:terbaru3)
        {
            String linkHref = link.attr("href");
            System.out.println("Link: "+linkHref);
            if (!db.ifUrlFeedExist(linkHref) && !db.ifUrlBeritaExist(linkHref)) {
                System.out.println("Link: "+linkHref);
                db.insertFeed(linkHref, "kompas");
            }
        }
    }

    public void getBerita()
    {
        Cobamysql db = new Cobamysql();
        List<String> feeds = db.getListFeed("kompas");
        for(String feed : feeds){
            if(!db.ifUrlBeritaExist(feed)) {
                Document doc = null;
                try {
                    doc = Jsoup.connect(feed).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String title = doc.select("h1.read__title").text();
                String[] arrtitle = title.split(" ");
                System.out.println("Title: " + title);
                String tgl = doc.select("div.read__time").text();
                String[] arrtgl = tgl.split("-");
                Elements konten = doc.select("div.read__content > p");
                String textkonten = "";
                String kontenisi = konten.text().trim();
                if(!arrtitle[0].equalsIgnoreCase("(foto)") && !arrtitle[0].equalsIgnoreCase("(video)") && kontenisi != "" && !kontenisi.isEmpty()) {
                    for (Element txt : konten) {
                        String isi = txt.text().trim();
                        if(isi != "" && !isi.isEmpty() && !isi.contains("Sumber:")) {
                            //System.out.println("isi :"+isi);
                            textkonten += "<p>" + txt.text() + "</p>";
                        }
                    }
                    //System.out.println("Tgl: "+arrtgl[1].trim());
                    db.insertBerita(feed, title, textkonten, arrtgl[1].trim(), "kompas");
                }
                //db.hapusFeed(feed);
            }
            db.hapusFeed(feed);
        }
    }
}
