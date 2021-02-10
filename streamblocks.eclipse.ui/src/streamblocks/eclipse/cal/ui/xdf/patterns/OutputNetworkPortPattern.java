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

/**
 * This class define an Output port for a network
 * 
 * @author Antoine Lorence
 * 
 */
public class OutputNetworkPortPattern extends NetworkPortPattern {

	@Override
	public String getCreateName() {
		return "Output port";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(final Object obj) {
		if (obj instanceof AstPort) {
			return XdfUtil.isOutputNetworkPort((AstPort) obj);
		}
		return false;
	}

	@Override
	public String getCreateDescription() {
		return "Create an ouput port directly in the network";
	}

	@Override
	protected void setIdentifier(ContainerShape topLevelShape) {
		PropsUtil.setOutputPort(topLevelShape);
	}

	@Override
	protected boolean checkIdentifier(PictogramElement pe) {
		return PropsUtil.isOutputPort(pe);
	}

	@Override
	protected Polygon getPortPolygon(final GraphicsAlgorithmContainer shape, final IGaService gaService) {
		final int[] points = { 0, 0, 0, SHAPE_HEIGHT, SHAPE_WIDTH, SHAPE_HEIGHT / 2 };
		final Polygon polygon = gaService.createPlainPolygon(shape, points);
		polygon.setStyle(StyleUtil.outputPortShape(getDiagram()));
		return polygon;
	}

	@Override
	protected void addPortToNetwork(final AstPort port, final AstNetwork network) {
		network.getOutputs().add(port);
	}
}
