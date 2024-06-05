/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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

package service;

import dao.CakeDao;
import dao.CakeLayerDao;
import dao.CakeToppingDao;
import dto.CakeInfo;
import dto.CakeLayerInfo;
import dto.CakeToppingInfo;
import entity.Cake;
import entity.CakeLayer;
import entity.CakeTopping;
import exception.CakeBakingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of CakeBakingService.
 */
@Service
@Transactional
public class CakeBakingServiceImpl implements CakeBakingService {

  private final CakeDao cakeDao;
  private final CakeLayerDao cakeLayerDao;
  private final CakeToppingDao cakeToppingDao;

  /**
   * Constructs a new instance of CakeBakingServiceImpl.
   *
   * @param cakeDao the DAO for cake-related operations
   * @param cakeLayerDao the DAO for cake layer-related operations
   * @param cakeToppingDao the DAO for cake topping-related operations
   */
  @Autowired
  public CakeBakingServiceImpl(CakeDao cakeDao, CakeLayerDao cakeLayerDao,
                               CakeToppingDao cakeToppingDao) {
    this.cakeDao = cakeDao;
    this.cakeLayerDao = cakeLayerDao;
    this.cakeToppingDao = cakeToppingDao;
  }

  @Override
  public void bakeNewCake(CakeInfo cakeInfo) throws CakeBakingException {
    var allToppings = getAvailableToppingEntities();
    var matchingToppings =
        allToppings.stream().filter(t -> t.getName().equals(cakeInfo.cakeToppingInfo.name))
            .toList();
    if (matchingToppings.isEmpty()) {
      throw new CakeBakingException(
          String.format("Topping %s is not available", cakeInfo.cakeToppingInfo.name));
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

    var topping = cakeToppingDao.findById(matchingToppings.iterator().next().getId());
    if (topping.isPresent()) {
      var cake = new Cake();
      cake.setTopping(topping.get());
      cake.setLayers(foundLayers);
      cakeDao.save(cake);
      topping.get().setCake(cake);
      cakeToppingDao.save(topping.get());
      Set<CakeLayer> foundLayersToUpdate =
          new HashSet<>(foundLayers); // copy set to avoid a ConcurrentModificationException

      for (var layer : foundLayersToUpdate) {
        layer.setCake(cake);
        cakeLayerDao.save(layer);
      }

    } else {
      throw new CakeBakingException(
          String.format("Topping %s is not available", cakeInfo.cakeToppingInfo.name));
    }
  }

  @Override
  public void saveNewTopping(CakeToppingInfo toppingInfo) {
    cakeToppingDao.save(new CakeTopping(toppingInfo.name, toppingInfo.calories));
  }

  @Override
  public void saveNewLayer(CakeLayerInfo layerInfo) {
    cakeLayerDao.save(new CakeLayer(layerInfo.name, layerInfo.calories));
  }

  private List<CakeTopping> getAvailableToppingEntities() {
    List<CakeTopping> result = new ArrayList<>();
    for (CakeTopping topping : cakeToppingDao.findAll()) {
      if (topping.getCake() == null) {
        result.add(topping);
      }
    }
    return result;
  }

  @Override
  public List<CakeToppingInfo> getAvailableToppings() {
    List<CakeToppingInfo> result = new ArrayList<>();
    for (CakeTopping next : cakeToppingDao.findAll()) {
      if (next.getCake() == null) {
        result.add(new CakeToppingInfo(next.getId(), next.getName(), next.getCalories()));
      }
    }
    return result;
  }

  private List<CakeLayer> getAvailableLayerEntities() {
    List<CakeLayer> result = new ArrayList<>();
    for (CakeLayer next : cakeLayerDao.findAll()) {
      if (next.getCake() == null) {
        result.add(next);
      }
    }
    return result;
  }

  @Override
  public List<CakeLayerInfo> getAvailableLayers() {
    List<CakeLayerInfo> result = new ArrayList<>();
    for (CakeLayer next : cakeLayerDao.findAll()) {
      if (next.getCake() == null) {
        result.add(new CakeLayerInfo(next.getId(), next.getName(), next.getCalories()));
      }
    }
    return result;
  }

  @Override
  public void deleteAllCakes() {
    cakeDao.deleteAll();
  }

  @Override
  public void deleteAllLayers() {
    cakeLayerDao.deleteAll();
  }

  @Override
  public void deleteAllToppings() {
    cakeToppingDao.deleteAll();
  }

  @Override
  public List<CakeInfo> getAllCakes() {
    List<CakeInfo> result = new ArrayList<>();
    for (Cake cake : cakeDao.findAll()) {
      var cakeToppingInfo =
          new CakeToppingInfo(cake.getTopping().getId(), cake.getTopping().getName(),
              cake.getTopping().getCalories());
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
