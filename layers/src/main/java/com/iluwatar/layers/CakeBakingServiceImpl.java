package com.iluwatar.layers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CakeBakingServiceImpl implements CakeBakingService {

	private AbstractApplicationContext context;

	public CakeBakingServiceImpl() {
		this.context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@Override
	public void bakeNewCake(CakeInfo cakeInfo) throws CakeBakingException {
		List<CakeToppingInfo> allToppings = getAllToppings();
		List<CakeToppingInfo> matchingToppings = allToppings.stream()
				.filter((t) -> t.name.equals(cakeInfo.cakeToppingInfo.name)).collect(Collectors.toList());
		if (matchingToppings.isEmpty()) {
			throw new CakeBakingException(String.format("Topping %s is not available", cakeInfo.cakeToppingInfo.name));
		}
		List<CakeLayer> allLayers = getAllLayerEntities();
		List<CakeLayer> foundLayers = new ArrayList<>();
		for (CakeLayerInfo info: cakeInfo.cakeLayerInfos) {
			Optional<CakeLayer> found = allLayers.stream().filter((layer) -> layer.getName().equals(info.name)).findFirst();
			if (!found.isPresent()) {
				throw new CakeBakingException(String.format("Layer %s is not available", info.name));
			} else {
				foundLayers.add(found.get());
			}
		}
		CakeToppingDao toppingBean = context.getBean(CakeToppingDao.class);
		CakeTopping topping = toppingBean.findOne(matchingToppings.iterator().next().id.get());
		CakeDao cakeBean = context.getBean(CakeDao.class);
		Cake cake = new Cake();
		cake = cakeBean.save(cake);
		cake.setTopping(topping);
		topping.setCake(cake);
		cake.setLayers(foundLayers);
		for (CakeLayer layer: foundLayers) {
			layer.setCake(cake);
		}
		cakeBean.save(cake);
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

	private List<CakeTopping> getAllToppingEntities() {
		CakeToppingDao bean = context.getBean(CakeToppingDao.class);
		List<CakeTopping> result = new ArrayList<>();
		Iterator<CakeTopping> iterator = bean.findAll().iterator();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}
	
	@Override
	public List<CakeToppingInfo> getAllToppings() {
		CakeToppingDao bean = context.getBean(CakeToppingDao.class);
		List<CakeToppingInfo> result = new ArrayList<>();
		Iterator<CakeTopping> iterator = bean.findAll().iterator();
		while (iterator.hasNext()) {
			CakeTopping next = iterator.next();
			result.add(new CakeToppingInfo(next.getId(), next.getName(), next.getCalories()));
		}
		return result;
	}

	private List<CakeLayer> getAllLayerEntities() {
		CakeLayerDao bean = context.getBean(CakeLayerDao.class);
		List<CakeLayer> result = new ArrayList<>();
		Iterator<CakeLayer> iterator = bean.findAll().iterator();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}
	
	@Override
	public List<CakeLayerInfo> getAllLayers() {
		CakeLayerDao bean = context.getBean(CakeLayerDao.class);
		List<CakeLayerInfo> result = new ArrayList<>();
		Iterator<CakeLayer> iterator = bean.findAll().iterator();
		while (iterator.hasNext()) {
			CakeLayer next = iterator.next();
			result.add(new CakeLayerInfo(next.getId(), next.getName(), next.getCalories()));
		}
		return result;
	}
}
