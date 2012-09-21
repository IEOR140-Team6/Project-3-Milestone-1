package com.mydomain;

import lejos.nxt.*;
import lejos.util.Datalogger;

/**
 * records the results of a 180 degree scan (angle and light intensity
 * to a dataLogger and plays it back over a USB port
 * @author owner.GLASSEY
 */

public class ScanRecorder
{

   public ScanRecorder(NXTRegulatedMotor theMotor, LightSensor eye)
   {
      motor = theMotor;    
      _eye = eye;
      _eye.setFloodlight(false);
   }

	/**
	    * rotate the scanner head to the angle
	    * @param angle
	    * @param instantReturn if true, the method is non-blocking
	    */
	   public void rotateTo(int angle, boolean instantReturn)
	   {
	      motor.rotateTo(angle, instantReturn);
	   }

   /**
    * returns the angle at which the maximum light intensity was found
    * @return 
    */
   public int getTargetBearing()
   {
      return _targetBearing;
   }
/**
    * returns the maximum light intensity found during the scan
    * @return  light intensity
    */
   public int getLight()
   {
      return _maxLight;
   }
/**
    * returns the angle in which the light sensor is pointing
    * @return the angle
    */
   public int getHeadAngle()
   {
      return motor.getTachoCount();
   }
 /**
    * sets the motor sped in deg/sec
    * @param speed 
    */
   public void setSpeed(int speed)
   {
      motor.setSpeed(speed);
   }

/**
    * scan from current head angle to limit angle and determine/store the best angle,
    * the angle with the maximum light intensity.
    * @param limitAngle 
    */
   public void scanTo(int limitAngle)
   {
	   _maxLight = 0; //set max light intensity to 0
      int oldAngle = motor.getTachoCount(); //set variable oldAngle to get head angle
      motor.rotateTo(limitAngle, true); //rotate from head angle to limit angle
      int Light = _eye.getLightValue(); //set variable Light to retrieve light value
      while (motor.isMoving()) //while the motor is moving
      {
         short angle = (short) motor.getTachoCount(); //set variable angle to get head angle
         int newLight = _eye.getLightValue(); //set variable newLight to get light value
         /*
          * If angle is not equal to oldAngle, then check to see if newLight (the light value
          * at angle) is greater than light, the light value at oldAngle. If so, then set 
          * Light to equal newLight. Then check to see if newLight is greater than _maxLight.
          * If so, then set _maxLight to equal newLight, and set the max angle to be at angle.
          */
         if (angle != oldAngle)
         {
        	if (newLight>Light)
        	{
           		Light = newLight;
           		if (newLight>_maxLight)
           		{
           			_maxLight=newLight;
           			_targetBearing = angle;
           		}
        	}
        	oldAngle = angle; //replace oldAngle with angle, and start the loop over.
         }
      }
   }

   /******* instance variabled ***************/
   NXTRegulatedMotor motor;
   LightSensor _eye;
   int _targetBearing;
   int _maxLight;
   boolean _found = false;
   Datalogger dl = new Datalogger();
}
