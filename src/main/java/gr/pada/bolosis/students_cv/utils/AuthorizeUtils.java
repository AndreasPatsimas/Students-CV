package gr.pada.bolosis.students_cv.utils;

import gr.pada.bolosis.students_cv.exceptions.authorization.AuthorizationFailedException;
import org.springframework.util.ObjectUtils;

import java.security.Principal;

public class AuthorizeUtils {

    public static void authorizeRequest(String username, Principal principal){

        if(ObjectUtils.isEmpty(principal) || !principal.getName().equals(username))
            throw new AuthorizationFailedException("Unauthorized for this action");
    }
}
