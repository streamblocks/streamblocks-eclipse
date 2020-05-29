/**
 * generated by Xtext 2.21.0
 */
package streamblocks.eclipse.cal.cal.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import streamblocks.eclipse.cal.cal.AstAnnotation;
import streamblocks.eclipse.cal.cal.AstExpression;
import streamblocks.eclipse.cal.cal.AstProcedure;
import streamblocks.eclipse.cal.cal.AstStatementCall;
import streamblocks.eclipse.cal.cal.CalPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ast Statement Call</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link streamblocks.eclipse.cal.cal.impl.AstStatementCallImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.impl.AstStatementCallImpl#getProcedure <em>Procedure</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.impl.AstStatementCallImpl#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AstStatementCallImpl extends AstStatementImpl implements AstStatementCall
{
  /**
   * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAnnotations()
   * @generated
   * @ordered
   */
  protected EList<AstAnnotation> annotations;

  /**
   * The cached value of the '{@link #getProcedure() <em>Procedure</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProcedure()
   * @generated
   * @ordered
   */
  protected AstProcedure procedure;

  /**
   * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParameters()
   * @generated
   * @ordered
   */
  protected EList<AstExpression> parameters;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AstStatementCallImpl()
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
    return CalPackage.Literals.AST_STATEMENT_CALL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<AstAnnotation> getAnnotations()
  {
    if (annotations == null)
    {
      annotations = new EObjectContainmentEList<AstAnnotation>(AstAnnotation.class, this, CalPackage.AST_STATEMENT_CALL__ANNOTATIONS);
    }
    return annotations;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public AstProcedure getProcedure()
  {
    if (procedure != null && procedure.eIsProxy())
    {
      InternalEObject oldProcedure = (InternalEObject)procedure;
      procedure = (AstProcedure)eResolveProxy(oldProcedure);
      if (procedure != oldProcedure)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, CalPackage.AST_STATEMENT_CALL__PROCEDURE, oldProcedure, procedure));
      }
    }
    return procedure;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AstProcedure basicGetProcedure()
  {
    return procedure;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setProcedure(AstProcedure newProcedure)
  {
    AstProcedure oldProcedure = procedure;
    procedure = newProcedure;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.AST_STATEMENT_CALL__PROCEDURE, oldProcedure, procedure));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<AstExpression> getParameters()
  {
    if (parameters == null)
    {
      parameters = new EObjectContainmentEList<AstExpression>(AstExpression.class, this, CalPackage.AST_STATEMENT_CALL__PARAMETERS);
    }
    return parameters;
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
      case CalPackage.AST_STATEMENT_CALL__ANNOTATIONS:
        return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
      case CalPackage.AST_STATEMENT_CALL__PARAMETERS:
        return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
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
      case CalPackage.AST_STATEMENT_CALL__ANNOTATIONS:
        return getAnnotations();
      case CalPackage.AST_STATEMENT_CALL__PROCEDURE:
        if (resolve) return getProcedure();
        return basicGetProcedure();
      case CalPackage.AST_STATEMENT_CALL__PARAMETERS:
        return getParameters();
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
      case CalPackage.AST_STATEMENT_CALL__ANNOTATIONS:
        getAnnotations().clear();
        getAnnotations().addAll((Collection<? extends AstAnnotation>)newValue);
        return;
      case CalPackage.AST_STATEMENT_CALL__PROCEDURE:
        setProcedure((AstProcedure)newValue);
        return;
      case CalPackage.AST_STATEMENT_CALL__PARAMETERS:
        getParameters().clear();
        getParameters().addAll((Collection<? extends AstExpression>)newValue);
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
      case CalPackage.AST_STATEMENT_CALL__ANNOTATIONS:
        getAnnotations().clear();
        return;
      case CalPackage.AST_STATEMENT_CALL__PROCEDURE:
        setProcedure((AstProcedure)null);
        return;
      case CalPackage.AST_STATEMENT_CALL__PARAMETERS:
        getParameters().clear();
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
      case CalPackage.AST_STATEMENT_CALL__ANNOTATIONS:
        return annotations != null && !annotations.isEmpty();
      case CalPackage.AST_STATEMENT_CALL__PROCEDURE:
        return procedure != null;
      case CalPackage.AST_STATEMENT_CALL__PARAMETERS:
        return parameters != null && !parameters.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //AstStatementCallImpl
