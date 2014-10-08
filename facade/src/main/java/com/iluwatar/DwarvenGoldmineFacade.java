package com.iluwatar;

import java.util.ArrayList;
import java.util.List;

public class DwarvenGoldmineFacade {

	List<DwarvenMineWorker> workers;

	public DwarvenGoldmineFacade() {
		workers = new ArrayList<>();
		workers.add(new DwarvenGoldDigger());
		workers.add(new DwarvenCartOperator());
		workers.add(new DwarvenTunnelDigger());
	}

	public void startNewDay() {
		for (DwarvenMineWorker worker : workers) {
			worker.wakeUp();
			worker.goToMine();
		}
	}

	public void digOutGold() {
		for (DwarvenMineWorker worker : workers) {
			worker.work();
		}
	}

	public void endDay() {
		for (DwarvenMineWorker worker : workers) {
			worker.goHome();
			worker.goToSleep();
		}
	}

}
