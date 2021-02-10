package streamblocks.eclipse.cal.ui.xdf.diagram;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * This feature is part of context menu. It simply open the "Properties" tab and
 * initialize it with the current selected elements. It is useful when user
 * doesn't know how to display this tab. In some case, it is hidden by default
 * (if eclipse has just been reinstalled, etc.)
 * 
 * @author Antoine Lorence
 * 
 */
public class OpenPropertiesFeature extends AbstractCustomFeature {

	public OpenPropertiesFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Show properties";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		// Properties tab can always be opened. There is always something
		// selected when user trigger context menu
		return true;
	}

	@Override
	public void execute(ICustomContext context) {
		final IViewPart propertiesPart;
		try {
			// Get and show the "Properties" tab
			propertiesPart = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.showView("org.eclipse.ui.views.PropertySheet");
		} catch (PartInitException e) {
			e.printStackTrace();
			return;
		}

		final DiagramEditor diagramEditor = (DiagramEditor) getDiagramBehavior()
				.getDiagramContainer();

		if (propertiesPart instanceof PropertySheet) {
			// Get the TabbedPropertyPage
			TabbedPropertySheetPage page = (TabbedPropertySheetPage) propertiesPart
					.getAdapter(TabbedPropertySheetPage.class);

			// If the adapter didn't find a TabbedPage, the Properties tab
			// displays the default table content, we have to initialize it
			if (page == null) {
				// Initialize the tab with the current Diagram Editor
				((PropertySheet) propertiesPart).partActivated(diagramEditor);
				page = (TabbedPropertySheetPage) propertiesPart
						.getAdapter(TabbedPropertySheetPage.class);
			}

			if (page != null) {
				// Immediately displays content related to the current
				// selection
				final ISelection currentSelection = new StructuredSelection(
						context.getPictogramElements());
				page.selectionChanged(diagramEditor, currentSelection);
			} else {
			//	OrccLogger.warnln("Unable to refresh Property tab...");
			}
		}
	}

	@Override
	public boolean hasDoneChanges() {
		// This feature does not modify anything in the diagram
		return false;
	}
}
