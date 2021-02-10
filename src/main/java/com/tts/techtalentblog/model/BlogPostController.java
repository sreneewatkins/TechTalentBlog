package com.tts.techtalentblog.model;

import com.tts.techtalentblog.repo.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/*
 create a controller for our Blog Post class.  This controller will
 determine how the data and user will move through our project.  The
 controller provides the connection between our templates (browser view)
 and the data from our database.
 Start by giving the controller class the @Controller annotation.
 This will help send the output to a template, rather than write output
 directly from the controller.
 */
@Controller
public class BlogPostController {

    /*
    This annotation adds our BlogPostRepository to the controller. @Autowired
    allows us to implement what's known as a dependency injection.
     */
    @Autowired
    private BlogPostRepository blogPostRepository;

    /*
    This annotation will be used for our index method, which will
    return the template specified - a template called "index" in our
    blog post template directory.
    This is the template our application will return when the user
    enters our root url.  It is our application's "Home Page".
     */
    @GetMapping(value="/")
    public String index(BlogPost blogPost) {
        return "blogpost/index";
    }

    /*
    We set up another method that will take in the data entered in the
    form and add it to the database.  This method will  Post the
    information to the database and display a "confirmation" on a new
    template called "result".
     */
    private BlogPost blogPost;

    @PostMapping(value = "/")
    public String addNewBlogPost(BlogPost blogPost, Model model) {
        blogPostRepository.save(new BlogPost(
                blogPost.getTitle(),
                blogPost.getAuthor(),
                blogPost.getBlogEntry()
        ));
        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("author", blogPost.getAuthor());
        model.addAttribute("blogEntry", blogPost.getBlogEntry());
        return "blogpost/result";
    }

}//end BlogPostController class
