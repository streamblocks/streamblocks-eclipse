/**
 * generated by Xtext 2.21.0
 */
package streamblocks.eclipse.cal.cal;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ast Statement Foreach</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link streamblocks.eclipse.cal.cal.AstStatementForeach#getGenerators <em>Generators</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.AstStatementForeach#getVariables <em>Variables</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.AstStatementForeach#getStatements <em>Statements</em>}</li>
 * </ul>
 *
 * @see streamblocks.eclipse.cal.cal.CalPackage#getAstStatementForeach()
 * @model
 * @generated
 */
public interface AstStatementForeach extends AstStatement
{
  /**
   * Returns the value of the '<em><b>Generators</b></em>' containment reference list.
   * The list contents are of type {@link streamblocks.eclipse.cal.cal.AstForeachGenerator}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Generators</em>' containment reference list.
   * @see streamblocks.eclipse.cal.cal.CalPackage#getAstStatementForeach_Generators()
   * @model containment="true"
   * @generated
   */
  EList<AstForeachGenerator> getGenerators();

  /**
   * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
   * The list contents are of type {@link streamblocks.eclipse.cal.cal.AstVariable}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Variables</em>' containment reference list.
   * @see streamblocks.eclipse.cal.cal.CalPackage#getAstStatementForeach_Variables()
   * @model containment="true"
   * @generated
   */
  EList<AstVariable> getVariables();

  /**
   * Returns the value of the '<em><b>Statements</b></em>' containment reference list.
   * The list contents are of type {@link streamblocks.eclipse.cal.cal.AstStatement}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Statements</em>' containment reference list.
   * @see streamblocks.eclipse.cal.cal.CalPackage#getAstStatementForeach_Statements()
   * @model containment="true"
   * @generated
   */
  EList<AstStatement> getStatements();

} // AstStatementForeach
