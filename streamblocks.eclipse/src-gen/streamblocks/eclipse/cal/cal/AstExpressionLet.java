/**
 * generated by Xtext 2.21.0
 */
package streamblocks.eclipse.cal.cal;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ast Expression Let</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link streamblocks.eclipse.cal.cal.AstExpressionLet#getVariables <em>Variables</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.AstExpressionLet#getExpr <em>Expr</em>}</li>
 * </ul>
 *
 * @see streamblocks.eclipse.cal.cal.CalPackage#getAstExpressionLet()
 * @model
 * @generated
 */
public interface AstExpressionLet extends AstExpression
{
  /**
   * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
   * The list contents are of type {@link streamblocks.eclipse.cal.cal.AstVariable}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Variables</em>' containment reference list.
   * @see streamblocks.eclipse.cal.cal.CalPackage#getAstExpressionLet_Variables()
   * @model containment="true"
   * @generated
   */
  EList<AstVariable> getVariables();

  /**
   * Returns the value of the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expr</em>' containment reference.
   * @see #setExpr(AstExpression)
   * @see streamblocks.eclipse.cal.cal.CalPackage#getAstExpressionLet_Expr()
   * @model containment="true"
   * @generated
   */
  AstExpression getExpr();

  /**
   * Sets the value of the '{@link streamblocks.eclipse.cal.cal.AstExpressionLet#getExpr <em>Expr</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expr</em>' containment reference.
   * @see #getExpr()
   * @generated
   */
  void setExpr(AstExpression value);

} // AstExpressionLet
