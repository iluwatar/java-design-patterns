package com.iluwater.pac;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudentAverageGradePresentation {

    /**
     * present average grade to user.
     *
     * @param avg is the average grade.
     */
    public void present(double avg) {
        //CS304 Issue link: https://github.com/iluwatar/java-design-patterns/issues/304
        LOGGER.info("The average grade is "+avg+"\n");
    }

}
