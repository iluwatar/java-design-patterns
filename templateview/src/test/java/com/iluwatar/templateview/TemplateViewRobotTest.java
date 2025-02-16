package com.iluwatar.templateview;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TemplateViewRobotTest {

  private TemplateViewRobot robot;

  @BeforeEach
  void setUp() {
    robot = new TemplateViewRobot();
  }

  @Test
  void shouldRenderHomePageCorrectly() {

    TemplateView homePage = new HomePageView();

    robot.render(homePage)
         .verifyContent("Rendering header...")
         .verifyContent("Rendering HomePage dynamic content...")
         .verifyContent("Rendering footer...");
  }

  @Test
  void shouldRenderContactPageCorrectly() {

    TemplateView contactPage = new ContactPageView();

    robot.render(contactPage)
         .verifyContent("Rendering header...")
         .verifyContent("Rendering ContactPage dynamic content...")
         .verifyContent("Rendering footer...");
  }

  @Test
  void shouldResetCapturedOutput() {
    TemplateView homePage = new HomePageView();

    robot.render(homePage)
         .verifyContent("Rendering HomePage dynamic content...")
         .reset()
         .render(new ContactPageView())
         .verifyContent("Rendering ContactPage dynamic content...");
  }
}
