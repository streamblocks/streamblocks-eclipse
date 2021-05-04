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
package streamblocks.eclipse.cal.ui.highlighting;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.nodemodel.BidiTreeIterable;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;

import streamblocks.eclipse.cal.cal.AstActor;
import streamblocks.eclipse.cal.cal.AstEntity;
import streamblocks.eclipse.cal.cal.AstEntityVariable;
import streamblocks.eclipse.cal.cal.AstExternalActor;
import streamblocks.eclipse.cal.cal.AstFunction;
import streamblocks.eclipse.cal.cal.AstNamespace;
import streamblocks.eclipse.cal.cal.AstNetwork;
import streamblocks.eclipse.cal.cal.AstTypeUser;
import streamblocks.eclipse.cal.cal.AstVariable;
import streamblocks.eclipse.cal.services.CalGrammarAccess;

public class CalSemanticHighlightingCalculator extends DefaultSemanticHighlightingCalculator {

	@Inject
	CalGrammarAccess ga;

	@Inject
	private EObjectAtOffsetHelper helper;

	@Override
	protected void searchAndHighlightElements(XtextResource resource, IHighlightedPositionAcceptor acceptor,
			CancelIndicator cancelIndicator) {
		IParseResult parseResult = resource.getParseResult();
		if (parseResult == null)
			throw new IllegalStateException("resource#parseResult may not be null");
		BidiTreeIterable<INode> tree = parseResult.getRootNode().getAsTreeIterable();
		for (INode node : tree) {
			if (cancelIndicator.isCanceled()) {
				return;
			}
			if (node.getGrammarElement() instanceof CrossReference) {
				if (ga.getAstLValueAccess().getTargetAstVariableCrossReference_0_0() == node.getGrammarElement()) {
					EObject target = helper.resolveElementAt(resource, node.getOffset());
					variableSemanticHighlighting(target, node, acceptor);
				} else if (ga.getAstExpressionSymbolReferenceAccess().getSymbolAstVariableCrossReference_1_0() == node
						.getGrammarElement()) {
					EObject target = helper.resolveElementAt(resource, node.getOffset());
					variableSemanticHighlighting(target, node, acceptor);
				} else if (ga.getAstEntityInstanceExprAccess().getTypeAstEntityCrossReference_0_0() == node
						.getGrammarElement()) {
					EObject target = helper.resolveElementAt(resource, node.getOffset());
					if (target instanceof AstEntity) {
						AstEntity entity = (AstEntity) target;
						if (entity.getActor() instanceof AstActor) {
							acceptor.addPosition(node.getOffset(), node.getLength(),
									CalHighlightingConfiguration.ACTOR_DECLARATION_ID);
						} else if (entity.getActor() instanceof AstNetwork) {
							acceptor.addPosition(node.getOffset(), node.getLength(),
									CalHighlightingConfiguration.NETWORK_DECLARATION_ID);
						} else {
							acceptor.addPosition(node.getOffset(), node.getLength(),
									CalHighlightingConfiguration.ACTOR_DECLARATION_ID);
						}
					}
				} else if (ga.getAstEntityVariableReferenceAccess()
						.getVariableAstEntityVariableCrossReference_0() == node.getGrammarElement()) {
					EObject target = helper.resolveElementAt(resource, node.getOffset());
					if (target instanceof AstEntityVariable) {
						acceptor.addPosition(node.getOffset(), node.getLength(),
								CalHighlightingConfiguration.ENTITY_DECLARATION_ID);
					}
				} else if (ga.getAstInputPatternAccess().getPortAstPortCrossReference_0_0_0() == node
						.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.PORT_ID);
				} else if (ga.getAstOutputExpressionAccess().getPortAstPortCrossReference_0_0_0() == node
						.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.PORT_ID);
				} else if (ga.getAstStatementReadAccess().getPortAstPortCrossReference_0_0() == node
						.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.PORT_ID);
				} else if (ga.getAstStatementWriteAccess().getPortAstPortCrossReference_0_0() == node
						.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.PORT_ID);
				} else if (ga.getAstStatementCallAccess().getProcedureAstProcedureCrossReference_1_0() == node
						.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.CALLABLE_ID);
				} else if (ga.getAstTypeAccess().getNameAstTypeUserCrossReference_1_1_0_0() == node
						.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.CALLABLE_ID);
				} else if (ga.getAstTransitionAccess().getSourceAstStateCrossReference_0_0() == node
						.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.FSM_STATE_ID);
				} else if (ga.getAstTransitionAccess().getTargetAstStateCrossReference_6_0() == node
						.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.FSM_STATE_ID);
				} else if (ga.getAstScheduleAccess().getInitialStateAstStateCrossReference_2_0() == node
						.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.FSM_STATE_ID);
				}
			} else if (node.getGrammarElement() instanceof RuleCall) {
				if (ga.getAstActorAccess().getNameIDTerminalRuleCall_2_0() == node.getGrammarElement()) {
					EObject target = helper.resolveElementAt(resource, node.getOffset());
					if (target instanceof AstActor) {
						acceptor.addPosition(node.getOffset(), node.getLength(),
								CalHighlightingConfiguration.ACTOR_DECLARATION_ID);
					}
				} else if (ga.getAstNetworkAccess().getNameIDTerminalRuleCall_2_0() == node.getGrammarElement()) {
					EObject target = helper.resolveElementAt(resource, node.getOffset());
					if (target instanceof AstNetwork) {
						acceptor.addPosition(node.getOffset(), node.getLength(),
								CalHighlightingConfiguration.NETWORK_DECLARATION_ID);
					}
				} else if (ga.getAstExternalActorAccess().getNameIDTerminalRuleCall_3_0() == node.getGrammarElement()) {
					EObject target = helper.resolveElementAt(resource, node.getOffset());
					if (target instanceof AstExternalActor) {
						acceptor.addPosition(node.getOffset(), node.getLength(),
								CalHighlightingConfiguration.ACTOR_DECLARATION_ID);
					}
				} else if (ga.getAstEntityDeclarationAccess().getNameIDTerminalRuleCall_0_0() == node
						.getGrammarElement()) {
					EObject target = helper.resolveElementAt(resource, node.getOffset());
					if (target instanceof AstEntityVariable) {
						acceptor.addPosition(node.getOffset(), node.getLength(),
								CalHighlightingConfiguration.ENTITY_DECLARATION_ID);
					}
				} else if (ga.getAstVariableDeclarationAccess().getNameIDTerminalRuleCall_2_0() == node
						.getGrammarElement()) {
					EObject target = helper.resolveElementAt(resource, node.getOffset());
					variableSemanticHighlighting(target, node, acceptor);
				} else if (ga.getAstPatternAccess().getAstPatternDeclarationParserRuleCall_4() == node
						.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(),
							CalHighlightingConfiguration.LOCAL_VARIABLE_REFERNCE_ID);
				} else if (ga.getAstPortAccess().getNameIDTerminalRuleCall_2_0() == node.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.PORT_ID);
				} else if (ga.getAstTagAccess().getIdentifiersIDTerminalRuleCall_0_0() == node.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(),
							CalHighlightingConfiguration.ACTION_TAG_ID);
				} else if (ga.getAstTagAccess().getIdentifiersIDTerminalRuleCall_1_1_0() == node.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(),
							CalHighlightingConfiguration.ACTION_TAG_ID);
				//} else if (ga.getAstStructureStatementConnectionAccess().getOutPortIDTerminalRuleCall_1_0() == node
				//		.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.PORT_ID);
				//} else if (ga.getAstStructureStatementConnectionAccess().getInPortIDTerminalRuleCall_4_0() == node
				//		.getGrammarElement()) {
				//	acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.PORT_ID);
				} else if (ga.getAstTypeParamAccess().getNameIDTerminalRuleCall_0_0_0() == node.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(),
							CalHighlightingConfiguration.TYPE_PARAMETER_ID);
				} else if (ga.getAstFunctionAccess().getNameIDTerminalRuleCall_3_0() == node.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.CALLABLE_ID);
				} else if (ga.getAstProcedureAccess().getNameIDTerminalRuleCall_3_0() == node.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.CALLABLE_ID);
				} else if (ga.getAstExternalFunctionAccess().getNameIDTerminalRuleCall_4_0() == node
						.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.CALLABLE_ID);
				} else if (ga.getAstExternalProcedureAccess().getNameIDTerminalRuleCall_4_0() == node
						.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.CALLABLE_ID);
				} else if (ga.getAstTypeDefinitionAccess().getNameIDTerminalRuleCall_1_0() == node
						.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.TYPE_ID);
				} else if (ga.getAstTaggedTupleAccess().getNameIDTerminalRuleCall_0_0() == node.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(),
							CalHighlightingConfiguration.LOCAL_VARIABLE_REFERNCE_ID);
				} else if (ga.getAstExpressionSymbolReferenceAccess().getCtorIDTerminalRuleCall_4_1_0() == node
						.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(),
							CalHighlightingConfiguration.LOCAL_VARIABLE_REFERNCE_ID);
				} else if (ga.getAstPatternDeconstructionAccess().getNameIDTerminalRuleCall_0_0() == node
						.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(),
							CalHighlightingConfiguration.LOCAL_VARIABLE_REFERNCE_ID);
				} else if (ga.getAstPatternAliasAccess().getAliasAstPatternDeclarationParserRuleCall_0_0_0() == node
						.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(),
							CalHighlightingConfiguration.LOCAL_VARIABLE_REFERNCE_ID);
				//} else if (ga.getAstAssignParameterAccess().getVarAssignment_0().  getNameIDTerminalRuleCall_0_0() == node
				//		.getGrammarElement()) {
				//	acceptor.addPosition(node.getOffset(), node.getLength(),
				//			CalHighlightingConfiguration.GLOBAL_VARIABLE_REFERENCE_ID);
				} else if (ga.getAstAnnotationAccess().getNameIDTerminalRuleCall_1_0() == node.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(),
							CalHighlightingConfiguration.ANNOTATIONS_ID);
				}
			} else if (node.getGrammarElement() instanceof Keyword) {
				if (ga.getAstAnnotationAccess().getCommercialAtKeyword_0() == node.getGrammarElement()) {
					acceptor.addPosition(node.getOffset(), node.getLength(),
							CalHighlightingConfiguration.ANNOTATIONS_ID);
				}
			}
		}

	}

	void variableSemanticHighlighting(EObject target, INode node, IHighlightedPositionAcceptor acceptor) {
		AstVariable variable = (AstVariable) target;
		if (variable instanceof AstFunction) {
			acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.CALLABLE_ID);
		} else if (variable instanceof AstTypeUser) {
			acceptor.addPosition(node.getOffset(), node.getLength(), CalHighlightingConfiguration.TYPE_ID);
		} else {
			if (variable.eContainer() instanceof AstActor || variable.eContainer() instanceof AstNetwork
					|| variable.eContainer() instanceof AstExternalActor) {
				acceptor.addPosition(node.getOffset(), node.getLength(),
						CalHighlightingConfiguration.GLOBAL_VARIABLE_REFERENCE_ID);
			} else if (variable.eContainer() instanceof AstNamespace) {
				acceptor.addPosition(node.getOffset(), node.getLength(),
						CalHighlightingConfiguration.NAMESPACE_VARIABLE_REFERENCE_ID);
			} else {
				acceptor.addPosition(node.getOffset(), node.getLength(),
						CalHighlightingConfiguration.LOCAL_VARIABLE_REFERNCE_ID);
			}
		}

	}
}
