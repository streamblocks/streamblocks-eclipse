package streamblocks.eclipse.cal.ui.xdf.patterns;

import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.ChopboxAnchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.AbstractConnectionPattern;
import org.eclipse.graphiti.pattern.IFeatureProviderWithPatterns;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;

import streamblocks.eclipse.cal.cal.AstAbstractActor;
import streamblocks.eclipse.cal.cal.AstEntityVariable;
import streamblocks.eclipse.cal.cal.AstEntityVariableReference;
import streamblocks.eclipse.cal.cal.AstNetwork;
import streamblocks.eclipse.cal.cal.AstPort;
import streamblocks.eclipse.cal.cal.AstStructureStatementConnection;
import streamblocks.eclipse.cal.cal.CalFactory;
import streamblocks.eclipse.cal.ui.xdf.styles.StyleUtil;
import streamblocks.eclipse.cal.ui.xdf.util.PropsUtil;

/**
 * Implements a visible connection between 2 ports.
 * 
 * Each connection links an input and an output port. A port can be represented
 * by an Input or an Output port directly contained in the network, or a port in
 * an Instance. In the second case, the real port is a port contained in an
 * Actor or in a Network, depending on how the instance has been refined.
 * 
 * @author Antoine Lorence
 * 
 */
public class ConnectionPattern extends AbstractConnectionPattern {

	private static enum PortKind {
		INPUT, OUTPUT
	}

	private static enum PortContainer {
		NETWORK, INSTANCE
	}

	private static enum ConnectionSide {
		SOURCE, TARGET
	}

	/**
	 * Define a simple utility class to encapsulate all information about a source
	 * or a target port. It is used to check validity of a connection, and to create
	 * the Connection instance to add in the Network.
	 * 
	 * @author Antoine Lorence
	 */
	public class PortInformation {
		private final AstEntityVariable vertex;
		private final AstPort port;
		private final PortKind kind;
		private final PortContainer container;

		public PortInformation(AstEntityVariable vertex, AstPort port, PortKind direction, PortContainer type) {
			this.vertex = vertex;
			this.port = port;
			this.kind = direction;
			this.container = type;
		}

		/**
		 * Return the vertex associated to the port. For an Instance port, it is the
		 * instance. For the current Network port, it is the port itself
		 * 
		 * @return
		 */
		public AstEntityVariable getVertex() {
			return vertex;
		}

		/**
		 * If the vertex is an instance, returns the Port of the refinement (actor or
		 * network) of this instance. In case of a port in the current Network, this
		 * method returns null.
		 * 
		 * @return
		 */
		public AstPort getPort() {
			return port;
		}

		/**
		 * Returns the port side (input or output)
		 * 
		 * @return
		 */
		public PortKind getKind() {
			return kind;
		}

		public PortContainer getContainer() {
			return container;
		}

		public ConnectionSide getConnectionSide() {
			if (container.equals(PortContainer.NETWORK)) {
				return kind == PortKind.INPUT ? ConnectionSide.SOURCE : ConnectionSide.TARGET;
			} else {
				return kind == PortKind.INPUT ? ConnectionSide.TARGET : ConnectionSide.SOURCE;
			}
		}
	}

	@Override
	public String getCreateName() {
		return "Connection";
	}

	@Override
	public String getCreateDescription() {
		return "Add a connection between 2 ports";
	}

	@Override
	public boolean canStartConnection(ICreateConnectionContext context) {
		final Anchor anchor = context.getSourceAnchor();
		final PortInformation src = getPortInformations(anchor);
		if (src == null) {
			return false;
		}

		if (src.getConnectionSide() == ConnectionSide.TARGET) {
			if (anchor.getIncomingConnections().size() > 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean canCreate(ICreateConnectionContext context) {
		// The current diagram has a Network attached
		if (!(getBusinessObjectForPictogramElement(getDiagram()) instanceof AstNetwork)) {
			return false;
		}

		final PortInformation src = getPortInformations(context.getSourceAnchor());
		final PortInformation trgt = getPortInformations(context.getTargetAnchor());
		if (src == null || trgt == null) {
			return false;
		}

		// Disallow connection between 2 network ports
		if (src.getContainer() == PortContainer.NETWORK && trgt.getContainer() == PortContainer.NETWORK) {
			return false;
		}

		// Check incompatible connections source/target
		if (src.getConnectionSide() == trgt.getConnectionSide()) {
			return false;
		}

		// Check (for the target) if no connection is already targeting this
		// port
		if (src.getConnectionSide() == ConnectionSide.TARGET) {
			if (context.getSourceAnchor().getIncomingConnections().size() > 0) {
				return false;
			}
		} else if (trgt.getConnectionSide() == ConnectionSide.TARGET) {
			if (context.getTargetAnchor().getIncomingConnections().size() > 0) {
				return false;
			}
		} else {
			/*
			 * OrccLogger.
			 * warnln("Oops, unknown error appear in ConnectionPattern. A connection cannot be created "
			 * + "between 2 sources or a bug may be fixed in the source code.");
			 */
			return false;
		}

		return true;
	}

	@Override
	public Connection create(ICreateConnectionContext context) {

		PortInformation src = getPortInformations(context.getSourceAnchor());
		final AddConnectionContext addContext;
		// Create connection context. In some case, connection source and target
		// need to be exchanged, to avoid arrow on the wrong side of the
		// connection shape
		if (src.getConnectionSide() != ConnectionSide.SOURCE) {
			addContext = new AddConnectionContext(context.getTargetAnchor(), context.getSourceAnchor());
			src = getPortInformations(context.getTargetAnchor());
		} else {
			addContext = new AddConnectionContext(context.getSourceAnchor(), context.getTargetAnchor());
		}

		final PortInformation tgt = getPortInformations(addContext.getTargetAnchor());
		// Create new business object
		final AstStructureStatementConnection dfConnection = CalFactory.eINSTANCE.createAstStructureStatementConnection();
		
		final AstEntityVariableReference from = CalFactory.eINSTANCE.createAstEntityVariableReference();
		from.setVariable(src.getVertex());
		dfConnection.setFrom(from);
		dfConnection.setOutPort(src.getPort().getName());
		
		final AstEntityVariableReference to = CalFactory.eINSTANCE.createAstEntityVariableReference();
		to.setVariable(tgt.getVertex());
		dfConnection.setTo(to);
		dfConnection.setInPort(tgt.getPort().getName());
		
		final AstNetwork network = (AstNetwork) getBusinessObjectForPictogramElement(getDiagram());
		
		network.getStructure().getConnections().add(dfConnection);

		addContext.setNewObject(dfConnection);
		final Connection newConnection = (Connection) getFeatureProvider().addIfPossible(addContext);

		return newConnection;
	}

	@Override
	public boolean canAdd(IAddContext context) {
		if (context instanceof IAddConnectionContext) {
			if (context.getNewObject() instanceof AstStructureStatementConnection) {
				return true;
			}
		}
		return super.canAdd(context);
	}

	@Override
	public PictogramElement add(IAddContext context) {
		final IAddConnectionContext addConContext = (IAddConnectionContext) context;
		final AstStructureStatementConnection addedConnection = (AstStructureStatementConnection) context.getNewObject();
		final IPeCreateService peCreateService = Graphiti.getPeCreateService();
		final IGaService gaService = Graphiti.getGaService();

		// Create the connection
		final FreeFormConnection connection = peCreateService.createFreeFormConnection(getDiagram());
		connection.setStart(addConContext.getSourceAnchor());
		connection.setEnd(addConContext.getTargetAnchor());

		// Create the line corresponding to the connection
		final Polyline polyline = gaService.createPolyline(connection);
		final ConnectionDecorator cd = peCreateService.createConnectionDecorator(connection, false, 1.0, true);
		// Draw the arrow on the target side of the connection
		final GraphicsAlgorithm arrow = createArrow(cd);

		// Setup styles
		polyline.setStyle(StyleUtil.connection(getDiagram()));
		arrow.setStyle(StyleUtil.connection(getDiagram()));

		// create link and wire it
		link(connection, addedConnection);
		return connection;
	}

	/**
	 * Create the arrow to display on the target side of the connection
	 * 
	 * @param gaContainer
	 * @return
	 */
	private GraphicsAlgorithm createArrow(final GraphicsAlgorithmContainer gaContainer) {
		final int w = 12, l = 5;
		final int[] xy = new int[] { 0, 0, -w, l, -w, -l };
		final Polygon figure = Graphiti.getGaService().createPolygon(gaContainer, xy);
		figure.setLineVisible(false);

		return figure;
	}

	/**
	 * Retrieve all information related to a port in a diagram. Build corresponding
	 * PortInformation structure.
	 * 
	 * @param anchor The anchor corresponding to a port in a Network or an Instance
	 * @return
	 */
	public PortInformation getPortInformations(final Anchor anchor) {
		if (anchor == null) {
			return null;
		}

		if (anchor instanceof FixPointAnchor) {
			// Instance port

			final FixPointAnchor brAnchor = (FixPointAnchor) anchor;
			final AstPort port = (AstPort) getBusinessObjectForPictogramElement(brAnchor);
			final PortKind kind = PropsUtil.isInstanceInPort(brAnchor) ? PortKind.INPUT : PortKind.OUTPUT;
			final AstEntityVariable parentInstance = (AstEntityVariable) getBusinessObjectForPictogramElement(brAnchor.getParent());

			return new PortInformation(parentInstance, port, kind, PortContainer.INSTANCE);

		} else if (anchor instanceof ChopboxAnchor) {
			// Network port

			final AnchorContainer container = anchor.getParent();
			final AstPort port = (AstPort) getBusinessObjectForPictogramElement(container);

			// Retrieve the pattern corresponding to the current port
			final IPattern ipattern = ((IFeatureProviderWithPatterns) getFeatureProvider())
					.getPatternForPictogramElement(anchor.getParent());

			// Check the type of this pattern to know the kind of the port
			final PortKind kind = ipattern instanceof InputNetworkPortPattern ? PortKind.INPUT : PortKind.OUTPUT;

			return new PortInformation(null, port, kind, PortContainer.NETWORK);
		}

		// Anchor without port ? Maybe an error here...
		return null;
	}
}
