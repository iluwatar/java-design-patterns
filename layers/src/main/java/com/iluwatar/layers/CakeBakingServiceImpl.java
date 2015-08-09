package com.iluwatar.layers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
		if (!matchingToppings.isEmpty()) {
//			CakeToppingDao toppingBean = context.getBean(CakeToppingDao.class);
//			toppingBean.delete(matchingToppings.iterator().next().id.get());
		} else {
			throw new CakeBakingException(String.format("Topping %s is not available", cakeInfo.cakeToppingInfo.name));
		}
		List<CakeLayerInfo> allLayers = getAllLayers();
		for (CakeLayerInfo info: cakeInfo.cakeLayerInfos) {
			Optional<CakeLayerInfo> found = allLayers.stream().filter((layer) -> layer.name.equals(info.name)).findFirst();
			if (found.isPresent()) {
//				CakeLayerDao layerBean = context.getBean(CakeLayerDao.class);
//				layerBean.delete(found.get().id.get());
			} else {
				throw new CakeBakingException(String.format("Layer %s is not available", info.name));
			}
		}
		CakeDao cakeBean = context.getBean(CakeDao.class);
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
