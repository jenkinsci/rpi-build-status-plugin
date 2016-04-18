/**
 * 
 */
package net.sleepymouse.jenkins.plugins.piborg.ledborg;

import static com.pi4j.io.gpio.PinState.*;
import static net.sleepymouse.jenkins.plugins.piborg.ledborg.Messages.LOG_MSG;

import java.io.PrintStream;
import java.util.logging.*;

import com.pi4j.io.gpio.*;

import static net.sleepymouse.jenkins.plugins.piborg.ledborg.Constants.Colour;

/**
 * 
 *
 */
public class RPiGPIOManager implements IGPIOManager
{
	private PrintStream				consoleLogger;
	private Logger					fileLogger;
	private GpioController			gpio;
	private GpioPinDigitalOutput	redLed;
	private GpioPinDigitalOutput	greenLed;
	private GpioPinDigitalOutput	blueLed;

	private final static String		RESET_MSG	= "Running LED colour test on reset";

	public RPiGPIOManager(GpioController gpio, PrintStream consoleLogger, Logger fileLogger)
	{
		this.gpio = gpio;
		this.consoleLogger = consoleLogger;
		this.fileLogger = fileLogger;
	}

	/**
	 * Default the RGB LED states
	 */
	public void reset()
	{
		redLed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "Red", LOW);
		greenLed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "Green", LOW);
		blueLed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "Blue", LOW);
		//
		// Cycle through colours for test
		fileLogger.log(Level.INFO, RESET_MSG);
		consoleLogger.println(LOG_MSG() + " " + RESET_MSG);
		for (Colour c : Colour.values())
		{
			setColour(c);
			try
			{
				Thread.sleep(750); // delay
			}
			catch (InterruptedException e)
			{
				// Don't care
			}
		}
	}

	/**
	 * Shutdown the GPIO connectivity
	 */
	public void shutdown()
	{
		gpio.shutdown();
	}

	/**
	 * Set the console logger
	 * 
	 * @param consoleLogger
	 *            Logger for console output
	 */
	public void setConsoleLogger(PrintStream consoleLogger)
	{
		this.consoleLogger = consoleLogger;
	}

	/**
	 * Set the display colour
	 * 
	 * @param colour
	 *            Colour to display
	 */
	public void setColour(Colour colour)
	{
		// BLUE, CYAN, GREEN, MAGENTA, RED, YELLOW, WHITE, OFF
		switch (colour)
		{
			case RED:
			{
				redLed.setState(HIGH);
				greenLed.setState(LOW);
				blueLed.setState(LOW);
				break;
			}
			case GREEN:
			{
				redLed.setState(LOW);
				greenLed.setState(HIGH);
				blueLed.setState(LOW);
				break;
			}
			case BLUE:
			{
				redLed.setState(LOW);
				greenLed.setState(LOW);
				blueLed.setState(HIGH);
				break;
			}
			case WHITE:
			{
				redLed.setState(HIGH);
				greenLed.setState(HIGH);
				blueLed.setState(HIGH);
				break;
			}
			case OFF:
			{
				redLed.setState(LOW);
				greenLed.setState(LOW);
				blueLed.setState(LOW);
				break;
			}
			case YELLOW:
			{
				redLed.setState(HIGH);
				greenLed.setState(HIGH);
				blueLed.setState(LOW);
				break;
			}
			case MAGENTA:
			{
				redLed.setState(HIGH);
				greenLed.setState(LOW);
				blueLed.setState(HIGH);
				break;
			}
			case CYAN:
			{
				redLed.setState(LOW);
				greenLed.setState(HIGH);
				blueLed.setState(HIGH);
				break;
			}
		}
	}

}
