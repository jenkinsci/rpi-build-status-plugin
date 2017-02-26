/**
 * 
 */
package net.sleepymouse.jenkins.plugins.piborg.ledborg;

import static net.sleepymouse.jenkins.plugins.Messages.LOAD_DESCRIPTOR_MSG;

import java.util.EnumSet;
import java.util.logging.*;

import org.kohsuke.stapler.*;

import hudson.model.AbstractProject;
import hudson.tasks.*;
import hudson.util.ListBoxModel;
import hudson.util.ListBoxModel.Option;
import net.sf.json.JSONObject;
import net.sleepymouse.jenkins.plugins.piborg.ledborg.Constants.Colour;

/**
 * Descriptor for {@link LedBorgPublisher}. Used as a singleton. The class is marked as public so that it can be
 * accessed from views.
 *
 * <p>
 * See <tt>src/main/resources/hudson/plugins/piborg/ledborg/LedBorgPublisher/*.jelly</tt> for the actual HTML fragment
 * for the configuration screen.
 */
public class Descriptor extends BuildStepDescriptor<Publisher>
{

	private final static Logger logger = Logger.getLogger(Descriptor.class.getName());

	/**
	 * In order to load the persisted global configuration, you have to call load() in the constructor.
	 */
	public Descriptor()
	{
		// Get persisted data
		logger.log(Level.INFO, LOAD_DESCRIPTOR_MSG());
		load();
	}

	@Override
	public boolean isApplicable(Class<? extends AbstractProject> aClass)
	{
		// Indicates that this builder can be used with all kinds of project types
		return true;
	}

	/**
	 * This human readable name is used in the configuration screen.
	 */
	@Override
	public String getDisplayName()
	{
		return "LED Borg controller";
	}

	@Override
	public boolean configure(StaplerRequest req, JSONObject formData) throws FormException
	{
		boolean result = super.configure(req, formData);
		save();
		return result;
	}

	/**
	 * List box model for build success colour selection
	 * 
	 * @param value
	 *            Previously selected value (if any)
	 * @return Model
	 */
	public ListBoxModel doFillSuccessStateItems(@QueryParameter("successState") String value)
	{
		return doFillItems(Colour.BLUE, value);
	}

	/**
	 * List box model for build unstable colour selection
	 * 
	 * @param value
	 *            Previously selected value (if any)
	 * @return Model
	 */
	public ListBoxModel doFillUnstableStateItems(@QueryParameter("unstableState") String value)
	{
		return doFillItems(Colour.YELLOW, value);
	}

	/**
	 * List box model for build failure colour selection
	 * 
	 * @param value
	 *            Previously selected value (if any)
	 * @return Model
	 */
	public ListBoxModel doFillFailureStateItems(@QueryParameter("failureState") String value)
	{
		return doFillItems(Colour.RED, value);
	}

	/**
	 * List box model for build not built colour selection
	 * 
	 * @param value
	 *            Previously selected value (if any)
	 * @return Model
	 */
	public ListBoxModel doFillNotBuiltStateItems(@QueryParameter("notBuiltState") String value)
	{
		return doFillItems(Colour.OFF, value);
	}

	/**
	 * List box model for build aborted colour selection
	 * 
	 * @param value
	 *            Previously selected value (if any)
	 * @return Model
	 */
	public ListBoxModel doFillAbortedStateItems(@QueryParameter("abortedState") String value)
	{
		return doFillItems(Colour.MAGENTA, value);
	}

	/**
	 * Construct the list box model for the colour selection drop down
	 * 
	 * @param c
	 *            Default colour
	 * @return List box model
	 */
	private ListBoxModel doFillItems(Colour c, String value)
	{
		ListBoxModel items = new ListBoxModel();
		//
		for (Colour e : EnumSet.allOf(Colour.class))
		{
			items.add(new Option(e.name(), e.name()));
		}
		// Set default ?
		if ((null == value) || (0 == value.length()))
		{
			items.get(c.ordinal()).selected = true;
		}
		return items;
	}

}