/**
 * 
 */
package net.sleepymouse.jenkins.plugins.piborg.ledborg;

import static net.sleepymouse.jenkins.plugins.Messages.*;

import java.io.PrintStream;
import java.util.logging.*;

import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.GpioUtil;

import net.sleepymouse.jenkins.plugins.piborg.ledborg.Constants.Colour;

/**
 * 
 *
 */
public class Controller
{
	private static volatile Controller	controller	= null;
	private static GpioController		gpio		= null;
	private static IGPIOManager			gpioManager	= null;
	//
	private PrintStream					consoleLogger;
	private Logger						fileLogger;

	/**
	 * Private constructor as we only want one GPIO controller.
	 * 
	 * @param consoleLogger
	 *            Logger for console output
	 * @param fileLogger
	 *            Logger for log file output (Diagnostics)
	 */
	private Controller(PrintStream consoleLogger, Logger fileLogger)
	{
		this.consoleLogger = consoleLogger;
		this.fileLogger = fileLogger;
		try
		{
			if (GpioUtil.isPrivilegedAccessRequired())
			{
				// Need non root access to GPIO
				this.fileLogger.log(Level.WARNING, PRIVILEGE_MSG());
				this.consoleLogger.println(LOG_MSG() + " " + PRIVILEGE_MSG());
			}
			else
			{
				// Get the GPIO interface
				this.fileLogger.log(Level.INFO, START_MSG());
				this.consoleLogger.println(LOG_MSG() + " " + START_MSG());
				GpioUtil.enableNonPrivilegedAccess();
				gpio = GpioFactory.getInstance();
				this.fileLogger.log(Level.INFO, RUNNING_MSG());
				this.consoleLogger.println(LOG_MSG() + " " + RUNNING_MSG());
			}
		}
		catch (Throwable t)
		{
			// Can be all sorts of reasons to end up here - Not a Pi, no library loaded etc
			this.fileLogger.log(Level.WARNING, FALLBACK_MSG() + ": " + t.getMessage());
			this.consoleLogger.println(LOG_MSG() + " " + FALLBACK_MSG());
		}
	}

	/**
	 * Lazy load the controller singleton
	 * 
	 * @param consoleLogger
	 *            Logger for console output
	 * @param fileLogger
	 *            Logger for log file output (Diagnostics)
	 * @return An instance of the controller
	 */
	public static Controller getInstance(PrintStream consoleLogger, Logger fileLogger)
	{
		if (null == controller)
		{
			synchronized (Controller.class)
			{
				if (null == controller)
				{
					controller = new Controller(consoleLogger, fileLogger);
					if (null != gpio)
					{
						gpioManager = new RPiGPIOManager(gpio, consoleLogger, fileLogger);
					}
					else
					{
						gpioManager = new FallbackGPIOManager(consoleLogger, fileLogger);
					}
					gpioManager.reset();
				}
			}
		}
		//
		// Keep updating this as the logger changes per build
		controller.setConsoleLogger(consoleLogger);
		return controller;
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
		gpioManager.setConsoleLogger(consoleLogger);
	}

	/**
	 * Default the RGB LED states
	 */
	public void reset()
	{
		gpioManager.reset();
	}

	/**
	 * Shutdown the GPIO connectivity
	 */
	public void shutdown()
	{
		gpioManager.shutdown();
	}

	/**
	 * Set the display colour
	 * 
	 * @param colour
	 *            Colour to display
	 */
	public void setColour(String colour)
	{
		fileLogger.log(Level.INFO, COLOUR_MSG() + colour);
		consoleLogger.println(LOG_MSG() + " " + COLOUR_MSG() + colour);
		try
		{
			// convert to enum
			gpioManager.setColour(Colour.valueOf(colour));
		}
		catch (Exception e)
		{
			// Colour has no enum match. Should not happen
			fileLogger.log(Level.SEVERE, BAD_COLOUR_MSG() + colour);
		}
	}

}
