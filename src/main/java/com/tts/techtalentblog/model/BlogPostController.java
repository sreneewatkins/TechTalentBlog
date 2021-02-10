package com.tts.techtalentblog.model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

}//end BlogPostController class
