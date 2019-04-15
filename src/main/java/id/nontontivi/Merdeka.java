package id.nontontivi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class Merdeka {
    private String url = "https://www.merdeka.com/artis/";

    public void getFeed() {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements terbaru = doc.select("div.meta-content > div > a");
        Cobamysql db = new Cobamysql();
        for(Element link:terbaru)
        {
            String linkHref = link.attr("href");
            System.out.println("Link: "+linkHref);
            if (!db.ifUrlFeedExist("https://merdeka.com"+linkHref) && !db.ifUrlBeritaExist("https://merdeka.com"+linkHref)) {
                db.insertFeed("https://merdeka.com"+linkHref, "merdeka");
                //System.out.println("Link: "+linkHref);
            }
        }
    }

    public void getBerita()
    {
        Cobamysql db = new Cobamysql();
        List<String> feeds = db.getListFeed("merdeka");
        for(String feed : feeds){
            if(!db.ifUrlBeritaExist(feed)) {
                Document doc = null;
                try {
                    doc = Jsoup.connect(feed).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String title = doc.select("div.mdk-dt-headline > h1").text();
                String[] arrtitle = title.split(" ");
                System.out.println("Title: " + title);
                String tgl = doc.select("span.date-post").text();
                Elements konten = doc.select("div.mdk-body-paragpraph > p");
                String textkonten = "";
                String kontenisi = konten.text().trim();
                if(!arrtitle[0].equalsIgnoreCase("(foto)") && !arrtitle[0].equalsIgnoreCase("(video)") && kontenisi != "" && !kontenisi.isEmpty()) {
                    for (Element txt : konten) {
                        String isi = txt.text().trim();
                        if(isi != "" && !isi.isEmpty() && !isi.contains("Baca juga:")) {
                            //System.out.println("isi :"+isi);
                            textkonten += "<p>" + txt.text() + "</p>";
                        }
                    }
                    //System.out.println("isi: "+textkonten);
                    db.insertBerita(feed, title, textkonten, tgl, "merdeka");
                }
            }
            db.hapusFeed(feed);
        }
    }
}
