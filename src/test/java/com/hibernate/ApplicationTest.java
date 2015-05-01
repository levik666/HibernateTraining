package com.hibernate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

    private Application application = new Application();

    @Test
    public void shouldSuccessfulStartApplication(){
        final String[] args = new String[]{};
        application.main(args);
    }


}
