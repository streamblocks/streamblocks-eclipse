/**
 * generated by Xtext 2.21.0
 */
package streamblocks.eclipse.cal.cal.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import streamblocks.eclipse.cal.cal.AstProcedure;
import streamblocks.eclipse.cal.cal.AstStatement;
import streamblocks.eclipse.cal.cal.AstVariable;
import streamblocks.eclipse.cal.cal.CalPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ast Procedure</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link streamblocks.eclipse.cal.cal.impl.AstProcedureImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.impl.AstProcedureImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.impl.AstProcedureImpl#getStatements <em>Statements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AstProcedureImpl extends AstVariableImpl implements AstProcedure
{
  /**
   * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParameters()
   * @generated
   * @ordered
   */
  protected EList<AstVariable> parameters;

  /**
   * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVariables()
   * @generated
   * @ordered
   */
  protected EList<AstVariable> variables;

  /**
   * The cached value of the '{@link #getStatements() <em>Statements</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStatements()
   * @generated
   * @ordered
   */
  protected EList<AstStatement> statements;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AstProcedureImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return CalPackage.Literals.AST_PROCEDURE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<AstVariable> getParameters()
  {
    if (parameters == null)
    {
      parameters = new EObjectContainmentEList<AstVariable>(AstVariable.class, this, CalPackage.AST_PROCEDURE__PARAMETERS);
    }
    return parameters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<AstVariable> getVariables()
  {
    if (variables == null)
    {
      variables = new EObjectContainmentEList<AstVariable>(AstVariable.class, this, CalPackage.AST_PROCEDURE__VARIABLES);
    }
    return variables;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<AstStatement> getStatements()
  {
    if (statements == null)
    {
      statements = new EObjectContainmentEList<AstStatement>(AstStatement.class, this, CalPackage.AST_PROCEDURE__STATEMENTS);
    }
    return statements;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case CalPackage.AST_PROCEDURE__PARAMETERS:
        return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
      case CalPackage.AST_PROCEDURE__VARIABLES:
        return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
      case CalPackage.AST_PROCEDURE__STATEMENTS:
        return ((InternalEList<?>)getStatements()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case CalPackage.AST_PROCEDURE__PARAMETERS:
        return getParameters();
      case CalPackage.AST_PROCEDURE__VARIABLES:
        return getVariables();
      case CalPackage.AST_PROCEDURE__STATEMENTS:
        return getStatements();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case CalPackage.AST_PROCEDURE__PARAMETERS:
        getParameters().clear();
        getParameters().addAll((Collection<? extends AstVariable>)newValue);
        return;
      case CalPackage.AST_PROCEDURE__VARIABLES:
        getVariables().clear();
        getVariables().addAll((Collection<? extends AstVariable>)newValue);
        return;
      case CalPackage.AST_PROCEDURE__STATEMENTS:
        getStatements().clear();
        getStatements().addAll((Collection<? extends AstStatement>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case CalPackage.AST_PROCEDURE__PARAMETERS:
        getParameters().clear();
        return;
      case CalPackage.AST_PROCEDURE__VARIABLES:
        getVariables().clear();
        return;
      case CalPackage.AST_PROCEDURE__STATEMENTS:
        getStatements().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case CalPackage.AST_PROCEDURE__PARAMETERS:
        return parameters != null && !parameters.isEmpty();
      case CalPackage.AST_PROCEDURE__VARIABLES:
        return variables != null && !variables.isEmpty();
      case CalPackage.AST_PROCEDURE__STATEMENTS:
        return statements != null && !statements.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //AstProcedureImpl
