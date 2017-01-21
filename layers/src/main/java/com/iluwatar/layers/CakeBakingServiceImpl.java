/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
package com.iluwatar.layers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Implementation of CakeBakingService
 *
 */
@Service
@Transactional
public class CakeBakingServiceImpl implements CakeBakingService {

  private AbstractApplicationContext context;

  public CakeBakingServiceImpl() {
    this.context = new ClassPathXmlApplicationContext("applicationContext.xml");
  }

  @Override
  public void bakeNewCake(CakeInfo cakeInfo) throws CakeBakingException {
    List<CakeTopping> allToppings = getAvailableToppingEntities();
    List<CakeTopping> matchingToppings =
        allToppings.stream().filter(t -> t.getName().equals(cakeInfo.cakeToppingInfo.name))
            .collect(Collectors.toList());
    if (matchingToppings.isEmpty()) {
      throw new CakeBakingException(String.format("Topping %s is not available",
          cakeInfo.cakeToppingInfo.name));
    }
    List<CakeLayer> allLayers = getAvailableLayerEntities();
    Set<CakeLayer> foundLayers = new HashSet<>();
    for (CakeLayerInfo info : cakeInfo.cakeLayerInfos) {
      Optional<CakeLayer> found =
          allLayers.stream().filter(layer -> layer.getName().equals(info.name)).findFirst();
      if (!found.isPresent()) {
        throw new CakeBakingException(String.format("Layer %s is not available", info.name));
      } else {
        foundLayers.add(found.get());
      }
    }
    CakeToppingDao toppingBean = context.getBean(CakeToppingDao.class);
    CakeTopping topping = toppingBean.findOne(matchingToppings.iterator().next().getId());
    CakeDao cakeBean = context.getBean(CakeDao.class);
    Cake cake = new Cake();
    cake.setTopping(topping);
    cake.setLayers(foundLayers);
    cakeBean.save(cake);
    topping.setCake(cake);
    toppingBean.save(topping);
    CakeLayerDao layerBean = context.getBean(CakeLayerDao.class);
    for (CakeLayer layer : foundLayers) {
      layer.setCake(cake);
      layerBean.save(layer);
    }
  }

  @Override
  public void saveNewTopping(CakeToppingInfo toppingInfo) {
    CakeToppingDao bean = context.getBean(CakeToppingDao.class);
    bean.save(new CakeTopping(toppingInfo.name, toppingInfo.calories));
  }

  @Override
  public void saveNewLayer(CakeLayerInfo layerInfo) {
    CakeLayerDao bean = context.getBean(CakeLayerDao.class);
    bean.save(new CakeLayer(layerInfo.name, layerInfo.calories));
  }

  private List<CakeTopping> getAvailableToppingEntities() {
    CakeToppingDao bean = context.getBean(CakeToppingDao.class);
    List<CakeTopping> result = new ArrayList<>();
    Iterator<CakeTopping> iterator = bean.findAll().iterator();
    while (iterator.hasNext()) {
      CakeTopping topping = iterator.next();
      if (topping.getCake() == null) {
        result.add(topping);
      }
    }
    return result;
  }

  @Override
  public List<CakeToppingInfo> getAvailableToppings() {
    CakeToppingDao bean = context.getBean(CakeToppingDao.class);
    List<CakeToppingInfo> result = new ArrayList<>();
    Iterator<CakeTopping> iterator = bean.findAll().iterator();
    while (iterator.hasNext()) {
      CakeTopping next = iterator.next();
      if (next.getCake() == null) {
        result.add(new CakeToppingInfo(next.getId(), next.getName(), next.getCalories()));
      }
    }
    return result;
  }

  private List<CakeLayer> getAvailableLayerEntities() {
    CakeLayerDao bean = context.getBean(CakeLayerDao.class);
    List<CakeLayer> result = new ArrayList<>();
    Iterator<CakeLayer> iterator = bean.findAll().iterator();
    while (iterator.hasNext()) {
      CakeLayer next = iterator.next();
      if (next.getCake() == null) {
        result.add(next);
      }
    }
    return result;
  }

  @Override
  public List<CakeLayerInfo> getAvailableLayers() {
    CakeLayerDao bean = context.getBean(CakeLayerDao.class);
    List<CakeLayerInfo> result = new ArrayList<>();
    Iterator<CakeLayer> iterator = bean.findAll().iterator();
    while (iterator.hasNext()) {
      CakeLayer next = iterator.next();
      if (next.getCake() == null) {
        result.add(new CakeLayerInfo(next.getId(), next.getName(), next.getCalories()));
      }
    }
    return result;
  }

  @Override
  public List<CakeInfo> getAllCakes() {
    CakeDao cakeBean = context.getBean(CakeDao.class);
    List<CakeInfo> result = new ArrayList<>();
    Iterator<Cake> iterator = cakeBean.findAll().iterator();
    while (iterator.hasNext()) {
      Cake cake = iterator.next();
      CakeToppingInfo cakeToppingInfo =
          new CakeToppingInfo(cake.getTopping().getId(), cake.getTopping().getName(), cake
              .getTopping().getCalories());
      List<CakeLayerInfo> cakeLayerInfos = new ArrayList<>();
      for (CakeLayer layer : cake.getLayers()) {
        cakeLayerInfos.add(new CakeLayerInfo(layer.getId(), layer.getName(), layer.getCalories()));
      }
      CakeInfo cakeInfo = new CakeInfo(cake.getId(), cakeToppingInfo, cakeLayerInfos);
      result.add(cakeInfo);
    }
    return result;
  }
}
