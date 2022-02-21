/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BlogDao;
import dao.DocumentDao;
import entity.Blog;
import entity.Document;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ELOCK2
 */
@SessionScoped
@ManagedBean(name = "BlogController", eager = true)
public class BlogController implements Serializable {

    private Blog blog;
    private BlogDao blogDao;
    private List<Blog> blogList;
    
    private Document document;
    private DocumentDao documentDao;

    @ManagedProperty(value = "#{CategoryController}")
    private CategoryController categoryController;
    
    @ManagedProperty(value = "#{DocumentController}")
    private DocumentController documentController;
    
    public Blog getBlog() {
        if (this.blog == null) {
            this.blog = new Blog();
        }
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public BlogDao getBlogDao() {
        if (this.blogDao == null) {
            this.blogDao = new BlogDao();
        }
        return blogDao;
    }

    public void setBlogDao(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    public List<Blog> getBlogList() {
        this.blogList = this.getBlogDao().getBlogs();
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }

    public String readMore(Blog blog) {
        this.blog = blog;
        return "readmore?faces-redirect=true";
    }

    public CategoryController getCategoryController() {
        return categoryController;
    }

    public void setCategoryController(CategoryController categoryController) {
        this.categoryController = categoryController;
    }

    public DocumentController getDocumentController() {
        return documentController;
    }

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public DocumentDao getDocumentDao() {
        return documentDao;
    }

    public void setDocumentDao(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }
      
    public void create() {
        
        int blog_id = this.getBlogDao().create(this.blog);
        this.getDocumentController().uploadWithBlogId(blog_id);
    }

 
    
}