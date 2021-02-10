package streamblocks.eclipse.cal.ui.xdf.features;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import streamblocks.eclipse.cal.cal.AstEntityVariable;

/**
 * This feature is used to open an Actor or an Instance in a new tab when user
 * perform a double click on a refined instance.
 * 
 * @author Antoine Lorence
 * 
 */
public class InstanceDblClickFeature extends AbstractCustomFeature {

	public InstanceDblClickFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		final PictogramElement pe = context.getInnerPictogramElement();
		final EObject eobject = Graphiti.getLinkService()
				.getBusinessObjectForLinkedPictogramElement(pe);
		if (eobject instanceof AstEntityVariable) {
			return ((AstEntityVariable) eobject).getEntity() != null;
		}
		return super.canExecute(context);
	}

	@Override
	public void execute(ICustomContext context) {
		final PictogramElement pe = context.getInnerPictogramElement();
		final AstEntityVariable instance = (AstEntityVariable) Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
		
		//instance.eResource().ge
		IFile file = null;
		/* -- FIXME
		if (instance.isActor()) {
			file = instance.getActor().getFile();
		} else if (instance.isNetwork()) {
			file = instance.getNetwork().getFile();
		} else {
			OrccLogger.severeln("Invalid instance");
			return;
		}*/
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		try {
			IDE.openEditor(page, file, true);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean hasDoneChanges() {
		return false;
	}
}
