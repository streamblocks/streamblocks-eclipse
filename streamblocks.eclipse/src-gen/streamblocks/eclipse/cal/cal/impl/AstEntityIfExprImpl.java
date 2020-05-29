/**
 * generated by Xtext 2.21.0
 */
package streamblocks.eclipse.cal.cal.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import streamblocks.eclipse.cal.cal.AstEntityExpr;
import streamblocks.eclipse.cal.cal.AstEntityIfExpr;
import streamblocks.eclipse.cal.cal.AstExpression;
import streamblocks.eclipse.cal.cal.CalPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ast Entity If Expr</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link streamblocks.eclipse.cal.cal.impl.AstEntityIfExprImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.impl.AstEntityIfExprImpl#getTrueEntity <em>True Entity</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.impl.AstEntityIfExprImpl#getFalseEntity <em>False Entity</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AstEntityIfExprImpl extends AstEntityExprImpl implements AstEntityIfExpr
{
  /**
   * The cached value of the '{@link #getCondition() <em>Condition</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCondition()
   * @generated
   * @ordered
   */
  protected AstExpression condition;

  /**
   * The cached value of the '{@link #getTrueEntity() <em>True Entity</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTrueEntity()
   * @generated
   * @ordered
   */
  protected AstEntityExpr trueEntity;

  /**
   * The cached value of the '{@link #getFalseEntity() <em>False Entity</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFalseEntity()
   * @generated
   * @ordered
   */
  protected AstEntityExpr falseEntity;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AstEntityIfExprImpl()
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
    return CalPackage.Literals.AST_ENTITY_IF_EXPR;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public AstExpression getCondition()
  {
    return condition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetCondition(AstExpression newCondition, NotificationChain msgs)
  {
    AstExpression oldCondition = condition;
    condition = newCondition;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CalPackage.AST_ENTITY_IF_EXPR__CONDITION, oldCondition, newCondition);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCondition(AstExpression newCondition)
  {
    if (newCondition != condition)
    {
      NotificationChain msgs = null;
      if (condition != null)
        msgs = ((InternalEObject)condition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CalPackage.AST_ENTITY_IF_EXPR__CONDITION, null, msgs);
      if (newCondition != null)
        msgs = ((InternalEObject)newCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CalPackage.AST_ENTITY_IF_EXPR__CONDITION, null, msgs);
      msgs = basicSetCondition(newCondition, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.AST_ENTITY_IF_EXPR__CONDITION, newCondition, newCondition));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public AstEntityExpr getTrueEntity()
  {
    return trueEntity;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetTrueEntity(AstEntityExpr newTrueEntity, NotificationChain msgs)
  {
    AstEntityExpr oldTrueEntity = trueEntity;
    trueEntity = newTrueEntity;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CalPackage.AST_ENTITY_IF_EXPR__TRUE_ENTITY, oldTrueEntity, newTrueEntity);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTrueEntity(AstEntityExpr newTrueEntity)
  {
    if (newTrueEntity != trueEntity)
    {
      NotificationChain msgs = null;
      if (trueEntity != null)
        msgs = ((InternalEObject)trueEntity).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CalPackage.AST_ENTITY_IF_EXPR__TRUE_ENTITY, null, msgs);
      if (newTrueEntity != null)
        msgs = ((InternalEObject)newTrueEntity).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CalPackage.AST_ENTITY_IF_EXPR__TRUE_ENTITY, null, msgs);
      msgs = basicSetTrueEntity(newTrueEntity, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.AST_ENTITY_IF_EXPR__TRUE_ENTITY, newTrueEntity, newTrueEntity));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public AstEntityExpr getFalseEntity()
  {
    return falseEntity;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetFalseEntity(AstEntityExpr newFalseEntity, NotificationChain msgs)
  {
    AstEntityExpr oldFalseEntity = falseEntity;
    falseEntity = newFalseEntity;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CalPackage.AST_ENTITY_IF_EXPR__FALSE_ENTITY, oldFalseEntity, newFalseEntity);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFalseEntity(AstEntityExpr newFalseEntity)
  {
    if (newFalseEntity != falseEntity)
    {
      NotificationChain msgs = null;
      if (falseEntity != null)
        msgs = ((InternalEObject)falseEntity).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CalPackage.AST_ENTITY_IF_EXPR__FALSE_ENTITY, null, msgs);
      if (newFalseEntity != null)
        msgs = ((InternalEObject)newFalseEntity).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CalPackage.AST_ENTITY_IF_EXPR__FALSE_ENTITY, null, msgs);
      msgs = basicSetFalseEntity(newFalseEntity, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.AST_ENTITY_IF_EXPR__FALSE_ENTITY, newFalseEntity, newFalseEntity));
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
      case CalPackage.AST_ENTITY_IF_EXPR__CONDITION:
        return basicSetCondition(null, msgs);
      case CalPackage.AST_ENTITY_IF_EXPR__TRUE_ENTITY:
        return basicSetTrueEntity(null, msgs);
      case CalPackage.AST_ENTITY_IF_EXPR__FALSE_ENTITY:
        return basicSetFalseEntity(null, msgs);
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
      case CalPackage.AST_ENTITY_IF_EXPR__CONDITION:
        return getCondition();
      case CalPackage.AST_ENTITY_IF_EXPR__TRUE_ENTITY:
        return getTrueEntity();
      case CalPackage.AST_ENTITY_IF_EXPR__FALSE_ENTITY:
        return getFalseEntity();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case CalPackage.AST_ENTITY_IF_EXPR__CONDITION:
        setCondition((AstExpression)newValue);
        return;
      case CalPackage.AST_ENTITY_IF_EXPR__TRUE_ENTITY:
        setTrueEntity((AstEntityExpr)newValue);
        return;
      case CalPackage.AST_ENTITY_IF_EXPR__FALSE_ENTITY:
        setFalseEntity((AstEntityExpr)newValue);
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
      case CalPackage.AST_ENTITY_IF_EXPR__CONDITION:
        setCondition((AstExpression)null);
        return;
      case CalPackage.AST_ENTITY_IF_EXPR__TRUE_ENTITY:
        setTrueEntity((AstEntityExpr)null);
        return;
      case CalPackage.AST_ENTITY_IF_EXPR__FALSE_ENTITY:
        setFalseEntity((AstEntityExpr)null);
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
      case CalPackage.AST_ENTITY_IF_EXPR__CONDITION:
        return condition != null;
      case CalPackage.AST_ENTITY_IF_EXPR__TRUE_ENTITY:
        return trueEntity != null;
      case CalPackage.AST_ENTITY_IF_EXPR__FALSE_ENTITY:
        return falseEntity != null;
    }
    return super.eIsSet(featureID);
  }

} //AstEntityIfExprImpl
