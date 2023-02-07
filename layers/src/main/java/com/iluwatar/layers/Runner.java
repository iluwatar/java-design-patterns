package com.iluwatar.layers;

import dto.CakeInfo;
import dto.CakeLayerInfo;
import dto.CakeToppingInfo;
import exception.CakeBakingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CakeBakingService;
import view.CakeViewImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {
    private final CakeBakingService cakeBakingService;
    public static final String STRAWBERRY = "strawberry";

    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    @Autowired
    public Runner(CakeBakingService cakeBakingService) {
        this.cakeBakingService = cakeBakingService;
    }
    @Override
    public void run(String... args) {
        //initialize sample data
        initializeData();
        // create view and render it
        var cakeView = new CakeViewImpl(cakeBakingService);
        cakeView.render();
    }

    /**
     * Initializes the example data.
     */
    private void initializeData() {
        cakeBakingService.saveNewLayer(new CakeLayerInfo("chocolate", 1200));
        cakeBakingService.saveNewLayer(new CakeLayerInfo("banana", 900));
        cakeBakingService.saveNewLayer(new CakeLayerInfo(STRAWBERRY, 950));
        cakeBakingService.saveNewLayer(new CakeLayerInfo("lemon", 950));
        cakeBakingService.saveNewLayer(new CakeLayerInfo("vanilla", 950));
        cakeBakingService.saveNewLayer(new CakeLayerInfo(STRAWBERRY, 950));

        cakeBakingService.saveNewTopping(new CakeToppingInfo("candies", 350));
        cakeBakingService.saveNewTopping(new CakeToppingInfo("cherry", 350));

        var cake1 = new CakeInfo(new CakeToppingInfo("candies", 0), List.of(
                new CakeLayerInfo("chocolate", 0),
                new CakeLayerInfo("banana", 0),
                new CakeLayerInfo(STRAWBERRY, 0)));
        try {
            cakeBakingService.bakeNewCake(cake1);
        } catch (CakeBakingException e) {
            LOGGER.debug("Cake baking exception", e);
        }
        var cake2 = new CakeInfo(new CakeToppingInfo("cherry", 0), List.of(
                new CakeLayerInfo("vanilla", 0),
                new CakeLayerInfo("lemon", 0),
                new CakeLayerInfo(STRAWBERRY, 0)));
        try {
            cakeBakingService.bakeNewCake(cake2);
        } catch (CakeBakingException e) {
            LOGGER.debug("Cake baking exception", e);
        }
    }
}
