package com.iluwater.federatedIdentity;

import io.javalin.Javalin;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import java.util.HashMap;

/**
 * provide authentication.
 *
 *<p>@author jasciiz
 */
public class IdP {
  String name;
  HashMap<String, String> hashMap;
  HashMap<String, Consumer> consumerList;
  Javalin app;
  int id;

  /**
   * construct with IdP name.
   *
   * <P>@param name the name of IdP.
   */
  public IdP(String name) {
    this.name = name;
    this.hashMap = new HashMap<String, String>();
    this.app = Javalin.create(config -> {
      config.registerPlugin(getConfiguredOpenApiPlugin());
    }).start(7001);
    this.id = 0;
    this.hashMap = new HashMap<>();
    this.consumerList = new HashMap<>();
  }

  /**
   * construct without parameter.
   *
   */
  public IdP() {
    this.name = "IdP1";
    this.hashMap = new HashMap<String, String>();
    this.app = Javalin.create(config -> {
      config.registerPlugin(getConfiguredOpenApiPlugin());
    }).start(7001);
    this.id = 0;
    this.hashMap = new HashMap<>();
    this.consumerList = new HashMap<>();
  }

  /**
   * listen all http request.
   *
   * <p>accept register, getClaim, getInfo requests.
   */
  public void listener() {
    this.app.post("/IdP/register", ctx -> {
      ctx.res.setCharacterEncoding("UTF-8");
      String user = ctx.body();
      String[] information = user.split("//");
      if (hashMap.containsKey(information[0])) {
        ctx.result("Please input another name.");
        return;
      }
      Consumer consumer = new Consumer(information[0], Integer.parseInt(information[1]),
          information[2], information[3]);
      consumerList.put(information[0], consumer);
      hashMap.put(information[0], String.valueOf(this.id));
      ctx.result("success");
    });

    this.app.post("/IdP/getClaim", ctx -> {
      ctx.res.setCharacterEncoding("UTF-8");
      String name = ctx.body();
      if (!hashMap.containsKey(name)) {
        ctx.result("fail");
        return;
      }
      String id = hashMap.get(name);
      ctx.result(this.name + "//" + id);
    });

    this.app.get("/IdP/getInfo", ctx -> ctx.result(this.name));

  }

  /**
   * make configure of Javalin link.
   *
   * <p>@return OpenApiPlugin
   */
  private OpenApiPlugin getConfiguredOpenApiPlugin() {
    Info info = new Info().version("1.0").description("RESTful Corpus Platform API");
    OpenApiOptions options = new OpenApiOptions(info)
        .activateAnnotationScanningFor("com.iluwater.dederatedIdentity.IdP")
        .path("/swagger-docs")
        .swagger(new SwaggerOptions("/swagger-ui"));
    return new OpenApiPlugin(options);
  }
}
