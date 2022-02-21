/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Blog;
import entity.Category;
import entity.Document;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DbConnection;

/**
 *
 * @author ELOCK2
 */
public class BlogDao {

    private DbConnection db;
    private Connection c;

    private CategoryDao categoryDao;
    private DocumentDao documentDao;

    public DbConnection getDb() {
        if (this.db == null) {
            this.db = new DbConnection();
        }
        return db;
    }

    public void setDb(DbConnection db) {
        this.db = db;
    }

    public Connection getC() {
        if (this.c == null) {
            this.c = getDb().connect();
        }
        return c;
    }

    public void setC(Connection c) {
        this.c = c;
    }

    public CategoryDao getCategoryDao() {
        if (this.categoryDao == null) {
            this.categoryDao = new CategoryDao();
        }
        return categoryDao;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<Blog> getBlogs() {
        List<Blog> blogList = new ArrayList();
        try {
            Statement st = this.getC().createStatement();
            ResultSet rs = st.executeQuery("select * from blog order by id desc");
            while (rs.next()) {
                //System.out.println(rs.getString("name"));
                Blog tmp = new Blog();
                tmp.setTitle(rs.getString("title"));
                tmp.setDetail(rs.getString("detail"));
                tmp.setId(rs.getInt("id"));
                tmp.setBlogCategories(this.getCategoryDao().getBlogCategories(tmp.getId()));
                //System.out.println(tmp.getBlogCategories().toString());
                blogList.add(tmp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BlogDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blogList;
    }

    public int create(Blog blog) {
        int blog_id = 0;
        try {
            PreparedStatement pst = this.getC().prepareStatement("INSERT INTO blog (title, detail) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, blog.getTitle());
            pst.setString(2, blog.getDetail());
            pst.executeUpdate();

            ResultSet gk = pst.getGeneratedKeys();
            if (gk.next()) {
                blog_id = gk.getInt(1);
            }
            if (blog_id > 0) {
                for (Category c : blog.getBlogCategories()) {
                    pst = this.getC().prepareStatement("INSERT INTO blog_category (blog_id, category_id) VALUES (?,?)");
                    pst.setInt(1, blog_id);
                    pst.setInt(2, c.getId());
                    pst.executeUpdate();
                }
            }

            //documentDao.insertWithBlogId(document,blog_id);   
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return blog_id;
    }

}