package com.tts.techtalentblog.repo;

import com.tts.techtalentblog.model.BlogPost;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/*
To post the info from the form to the database, we need to create an
interface that will aid in adding data to our database.  This is called
a repository. In the repository, we want to extend the functionality to
include the functionality of the Crud Repository. This will import the
Spring CrudRepository and the methods needed to modify data in our
database. You could annotate as repository or not.
 */
@Repository
public interface BlogPostRepository extends CrudRepository<BlogPost, Long> {

    // Generics are type parameters <Type is BlogPost, ID is Long>
    //JpaRepository. One leaves your database more exposed. Not sure
    //which to avoid.

}//end BlogPostRepository
