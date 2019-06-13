package com.dor.coupons.job;

import java.util.Timer;
import java.util.TimerTask;

import com.dor.coupons.exception.ApplicationException;

public class MyTimer {

	public MyTimer() throws ApplicationException {
		createTimer();
	}

	public static void createTimer() throws ApplicationException {

		TimerTask task = new MyTimerTask();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 1000);
		System.out.println("The timer task started");
	}
}