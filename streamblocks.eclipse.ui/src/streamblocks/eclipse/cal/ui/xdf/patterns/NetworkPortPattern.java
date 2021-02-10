package streamblocks.eclipse.cal.ui.xdf.patterns;

import javax.sound.sampled.Port;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.func.IDirectEditing;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.AbstractPattern;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaLayoutService;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;

import com.google.common.graph.Network;

import streamblocks.eclipse.cal.cal.AstEntityVariable;
import streamblocks.eclipse.cal.cal.AstNetwork;
import streamblocks.eclipse.cal.cal.AstPort;
import streamblocks.eclipse.cal.cal.AstType;
import streamblocks.eclipse.cal.cal.CalFactory;
import streamblocks.eclipse.cal.cal.util.CalAdapterFactory;
import streamblocks.eclipse.cal.cal.util.CalSwitch;
import streamblocks.eclipse.cal.ui.xdf.styles.StyleUtil;
import streamblocks.eclipse.cal.ui.xdf.util.PropsUtil;
import streamblocks.eclipse.cal.ui.xdf.util.XdfUtil;

/**
 * This class is the common part for Network port management. Concrete
 * implementations handle specific cases for input and output ports.
 * 
 * @see InputNetworkPortPattern
 * @see OutputNetworkPortPattern
 * 
 * @author Antoine Lorence
 * @author Endri Bezati
 * 
 */
abstract public class NetworkPortPattern extends AbstractPattern implements IPattern {

	public final static int SHAPE_HEIGHT = 34;
	public final static int SHAPE_WIDTH = (int) (SHAPE_HEIGHT * 0.866);
	private final static int TEXT_PORT_SPACE = 4;
	private final static int TEXT_DEFAULT_WIDTH = 50;
	private final static int TEXT_DEFAULT_HEIGHT = 12;

	public static final String SHAPE_ID = "PORT_SHAPE";
	private static final String LABEL_ID = "PORT_LABEL";

	public NetworkPortPattern() {
		super(null);
	}

	@Override
	abstract public String getCreateName();

	abstract protected void setIdentifier(ContainerShape topLevelShape);

	abstract protected boolean checkIdentifier(PictogramElement pe);

	abstract protected Polygon getPortPolygon(final GraphicsAlgorithmContainer shape, final IGaService gaService);

	abstract protected void addPortToNetwork(AstPort port, AstNetwork network);

	@Override
	protected boolean isPatternRoot(PictogramElement pe) {
		return checkIdentifier(pe);
	}

	@Override
	protected boolean isPatternControlled(PictogramElement pe) {

		if (isPatternRoot(pe)) {
			return true;
		}
		final String identifier = PropsUtil.getIdentifier(pe);
		final String[] otherIds = { SHAPE_ID, LABEL_ID };
		for (final String e : otherIds) {
			if (e.equals(identifier)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		boolean isText = context.getGraphicsAlgorithm() instanceof Text;
		boolean isLabel = PropsUtil.isExpectedPc(context.getGraphicsAlgorithm(), LABEL_ID);
		return isText && isLabel;
	}

	@Override
	public int getEditingType() {
		return IDirectEditing.TYPE_TEXT;
	}

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		AstPort obj = (AstPort) getBusinessObjectForPictogramElement(context.getPictogramElement());
		return obj.getName();
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		final AstPort port = (AstPort) getBusinessObjectForPictogramElement(context
				.getPictogramElement());
		return checkValueValid(value, port);
	}

	public String checkValueValid(final String value, final AstPort port) {
		if (value.length() < 1) {
			return "Please enter a text to name the Port.";
		}
		if (!value.matches("[a-zA-Z][a-zA-Z0-9_]*")) {
			return "Port name must start with a letter, and contains only alphanumeric characters";
		}
		final AstNetwork network = (AstNetwork) getBusinessObjectForPictogramElement(getDiagram());
		// -- Check Input Ports
		for(final AstPort inputPort : network.getInputs() ) {
			if(inputPort != port && port.getName().equals(inputPort.getName())) {
				return "The network already contains an input port with the same name (" + port.getName() + ")";
			}
		}
		
		for(final AstEntityVariable entity : network.getEntities()) {
			if(port.getName().equals(entity.getName())) {
				return "The network already contains an entity with the same name (" + port.getName() + ")";
			}
		}
		
		for(final AstPort outputPort : network.getOutputs() ) {
			if(outputPort != port && port.getName().equals(outputPort.getName())) {
				return "The network already contains an output port with the same name (" + port.getName() + ")";
			}
		}
		
		// null -> value is valid
		return null;
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		PictogramElement pe = context.getPictogramElement();
		final AstPort port = (AstPort) getBusinessObjectForPictogramElement(pe);
		port.setName(value);

		updatePictogramElement(pe);
	}

	@Override
	public boolean stretchFieldToFitText() {
		return true;
	}

	@Override
	public void preDelete(IDeleteContext context) {
		final PictogramElement pe = context.getPictogramElement();
		if (pe instanceof AnchorContainer) {
			XdfUtil.deleteConnections(getFeatureProvider(),
					(AnchorContainer) pe);
		}
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		// We create the instance in a diagram
		if (context.getTargetContainer() instanceof Diagram) {
			// A network is associated to this diagram
			final Object bo = getBusinessObjectForPictogramElement(context.getTargetContainer());
			if (bo instanceof Network) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Object[] create(ICreateContext context) {
		final AstNetwork network = (AstNetwork) getBusinessObjectForPictogramElement(getDiagram());

		// Create the Port instance
		final AstPort newPort = (AstPort) CalFactory.eINSTANCE.createAstPort();
		newPort.setName(XdfUtil.uniqueVertexName(network, "port"));

		// Set default type to i32 for the port
		AstType type = CalFactory.eINSTANCE.createAstType();
		type.setBuiltin("int");
		newPort.setType(type);

		// Add the newly created port to the network
		addPortToNetwork(newPort, network);

		addGraphicalRepresentation(context, newPort);

		// activate direct editing after object creation
		getFeatureProvider().getDirectEditingInfo().setActive(true);

		return new Object[] { newPort };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		if (context.getTargetContainer() instanceof Diagram) {
			return isMainBusinessObjectApplicable(context.getNewObject());
		}
		return false;
	}

	@Override
	public PictogramElement add(IAddContext context) {
		final Diagram targetDiagram = (Diagram) context.getTargetContainer();
		final IPeCreateService peCreateService = Graphiti.getPeCreateService();
		final IGaService gaService = Graphiti.getGaService();

		final AstPort addedDomainObject = (AstPort) context.getNewObject();

		// Create the container
		final ContainerShape topLevelShape = peCreateService.createContainerShape(targetDiagram, true);
		setIdentifier(topLevelShape);
		peCreateService.createChopboxAnchor(topLevelShape);

		// The main container is an invisible rectangle
		final Rectangle topLevelInvisibleRect = gaService.createInvisibleRectangle(topLevelShape);

		// Draw the port according to its direction
		final Polygon polygon = getPortPolygon(topLevelInvisibleRect, gaService);
		gaService.setSize(polygon, SHAPE_WIDTH, SHAPE_HEIGHT);
		PropsUtil.setIdentifier(polygon, SHAPE_ID);

		// Add the label of the port
		final Text text = gaService.createPlainText(topLevelInvisibleRect);
		PropsUtil.setIdentifier(text, LABEL_ID);

		// Configure text properties
		text.setStyle(StyleUtil.portText(getDiagram()));

		// We define an arbitrary width to text, allowing user to see chars
		// when first direct editing port name
		gaService.setLocationAndSize(text, 0, SHAPE_HEIGHT + TEXT_PORT_SPACE, TEXT_DEFAULT_WIDTH, TEXT_DEFAULT_HEIGHT);

		// Initialize the port if domain object already exists
		if (addedDomainObject.getName() != null) {
			text.setValue(addedDomainObject.getName());
		}

		// Configure direct editing
		// 1- Get the IDirectEditingInfo object
		final IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();
		// 2- These 2 members will be used to retrieve the pattern to call for
		// direct editing
		directEditingInfo.setPictogramElement(topLevelShape);
		directEditingInfo.setGraphicsAlgorithm(text);
		// 3- This PictogramElement is used to locate input on the diagram
		directEditingInfo.setMainPictogramElement(topLevelShape);

		link(topLevelShape, addedDomainObject);

		gaService.setLocation(topLevelInvisibleRect, context.getX(), context.getY());
		layoutPictogramElement(topLevelShape);

		return topLevelShape;
	}

	/**
	 * @see NetworkPortPattern#layout(ILayoutContext)
	 */
	@Override
	public boolean canResizeShape(IResizeShapeContext context) {
		return false;
	}

	@Override
	public boolean canLayout(ILayoutContext context) {
		return isPatternRoot(context.getPictogramElement());
	}

	/**
	 * Port can't be resized. In that particular case, layout() does everything
	 * needed for a nice display. It adapts the size of text to its value and
	 * ensures everything is always centered
	 * 
	 * @param context
	 * @return
	 */
	@Override
	public boolean layout(ILayoutContext context) {
		final IGaLayoutService gaService = Graphiti.getGaLayoutService();
		final PictogramElement topLevelPe = context.getPictogramElement();
		final GraphicsAlgorithm topLevelGa = topLevelPe.getGraphicsAlgorithm();

		final Text txt = (Text) PropsUtil.findPcFromIdentifier(topLevelPe, LABEL_ID);
		final Polygon poly = (Polygon) PropsUtil.findPcFromIdentifier(topLevelPe, SHAPE_ID);

		final int minTxtWidth = XdfUtil.getTextMinWidth(txt);
		final int newTextWidth = Math.max(minTxtWidth, SHAPE_WIDTH);

		// Update text width
		gaService.setSize(txt, newTextWidth, XdfUtil.getTextMinHeight(txt));
		// Update top level invisible rectangle size & position
		gaService.setSize(topLevelGa, newTextWidth, SHAPE_HEIGHT + TEXT_PORT_SPACE + TEXT_DEFAULT_HEIGHT);
		// Update position of polygon in the top level rectangle
		gaService.setLocation(poly, (newTextWidth - SHAPE_WIDTH) / 2, 0);

		return true;
	}

	@Override
	public IReason updateNeeded(IUpdateContext context) {
		final PictogramElement pe = context.getPictogramElement();

		if (isPatternRoot(pe)) {
			final Text text = (Text) PropsUtil.findPcFromIdentifier(pe, LABEL_ID);
			if (text == null) {
				return Reason.createFalseReason("Label Not found !!");
			}

			final AstPort port = (AstPort) getBusinessObjectForPictogramElement(pe);
			if (!text.getValue().equals(port.getName())) {
				return Reason.createTrueReason("The port name has been updated from outside of the diagram");
			}
		}

		return super.updateNeeded(context);
	}

	@Override
	public boolean update(IUpdateContext context) {
		final PictogramElement pe = context.getPictogramElement();

		if (!isPatternRoot(pe)) {
			return false;
		}

		final Text txt = (Text) PropsUtil.findPcFromIdentifier(pe, LABEL_ID);
		final AstPort port = (AstPort) getBusinessObjectForPictogramElement(pe);
		txt.setValue(port.getName());

		layoutPictogramElement(pe);

		return true;
	}

	/**
	 * Returns the PictogramElement to use as main element for displaying a
	 * selection around a port.
	 * 
	 * @param pe
	 * @return
	 */
	public GraphicsAlgorithm getSelectionBorder(PictogramElement pe) {
		if (isPatternRoot(pe)) {
			return (GraphicsAlgorithm) PropsUtil.findPcFromIdentifier(pe, SHAPE_ID);
		}
		return null;
	}

	/**
	 * Find and return the type associated with the Network port. This
	 * information is stored in the linked objects.
	 * 
	 * @param pe
	 * @return
	 * @deprecated This method will not work until the port type is stored as
	 *             string in the shape and a type parser can be used to crete
	 *             the Type object from this String
	
	@Deprecated
	public Type getTypeFromShape(PictogramElement pe) {
		if (isPatternRoot(pe)) {
			for (EObject businessObject : pe.getLink().getBusinessObjects()) {
				if (businessObject instanceof Type) {
					return (Type) businessObject;
				}
			}
		}
		return null;
	}
 */
	/**
	 * Return the name of the port, from information contained in graphical
	 * representation only. If this port has no name, returns an empty String.
	 * 
	 * @param pe
	 * @return
	 */
	public String getNameFromShape(final PictogramElement pe) {
		if (isPatternRoot(pe)) {
			final Text label = (Text) PropsUtil.findPcFromIdentifier(pe, LABEL_ID);
			return label.getValue();
		}
		return "";
	}

	/**
	 * Retrieve the ChopboxAnchor for the port.
	 * 
	 * @param container
	 * @return
	 */
	public Anchor getAnchor(final AnchorContainer container) {
		return Graphiti.getPeService().getChopboxAnchor(container);
	}
}
