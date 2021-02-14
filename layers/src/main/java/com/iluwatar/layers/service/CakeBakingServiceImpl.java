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

package com.iluwatar.layers.service;

import com.iluwatar.layers.dao.CakeDao;
import com.iluwatar.layers.dao.CakeLayerDao;
import com.iluwatar.layers.dao.CakeToppingDao;
import com.iluwatar.layers.dto.CakeInfo;
import com.iluwatar.layers.dto.CakeLayerInfo;
import com.iluwatar.layers.dto.CakeToppingInfo;
import com.iluwatar.layers.entity.Cake;
import com.iluwatar.layers.entity.CakeLayer;
import com.iluwatar.layers.entity.CakeTopping;
import com.iluwatar.layers.exception.CakeBakingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of CakeBakingService.
 */
@Service
@Transactional
public class CakeBakingServiceImpl implements CakeBakingService {

  private final AbstractApplicationContext context;

  public CakeBakingServiceImpl() {
    this.context = new ClassPathXmlApplicationContext("applicationContext.xml");
  }

  @Override
  public void bakeNewCake(CakeInfo cakeInfo) throws CakeBakingException {
    var allToppings = getAvailableToppingEntities();
    var matchingToppings =
        allToppings.stream().filter(t -> t.getName().equals(cakeInfo.cakeToppingInfo.name))
            .collect(Collectors.toList());
    if (matchingToppings.isEmpty()) {
      throw new CakeBakingException(String.format("Topping %s is not available",
          cakeInfo.cakeToppingInfo.name));
    }
    var allLayers = getAvailableLayerEntities();
    Set<CakeLayer> foundLayers = new HashSet<>();
    for (var info : cakeInfo.cakeLayerInfos) {
      var found = allLayers.stream().filter(layer -> layer.getName().equals(info.name)).findFirst();
      if (found.isEmpty()) {
        throw new CakeBakingException(String.format("Layer %s is not available", info.name));
      } else {
        foundLayers.add(found.get());
      }
    }
    var toppingBean = context.getBean(CakeToppingDao.class);
    var topping = toppingBean.findById(matchingToppings.iterator().next().getId());
    var cakeBean = context.getBean(CakeDao.class);
    if (topping.isPresent()) {
      var cake = new Cake();
      cake.setTopping(topping.get());
      cake.setLayers(foundLayers);
      cakeBean.save(cake);
      topping.get().setCake(cake);
      toppingBean.save(topping.get());
      var layerBean = context.getBean(CakeLayerDao.class);
      for (var layer : foundLayers) {
        layer.setCake(cake);
        layerBean.save(layer);
      }
    } else {
      throw new CakeBakingException(String.format("Topping %s is not available",
          cakeInfo.cakeToppingInfo.name));
    }
  }

  @Override
  public void saveNewTopping(CakeToppingInfo toppingInfo) {
    var bean = context.getBean(CakeToppingDao.class);
    bean.save(new CakeTopping(toppingInfo.name, toppingInfo.calories));
  }

  @Override
  public void saveNewLayer(CakeLayerInfo layerInfo) {
    var bean = context.getBean(CakeLayerDao.class);
    bean.save(new CakeLayer(layerInfo.name, layerInfo.calories));
  }

  private List<CakeTopping> getAvailableToppingEntities() {
    var bean = context.getBean(CakeToppingDao.class);
    List<CakeTopping> result = new ArrayList<>();
    for (CakeTopping topping : bean.findAll()) {
      if (topping.getCake() == null) {
        result.add(topping);
      }
    }
    return result;
  }

  @Override
  public List<CakeToppingInfo> getAvailableToppings() {
    var bean = context.getBean(CakeToppingDao.class);
    List<CakeToppingInfo> result = new ArrayList<>();
    for (CakeTopping next : bean.findAll()) {
      if (next.getCake() == null) {
        result.add(new CakeToppingInfo(next.getId(), next.getName(), next.getCalories()));
      }
    }
    return result;
  }

  private List<CakeLayer> getAvailableLayerEntities() {
    var bean = context.getBean(CakeLayerDao.class);
    List<CakeLayer> result = new ArrayList<>();
    for (CakeLayer next : bean.findAll()) {
      if (next.getCake() == null) {
        result.add(next);
      }
    }
    return result;
  }

  @Override
  public List<CakeLayerInfo> getAvailableLayers() {
    var bean = context.getBean(CakeLayerDao.class);
    List<CakeLayerInfo> result = new ArrayList<>();
    for (CakeLayer next : bean.findAll()) {
      if (next.getCake() == null) {
        result.add(new CakeLayerInfo(next.getId(), next.getName(), next.getCalories()));
      }
    }
    return result;
  }

  @Override
  public List<CakeInfo> getAllCakes() {
    var cakeBean = context.getBean(CakeDao.class);
    List<CakeInfo> result = new ArrayList<>();
    for (Cake cake : cakeBean.findAll()) {
      var cakeToppingInfo =
          new CakeToppingInfo(cake.getTopping().getId(), cake.getTopping().getName(), cake
              .getTopping().getCalories());
      List<CakeLayerInfo> cakeLayerInfos = new ArrayList<>();
      for (var layer : cake.getLayers()) {
        cakeLayerInfos.add(new CakeLayerInfo(layer.getId(), layer.getName(), layer.getCalories()));
      }
      var cakeInfo = new CakeInfo(cake.getId(), cakeToppingInfo, cakeLayerInfos);
      result.add(cakeInfo);
    }
    return result;
  }
}
