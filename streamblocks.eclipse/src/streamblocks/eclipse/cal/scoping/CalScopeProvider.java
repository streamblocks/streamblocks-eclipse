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
package streamblocks.eclipse.cal.scoping;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;

import streamblocks.eclipse.cal.cal.AstAbstractActor;
import streamblocks.eclipse.cal.cal.AstAction;
import streamblocks.eclipse.cal.cal.AstActor;
import streamblocks.eclipse.cal.cal.AstEntity;
import streamblocks.eclipse.cal.cal.AstEntityExpr;
import streamblocks.eclipse.cal.cal.AstEntityIfExpr;
import streamblocks.eclipse.cal.cal.AstEntityInstanceExpr;
import streamblocks.eclipse.cal.cal.AstEntityListExpr;
import streamblocks.eclipse.cal.cal.AstEntityVariable;
import streamblocks.eclipse.cal.cal.AstExpressionAlternative;
import streamblocks.eclipse.cal.cal.AstExpressionList;
import streamblocks.eclipse.cal.cal.AstForeachGenerator;
import streamblocks.eclipse.cal.cal.AstFunction;
import streamblocks.eclipse.cal.cal.AstGenerator;
import streamblocks.eclipse.cal.cal.AstInputPattern;
import streamblocks.eclipse.cal.cal.AstNamespace;
import streamblocks.eclipse.cal.cal.AstNetwork;
import streamblocks.eclipse.cal.cal.AstPattern;
import streamblocks.eclipse.cal.cal.AstPort;
import streamblocks.eclipse.cal.cal.AstProcedure;
import streamblocks.eclipse.cal.cal.AstStatementAlternative;
import streamblocks.eclipse.cal.cal.AstStatementBlock;
import streamblocks.eclipse.cal.cal.AstStatementForeach;
import streamblocks.eclipse.cal.cal.AstStatementRead;
import streamblocks.eclipse.cal.cal.AstStatementWrite;
import streamblocks.eclipse.cal.cal.AstStructureStatementConnection;
import streamblocks.eclipse.cal.cal.AstStructureStatementForeach;
import streamblocks.eclipse.cal.cal.AstTaggedTuple;
import streamblocks.eclipse.cal.cal.AstTypeDefinitionParameter;
import streamblocks.eclipse.cal.cal.AstTypeUser;
import streamblocks.eclipse.cal.cal.AstVariable;
import streamblocks.eclipse.cal.cal.FormalParameter;
import streamblocks.eclipse.cal.util.Util;

public class CalScopeProvider extends AbstractDeclarativeScopeProvider {

	public IScope scope_AstEntityVariableReference_variable(AstStructureStatementConnection structure,
			EReference reference) {
		AstNetwork network = EcoreUtil2.getContainerOfType(structure, AstNetwork.class);
		List<AstEntityVariable> instances = new ArrayList<AstEntityVariable>();
		for (AstEntityVariable decl : network.getEntities()) {
			instances.add(decl);
		}
		return Scopes.scopeFor(instances, getScope(network, reference));
	}

	public IScope scope_AstVariable(AstNamespace namespace, EReference reference) {
		List<AstVariable> elements = new ArrayList<AstVariable>();
		elements.addAll(namespace.getFunctions());
		elements.addAll(namespace.getProcedures());
		elements.addAll(namespace.getTypedefs());
		return Scopes.scopeFor(elements, delegateGetScope(namespace, reference));
	}

	public IScope scope_AstInputPattern_port(AstAction action, EReference reference) {
		AstActor actor = (AstActor) action.eContainer();
		return Scopes.scopeFor(actor.getInputs(), getScope(actor, reference));
	}

	public IScope scope_AstStatementRead_port(AstStatementRead read, EReference reference) {
		AstActor actor = EcoreUtil2.getContainerOfType(read, AstActor.class);
		return Scopes.scopeFor(actor.getInputs(), getScope(actor, reference));
	}

	public IScope scope_AstStructureStatementConnection_outPort(AstStructureStatementConnection connection,
			EReference reference) {
		AstNetwork network = EcoreUtil2.getContainerOfType(connection, AstNetwork.class);
		List<AstPort> elements = new ArrayList<AstPort>();
		elements.addAll(network.getInputs());

		if (connection.getFrom() != null) {
			AstEntityVariable actor = connection.getFrom().getVariable();
			AstEntityExpr entity = actor.getEntity();

			elements.addAll(getPorts(false, entity));
		}

		return Scopes.scopeFor(elements, getScope(network, reference));
	}

	public IScope scope_AstStructureStatementConnection_inPort(AstStructureStatementConnection connection,
			EReference reference) {
		AstNetwork network = EcoreUtil2.getContainerOfType(connection, AstNetwork.class);
		List<AstPort> elements = new ArrayList<AstPort>();
		elements.addAll(network.getOutputs());

		if (connection.getTo() != null) {
			AstEntityVariable actor = connection.getTo().getVariable();
			AstEntityExpr entity = actor.getEntity();

			elements.addAll(getPorts(true, entity));
		}
		return Scopes.scopeFor(elements, getScope(network, reference));
	}

	public IScope scope_AstStatementWrite_port(AstStatementWrite write, EReference reference) {
		AstActor actor = EcoreUtil2.getContainerOfType(write, AstActor.class);
		return Scopes.scopeFor(actor.getOutputs(), getScope(actor, reference));
	}

	public IScope scope_AstOutputExpression_port(AstAction action, EReference reference) {
		AstActor actor = (AstActor) action.eContainer();
		return Scopes.scopeFor(actor.getOutputs(), getScope(actor, reference));
	}

	public IScope scope_AstProcedure(AstActor actor, EReference reference) {
		return Scopes.scopeFor(actor.getProcedures(), delegateGetScope(actor, reference));
	}

	public IScope scope_AstVariable(AstAction action, EReference reference) {
		List<AstVariable> elements = new ArrayList<AstVariable>();
		for (AstInputPattern inputPattern : action.getInputs()) {
			for (AstPattern pattern : inputPattern.getTokens()) {
				Util.doPattern(elements, pattern);
			}
		}
		elements.addAll(action.getVariables());

		AstActor actor = (AstActor) action.eContainer();
		return Scopes.scopeFor(elements, getScope(actor, reference));
	}

	public IScope scope_AstVariable(AstActor actor, EReference reference) {
		List<AstVariable> elements = new ArrayList<AstVariable>();

		for (FormalParameter param : actor.getParameters()) {
			if (param.getVarParam() != null) {
				elements.add(param.getVarParam());
			}
		}
		elements.addAll(actor.getStateVariables());
		elements.addAll(actor.getFunctions());

		return Scopes.scopeFor(elements, delegateGetScope(actor, reference));
	}

	public IScope scope_AstVariable(AstEntityVariable decl, EReference reference) {
		AstNetwork network = (AstNetwork) decl.eContainer();
		List<AstVariable> elements = new ArrayList<AstVariable>();
		elements.addAll(network.getVariables());
		for (FormalParameter param : network.getParameters()) {
			if (param.getVarParam() != null) {
				elements.add(param.getVarParam());
			}
		}
		return Scopes.scopeFor(elements, getScope(network, reference));
	}

	public IScope scope_AstVariable(AstNetwork network, EReference reference) {
		List<AstVariable> elements = new ArrayList<AstVariable>();
		for (FormalParameter param : network.getParameters()) {
			if (param.getVarParam() != null) {
				elements.add(param.getVarParam());
			}
		}
		elements.addAll(network.getVariables());

		return Scopes.scopeFor(elements, delegateGetScope(network, reference));
	}

	public IScope scope_AstTypeUser(AstNetwork network, EReference reference) {
		List<AstTypeUser> elements = new ArrayList<AstTypeUser>();
		for (FormalParameter param : network.getParameters()) {
			if (param.getTypeParam() != null) {
				elements.add(param.getTypeParam());
			}
		}

		return Scopes.scopeFor(elements, delegateGetScope(network, reference));
	}

	public IScope scope_AstTypeUser(AstActor actor, EReference reference) {
		List<AstTypeUser> elements = new ArrayList<AstTypeUser>();
		for (FormalParameter param : actor.getParameters()) {
			if (param.getTypeParam() != null) {
				elements.add(param.getTypeParam());
			}
		}

		return Scopes.scopeFor(elements, delegateGetScope(actor, reference));
	}

	public IScope scope_AstVariable(AstExpressionList list, EReference reference) {
		List<AstVariable> elements = new ArrayList<AstVariable>();
		for (AstGenerator generator : list.getGenerators()) {
			elements.add(generator.getVariable());
		}
		EObject container = list.eContainer();
		return Scopes.scopeFor(elements, getScope(container, reference));
	}

	public IScope scope_AstVariable(AstFunction function, EReference reference) {
		List<AstVariable> elements = new ArrayList<AstVariable>();
		elements.addAll(function.getParameters());
		elements.addAll(function.getVariables());

		EObject cter = function.eContainer();
		return Scopes.scopeFor(elements, getScope(cter, reference));
	}

	public IScope scope_AstVariable(AstProcedure procedure, EReference reference) {
		List<AstVariable> elements = new ArrayList<AstVariable>();
		elements.addAll(procedure.getParameters());
		elements.addAll(procedure.getVariables());

		EObject cter = procedure.eContainer();
		return Scopes.scopeFor(elements, getScope(cter, reference));
	}

	public IScope scope_AstVariable(AstStatementForeach foreach, EReference reference) {
		List<AstVariable> variables = new ArrayList<AstVariable>();
		for (AstForeachGenerator g : foreach.getGenerators()) {
			variables.add(g.getVariable());
		}
		variables.addAll(foreach.getVariables());
		return Scopes.scopeFor(variables, getScope(foreach.eContainer(), reference));
	}

	public IScope scope_AstVariable(AstEntityListExpr entityList, EReference reference) {
		List<AstVariable> variables = new ArrayList<AstVariable>();
		if (!entityList.getGenerators().isEmpty()) {
			for (AstGenerator g : entityList.getGenerators()) {
				variables.add(g.getVariable());
			}
		}

		return Scopes.scopeFor(variables, getScope(entityList.eContainer(), reference));
	}

	public IScope scope_AstVariable(AstStructureStatementForeach foreach, EReference reference) {
		List<AstVariable> variables = new ArrayList<AstVariable>();
		for (AstForeachGenerator g : foreach.getGenerators()) {
			variables.add(g.getVariable());
		}
		variables.addAll(foreach.getVariables());
		return Scopes.scopeFor(variables, getScope(foreach.eContainer(), reference));
	}

	public IScope scope_AstVariable(AstStatementBlock block, EReference reference) {
		List<AstVariable> variables = new ArrayList<AstVariable>();
		variables.addAll(block.getVariables());

		return Scopes.scopeFor(variables, getScope(block.eContainer(), reference));
	}

	public IScope scope_AstTypeUser(AstTaggedTuple tuple, EReference reference) {
		List<AstTypeUser> typeParameters = new ArrayList<AstTypeUser>();
		AstTypeUser typedef = (AstTypeUser) tuple.eContainer();
		for (AstTypeDefinitionParameter param : typedef.getParameters()) {
			if (param.getType() != null)
				typeParameters.add(param.getType());
		}

		return Scopes.scopeFor(typeParameters, getScope(typedef, reference));
	}

	public IScope scope_AstVariable(AstTaggedTuple tuple, EReference reference) {
		List<AstVariable> variables = new ArrayList<AstVariable>();
		AstTypeUser typedef = (AstTypeUser) tuple.eContainer();
		for (AstTypeDefinitionParameter param : typedef.getParameters()) {
			if (param.getValue() != null)
				variables.add(param.getValue());
		}

		return Scopes.scopeFor(variables, getScope(typedef, reference));
	}

	public IScope scope_AstVariable(AstStatementAlternative alternative, EReference reference) {
		List<AstVariable> variables = new ArrayList<AstVariable>();
		Util.doPattern(variables, alternative.getPattern());

		return Scopes.scopeFor(variables, getScope(alternative.eContainer(), reference));
	}

	public IScope scope_AstVariable(AstExpressionAlternative alternative, EReference reference) {
		List<AstVariable> variables = new ArrayList<AstVariable>();
		Util.doPattern(variables, alternative.getPattern());

		return Scopes.scopeFor(variables, getScope(alternative.eContainer(), reference));
	}

	private List<AstPort> getPorts(Boolean isInput, AstEntityExpr entity) {
		List<AstPort> ports = new ArrayList<>();

		if (entity instanceof AstEntityInstanceExpr) {
			AstEntityInstanceExpr instance = (AstEntityInstanceExpr) entity;
			AstEntity entityType = instance.getType();

			AstAbstractActor actor = entityType.getActor();
			if (actor == null) {
				return ports;
			}

			ports.addAll(isInput ? actor.getInputs() : actor.getOutputs());

		} else if (entity instanceof AstEntityListExpr) {
			AstEntityListExpr instance = (AstEntityListExpr) entity;
			for (AstEntityExpr e : instance.getExpressions()) {
				ports.addAll(getPorts(isInput, e));
			}
		} else {
			AstEntityIfExpr instance = (AstEntityIfExpr) entity;
			ports.addAll(getPorts(isInput, instance.getTrueEntity()));
			ports.addAll(getPorts(isInput, instance.getFalseEntity()));
		}

		return ports;
	}

}
