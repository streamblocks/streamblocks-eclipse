/**
 * generated by Xtext 2.21.0
 */
package streamblocks.eclipse.cal.cal;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ast Process Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link streamblocks.eclipse.cal.cal.AstProcessDescription#isRepeated <em>Repeated</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.AstProcessDescription#getStatements <em>Statements</em>}</li>
 * </ul>
 *
 * @see streamblocks.eclipse.cal.cal.CalPackage#getAstProcessDescription()
 * @model
 * @generated
 */
public interface AstProcessDescription extends EObject
{
  /**
   * Returns the value of the '<em><b>Repeated</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Repeated</em>' attribute.
   * @see #setRepeated(boolean)
   * @see streamblocks.eclipse.cal.cal.CalPackage#getAstProcessDescription_Repeated()
   * @model
   * @generated
   */
  boolean isRepeated();

  /**
   * Sets the value of the '{@link streamblocks.eclipse.cal.cal.AstProcessDescription#isRepeated <em>Repeated</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Repeated</em>' attribute.
   * @see #isRepeated()
   * @generated
   */
  void setRepeated(boolean value);

  /**
   * Returns the value of the '<em><b>Statements</b></em>' containment reference list.
   * The list contents are of type {@link streamblocks.eclipse.cal.cal.AstStatement}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Statements</em>' containment reference list.
   * @see streamblocks.eclipse.cal.cal.CalPackage#getAstProcessDescription_Statements()
   * @model containment="true"
   * @generated
   */
  EList<AstStatement> getStatements();

} // AstProcessDescription
