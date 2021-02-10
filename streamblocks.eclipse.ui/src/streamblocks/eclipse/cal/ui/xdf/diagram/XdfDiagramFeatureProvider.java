package streamblocks.eclipse.cal.ui.xdf.diagram;

import org.eclipse.core.resources.IFile;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICopyFeature;
import org.eclipse.graphiti.features.IFeature;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICopyContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.impl.CreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.ReconnectionContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.features.impl.DefaultReconnectionFeature;
import org.eclipse.graphiti.features.impl.DefaultRemoveFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.pattern.DefaultFeatureProviderWithPatterns;

import de.cau.cs.kieler.kiml.klayoutdata.KShapeLayout;
import de.cau.cs.kieler.kiml.options.EdgeRouting;
import de.cau.cs.kieler.kiml.options.LayoutOptions;
import streamblocks.eclipse.cal.cal.AstEntityVariable;
import streamblocks.eclipse.cal.cal.AstEntityVariableReference;
import streamblocks.eclipse.cal.cal.AstPort;
import streamblocks.eclipse.cal.cal.AstStructureStatementConnection;
import streamblocks.eclipse.cal.cal.CalFactory;
import streamblocks.eclipse.cal.ui.xdf.features.CopyFeature;
import streamblocks.eclipse.cal.ui.xdf.features.DropInstanceFromFileFeature;
import streamblocks.eclipse.cal.ui.xdf.layout.AutoLayoutFeature;
import streamblocks.eclipse.cal.ui.xdf.patterns.ConnectionPattern;
import streamblocks.eclipse.cal.ui.xdf.patterns.InputNetworkPortPattern;
import streamblocks.eclipse.cal.ui.xdf.patterns.InstancePattern;
import streamblocks.eclipse.cal.ui.xdf.patterns.OutputNetworkPortPattern;
import streamblocks.eclipse.cal.ui.xdf.util.PropsUtil;
import streamblocks.eclipse.cal.util.StreamBlocksLogger;

/**
 * This is a default class, from a new Graphiti project. It should be modified
 * to fit our needs.
 * 
 * @author Antoine Lorence
 * 
 */
public class XdfDiagramFeatureProvider extends DefaultFeatureProviderWithPatterns {

	// private final PasteFeature pasteFeature;
	private final CopyFeature copyFeature;
	// private final UpdateDiagramFeature updateFeature;
	private final DropInstanceFromFileFeature dropInstanceFeature;
	private final ICustomFeature[] layoutFeatures;

	public XdfDiagramFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);
		addPattern(new InstancePattern());
		addPattern(new InputNetworkPortPattern());
		addPattern(new OutputNetworkPortPattern());
		addConnectionPattern(new ConnectionPattern());

		copyFeature = new CopyFeature(this);
		// pasteFeature = new PasteFeature(this);
		// updateFeature = new UpdateDiagramFeature(this);
		dropInstanceFeature = new DropInstanceFromFileFeature(this);

		final ICustomFeature orthogonal = new AutoLayoutFeature(this, "Orthogonal routing (default)") {
			@Override
			protected void configureDiagramNode(KShapeLayout diagramLayout) {
				super.configureDiagramNode(diagramLayout);

				diagramLayout.setProperty(LayoutOptions.EDGE_ROUTING, EdgeRouting.ORTHOGONAL);
			}
		};

		final ICustomFeature polyline = new AutoLayoutFeature(this, "Polyline routing") {
			@Override
			protected void configureDiagramNode(KShapeLayout diagramLayout) {
				super.configureDiagramNode(diagramLayout);

				diagramLayout.setProperty(LayoutOptions.EDGE_ROUTING, EdgeRouting.POLYLINE);
			}
		};

		// The first entry is the default
		layoutFeatures = new ICustomFeature[] { orthogonal, polyline };
	}

	/**
	 * Return the default layout feature. It is the first in the layout features
	 * array
	 * 
	 * @return
	 */
	public ICustomFeature getDefaultLayoutFeature() {
		return layoutFeatures[0];
	}

	/**
	 * Return the list of available layout features.
	 * 
	 * @return An array of ICustomFeatures
	 */
	public ICustomFeature[] getLayoutFeatures() {
		return layoutFeatures;
	}

	@Override
	public ICustomFeature[] getCustomFeatures(ICustomContext context) {
		// Custom features are created in XdfDiagramToolBehaviorProvider. This allows to
		// arrange
		// these features in sub-menus
		return super.getCustomFeatures(context);
	}

	/*
	 * @Override protected IUpdateFeature getUpdateFeatureAdditional(IUpdateContext
	 * context) { return updateFeature; }
	 */
	/**
	 * Implements creation of new connection by dragging instance port.
	 */
	@Override
	public IFeature[] getDragAndDropFeatures(IPictogramElementContext context) {
		return getCreateConnectionFeatures();
	}

	@Override
	public IAddFeature getAddFeature(IAddContext context) {
		if (context.getNewObject() instanceof IFile) {
			return dropInstanceFeature;
		}
		return super.getAddFeature(context);
	}

	@Override
	public ICopyFeature getCopyFeature(ICopyContext context) {
		return copyFeature;
	}

	/*
	 * @Override public IPasteFeature getPasteFeature(IPasteContext context) {
	 * return pasteFeature; }
	 */
	/**
	 * We never want to remove elements from the diagram. We always want to delete
	 * them. So it is useless to display the menu entry, the button in the
	 * contextual palette. This feature is globally disabled
	 */
	@Override
	public IRemoveFeature getRemoveFeature(IRemoveContext context) {
		return new DefaultRemoveFeature(this) {
			@Override
			public boolean isAvailable(IContext context) {
				return false;
			}
		};
	}

	/**
	 * We want to allow reconnection, even if an existing one is in a bad state. If
	 * the source or target of a connection is a null or non-existing object, we
	 * don't care this is this side the user wants to reconnect.
	 * 
	 * This method also prevent bad connection by reusing logic from
	 * ConnectionPattern.canXXX methods
	 */
	@Override
	public IReconnectionFeature getReconnectionFeature(IReconnectionContext context) {
		return new DefaultReconnectionFeature(this) {
			@Override
			public boolean canReconnect(IReconnectionContext context) {
				final Anchor newAnchor = getNewAnchor(context);
				final Connection connection = context.getConnection();

				// Classical checks. Same as original method, without checking
				// null value of start/end in the original connection
				if ((connection == null) || (newAnchor == null) || (newAnchor.getParent() instanceof Diagram)) {
					return false;
				}

				// Delegate to ConnectionPattern to decide if a reconnection can
				// be performed or not
				final ConnectionPattern conPattern = (ConnectionPattern) getConnectionPatterns().get(0);
				final CreateConnectionContext ctxt = new CreateConnectionContext();
				if (context.getReconnectType().equals(ReconnectionContext.RECONNECT_TARGET)) {
					ctxt.setSourceAnchor(connection.getStart());
					ctxt.setTargetAnchor(newAnchor);
					return conPattern.canCreate(ctxt);
				} else {
					ctxt.setSourceAnchor(newAnchor);
					ctxt.setTargetAnchor(connection.getEnd());
					return conPattern.canStartConnection(ctxt);
				}
			}

			@Override
			public void postReconnect(IReconnectionContext context) {

				// On reconnection, we have to update the business model object
				// source or target
				final AstStructureStatementConnection dfConnection = (AstStructureStatementConnection) getBusinessObjectForPictogramElement(
						context.getConnection());
				final Anchor newAnchor = context.getNewAnchor();
				final AstEntityVariable vertex = (AstEntityVariable) getBusinessObjectForPictogramElement(
						newAnchor.getParent());

				if (context.getReconnectType().equals(ReconnectionContext.RECONNECT_SOURCE)) {

					if (PropsUtil.isInputPort(newAnchor.getParent())) {
						final AstEntityVariableReference ref = CalFactory.eINSTANCE.createAstEntityVariableReference();
						ref.setVariable(vertex);
						dfConnection.setFrom(ref);
						dfConnection.setOutPort("");

					} else if (PropsUtil.isInstanceOutPort(newAnchor)) {
						final AstEntityVariableReference ref = CalFactory.eINSTANCE.createAstEntityVariableReference();
						ref.setVariable(vertex);
						dfConnection.setFrom(ref);
						final AstPort port = (AstPort) getBusinessObjectForPictogramElement(newAnchor);
						dfConnection.setOutPort(port.getName());
					} else {
						StreamBlocksLogger.severeln("Unable to get the new source type.");
					}
				} else {
					if (PropsUtil.isOutputPort(newAnchor.getParent())) {
						final AstEntityVariableReference ref = CalFactory.eINSTANCE.createAstEntityVariableReference();
						ref.setVariable(vertex);
						dfConnection.setTo(ref);
						dfConnection.setInPort("");

					} else if (PropsUtil.isInstanceInPort(newAnchor)) {
						final AstEntityVariableReference ref = CalFactory.eINSTANCE.createAstEntityVariableReference();
						ref.setVariable(vertex);
						dfConnection.setTo(ref);
						final AstPort port = (AstPort) getBusinessObjectForPictogramElement(newAnchor);
						dfConnection.setInPort(port.getName());
					} else {
						StreamBlocksLogger.severeln("Unable to get the new target type.");
					}
				}
			}
		};
	}
}
