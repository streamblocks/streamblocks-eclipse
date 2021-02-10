package streamblocks.eclipse.cal.ui.xdf.features;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.IFeatureProviderWithPatterns;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IPeService;
import org.eclipse.jface.dialogs.Dialog;

import streamblocks.eclipse.cal.cal.AstEntityVariable;
import streamblocks.eclipse.cal.ui.xdf.dialogs.EntitySelectionDialog;
import streamblocks.eclipse.cal.ui.xdf.patterns.InstancePattern;
import streamblocks.eclipse.cal.ui.xdf.util.XdfUtil;

/**
 * This class implements the dialog shown to user when he want to set or modify
 * the entity (Actor or Network) contained by an instance.
 * 
 * @author Antoine Lorence
 * 
 */
public class UpdateRefinementFeature extends AbstractCustomFeature {

	private final IPeService peService;
	private boolean hasDoneChanges = false;

	public UpdateRefinementFeature(final IFeatureProvider fp) {
		super(fp);
		peService = Graphiti.getPeService();
	}

	@Override
	public String getName() {
		return "Set/Update refinement";
	}

	@Override
	public String getDescription() {
		return "Assign an existing Actor or Network to this Instance";
	}

	@Override
	public boolean canExecute(final ICustomContext context) {

		PictogramElement[] pes = context.getPictogramElements();
		if (pes.length != 1) {
			return false;
		}

		final PictogramElement pe = pes[0];
		if (getBusinessObjectForPictogramElement(pe) instanceof AstEntityVariable) {
			return true;
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(final ICustomContext context) {

		final AstEntityVariable instance = (AstEntityVariable) getBusinessObjectForPictogramElement(context.getPictogramElements()[0]);

		final EntitySelectionDialog entityDialog;
		try {
			entityDialog = new EntitySelectionDialog(XdfUtil.getDefaultShell(), XdfUtil.getProject(instance));
		} catch (CoreException e) {
			e.printStackTrace();
			return;
		}

		entityDialog.setTitle("Select an entity");
		entityDialog.setMessage("Choose the entity you want to use "
				+ "in this Instance. You can filter "
				+ "results with string patterns.");

		final int returnCode = entityDialog.open();
		if (returnCode == Dialog.CANCEL) {
			hasDoneChanges = false;
			return;
		}

		// Now, we want to display ports of the selected entity in the instance
		// shape (graphical representation of the Instance)
		final ContainerShape instanceShape;
		{
			final PictogramElement clickedPe = context.getPictogramElements()[0];
			// We want to get the instance container shape
			if (clickedPe.isActive()) {
				// This this the shape we want to use
				instanceShape = (ContainerShape) clickedPe;
			} else {
				// Get the active container shape
				instanceShape = (ContainerShape) peService.getActiveContainerPe(clickedPe);
			}
		}

		final EObject refinement = (EObject) entityDialog.getFirstResult();
		// Do not re-apply the same refinement on the instance
		if (refinement.equals(instance.getEntity())) {
			return;
		}

		// We get the corresponding Pattern. It must be an InstancePattern
		final IPattern ipattern = ((IFeatureProviderWithPatterns) getFeatureProvider())
				.getPatternForPictogramElement(instanceShape);
		final InstancePattern pattern = (InstancePattern) ipattern;

		// Set refinement on selected instance.
		hasDoneChanges = pattern.updateRefinementAndRestoreConnections(
				instanceShape, refinement, "The refinement for instance \""
						+ instance.getName() + "\" has been updated:");

	}

	@Override
	public boolean hasDoneChanges() {
		return hasDoneChanges;
	}
}
