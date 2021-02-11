package com.tts.techtalentblog.controller;

import com.tts.techtalentblog.model.BlogPost;
import com.tts.techtalentblog.repo.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    /*  this would be used instead of @Autowired - constructor based
    dependence injection. Best practice if only one dependency.
    public BlogPostController(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }
*/
    private static List<BlogPost> posts = new ArrayList<>();

    /*
    This annotation will be used for our index method, which will
    return the template specified - a template called "index" in our
    blog post template directory.
    This is the template our application will return when the user
    enters our root url.  It is our application's "Home Page".
    Add a Model and then add our posts list to that Model as an attribute.
    This will allow us to access that model, and therefore our list on our
    Index page.
     */
    @GetMapping(value="/")
    public String index(BlogPost blogPost, Model model) {
        model.addAttribute("posts", posts);
        return "blogpost/index";
    }

    /*
    We set up another method that will take in the data entered in the
    form and add it to the database.  This method will  Post the
    information to the database and display a "confirmation" on a new
    template called "result".
     */
    /*
    Now, we have our lists of all blog posts. Our index.html page needs to be
    able to access this list so lets go ahead and edit our index method in our
    BlogPostController to make that happen.
    We're going to add a Model and then add our posts list to that Model as an
    attribute. This will allow us to access that model, and therefore our list
    on our Index page.
     */
    private BlogPost blogPost;

    /*
    We've told the application to expect a Post Request from the URL /blogposts
    and to return the blogpost/result page. Now we need to give our users a way
    to actually get to that page.
     */
    @GetMapping(value = "/blogposts/new")
    public String newBlog (BlogPost blogPost) {
        return "blogpost/new";
    }

    @PostMapping(value = "/blogposts")
    public String addNewBlogPost(BlogPost blogPost, Model model) {
//        blogPostRepository.save(new BlogPost(
//                blogPost.getTitle(),
//                blogPost.getAuthor(),
//                blogPost.getBlogEntry()
//        ));
        blogPostRepository.save(blogPost);
        posts.add(blogPost);
        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("author", blogPost.getAuthor());
        model.addAttribute("blogEntry", blogPost.getBlogEntry());
        return "blogpost/result";
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.DELETE)
    public String deletePostWithId(@PathVariable Long id, BlogPost blogPost) {
        blogPostRepository.deleteById(id);
        return "blogpost/index";
    }

}//end BlogPostController class
