package streamblocks.eclipse.cal.ui;

import org.eclipse.swt.graphics.Image;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;

import streamblocks.eclipse.ui.internal.EclipseActivator;

public class Activator extends EclipseActivator {

	public static String DIAGRAM_TYPE = "xdfDiagram";

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return (Activator) getInstance();
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	/**
	 * Returns an image for the image file at the given plug-in relative path.
	 * 
	 * @param path
	 *            the path
	 * @return the image
	 */
	public static Image getImage(String path) {
		ImageRegistry ir = getDefault().getImageRegistry();
		Image image = ir.get(path);
		if (image == null) {
			ImageDescriptor id = imageDescriptorFromPlugin(PLUGIN_ID, path);
			image = id.createImage();
			ir.put(path, image);
		}

		return image;
	}
	
	
}
