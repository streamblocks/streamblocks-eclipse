package streamblocks.eclipse.cal.ui.xdf.patterns;

import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.IGaService;

import streamblocks.eclipse.cal.cal.AstNetwork;
import streamblocks.eclipse.cal.cal.AstPort;
import streamblocks.eclipse.cal.ui.xdf.styles.StyleUtil;
import streamblocks.eclipse.cal.ui.xdf.util.PropsUtil;
import streamblocks.eclipse.cal.ui.xdf.util.XdfUtil;

public class InputNetworkPortPattern extends NetworkPortPattern {

	@Override
	public String getCreateName() {
		return "Input port";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(final Object obj) {
		if (obj instanceof AstPort) {
			return XdfUtil.isInputNetworkPort((AstPort) obj);
		}
		return false;
	}

	@Override
	public String getCreateDescription() {
		return "Create an input port directly in the network";
	}

	@Override
	protected void setIdentifier(ContainerShape topLevelShape) {
		PropsUtil.setInputPort(topLevelShape);
	}

	@Override
	protected boolean checkIdentifier(PictogramElement pe) {
		return PropsUtil.isInputPort(pe);
	}

	@Override
	protected Polygon getPortPolygon(final GraphicsAlgorithmContainer shape, final IGaService gaService) {
		final int[] points = { 0, SHAPE_HEIGHT / 2, SHAPE_WIDTH, 0, SHAPE_WIDTH, SHAPE_HEIGHT };
		final Polygon polygon = gaService.createPlainPolygon(shape, points);
		polygon.setStyle(StyleUtil.inputPortShape(getDiagram()));
		return polygon;
	}

	@Override
	protected void addPortToNetwork(final AstPort port, final AstNetwork network) {
		network.getInputs().add(port);
	}

}
