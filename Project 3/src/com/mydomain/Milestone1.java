/*
 * Objective of the milestone1 class is to move the robot to approach the light.
 * Once the robot successfully detects a light
 */
package com.mydomain;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;

public class Milestone1 
{
	public static void main(String[] args) 
	{
		// Creating instance variables for the robot, scanner, and the racer
		
		DifferentialPilot myPilot = new DifferentialPilot((float)(56/25.4),5.5f,Motor.A,Motor.C,false);
		LightSensor scanner = new LightSensor(SensorPort.S2);
		ScanRecorder mySR = new ScanRecorder(Motor.B,scanner);
		Racer myRacer = new Racer(myPilot, mySR);

		// i is a way of counting the number of trips that the robot will have to make throughout the milestone
		int i = 0;
		myPilot.setAcceleration(25); //set acceleration to 25 in/sec/sec
		myPilot.setRotateSpeed(720); //set rotation to 720 degrees/sec
		while (i<8)
		{
			/* 13 is a speed that is quick enough, but not too fast so that the scanner can function
			 * approximately while the robot is moving
			 */
			myRacer.Speed(13);
			myRacer.gotoLight(49); // 49 is a relatively intense light value
			myPilot.rotate(180); // turn around to approach the light on the other side
			i++; //increase i by one
		}
	}

}

