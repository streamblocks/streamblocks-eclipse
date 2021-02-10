package streamblocks.eclipse.cal.ui.xdf.layout;

import java.util.Map;


import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.cau.cs.kieler.core.alg.BasicProgressMonitor;
import de.cau.cs.kieler.core.kgraph.KEdge;
import de.cau.cs.kieler.core.kgraph.KGraphElement;
import de.cau.cs.kieler.core.kgraph.KNode;
import de.cau.cs.kieler.kiml.klayoutdata.KEdgeLayout;
import de.cau.cs.kieler.kiml.klayoutdata.KShapeLayout;
import de.cau.cs.kieler.kiml.options.LayoutOptions;
import de.cau.cs.kieler.kiml.options.PortConstraints;
import de.cau.cs.kieler.klay.layered.LayeredLayoutProvider;
import streamblocks.eclipse.cal.ui.xdf.util.PropsUtil;

/**
 * This custom feature implements an auto-layout for any graph. It uses the
 * LayeredLayout algorithm from Kieler project. More information:
 * http://www.informatik.uni-kiel.de/en/rtsys/kieler/
 * 
 * @author Antoine Lorence
 * 
 */
public class AutoLayoutFeature extends AbstractCustomFeature {

	private boolean hasDoneChanges;

	final private String layoutName;

	public AutoLayoutFeature(IFeatureProvider fp, final String layoutName) {
		super(fp);
		hasDoneChanges = false;
		this.layoutName = layoutName;
	}

	@Override
	public String getDescription() {
		return "Layout diagram with Kieler Layouter"; //$NON-NLS-1$
	}

	@Override
	public String getName() {
		return layoutName; //$NON-NLS-1$
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public boolean hasDoneChanges() {
		return hasDoneChanges;
	}

	@Override
	public void execute(ICustomContext ctxt) {

		final XdfDiagramLayoutManager manager = new XdfDiagramLayoutManager(getDiagram());

		configureLayout(manager.getTopLevelNode(), manager.getPeKGraphMap());

		final LayeredLayoutProvider provider = new LayeredLayoutProvider();
		provider.doLayout(manager.getTopLevelNode(), new BasicProgressMonitor());

		manager.applyLayout();
		hasDoneChanges = true;
	}

	private void configureLayout(final KNode diagramNode, final Map<PictogramElement, KGraphElement> peKnodeMap) {

		for (final Shape shape : getDiagram().getChildren()) {
			final KShapeLayout shapeLayout = ((KNode) peKnodeMap.get(shape)).getData(KShapeLayout.class);
			if (PropsUtil.isInstance(shape)) {
				if (shapeLayout != null) {
					configureInstanceNode(shapeLayout);
				}
			} else if (PropsUtil.isPort(shape)) {
				if (shapeLayout != null) {
					configureNetworkPortNode(shapeLayout);
				}
			}
		}

		for (final Connection connection : getDiagram().getConnections()) {
			final KEdgeLayout edgeLayout = ((KEdge) peKnodeMap.get(connection)).getData(KEdgeLayout.class);
			if (edgeLayout != null) {
				configureConnectionEdge(edgeLayout);
			}
		}

		final KShapeLayout diagramLayout = diagramNode.getData(KShapeLayout.class);
		if (diagramLayout != null) {
			configureDiagramNode(diagramLayout);
		}
	}

	protected void configureDiagramNode(final KShapeLayout diagramLayout) {

		diagramLayout.setProperty(LayoutOptions.SPACING, 30.0f);

		/*
		 * Some options have been tested in the past:
		 * 
		 * mappingShapeLayout.setProperty(Properties.NODE_LAYERING,
		 * LayeringStrategy.LONGEST_PATH);
		 * mappingShapeLayout.setProperty(Properties.MERGE_PORTS, true);
		 * mappingShapeLayout.setProperty(Properties.CROSS_MIN,
		 * CrossingMinimizationStrategy.INTERACTIVE);
		 */
	}

	protected void configureInstanceNode(final KShapeLayout instanceLayout) {
		// We never want to move port inside an Instance
		instanceLayout.setProperty(LayoutOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS);
	}

	protected void configureNetworkPortNode(final KShapeLayout portLayout) {
	}

	protected void configureConnectionEdge(final KEdgeLayout edgeLayout) {
	}
}
