package streamblocks.eclipse.cal.ui.xdf.features;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICopyContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.AbstractCopyFeature;

import streamblocks.eclipse.cal.cal.AstEntityVariable;
import streamblocks.eclipse.cal.cal.AstPort;

/**
 * Implements the ability to copy/cut objects from diagram.
 * 
 * @author Antoine Lorence
 * 
 */
public class CopyFeature extends AbstractCopyFeature {

	public CopyFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canCopy(ICopyContext context) {
		final PictogramElement[] pes = context.getPictogramElements();
		if (pes == null || pes.length == 0) {
			// Nothing selected
			return false;
		}

		// We can copy anything
		return true;
	}

	@Override
	public void copy(ICopyContext context) {
		final List<EObject> vertices = new ArrayList<EObject>();
		for (final PictogramElement pe : context.getPictogramElements()) {
			final EObject businessObject = (EObject) getBusinessObjectForPictogramElement(pe);
			if (businessObject instanceof AstEntityVariable || businessObject instanceof AstPort) {
				vertices.add(businessObject);
			}
		} 
		// Store selected business objects into clipboard
		putToClipboard(vertices.toArray());
	}

}
