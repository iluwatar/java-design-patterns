package com.iluwater.pac;

/**
 * a class used to present the students' course information.
 */
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudentCoursePresentation {
    /**
     * present to the user.
     */
    public static void present(StudentCourseAbstraction abstraction) {
        Object[] objects = abstraction.getNames();
        //CS304 Issue link: https://github.com/iluwatar/java-design-patterns/issues/304
        for (Object object : objects) {
            LOGGER.info(object + " " + abstraction.getGrade((String) object));
        }
    }

}
