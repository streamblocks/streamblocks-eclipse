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
package streamblocks.eclipse.cal.ui.contentassist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

import com.google.inject.Inject;

import streamblocks.eclipse.cal.cal.AstAbstractActor;
import streamblocks.eclipse.cal.cal.AstEntity;
import streamblocks.eclipse.cal.cal.AstEntityExpr;
import streamblocks.eclipse.cal.cal.AstEntityIfExpr;
import streamblocks.eclipse.cal.cal.AstEntityInstanceExpr;
import streamblocks.eclipse.cal.cal.AstEntityListExpr;
import streamblocks.eclipse.cal.cal.AstEntityVariable;
import streamblocks.eclipse.cal.cal.AstPort;
import streamblocks.eclipse.cal.cal.AstStructureStatementConnection;
import streamblocks.eclipse.cal.cal.CalPackage;

/**
 * See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#content-assist
 * on how to customize the content assistant.
 */
public class CalProposalProvider extends AbstractCalProposalProvider {

	@Inject
	ResourceDescriptionsProvider rdp;

	@Override
	public void completeAstNamespace_Imports(EObject model, Assignment assignment, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		IResourceDescriptions rd = rdp.getResourceDescriptions(model.eResource());
		for (IEObjectDescription d : rd.getExportedObjectsByType(CalPackage.eINSTANCE.getAstNamespace())) {
			acceptor.accept(createCompletionProposal(d.getQualifiedName().toString(), context));
		}
	}

	@Override
	public void completeAstStructureStatementConnection_InPort(EObject model, Assignment assignment,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		if (model instanceof AstStructureStatementConnection) {
			AstStructureStatementConnection connection = (AstStructureStatementConnection) model;
			AstEntityVariable actor = connection.getTo().getVariable();
			AstEntityExpr entity = actor.getEntity();
			completeAstStructureStatementConnection(true, entity, context).forEach(p -> acceptor.accept(p));
		}
	}

	@Override
	public void completeAstStructureStatementConnection_OutPort(EObject model, Assignment assignment,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		if (model instanceof AstStructureStatementConnection) {
			AstStructureStatementConnection connection = (AstStructureStatementConnection) model;
			AstEntityVariable actor = connection.getFrom().getVariable();
			AstEntityExpr entity = actor.getEntity();
			completeAstStructureStatementConnection(false, entity, context).forEach(p -> acceptor.accept(p));

		}
	}

	@Override
	public void completeAstExpressionSymbolReference_Ctor(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeRuleCall(((RuleCall)assignment.getTerminal()), context, acceptor);
	}
	
	private List<ICompletionProposal> completeAstStructureStatementConnection(Boolean isInput, AstEntityExpr entity,
			ContentAssistContext context) {
		List<ICompletionProposal> proposals = new ArrayList<>();

		if (entity instanceof AstEntityInstanceExpr) {
			AstEntityInstanceExpr instance = (AstEntityInstanceExpr) entity;
			AstEntity entityType = instance.getType();
			
			AstAbstractActor actor = entityType.getActor();
			if(actor == null) {
				return proposals;
			}
			
			List<AstPort> ports = isInput ? actor.getInputs() : actor.getOutputs();
			
			for (AstPort port : ports) {
				proposals.add(createCompletionProposal(port.getName(), context));
			}
		} else if (entity instanceof AstEntityListExpr) {
			AstEntityListExpr instance = (AstEntityListExpr) entity;
			for (AstEntityExpr e : instance.getExprs()) {
				proposals.addAll(completeAstStructureStatementConnection(isInput, e, context));
			}
		} else {
			AstEntityIfExpr instance = (AstEntityIfExpr) entity;
			proposals.addAll(completeAstStructureStatementConnection(isInput, instance.getTrueEntity(), context));
			proposals.addAll(completeAstStructureStatementConnection(isInput, instance.getFalseEntity(), context));
		}

		return proposals;
	}

}
