package streamblocks.eclipse.cal.ui.xdf.dialogs;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

import streamblocks.eclipse.cal.cal.AstActor;
import streamblocks.eclipse.cal.cal.AstNetwork;
import streamblocks.eclipse.cal.cal.CalPackage;
import streamblocks.eclipse.cal.ui.Activator;
import streamblocks.eclipse.cal.ui.util.StreamBlocksUtil;

/**
 * Define a dialog used to display a list of all Actors/Network for the current
 * project. The user can filter to find the one he wants. When the dialog is
 * validated, 
 * 
 * @author Antoine Lorence
 * 
 */
public class EntitySelectionDialog extends FilteredItemsSelectionDialog {

	/**
	 * This class is provide filtering on elements from pattern typed by user in
	 * the search field.
	 * 
	 * @author Antoine Lorence
	 * 
	 */
	private class EntityFilter extends ItemsFilter {

		public EntityFilter() {
			super();
			// By default, we want to display the full list. We also want to
			// search for all content ending with chars typed by user. That's
			// why we prepend the pattern (characters typed by user) with a
			// wildcard.
			patternMatcher.setPattern("*" + patternMatcher.getPattern());
		}

		@Override
		public boolean isConsistentItem(Object item) {
			return true;
		}

		@Override
		public boolean matchItem(Object item) {
			return matches(getElementName(item));
		}
	};

	/**
	 * This provider is responsible of displaying the name of each item in the
	 * list. In the EntitySelectionDialog, each element (Actor or Network) is
	 * displayed by its qualified name. this is done using Network.getName() or
	 * Actor.getName() method.
	 * 
	 * This class also provide an icon according to the type of element.
	 * 
	 * @author Antoine Lorence
	 * 
	 */
	private class EntityListLabelProvider implements ILabelProvider {

		final List<ILabelProviderListener> listeners = new ArrayList<ILabelProviderListener>();

		@Override
		public void removeListener(ILabelProviderListener listener) {
			listeners.remove(listener);
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void dispose() {
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			listeners.add(listener);
		}

		@Override
		public String getText(Object element) {
			String result = "null";
			if (element instanceof EObject) {
				final EObject obj = (EObject) element;
				
				if (obj.eClass().equals(CalPackage.eINSTANCE.getAstActor())) {
					result = ((AstActor) obj).getName();
				} else if (obj.eClass().equals(CalPackage.eINSTANCE.getAstNetwork())) {
					result = ((AstNetwork) obj).getName();
				}
			} else {
				result = "BAD TYPE: " + element;
			}
			return result;
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof EObject) {
				EObject obj = (EObject) element;
				if (obj.eClass().equals(CalPackage.eINSTANCE.getAstActor())) {
					return Activator.getImage("icons/orcc.png");
				} else if (obj.eClass().equals(CalPackage.eINSTANCE.getAstNetwork())) {
					return Activator.getImage("icons/network.gif");
				}
			}
			return null;
		}
	};

	private static final String SETTINGS = EntitySelectionDialog.class.getCanonicalName();

	private final List<EObject> objects;
	private final static ResourceSet emfResourceSet = new ResourceSetImpl();

	/**
	 * @param shell
	 * @param multi
	 * @throws CoreException
	 */
	public EntitySelectionDialog(Shell shell, IProject project) throws CoreException {
		super(shell);

		setListLabelProvider(new EntityListLabelProvider());

		objects = new ArrayList<EObject>();

		// Compute the list of all source and ouput folders
		final List<IFolder> searchFolders = StreamBlocksUtil.getAllSourceFolders(project);
		searchFolders.addAll(StreamBlocksUtil.getOutputFolders(project));

		for (final IFolder searchFolder : searchFolders) {
			fillEntitiesList(searchFolder);
		}
	}

	@Override
	protected Control createExtendedContentArea(Composite parent) {
		return null;
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings()
				.getSection(SETTINGS);

		if (settings == null) {
			settings = Activator.getDefault().getDialogSettings().addNewSection(SETTINGS);
		}
		return settings;
	}

	@Override
	protected IStatus validateItem(Object item) {
		return Status.OK_STATUS;
	}

	@Override
	protected ItemsFilter createFilter() {
		return new EntityFilter();
	}

	@Override
	protected Comparator<?> getItemsComparator() {
		// Items are returned ordered alphabetically
		return new Comparator<Object>() {

			@Override
			public int compare(Object o1, Object o2) {
				return getElementName(o1).compareTo(getElementName(o2));
			}
		};
	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider, ItemsFilter itemsFilter,
			IProgressMonitor monitor) throws CoreException {

		monitor.beginTask("Searching", objects.size());
		for (final EObject obj : objects) {
			contentProvider.add(obj, itemsFilter);
			monitor.worked(1);
		}
		monitor.done();
	}

	@Override
	public String getElementName(Object item) {
		String result = "null";
		if (item instanceof EObject) {
			final EObject obj = (EObject) item;
			if (obj.eClass().equals(CalPackage.eINSTANCE.getAstActor())) {
				result = ((AstActor) obj).getName();
			} else if (obj.eClass().equals(CalPackage.eINSTANCE.getAstNetwork())) {
				result = ((AstNetwork) obj).getName();
			}
		}
		return result;
	}

	/**
	 * Add all Actors and Networks contained in the given folder to the objects
	 * list, to allow them to be displayed later.
	 * 
	 * @param folder
	 * @throws CoreException
	 */
	private void fillEntitiesList(IFolder folder) throws CoreException {

		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		for (IResource resource : folder.members()) {
			if (resource.getType() == IResource.FOLDER) {
				fillEntitiesList((IFolder) resource);
			} else if (resource.getType() == IResource.FILE) {
				final String suffix = resource.getFileExtension();
				// Wee keep only cal Actors/Units and xdf networks
				if (!StreamBlocksUtil.IR_SUFFIX.equals(suffix)
						&& !StreamBlocksUtil.NETWORK_SUFFIX.equals(suffix)) {
					continue;
				}

				final URI uri = URI.createPlatformResourceURI(resource.getFullPath().toString(), true);

				if (!root.exists(resource.getFullPath())) {
					continue;
				}

				EObject eobject = null;
				try {
					// FIXME: if a Network has to be updated, it will not be
					// stored to the final list
					final Resource emfRes = emfResourceSet.getResource(uri, true);

					// Do not try to read for invalid or empty files
					if (emfRes == null || emfRes.getContents().size() == 0) {
						continue;
					}
					eobject = emfRes.getContents().get(0);
					if (eobject == null) {
						continue;
					}
				} catch (NullPointerException e) {
					continue;
				} catch (Exception e) {
					continue;
				}

				final EClass classz = eobject.eClass();
				if (classz == null) {
					continue;
				}
				if (CalPackage.eINSTANCE.getAstNetwork().equals(classz)
						|| CalPackage.eINSTANCE.getAstActor().equals(classz)) {
					objects.add(eobject);
				}
			}
		}
	}
}
