package streamblocks.eclipse.cal.ui.xdf.features;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.IFeatureProviderWithPatterns;
import org.eclipse.jface.dialogs.MessageDialog;

import streamblocks.eclipse.cal.cal.AstAbstractActor;
import streamblocks.eclipse.cal.cal.AstEntity;
import streamblocks.eclipse.cal.cal.AstEntityInstanceExpr;
import streamblocks.eclipse.cal.cal.AstEntityVariable;
import streamblocks.eclipse.cal.cal.AstUnit;
import streamblocks.eclipse.cal.cal.CalFactory;
import streamblocks.eclipse.cal.ui.util.StreamBlocksUtil;
import streamblocks.eclipse.cal.ui.xdf.patterns.InstancePattern;
import streamblocks.eclipse.cal.ui.xdf.util.PropsUtil;
import streamblocks.eclipse.cal.ui.xdf.util.XdfUtil;

/**
 * This special feature allow user to drag a file from the project explorer and
 * drop it directly in the diagram. An instance is created with a refinement
 * corresponding to the dropped entity. The user is automatically prompted for a
 * name to give to the instance newly created.
 * 
 * @author Antoine Lorence
 * 
 */
public class DropInstanceFromFileFeature extends AbstractAddFeature {

	private boolean hasDoneChanges = false;

	public DropInstanceFromFileFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		
		final IFile file = (IFile) context.getNewObject();
		final String extension = file.getFileExtension();
		if (StreamBlocksUtil.NETWORK_SUFFIX.equals(extension)
				|| StreamBlocksUtil.CAL_SUFFIX.equals(extension)) {

			final ContainerShape target = context.getTargetContainer();
			if (target == getDiagram() || PropsUtil.isInstance(target)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public PictogramElement add(IAddContext context) {

		IFile file = (IFile) context.getNewObject();
		if (StreamBlocksUtil.CAL_SUFFIX.equals(file.getFileExtension())) {
			file = StreamBlocksUtil.getFile(file.getProject(),
					StreamBlocksUtil.getQualifiedName(file), StreamBlocksUtil.IR_SUFFIX);
		}

		final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		final Resource resource = getDiagramBehavior().getEditingDomain().getResourceSet().getResource(uri, true);
		// Sometimes, XdfParser fails for some reasons
		if (resource.getContents() == null) {
			MessageDialog.openWarning(XdfUtil.getDefaultShell(), "Warning", "This file has some errors. You can't use it here.");
			return null;
		} else if (resource.getContents().size() == 0) {
			MessageDialog.openWarning(XdfUtil.getDefaultShell(), "Warning",
					"No content found in this file, it is impossible to generate a refined Instance.");
			return null;
		}
		final EObject eobject = resource.getContents().get(0);

		if (eobject instanceof AstUnit) {
			MessageDialog.openWarning(XdfUtil.getDefaultShell(), "Warning", "You cannot drop a Unit here.");
			return null;
		}

		final ContainerShape target = context.getTargetContainer();
		if (target == getDiagram()) {
			return addToDiagram(eobject, context);
		} else if (PropsUtil.isInstance(target)) {
			return updateRefinement(target, eobject);
		}

		return null;
	}

	private PictogramElement addToDiagram(final EObject eobject,
			final IAddContext context) {

		final AstEntityVariable instance = CalFactory.eINSTANCE.createAstEntityVariable();
		final int objectCpt = getDiagram().getChildren().size() + 1;
		instance.setName("instance_" + objectCpt);
		
		AstEntityInstanceExpr instanceExpr = CalFactory.eINSTANCE.createAstEntityInstanceExpr();
		AstEntity entity = CalFactory.eINSTANCE.createAstEntity();
		entity.setActor((AstAbstractActor)eobject);
		instanceExpr.setType(entity);
		instance.setEntity(instanceExpr);

		final AddContext addInstanceContext = new AddContext(context, instance);
		final PictogramElement addedPe = getFeatureProvider().addIfPossible(addInstanceContext);

		getFeatureProvider().getDirectEditingInfo().setActive(true);
		hasDoneChanges = true;
		return addedPe;
	}

	private PictogramElement updateRefinement(
			final ContainerShape instanceShape, final EObject refinement) {

		final InstancePattern pattern = (InstancePattern) ((IFeatureProviderWithPatterns) getFeatureProvider())
				.getPatternForPictogramElement(instanceShape);
		final AstEntityVariable instance = (AstEntityVariable) getBusinessObjectForPictogramElement(instanceShape);

		// Do not re-apply the same refinement on the instance
		if (refinement.equals(instance.getEntity())) {
			return instanceShape;
		}

		hasDoneChanges = pattern.updateRefinementAndRestoreConnections(
				instanceShape, refinement, "The refinement for instance \""
						+ instance.getName() + "\" has been updated:");
		return instanceShape;
	}

	@Override
	public boolean hasDoneChanges() {
		return hasDoneChanges;
	}
}
