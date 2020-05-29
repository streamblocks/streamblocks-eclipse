/*
 * ----------------------------------------------------------------------------
 *  ____  _                            ____  _            _
 * / ___|| |_ _ __ ___  __ _ _ __ ___ | __ )| | ___   ___| | _____
 * \___ \| __| '__/ _ \/ _` | '_ ` _ \|  _ \| |/ _ \ / __| |/ / __|
 *  ___) | |_| | |  __/ (_| | | | | | | |_) | | (_) | (__|   <\__ \
 * |____/ \__|_|  \___|\__,_|_| |_| |_|____/|_|\___/ \___|_|\_\___/
 * ----------------------------------------------------------------------------
 * Copyright (c) 2020, Streamgenomics sarl
 * All rights reserved.
 * ----------------------------------------------------------------------------
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the Streamgenomics sarl nor the names of its
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
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package streamblocks.eclipse.cal.ui.outline;

import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider;

import com.google.inject.Inject;

import streamblocks.eclipse.cal.cal.AstAbstractActor;
import streamblocks.eclipse.cal.cal.AstAction;
import streamblocks.eclipse.cal.cal.AstActor;
import streamblocks.eclipse.cal.cal.AstEntity;
import streamblocks.eclipse.cal.cal.AstEntityVariable;
import streamblocks.eclipse.cal.cal.AstExternalActor;
import streamblocks.eclipse.cal.cal.AstFunction;
import streamblocks.eclipse.cal.cal.AstInequality;
import streamblocks.eclipse.cal.cal.AstNamespace;
import streamblocks.eclipse.cal.cal.AstNetwork;
import streamblocks.eclipse.cal.cal.AstPort;
import streamblocks.eclipse.cal.cal.AstPriority;
import streamblocks.eclipse.cal.cal.AstProcedure;
import streamblocks.eclipse.cal.cal.AstSchedule;
import streamblocks.eclipse.cal.cal.AstTransition;
import streamblocks.eclipse.cal.cal.AstVariable;
import streamblocks.eclipse.cal.cal.CalPackage;

/**
 * Customization of the default outline structure.
 *
 * See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#outline
 */
public class CalOutlineTreeProvider extends DefaultOutlineTreeProvider {

	@Inject
	private IImageHelper imageHelper;

	protected void _createNode(IOutlineNode parentNode, AstNamespace namespace) {

		if (!namespace.getImports().isEmpty()) {
			createEStructuralFeatureNode(parentNode, namespace, CalPackage.eINSTANCE.getAstNamespace_Imports(),
					(Image) null, "imports", false);
		}

		if (!namespace.getNamespaces().isEmpty()) {
			createEStructuralFeatureNode(parentNode, namespace, CalPackage.eINSTANCE.getAstNamespace_Namespaces(),
					(Image) null, "namespaces", false);
		}

		if (!namespace.getEntities().isEmpty()) {
			for (AstEntity entity : namespace.getEntities()) {
				createEStructuralFeatureNode(parentNode, entity, CalPackage.eINSTANCE.getAstEntity_Actor(),
						(Image) null, entity.getActor().getName(), false);
			}
		}

		if (!namespace.getFunctions().isEmpty()) {
			createEStructuralFeatureNode(parentNode, namespace, CalPackage.eINSTANCE.getAstNamespace_Functions(),
					(Image) null, "functions", false);
		}

		if (!namespace.getTypedefs().isEmpty()) {
			createEStructuralFeatureNode(parentNode, namespace, CalPackage.eINSTANCE.getAstNamespace_Typedefs(),
					(Image) null, "types", false);
		}

		if (!namespace.getVariables().isEmpty()) {
			createEStructuralFeatureNode(parentNode, namespace, CalPackage.eINSTANCE.getAstNamespace_Variables(),
					(Image) null, "constants", false);
		}

		if (!namespace.getExternals().isEmpty()) {
			createEStructuralFeatureNode(parentNode, namespace, CalPackage.eINSTANCE.getAstNamespace_Externals(),
					(Image) null, "external declarations", false);
		}

	}

	protected void _createNode(IOutlineNode parentNode, AstActor actor) {
		if (!actor.getParameters().isEmpty()) {
			createEStructuralFeatureNode(parentNode, actor, CalPackage.eINSTANCE.getAstAbstractActor_Parameters(),
					(Image) null, "parameters", false);
		}

		if (!actor.getInputs().isEmpty()) {
			createEStructuralFeatureNode(parentNode, actor, CalPackage.eINSTANCE.getAstAbstractActor_Inputs(),
					(Image) null, "input ports", false);
		}

		if (!actor.getOutputs().isEmpty()) {
			createEStructuralFeatureNode(parentNode, actor, CalPackage.eINSTANCE.getAstAbstractActor_Outputs(),
					(Image) null, "output ports", false);
		}

		if (!actor.getStateVariables().isEmpty()) {
			createEStructuralFeatureNode(parentNode, actor, CalPackage.eINSTANCE.getAstActor_StateVariables(),
					(Image) null, "state variables", false);
		}

		if (!actor.getFunctions().isEmpty()) {
			createEStructuralFeatureNode(parentNode, actor, CalPackage.eINSTANCE.getAstActor_Functions(), (Image) null,
					"functions", false);
		}

		if (!actor.getProcedures().isEmpty()) {
			createEStructuralFeatureNode(parentNode, actor, CalPackage.eINSTANCE.getAstActor_Procedures(), (Image) null,
					"procedures", false);
		}

		if (!actor.getActions().isEmpty()) {
			createEStructuralFeatureNode(parentNode, actor, CalPackage.eINSTANCE.getAstActor_Actions(), (Image) null,
					"actions", false);
		}

		if (actor.getSchedules() != null) {
			createEStructuralFeatureNode(parentNode, actor, CalPackage.eINSTANCE.getAstActor_Schedules(), (Image) null,
					"schedule", false);
		}

		if (!actor.getPriorities().isEmpty()) {
			createEStructuralFeatureNode(parentNode, actor, CalPackage.eINSTANCE.getAstActor_Priorities(), (Image) null,
					"priorities", false);
		}
	}

	protected void _createNode(IOutlineNode parentNode, AstEntity entity) {

		AstAbstractActor actor = entity.getActor();
		String kind = "";
		Image image = null;
		if (actor instanceof AstActor) {
			image = _image(actor);
			kind = " [actor]";
		} else if (actor instanceof AstNetwork) {
			kind = " [network]";
		} else if (actor instanceof AstExternalActor) {
			kind = " [external]";
		}
		createEStructuralFeatureNode(parentNode, entity, CalPackage.eINSTANCE.getAstEntity_Actor(), image,
				actor.getName() + kind, false);
	}

	protected void _createNode(IOutlineNode parentNode, AstNetwork network) {
		if (!network.getVariables().isEmpty()) {
			createEStructuralFeatureNode(parentNode, network, CalPackage.eINSTANCE.getAstNetwork_Variables(),
					(Image) null, "variables", false);
		}

		if (!network.getEntities().isEmpty()) {
			createEStructuralFeatureNode(parentNode, network, CalPackage.eINSTANCE.getAstNetwork_Entities(),
					(Image) null, "entities", false);
		}
	}

	protected void _createNode(IOutlineNode parentNode, AstPriority priority) {
		for (AstInequality inequality : priority.getInequalities()) {
			createNode(parentNode, inequality);
		}
	}

	protected void _createNode(IOutlineNode parentNode, AstSchedule schedule) {
		for (AstTransition transition : schedule.getTransitions()) {
			createNode(parentNode, transition);
		}
	}

	protected boolean _isLeaf(AstAction action) {
		return true;
	}

	protected boolean _isLeaf(AstPort port) {
		return true;
	}

	protected boolean _isLeaf(AstProcedure procedure) {
		return true;
	}

	protected boolean _isLeaf(AstTransition transition) {
		return true;
	}

	protected boolean _isLeaf(AstFunction function) {
		return true;
	}

	protected boolean _isLeaf(AstVariable variable) {
		return true;
	}
	
	protected boolean _isLeaf(AstEntityVariable entityVariable) {
		return true;
	}

}
