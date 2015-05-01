package com.hibernate.service;


import com.hibernate.entity.User;
import org.hibernate.ObjectNotFoundException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final String USER_NAME_1 = "test_user_name1";
    private static final String PASSWORD_1 = "test_password1";
    private static final String EMAIL_1 = "test_email1";

    private static final String USER_NAME_2 = "test_user_name2";
    private static final String PASSWORD_2 = "test_password2";
    private static final String EMAIL_2 = "test_email2";

    private static final String USER_NAME_3 = "test_user_name3";
    private static final String PASSWORD_3 = "test_password3";
    private static final String EMAIL_3 = "test_email3";

    private static final int USER_ID_NOT_FOUND = 100;
    private static final String EMAIL_BEFORE_SAVE = "test_email1_before_save";
    private static final String EMAIL_AFTER_SAVE = "test_email1_after_save";


    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    private UserService userService = applicationContext.getBean(UserService.class);

    @Test
    public void shouldSuccessfulSaveNewUserWithOutTransactionAndFoundItWithOutSessionCallFlush(){
        final User user = createUser(USER_NAME_1, PASSWORD_1, EMAIL_1);
        int id = userService.saveWithOutTransaction(user);

        final User actualUser = userService.findById(id);

        assertNotNull("User should be exist ", actualUser);
        assertEquals("User should be equals", user, actualUser);
    }

    @Test
    public void shouldSuccessfulSaveNewUserWithOutTransactionAndFoundItWithSessionCallFlush(){
        final User user = createUser(USER_NAME_2, PASSWORD_2, EMAIL_2);
        int id = userService.saveWithOutTransactionButAfterSaveExecuteSessionFlush(user);

        final User actualUser = userService.findById(id);

        assertNotNull("User should be exist ", actualUser);
        assertEquals("User should be equals", user, actualUser);
    }

    @Test
    public void shouldSuccessfulSaveNewUserWithTransaction(){
        final User user = createUser(USER_NAME_3, PASSWORD_3, EMAIL_3);
        int id = userService.saveWithTransaction(user);

        final User actualUser = userService.findById(id);

        assertNotNull("User should be exist ", actualUser);
        assertEquals("User should be equals", user, actualUser);
    }

    @Test
    public void shouldSuccessfulFindUserByIdAndUpdateEmail(){
        final User user = createUser(USER_NAME_3, PASSWORD_3, EMAIL_3);
        int user3Id = userService.saveWithTransaction(user);

        int id = userService.findUserByIdAndUpdateEmail(user3Id, EMAIL_BEFORE_SAVE, EMAIL_AFTER_SAVE);

        final User actualUser = userService.findById(id);

        assertNotNull("User should be exist ", actualUser);
        assertNotEquals("User email should not be email before save", EMAIL_BEFORE_SAVE, actualUser.getEmail());
        assertEquals("User email should be after save", EMAIL_AFTER_SAVE, actualUser.getEmail());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void shouldThrowExceptionDueToObjectNotFound(){
        userService.loadWithTransaction(USER_ID_NOT_FOUND);
    }

    @Test
    public void shouldReturnNullDueToObjectNotFound(){
        User actualUser = userService.getWithTransaction(USER_ID_NOT_FOUND);
        assertNull("Should return null due to user not fount", actualUser);
    }

    @Test
    @Ignore
    //TODO Understand why proxy can't initialize
    public void shouldSuccessfulLoadWithOutTransaction(){
        final User expectedUser = createUser(USER_NAME_1, PASSWORD_1, EMAIL_1);
        int user1Id = userService.saveWithTransaction(expectedUser);

        final User actualUser = userService.loadWithOutTransaction(user1Id);
        assertTrue("User should be equals", actualUser.equals(expectedUser));
    }

    @Test
    @Ignore
    //TODO Understand why proxy can't initialize
    public void shouldSuccessfulLoadWithTransaction(){
        final User expectedUser = createUser(USER_NAME_1, PASSWORD_1, EMAIL_1);
        int user1Id = userService.saveWithTransaction(expectedUser);

        final User actualUser = userService.loadWithTransaction(user1Id);
        assertEquals("User should be equals", expectedUser, actualUser);
    }

    @Test
    public void shouldSuccessfulGetWithOutTransaction(){
        final User expectedUser = createUser(USER_NAME_1, PASSWORD_1, EMAIL_1);
        int user1Id = userService.saveWithTransaction(expectedUser);

        final User actualUser = userService.getWithTransaction(user1Id);
        assertTrue("User should be equals", actualUser.equals(expectedUser));
    }

    @Test
    public void shouldSuccessfulGetWithTransaction(){
        final User expectedUser = createUser(USER_NAME_1, PASSWORD_1, EMAIL_1);
        int user1Id = userService.saveWithTransaction(expectedUser);

        final User actualUser = userService.getWithOutTransaction(user1Id);
        assertEquals("User should be equals", expectedUser, actualUser);
    }


    private User createUser(final String userName, final String password, final String email){
        final User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }
}
