/*
 * Copyright (c) 2013, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package streamblocks.eclipse.cal.ui.xdf.util;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.DeleteContext;
import org.eclipse.graphiti.features.context.impl.MultiDeleteInfo;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Font;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.IFeatureProviderWithPatterns;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.ILinkService;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.eclipse.graphiti.ui.services.IUiLayoutService;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import streamblocks.eclipse.cal.cal.AstNetwork;
import streamblocks.eclipse.cal.cal.AstPort;
import streamblocks.eclipse.cal.cal.AstStructureStatementConnection;
import streamblocks.eclipse.cal.cal.CalFactory;
import streamblocks.eclipse.cal.ui.Activator;
import streamblocks.eclipse.cal.ui.xdf.patterns.InputNetworkPortPattern;
import streamblocks.eclipse.cal.ui.xdf.patterns.InstancePattern;
import streamblocks.eclipse.cal.ui.xdf.patterns.OutputNetworkPortPattern;

/**
 * @author Antoine Lorence
 * @author Endri Bezati
 */
public class XdfUtil {
	private static ResourceSet resourceSet = new ResourceSetImpl();

	public static Shell getDefaultShell() {
		return PlatformUI.getWorkbench().getDisplay().getActiveShell();
	}

	public static IProject getProject(final EObject object) {
		String path = object.eResource().getURI().toPlatformString(true);
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path)).getProject();
	}

	/**
	 * Create a resource from the given URI and append a new Network instance to its
	 * contents. The default internal resourceSet is used.
	 * 
	 * @param uri
	 * @return
	 * @throws IOException
	 * @see {@link #createNetworkResource(ResourceSet, URI)}
	 */
	public static AstNetwork createNetworkResource(final URI uri) throws IOException {
		return createNetworkResource(resourceSet, uri);
	}

	/**
	 * Create a resource from the given URI and append a new Network instance to its
	 * contents. The resourceSet used must be authorized to write on the disk. This
	 * means that the default EditingDomain's resourceSet must be used in a write
	 * transaction (for example). If it is not possible, do not provide a
	 * resourceSet, the default one will be used.
	 * 
	 * @param resourceSet
	 * @param uri
	 * @return The created network
	 * @throws IOException
	 */
	public static AstNetwork createNetworkResource(final ResourceSet resourceSet, final URI uri) throws IOException {

		final String fileName;
		if (uri.isPlatform()) {
			fileName = uri.toPlatformString(true);
		} else {
			fileName = uri.toString();
		}

		// Create the network
		final AstNetwork network = CalFactory.eINSTANCE.createAstNetwork(); // DfFactory.eINSTANCE.createNetwork(fileName);

		// Compute the new network name
		final Path networkPath = new Path(uri.trimFileExtension().path());
		// 3 first segments are resource/<PROJECT>/src
		network.setName(networkPath.removeFirstSegments(3).toString().replace('/', '.'));

		// Create the resource
		Resource res = resourceSet.createResource(uri);
		res.getContents().add(network);
		res.save(Collections.EMPTY_MAP);

		return network;
	}

	/**
	 * Create a resource from the given URI and append a new Diagram instance to its
	 * contents. The default internal resourceSet is used.
	 * 
	 * @param uri
	 * @return
	 * @throws IOException
	 * @see {@link #createDiagramResource(ResourceSet, URI)}
	 */
	public static Diagram createDiagramResource(final URI uri) throws IOException {
		return createDiagramResource(resourceSet, uri);
	}

	/**
	 * Create a resource from the given URI and append a new Diagram instance to its
	 * contents. The resourceSet used must be authorized to write on the disk. This
	 * means that the default EditingDomain's resourceSet must be used in a write
	 * transaction (for example). If it is not possible, do not provide a
	 * resourceSet, the default one will be used.
	 * 
	 * @param resourceSet
	 * @param uri
	 * @return The Diagram created
	 * @throws IOException
	 */
	public static Diagram createDiagramResource(final ResourceSet resourceSet, final URI uri) throws IOException {
		// Compute the new diagram name
		final String name = uri.trimFileExtension().lastSegment();

		// Create the diagram
		final Diagram diagram = Graphiti.getPeCreateService().createDiagram(Activator.DIAGRAM_TYPE, name, true);

		// Create the resource
		Resource res = resourceSet.createResource(uri);
		res.getContents().add(diagram);
		res.setTrackingModification(true);
		res.save(Collections.EMPTY_MAP);

		return diagram;
	}

	/**
	 * Returns the minimal width needed to display the value of given Text with its
	 * associated Font.
	 * 
	 * @param text
	 * @return
	 */
	public static int getTextMinWidth(Text text) {
		final IUiLayoutService uiLayoutService = GraphitiUi.getUiLayoutService();
		final Font currentFont = Graphiti.getGaService().getFont(text, true);
		return uiLayoutService.calculateTextSize(text.getValue(), currentFont).getWidth();
	}

	/**
	 * Returns the minimal height needed to display the value of given Text with its
	 * associated Font.
	 * 
	 * @param text
	 * @return
	 */
	public static int getTextMinHeight(Text text) {
		final IUiLayoutService uiLayoutService = GraphitiUi.getUiLayoutService();
		final Font currentFont = Graphiti.getGaService().getFont(text, true);
		return uiLayoutService.calculateTextSize(text.getValue(), currentFont).getHeight();
	}

	/**
	 * Check if the given port is contained in a Network in its inputs.
	 * 
	 * @param port
	 * @return
	 */
	public static boolean isInputNetworkPort(final AstPort port) {
		if (port.eContainer() instanceof AstNetwork) {
			AstNetwork network = (AstNetwork) port.eContainer();
			return network.getInputs().contains(port);
		}

		return false;
	}

	/**
	 * Check if the given port is contained in a Network in its outputs.
	 * 
	 * @param port
	 * @return
	 */
	public static boolean isOutputNetworkPort(final AstPort port) {
		if (port.eContainer() instanceof AstNetwork) {
			AstNetwork network = (AstNetwork) port.eContainer();
			return network.getOutputs().contains(port);
		}

		return false;
	}

	/**
	 * <p>
	 * Initialize a ready to use AddConnectionContext object from a network
	 * connection. This method does not add the given connection to the network.
	 * </p>
	 * 
	 * <p>
	 * The returned IAddConnectionContext is ready to use. The business object has
	 * been set to the given connection and the target container has been set to the
	 * given diagram.
	 * </p>
	 * 
	 * @param fp         The IFeatureProviderWithPatterns instance
	 * @param diagram    The diagram where the connection will be added
	 * @param connection The connection
	 * @return
	 */
	public static IAddConnectionContext getAddConnectionContext(final IFeatureProviderWithPatterns fp,
			final Diagram diagram, final AstStructureStatementConnection connection) {

		final ILinkService linkServ = Graphiti.getLinkService();

		// retrieve the source and target PictogramElements
		final List<PictogramElement> sourcesPE = linkServ.getPictogramElements(diagram, connection.getFrom().getVariable());
		if (sourcesPE == null || sourcesPE.isEmpty()) {
			/*
			OrccLogger.warnln("[getAddConnectionContext] Unable to "
					+ "retrieve the PictogramElement corresponding to the source " + connection.getSource() + ".");*/
			return null;
		}
		final List<PictogramElement> targetsPE = linkServ.getPictogramElements(diagram, connection.getTo().getVariable());
		if (targetsPE == null || targetsPE.isEmpty()) {
			/*
			OrccLogger.warnln("[getAddConnectionContext] Unable to "
					+ "retrieve the PictogramElement corresponding to the target " + connection.getTarget() + ".");*/
			return null;
		}

		// source/target PictogramElement
		final PictogramElement sourcePe = sourcesPE.get(0);
		final PictogramElement targetPe = targetsPE.get(0);

		final Anchor sourceAnchor, targetAnchor;
		if (PropsUtil.isInputPort(sourcePe)) {
			// Connection from a network port
			final InputNetworkPortPattern spattern = (InputNetworkPortPattern) fp
					.getPatternForPictogramElement(sourcePe);
			sourceAnchor = spattern.getAnchor((AnchorContainer) sourcePe);
		} else {
			// Connection from an instance port
			final InstancePattern spattern = (InstancePattern) fp.getPatternForPictogramElement(sourcePe);
			sourceAnchor = spattern.getAnchorForPort(sourcePe, connection.getOutPort());
		}

		if (PropsUtil.isOutputPort(targetPe)) {
			// Connection to a network port
			final OutputNetworkPortPattern tpattern = (OutputNetworkPortPattern) fp
					.getPatternForPictogramElement(targetPe);
			targetAnchor = tpattern.getAnchor((AnchorContainer) targetPe);
		} else {
			// Connection to an instance port
			final InstancePattern tpattern = (InstancePattern) fp.getPatternForPictogramElement(targetPe);
			
			targetAnchor = tpattern.getAnchorForPort(targetPe, connection.getInPort());
		}

		final AddConnectionContext result = new AddConnectionContext(sourceAnchor, targetAnchor);
		result.setTargetContainer(diagram);
		result.setNewObject(connection);
		return result;
	}

	/**
	 * If the given AnchorContainer has graphiti connections from / to itself, this
	 * method delete all these connections as well the business objects
	 * (net.sf.orcc.df.Connection) linked
	 * 
	 * @param fp The current FeatureProvider instance
	 * @param ac An anchor container, typically an Instance or a NetworkPort shape
	 */
	public static void deleteConnections(final IFeatureProvider fp, final AnchorContainer ac) {
		// Make a copy of all connections, because we will delete them entirely
		final List<org.eclipse.graphiti.mm.pictograms.Connection> connections = Graphiti.getPeService()
				.getAllConnections(ac);
		for (final org.eclipse.graphiti.mm.pictograms.Connection connection : connections) {
			deleteConnection(fp, connection);
		}
	}

	/**
	 * Delete the given connection as well its net.sf.orcc.df.Connection linked (its
	 * business object)
	 * 
	 * @param fp The current FeatureProvider instance
	 * @param c  A connection pictogram (FreeFormConnection or any other class
	 *           derived from Connection)
	 */
	public static boolean deleteConnection(final IFeatureProvider fp,
			final org.eclipse.graphiti.mm.pictograms.Connection c) {
		// Initialize the delete context
		final DeleteContext delCtxt = new DeleteContext(c);
		delCtxt.setMultiDeleteInfo(new MultiDeleteInfo(false, false, 1));

		// Silently execute deletion (user will not be asked before deletion)
		final IDeleteFeature delFeature = fp.getDeleteFeature(delCtxt);
		if (delFeature.canDelete(delCtxt)) {
			delFeature.delete(delCtxt);
			return true;
		}
		return false;
	}

	/**
	 * Check if the given network contains a vertex (instance or port) with the
	 * given base name. If yes, return a new unique name formed from the given base
	 * and a numeric suffix. If not, returns the given base.
	 * 
	 * @param network The network to check for existing vertex with the given name
	 * @param base    The base name to assign to a new vertex
	 * @return A unique name to assign to a new Instance / Port in the given network
	 */
	public static String uniqueVertexName(final AstNetwork network, final String base) {
		final Set<String> existingNames = new HashSet<String>();

		network.getInputs().forEach(i -> existingNames.add(i.getName()));
		network.getEntities().forEach(e -> existingNames.add(e.getName()));
		network.getOutputs().forEach(o -> existingNames.add(o.getName()));

		if (!existingNames.contains(base)) {
			return base;
		} else {
			int index = 0;
			while (existingNames.contains(base + '_' + index)) {
				++index;
			}
			return base + '_' + index;
		}
	}
}
