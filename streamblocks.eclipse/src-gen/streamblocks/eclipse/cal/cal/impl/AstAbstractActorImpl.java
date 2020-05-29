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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import streamblocks.eclipse.cal.cal.AstAbstractActor;
import streamblocks.eclipse.cal.cal.AstPort;
import streamblocks.eclipse.cal.cal.AstVariable;
import streamblocks.eclipse.cal.cal.CalPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ast Abstract Actor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link streamblocks.eclipse.cal.cal.impl.AstAbstractActorImpl#getName <em>Name</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.impl.AstAbstractActorImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.impl.AstAbstractActorImpl#getInputs <em>Inputs</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.impl.AstAbstractActorImpl#getOutputs <em>Outputs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AstAbstractActorImpl extends MinimalEObjectImpl.Container implements AstAbstractActor
{
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

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
   * The cached value of the '{@link #getInputs() <em>Inputs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInputs()
   * @generated
   * @ordered
   */
  protected EList<AstPort> inputs;

  /**
   * The cached value of the '{@link #getOutputs() <em>Outputs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOutputs()
   * @generated
   * @ordered
   */
  protected EList<AstPort> outputs;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AstAbstractActorImpl()
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
    return CalPackage.Literals.AST_ABSTRACT_ACTOR;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, CalPackage.AST_ABSTRACT_ACTOR__NAME, oldName, name));
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
      parameters = new EObjectContainmentEList<AstVariable>(AstVariable.class, this, CalPackage.AST_ABSTRACT_ACTOR__PARAMETERS);
    }
    return parameters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<AstPort> getInputs()
  {
    if (inputs == null)
    {
      inputs = new EObjectContainmentEList<AstPort>(AstPort.class, this, CalPackage.AST_ABSTRACT_ACTOR__INPUTS);
    }
    return inputs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<AstPort> getOutputs()
  {
    if (outputs == null)
    {
      outputs = new EObjectContainmentEList<AstPort>(AstPort.class, this, CalPackage.AST_ABSTRACT_ACTOR__OUTPUTS);
    }
    return outputs;
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
      case CalPackage.AST_ABSTRACT_ACTOR__PARAMETERS:
        return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
      case CalPackage.AST_ABSTRACT_ACTOR__INPUTS:
        return ((InternalEList<?>)getInputs()).basicRemove(otherEnd, msgs);
      case CalPackage.AST_ABSTRACT_ACTOR__OUTPUTS:
        return ((InternalEList<?>)getOutputs()).basicRemove(otherEnd, msgs);
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
      case CalPackage.AST_ABSTRACT_ACTOR__NAME:
        return getName();
      case CalPackage.AST_ABSTRACT_ACTOR__PARAMETERS:
        return getParameters();
      case CalPackage.AST_ABSTRACT_ACTOR__INPUTS:
        return getInputs();
      case CalPackage.AST_ABSTRACT_ACTOR__OUTPUTS:
        return getOutputs();
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
      case CalPackage.AST_ABSTRACT_ACTOR__NAME:
        setName((String)newValue);
        return;
      case CalPackage.AST_ABSTRACT_ACTOR__PARAMETERS:
        getParameters().clear();
        getParameters().addAll((Collection<? extends AstVariable>)newValue);
        return;
      case CalPackage.AST_ABSTRACT_ACTOR__INPUTS:
        getInputs().clear();
        getInputs().addAll((Collection<? extends AstPort>)newValue);
        return;
      case CalPackage.AST_ABSTRACT_ACTOR__OUTPUTS:
        getOutputs().clear();
        getOutputs().addAll((Collection<? extends AstPort>)newValue);
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
      case CalPackage.AST_ABSTRACT_ACTOR__NAME:
        setName(NAME_EDEFAULT);
        return;
      case CalPackage.AST_ABSTRACT_ACTOR__PARAMETERS:
        getParameters().clear();
        return;
      case CalPackage.AST_ABSTRACT_ACTOR__INPUTS:
        getInputs().clear();
        return;
      case CalPackage.AST_ABSTRACT_ACTOR__OUTPUTS:
        getOutputs().clear();
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
      case CalPackage.AST_ABSTRACT_ACTOR__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case CalPackage.AST_ABSTRACT_ACTOR__PARAMETERS:
        return parameters != null && !parameters.isEmpty();
      case CalPackage.AST_ABSTRACT_ACTOR__INPUTS:
        return inputs != null && !inputs.isEmpty();
      case CalPackage.AST_ABSTRACT_ACTOR__OUTPUTS:
        return outputs != null && !outputs.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (name: ");
    result.append(name);
    result.append(')');
    return result.toString();
  }

} //AstAbstractActorImpl
