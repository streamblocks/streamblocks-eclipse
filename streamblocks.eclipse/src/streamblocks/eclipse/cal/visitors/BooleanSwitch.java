package streamblocks.eclipse.cal.visitors;

import streamblocks.eclipse.cal.cal.AstAction;
import streamblocks.eclipse.cal.cal.AstActor;
import streamblocks.eclipse.cal.cal.AstAssignParameter;
import streamblocks.eclipse.cal.cal.AstAssignVarParameter;
import streamblocks.eclipse.cal.cal.AstAttribute;
import streamblocks.eclipse.cal.cal.AstEntity;
import streamblocks.eclipse.cal.cal.AstEntityExpr;
import streamblocks.eclipse.cal.cal.AstEntityIfExpr;
import streamblocks.eclipse.cal.cal.AstEntityInstanceExpr;
import streamblocks.eclipse.cal.cal.AstEntityListExpr;
import streamblocks.eclipse.cal.cal.AstExpression;
import streamblocks.eclipse.cal.cal.AstExpressionAlternative;
import streamblocks.eclipse.cal.cal.AstExpressionBinary;
import streamblocks.eclipse.cal.cal.AstExpressionCase;
import streamblocks.eclipse.cal.cal.AstExpressionElsif;
import streamblocks.eclipse.cal.cal.AstExpressionIf;
import streamblocks.eclipse.cal.cal.AstExpressionLambda;
import streamblocks.eclipse.cal.cal.AstExpressionLet;
import streamblocks.eclipse.cal.cal.AstExpressionList;
import streamblocks.eclipse.cal.cal.AstExpressionMap;
import streamblocks.eclipse.cal.cal.AstExpressionProc;
import streamblocks.eclipse.cal.cal.AstExpressionSet;
import streamblocks.eclipse.cal.cal.AstExpressionSymbolReference;
import streamblocks.eclipse.cal.cal.AstExpressionUnary;
import streamblocks.eclipse.cal.cal.AstForeachGenerator;
import streamblocks.eclipse.cal.cal.AstFunction;
import streamblocks.eclipse.cal.cal.AstGenerator;
import streamblocks.eclipse.cal.cal.AstInitialize;
import streamblocks.eclipse.cal.cal.AstInputPattern;
import streamblocks.eclipse.cal.cal.AstLValue;
import streamblocks.eclipse.cal.cal.AstMemberAccess;
import streamblocks.eclipse.cal.cal.AstNetwork;
import streamblocks.eclipse.cal.cal.AstOutputExpression;
import streamblocks.eclipse.cal.cal.AstPattern;
import streamblocks.eclipse.cal.cal.AstPort;
import streamblocks.eclipse.cal.cal.AstProcedure;
import streamblocks.eclipse.cal.cal.AstProcessDescription;
import streamblocks.eclipse.cal.cal.AstStatement;
import streamblocks.eclipse.cal.cal.AstStatementAlternative;
import streamblocks.eclipse.cal.cal.AstStatementAssign;
import streamblocks.eclipse.cal.cal.AstStatementBlock;
import streamblocks.eclipse.cal.cal.AstStatementCall;
import streamblocks.eclipse.cal.cal.AstStatementCase;
import streamblocks.eclipse.cal.cal.AstStatementElsif;
import streamblocks.eclipse.cal.cal.AstStatementForeach;
import streamblocks.eclipse.cal.cal.AstStatementIf;
import streamblocks.eclipse.cal.cal.AstStatementRead;
import streamblocks.eclipse.cal.cal.AstStatementWhile;
import streamblocks.eclipse.cal.cal.AstStatementWrite;
import streamblocks.eclipse.cal.cal.AstStructure;
import streamblocks.eclipse.cal.cal.AstStructureStatement;
import streamblocks.eclipse.cal.cal.AstStructureStatementConnection;
import streamblocks.eclipse.cal.cal.AstStructureStatementElsif;
import streamblocks.eclipse.cal.cal.AstStructureStatementForeach;
import streamblocks.eclipse.cal.cal.AstStructureStatementIf;
import streamblocks.eclipse.cal.cal.AstVariable;
import streamblocks.eclipse.cal.cal.FormalParameter;
import streamblocks.eclipse.cal.cal.Mapping;
import streamblocks.eclipse.cal.cal.util.CalSwitch;

public class BooleanSwitch extends CalSwitch<Boolean> {

	@Override
	public Boolean caseAstAction(AstAction action) {

		for (AstInputPattern input : action.getInputs()) {
			if (doSwitch(input)) {
				return true;
			}
		}

		if (action.getGuards() != null) {
			for (AstExpression expr : action.getGuards()) {
				if (doSwitch(expr)) {
					return true;
				}
			}
		}

		for (AstVariable variable : action.getVariables()) {
			if (doSwitch(variable)) {
				return true;
			}
		}

		for (AstStatement statements : action.getStatements()) {
			if (doSwitch(statements)) {
				return true;
			}
		}

		for (AstOutputExpression output : action.getOutputs()) {
			if (doSwitch(output)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstActor(AstActor actor) {
		for (FormalParameter parameter : actor.getParameters()) {
			if (doSwitch(parameter)) {
				return true;
			}
		}

		for (AstPort port : actor.getInputs()) {
			if (doSwitch(port)) {
				return true;
			}
		}

		for (AstPort port : actor.getOutputs()) {
			if (doSwitch(port)) {
				return true;
			}
		}

		for (AstAction action : actor.getActions()) {
			if (doSwitch(action)) {
				return true;
			}
		}

		for (AstProcessDescription process : actor.getProcesses()) {
			if (doSwitch(process)) {
				return true;
			}
		}

		for (AstAction action : actor.getInitializes()) {
			if (doSwitch(action)) {
				return true;
			}
		}

		for (AstVariable variable : actor.getStateVariables()) {
			if (doSwitch(variable)) {
				return true;
			}
		}

		for (AstVariable function : actor.getFunctions()) {
			if (doSwitch(function)) {
				return true;
			}
		}

		for (AstVariable procedure : actor.getProcedures()) {
			if (doSwitch(procedure)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstAssignParameter(AstAssignParameter assignParam) {
		if (assignParam.getVar() != null) {
			if (doSwitch(assignParam.getVar())) {
				return true;
			}
		}

		if (assignParam.getType() != null) {
			if (doSwitch(assignParam.getType())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstAssignVarParameter(AstAssignVarParameter varParam) {
		if (doSwitch(varParam.getValue())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstAttribute(AstAttribute attr) {
		if (doSwitch(attr.getValue())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstEntity(AstEntity entity) {
		if (doSwitch(entity.getActor())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstEntityIfExpr(AstEntityIfExpr entityIf) {
		if (doSwitch(entityIf.getCondition())) {
			return true;
		}

		if (doSwitch(entityIf.getTrueEntity())) {
			return true;
		}

		if (doSwitch(entityIf.getFalseEntity())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstEntityInstanceExpr(AstEntityInstanceExpr entityInstance) {
		for (AstAttribute attribute : entityInstance.getAttribute()) {
			if (doSwitch(attribute)) {
				return true;
			}
		}

		for (AstAssignParameter parameter : entityInstance.getParameters()) {
			if (doSwitch(parameter)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstEntityListExpr(AstEntityListExpr entityList) {
		for (AstGenerator generator : entityList.getGenerators()) {
			if (doSwitch(generator)) {
				return true;
			}
		}

		for (AstEntityExpr entity : entityList.getExpressions()) {
			if (doSwitch(entity)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean caseAstExpressionAlternative(AstExpressionAlternative exprAlt) {
		if (doSwitch(exprAlt.getPattern())) {
			return true;
		}

		for (AstExpression guard : exprAlt.getGuards()) {
			if (doSwitch(guard)) {
				return true;
			}
		}

		if (doSwitch(exprAlt.getExpression())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstExpressionBinary(AstExpressionBinary binary) {
		if (doSwitch(binary.getLeft()) || doSwitch(binary.getRight())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstExpressionCase(AstExpressionCase exprCase) {

		if (doSwitch(exprCase.getExpression())) {
			return true;
		}

		for (AstExpressionAlternative altCase : exprCase.getCases()) {
			if (doSwitch(altCase)) {
				return true;
			}
		}

		if (exprCase.getDefault() != null) {
			if (doSwitch(exprCase.getDefault())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstExpressionElsif(AstExpressionElsif elseIf) {

		if (doSwitch(elseIf.getCondition())) {
			return true;
		}

		if (doSwitch(elseIf.getThen())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstExpressionIf(AstExpressionIf exprIf) {
		if (doSwitch(exprIf.getCondition())) {
			return true;
		}

		if (doSwitch(exprIf.getThen())) {
			return true;
		}

		for (AstExpressionElsif elseIf : exprIf.getElsifs()) {
			if (doSwitch(elseIf)) {
				return true;
			}
		}

		if (doSwitch(exprIf.getElse())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstExpressionLambda(AstExpressionLambda exprLambda) {

		for (AstVariable param : exprLambda.getParameters()) {
			if (doSwitch(param)) {
				return true;
			}
		}

		for (AstVariable variable : exprLambda.getVariables()) {
			if (doSwitch(variable)) {
				return true;
			}
		}

		if (doSwitch(exprLambda.getExpression())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstExpressionLet(AstExpressionLet exprLet) {

		for (AstVariable variable : exprLet.getVariables()) {
			if (doSwitch(variable)) {
				return true;
			}
		}

		if (doSwitch(exprLet.getExpr())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstExpressionList(AstExpressionList list) {

		for (AstExpression expression : list.getExpressions()) {
			if (doSwitch(expression)) {
				return true;
			}
		}

		for (AstGenerator generator : list.getGenerators()) {
			if (doSwitch(generator)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstExpressionMap(AstExpressionMap exprMap) {

		for (Mapping mapping : exprMap.getMappings()) {
			if (doSwitch(mapping)) {
				return true;
			}
		}

		if (exprMap.getGenerator() != null) {
			if (doSwitch(exprMap.getGenerator())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstExpressionProc(AstExpressionProc exprProc) {

		for (AstVariable param : exprProc.getParameters()) {
			if (doSwitch(param)) {
				return true;
			}
		}

		for (AstVariable variable : exprProc.getVariables()) {
			if (doSwitch(variable)) {
				return true;
			}
		}

		for (AstStatement statement : exprProc.getStatements()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstExpressionSet(AstExpressionSet exprSet) {

		for (AstExpression expr : exprSet.getExprs()) {
			if (doSwitch(expr)) {
				return true;
			}
		}

		if (exprSet.getGenerator() != null) {
			if (doSwitch(exprSet.getGenerator())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstExpressionSymbolReference(AstExpressionSymbolReference symRef) {
		if (doSwitch(symRef.getSymbol())) {
			return true;
		}

		for (AstExpression index : symRef.getIndexes()) {
			if (doSwitch(index)) {
				return true;
			}
		}

		for (AstMemberAccess member : symRef.getMember()) {
			if (doSwitch(member)) {
				return true;
			}
		}

		for (AstExpression parameter : symRef.getParameters()) {
			if (doSwitch(parameter)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstExpressionUnary(AstExpressionUnary unary) {
		return doSwitch(unary.getExpression());
	}

	@Override
	public Boolean caseAstForeachGenerator(AstForeachGenerator generator) {
		if (doSwitch(generator.getVariable())) {
			return true;
		}

		if (doSwitch(generator.getExpression())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstFunction(AstFunction function) {
		for (AstVariable variable : function.getParameters()) {
			if (doSwitch(variable)) {
				return true;
			}
		}

		for (AstVariable variable : function.getVariables()) {
			if (doSwitch(variable)) {
				return true;
			}
		}

		if (doSwitch(function.getExpression())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstGenerator(AstGenerator generator) {
		if (doSwitch(generator.getVariable())) {
			return true;
		}

		if (doSwitch(generator.getExpression())) {
			return true;
		}

		for (AstExpression filter : generator.getFilter()) {
			if (doSwitch(filter)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstInitialize(AstInitialize action) {
		for (AstInputPattern input : action.getInputs()) {
			if (doSwitch(input)) {
				return true;
			}
		}

		if (action.getGuards() != null) {
			for (AstExpression expr : action.getGuards()) {
				if (doSwitch(expr)) {
					return true;
				}
			}
		}

		for (AstVariable variable : action.getVariables()) {
			if (doSwitch(variable)) {
				return true;
			}
		}

		for (AstStatement statements : action.getStatements()) {
			if (doSwitch(statements)) {
				return true;
			}
		}

		for (AstOutputExpression output : action.getOutputs()) {
			if (doSwitch(output)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstInputPattern(AstInputPattern input) {
		if (doSwitch(input.getPort())) {
			return true;
		}

		for (AstPattern pattern : input.getTokens()) {
			if (doSwitch(pattern)) {
				return true;
			}
		}

		if (doSwitch(input.getRepeat())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstLValue(AstLValue lvalue) {

		if (doSwitch(lvalue.getTarget())) {
			return true;
		}

		for (AstExpression index : lvalue.getIndexes()) {
			if (doSwitch(index)) {
				return true;
			}
		}

		for (AstMemberAccess memberAccess : lvalue.getMember()) {
			if (doSwitch(memberAccess)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstMemberAccess(AstMemberAccess mAccess) {
		for (AstExpression idx : mAccess.getMemberIndex()) {
			if (doSwitch(idx)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstNetwork(AstNetwork object) {

		return false;
	}

	@Override
	public Boolean caseAstOutputExpression(AstOutputExpression output) {
		if (doSwitch(output.getPort())) {
			return true;
		}

		for (AstExpression value : output.getValues()) {
			if (doSwitch(value)) {
				return true;
			}
		}

		if (doSwitch(output.getRepeat())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstPort(AstPort port) {
		return doSwitch(port.getType());
	}

	@Override
	public Boolean caseAstProcedure(AstProcedure procedure) {

		for (AstVariable variable : procedure.getParameters()) {
			if (doSwitch(variable)) {
				return true;
			}
		}

		for (AstVariable variable : procedure.getVariables()) {
			if (doSwitch(variable)) {
				return true;
			}
		}

		for (AstStatement statement : procedure.getStatements()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstProcessDescription(AstProcessDescription process) {
		for (AstStatement statement : process.getStatements()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		return false;
	}


	@Override
	public Boolean caseAstStatementAlternative(AstStatementAlternative stmAlt) {
		if (doSwitch(stmAlt.getPattern())) {
			return true;
		}

		for (AstExpression guard : stmAlt.getGuards()) {
			if (doSwitch(guard)) {
				return true;
			}
		}

		for (AstStatement statement : stmAlt.getStatements()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstStatementAssign(AstStatementAssign assign) {
		if (doSwitch(assign.getLvalue())) {
			return true;
		}

		if (doSwitch(assign.getValue())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstStatementBlock(AstStatementBlock block) {

		for (AstVariable variable : block.getVariables()) {
			if (doSwitch(variable)) {
				return true;
			}
		}

		for (AstStatement statement : block.getStatements()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstStatementCall(AstStatementCall call) {
		for (AstExpression parameter : call.getParameters()) {
			if (doSwitch(parameter)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstStatementCase(AstStatementCase stmtCase) {

		if (doSwitch(stmtCase.getExpression())) {
			return true;
		}

		for (AstStatementAlternative alternative : stmtCase.getCases()) {
			if (doSwitch(alternative)) {
				return true;
			}
		}

		if (stmtCase.getDefault() != null) {
			if (doSwitch(stmtCase.getDefault())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstStatementElsif(AstStatementElsif elseIf) {
		if (doSwitch(elseIf.getCondition())) {
			return true;
		}

		for (AstStatement statement : elseIf.getThen()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstStatementForeach(AstStatementForeach foreach) {
		for (AstForeachGenerator generator : foreach.getGenerators()) {
			if (doSwitch(generator)) {
				return true;
			}
		}

		for (AstVariable variable : foreach.getVariables()) {
			if (doSwitch(variable)) {
				return true;
			}
		}

		for (AstStatement statement : foreach.getStatements()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstStatementIf(AstStatementIf stmIf) {
		if (doSwitch(stmIf.getCondition())) {
			return true;
		}

		for (AstStatement statement : stmIf.getThen()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		for (AstStatementElsif statement : stmIf.getElsifs()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		for (AstStatement statement : stmIf.getElse()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstStatementRead(AstStatementRead read) {
		if (doSwitch(read.getPort())) {
			return true;
		}

		for (AstLValue lvalue : read.getLvalues()) {
			if (doSwitch(lvalue)) {
				return true;
			}
		}

		if (doSwitch(read.getRepeat())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstStatementWhile(AstStatementWhile stmtWhile) {
		if (doSwitch(stmtWhile.getCondition())) {
			return true;
		}

		for (AstStatement statement : stmtWhile.getStatements()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstStatementWrite(AstStatementWrite write) {
		if (doSwitch(write.getPort())) {
			return true;
		}

		for (AstExpression expr : write.getValues()) {
			if (doSwitch(expr)) {
				return true;
			}
		}

		if (doSwitch(write.getRepeat())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstStructure(AstStructure structure) {
		for(AstStructureStatement connection : structure.getConnections()) {
			if (doSwitch(connection)) {
				return true;
			}
		}
		
		
		return false;
	}


	@Override
	public Boolean caseAstStructureStatementConnection(AstStructureStatementConnection stmtConnection) {
		
		for(AstExpression toIndex : stmtConnection.getToIndexes()) {
			if (doSwitch(toIndex)) {
				return true;
			}
		}
		
		for(AstExpression fromIndex : stmtConnection.getFromIndexes()) {
			if (doSwitch(fromIndex)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Boolean caseAstStructureStatementElsif(AstStructureStatementElsif stmtElsif) {
		if (doSwitch(stmtElsif.getCondition())) {
			return true;
		}
		
		
		for(AstStructureStatement statement : stmtElsif.getThen()) {
			if (doSwitch(statement)) {
				return true;
			}
		}
		
		
		return false;
	}

	@Override
	public Boolean caseAstStructureStatementForeach(AstStructureStatementForeach stmtForeach) {
		
		for(AstForeachGenerator generator : stmtForeach.getGenerators()) {
			if (doSwitch(generator)) {
				return true;
			}
		}
		
		for(AstVariable variable : stmtForeach.getVariables()) {
			if (doSwitch(variable)) {
				return true;
			}
		}
		
		for(AstStructureStatement statement : stmtForeach.getStatements()) {
			if (doSwitch(statement)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Boolean caseAstStructureStatementIf(AstStructureStatementIf stmtIf) {
		
		if (doSwitch(stmtIf.getCondition())) {
			return true;
		}
		
		
		for(AstStructureStatement statement : stmtIf.getThen()) {
			if (doSwitch(statement)) {
				return true;
			}
		}
		
		for(AstStructureStatementElsif statement : stmtIf.getElsifs()) {
			if (doSwitch(statement)) {
				return true;
			}
		}
		
		for(AstStructureStatement statement : stmtIf.getElse()) {
			if (doSwitch(statement)) {
				return true;
			}
		}
		
		
		return false;
	}

	
	@Override
	public Boolean caseMapping(Mapping mapping) {

		if (doSwitch(mapping.getKey())) {
			return true;
		}

		if (doSwitch(mapping.getValue())) {
			return true;
		}

		return false;
	}

	

}
