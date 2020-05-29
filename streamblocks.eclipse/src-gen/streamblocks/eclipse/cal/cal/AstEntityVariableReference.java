/**
 * generated by Xtext 2.21.0
 */
package streamblocks.eclipse.cal.cal;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ast Entity Variable Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link streamblocks.eclipse.cal.cal.AstEntityVariableReference#getVariable <em>Variable</em>}</li>
 * </ul>
 *
 * @see streamblocks.eclipse.cal.cal.CalPackage#getAstEntityVariableReference()
 * @model
 * @generated
 */
public interface AstEntityVariableReference extends EObject
{
  /**
   * Returns the value of the '<em><b>Variable</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Variable</em>' reference.
   * @see #setVariable(AstEntityVariable)
   * @see streamblocks.eclipse.cal.cal.CalPackage#getAstEntityVariableReference_Variable()
   * @model
   * @generated
   */
  AstEntityVariable getVariable();

  /**
   * Sets the value of the '{@link streamblocks.eclipse.cal.cal.AstEntityVariableReference#getVariable <em>Variable</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Variable</em>' reference.
   * @see #getVariable()
   * @generated
   */
  void setVariable(AstEntityVariable value);

} // AstEntityVariableReference
