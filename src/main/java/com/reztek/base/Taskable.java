package com.reztek.base;

public abstract class Taskable {
	protected int __delay = 0;
	private int ___delayCount = 0;
	
	public abstract void runTask();
	
	public void __taskTick() {
		if (++___delayCount >= __delay) {
			runTask();
			___delayCount = 0;
		}
	}
	
	public void setTaskDelay(int minute) {
		if (minute < 0) return;
		__delay = minute;
	}
	
	public int getTaskDelay() {
		return __delay;
	}
}
