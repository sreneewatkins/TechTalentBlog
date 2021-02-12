package com.tts.techtalentblog.controller;

import com.tts.techtalentblog.model.BlogPost;
import com.tts.techtalentblog.repo.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
    If you look at the documentation for findAll(), you'll see it returns a
    Java object called an Iterable. Its a very basic type of object that
    really only does one thing - allow a group of objects to be iterated on
    (aka run a for-each loop on it). That's why we're able to include it
    directly in our for-each loop above - that's what its built for.
     */
    @GetMapping(value="/")
    public String index(BlogPost blogPost, Model model) {
        posts.removeAll(posts);
        for (BlogPost post : blogPostRepository.findAll()) {
            posts.add(post);
        }
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
        blogPostRepository.save(blogPost);
//        posts.add(blogPost);
//        (List<BlogPost>)blogPostRepository.findAll();  //must cast this
        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("author", blogPost.getAuthor());
        model.addAttribute("blogEntry", blogPost.getBlogEntry());
        return "blogpost/result";
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.DELETE)
    public String deletePostWithId(@PathVariable Long id) {
        //There is no blogPost being passed from the delete button on index.html
        //removes object from arrayList
        posts.removeIf(post -> post.getId() == id);
        /*
        for (BlogPost post : posts ) {
            if (post.getId() == id) {
                posts.remove(post);
            }
        }
        */
        //removes object from database
        blogPostRepository.deleteById(id);
        return "blogpost/index";
    }

    /*
    The only new thing here is deleteById(), which is another inherited method
    from our Repository that does exactly what it says it does - deletes
    records with the primary key you provide it!
     */
    @RequestMapping(value = "blogposts/delete/{id}")
    public String deletePostById(@PathVariable Long id, BlogPost blogPost) {
        blogPostRepository.deleteById(id);
        return "blogpost/delete";
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.GET)
    public String editPostWithId(@PathVariable Long id, BlogPost blogPost, Model model) {
        Optional<BlogPost> post = blogPostRepository.findById(id);
        if (post.isPresent()) {
            BlogPost actualPost = post.get();
            model.addAttribute("blogPost", actualPost);
        }
        return "blogpost/edit";
    }
    /*
    deletePostWithID method:
    service.deleteBlogPost(id);
    posts = service.listAll();
    model.addAttirbute("posts", posts);
    return "blogpost/index";
     */
    /*
    we just created a new endpoint of our app that we need to map correctly:
    blogposts/update/{id}.
    But if you look at the body of our if-statement, you can tell that we're
    going about updating our existing post much differently than just saving
    a new BlogPost like in addNewBlogPost(). We're using our getters and
    setters that we generated in BlogPost.java to write over the existing
    data with our new data.
    Notice that we're still using blogPostRepository.save() like we are in
    addNewBlogPost()! That's because save() is so smart that it handles both
    the creation of new records AND the updating of existing ones.
     */
    @RequestMapping(value = "/blogposts/update/{id}")
    public String updateExistingPost(@PathVariable Long id, BlogPost blogPost, Model model) {
        Optional<BlogPost> post = blogPostRepository.findById(id);
        if (post.isPresent()) {
            BlogPost actualPost = post.get();
            actualPost.setTitle(blogPost.getTitle());
            actualPost.setAuthor(blogPost.getAuthor());
            actualPost.setBlogEntry(blogPost.getBlogEntry());
            blogPostRepository.save(actualPost);
            model.addAttribute("blogPost", actualPost);
        }
        //maybe create an edited results page to return to?
        return "blogpost/result";
    }

}//end BlogPostController class
