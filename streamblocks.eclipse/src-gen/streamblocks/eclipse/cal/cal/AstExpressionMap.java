/**
 * generated by Xtext 2.21.0
 */
package streamblocks.eclipse.cal.cal;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ast Expression Map</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link streamblocks.eclipse.cal.cal.AstExpressionMap#getMappings <em>Mappings</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.AstExpressionMap#getGenerator <em>Generator</em>}</li>
 * </ul>
 *
 * @see streamblocks.eclipse.cal.cal.CalPackage#getAstExpressionMap()
 * @model
 * @generated
 */
public interface AstExpressionMap extends AstExpression
{
  /**
   * Returns the value of the '<em><b>Mappings</b></em>' containment reference list.
   * The list contents are of type {@link streamblocks.eclipse.cal.cal.Mapping}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mappings</em>' containment reference list.
   * @see streamblocks.eclipse.cal.cal.CalPackage#getAstExpressionMap_Mappings()
   * @model containment="true"
   * @generated
   */
  EList<Mapping> getMappings();

  /**
   * Returns the value of the '<em><b>Generator</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Generator</em>' containment reference.
   * @see #setGenerator(AstGenerator)
   * @see streamblocks.eclipse.cal.cal.CalPackage#getAstExpressionMap_Generator()
   * @model containment="true"
   * @generated
   */
  AstGenerator getGenerator();

  /**
   * Sets the value of the '{@link streamblocks.eclipse.cal.cal.AstExpressionMap#getGenerator <em>Generator</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Generator</em>' containment reference.
   * @see #getGenerator()
   * @generated
   */
  void setGenerator(AstGenerator value);

} // AstExpressionMap
