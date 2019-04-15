package id.nontontivi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Cobamysql {
    private Connection conn = null;

    public void testDb()
    {
        //Connection conn = null;
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost/nontontivi_crawler?" +
                            "user=root&password=paswot");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void insertFeed(String url, String web)
    {
        if(conn == null){
            testDb();
        }

        Statement stmt = null;
        //ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            stmt.execute("INSERT INTO feed(url,website) VALUES('"+url+"','"+web+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean ifUrlFeedExist(String url)
    {
        if(conn == null){
            testDb();
        }

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM feed WHERE url = '"+url+"'");
            int jum = 0;
            while (rs.next()) {
                jum = rs.getInt(1);
            }

            if(jum == 0)
                return false;
            else
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean ifUrlBeritaExist(String url)
    {
        if(conn == null){
            testDb();
        }

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM berita WHERE url = '"+url+"'");
            int jum = 0;
            while (rs.next()) {
                jum = rs.getInt(1);
            }

            if(jum == 0)
                return false;
            else
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<String> getListFeed(String web)
    {
        if(conn == null){
            testDb();
        }

        Statement stmt = null;
        ResultSet rs = null;
        //String[] url = null;
        List<String> url = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT url FROM feed WHERE website = '"+web+"'");
            //int x = 0;
            url = new ArrayList<String>();
            while (rs.next()) {
                url.add(rs.getString("url"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return url;
    }

    public void insertBerita(String url, String judul, String konten, String tgl,String source)
    {
        if(conn == null){
            testDb();
        }

        Statement stmt = null;
        //ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            String sql = "INSERT INTO berita(url,judul,konten,source,tgl_berita,timestamp)" + "VALUES (?, ?, ?, ?, ?, NOW())";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,url);
            ps.setString(2,judul);
            ps.setString(3,konten);
            ps.setString(4,source);
            ps.setString(5,tgl);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void hapusFeed(String url)
    {
        if(conn == null){
            testDb();
        }

        Statement stmt = null;
        //ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            stmt.execute("DELETE FROM feed WHERE url = '"+url+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
