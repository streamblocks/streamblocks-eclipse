/**
 * generated by Xtext 2.21.0
 */
package streamblocks.eclipse.cal.cal;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ast Expression Lambda</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link streamblocks.eclipse.cal.cal.AstExpressionLambda#getName <em>Name</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.AstExpressionLambda#getParameters <em>Parameters</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.AstExpressionLambda#getType <em>Type</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.AstExpressionLambda#getVariables <em>Variables</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.AstExpressionLambda#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @see streamblocks.eclipse.cal.cal.CalPackage#getAstExpressionLambda()
 * @model
 * @generated
 */
public interface AstExpressionLambda extends AstExpression
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see streamblocks.eclipse.cal.cal.CalPackage#getAstExpressionLambda_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link streamblocks.eclipse.cal.cal.AstExpressionLambda#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
   * The list contents are of type {@link streamblocks.eclipse.cal.cal.AstVariable}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parameters</em>' containment reference list.
   * @see streamblocks.eclipse.cal.cal.CalPackage#getAstExpressionLambda_Parameters()
   * @model containment="true"
   * @generated
   */
  EList<AstVariable> getParameters();

  /**
   * Returns the value of the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' containment reference.
   * @see #setType(AstType)
   * @see streamblocks.eclipse.cal.cal.CalPackage#getAstExpressionLambda_Type()
   * @model containment="true"
   * @generated
   */
  AstType getType();

  /**
   * Sets the value of the '{@link streamblocks.eclipse.cal.cal.AstExpressionLambda#getType <em>Type</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' containment reference.
   * @see #getType()
   * @generated
   */
  void setType(AstType value);

  /**
   * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
   * The list contents are of type {@link streamblocks.eclipse.cal.cal.AstVariable}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Variables</em>' containment reference list.
   * @see streamblocks.eclipse.cal.cal.CalPackage#getAstExpressionLambda_Variables()
   * @model containment="true"
   * @generated
   */
  EList<AstVariable> getVariables();

  /**
   * Returns the value of the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expression</em>' containment reference.
   * @see #setExpression(AstExpression)
   * @see streamblocks.eclipse.cal.cal.CalPackage#getAstExpressionLambda_Expression()
   * @model containment="true"
   * @generated
   */
  AstExpression getExpression();

  /**
   * Sets the value of the '{@link streamblocks.eclipse.cal.cal.AstExpressionLambda#getExpression <em>Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expression</em>' containment reference.
   * @see #getExpression()
   * @generated
   */
  void setExpression(AstExpression value);

} // AstExpressionLambda
