/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package domainapp.webapp;

import com.google.common.base.Joiner;
import com.google.common.io.Resources;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;
import com.google.inject.util.Providers;
import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchTheme;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchThemeProvider;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.isis.viewer.wicket.viewer.IsisWicketApplication;
import org.apache.isis.viewer.wicket.viewer.integration.wicket.AuthenticatedWebSessionForIsis;
import org.apache.wicket.Session;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.http.WebRequest;


/**
 * As specified in <tt>web.xml</tt>.
 *
 * <p>See:
 *
 * <pre>
 * &lt;filter&gt;
 *   &lt;filter-name&gt;wicket&lt;/filter-name&gt;
 *    &lt;filter-class&gt;org.apache.wicket.protocol.http.WicketFilter&lt;/filter-class&gt;
 *    &lt;init-param&gt;
 *      &lt;param-name&gt;applicationClassName&lt;/param-name&gt;
 *      &lt;param-value&gt;webapp.SimpleApplication&lt;/param-value&gt;
 *    &lt;/init-param&gt;
 * &lt;/filter&gt;
 * </pre>
 */
@Slf4j
public class SimpleApplication extends IsisWicketApplication {
  private static final long serialVersionUID = 1L;

  /**
   * uncomment for a (slightly hacky) way of allowing logins using query args, eg:
   *
   * <tt>{@code ?user=sven&pass=pass}</tt>
   *
   * <p>for demos only, obvious.
   */
  private static final boolean DEMO_MODE_USING_CREDENTIALS_AS_QUERYARGS = false;


  @Override
  protected void init() {
    super.init();

    var settings = Bootstrap.getSettings();
    settings.setThemeProvider(new BootswatchThemeProvider(BootswatchTheme.Flatly));
  }

  @Override
  public Session newSession(final Request request, final Response response) {
    if (!DEMO_MODE_USING_CREDENTIALS_AS_QUERYARGS) {
      return super.newSession(request, response);
    }

    // else demo mode
    final var s = (AuthenticatedWebSessionForIsis) super.newSession(request, response);
    var requestParameters = request.getRequestParameters();
    final var user = requestParameters.getParameterValue("user");
    final var password = requestParameters.getParameterValue("pass");
    s.signIn(user.toString(), password.toString());
    return s;
  }

  @Override
  public WebRequest newWebRequest(HttpServletRequest servletRequest, String filterPath) {
    if (!DEMO_MODE_USING_CREDENTIALS_AS_QUERYARGS) {
      return super.newWebRequest(servletRequest, filterPath);
    }

    // else demo mode
    try {
      var uname = servletRequest.getParameter("user");
      if (uname != null) {
        servletRequest.getSession().invalidate();
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
    return super.newWebRequest(servletRequest, filterPath);
  }

  @Override
  protected Module newIsisWicketModule() {
    final var isisDefaults = super.newIsisWicketModule();

    final Module overrides = new AbstractModule() {
      @Override
      protected void configure() {
        bind(String.class).annotatedWith(Names.named("applicationName")).toInstance("Simple App");
        bind(String.class).annotatedWith(Names.named("applicationCss")).toInstance(
            "css/application.css");
        bind(String.class).annotatedWith(Names.named("applicationJs")).toInstance(
            "scripts/application.js");
        bind(String.class).annotatedWith(Names.named("welcomeMessage")).toInstance(
            readLines(getClass(), "welcome.html"));
        bind(String.class).annotatedWith(Names.named("aboutMessage")).toInstance("Simple App");
        bind(InputStream.class).annotatedWith(Names.named("metaInfManifest")).toProvider(
            Providers.of(getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF")));
      }
    };

    return Modules.override(isisDefaults).with(overrides);
  }

  @SuppressWarnings({"UnstableApiUsage", "SameParameterValue"})
  private static String readLines(final Class<?> contextClass, final String resourceName) {
    try {
      var resource = Resources.getResource(contextClass, resourceName);
      var readLines = Resources.readLines(resource, Charset.defaultCharset());
      return Joiner.on("\n").join(readLines);
    } catch (IOException e) {
      return "This is a simple app";
    }
  }

}
