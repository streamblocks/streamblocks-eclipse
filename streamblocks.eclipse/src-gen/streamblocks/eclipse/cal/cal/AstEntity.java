/**
 * generated by Xtext 2.21.0
 */
package streamblocks.eclipse.cal.cal;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ast Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link streamblocks.eclipse.cal.cal.AstEntity#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link streamblocks.eclipse.cal.cal.AstEntity#getActor <em>Actor</em>}</li>
 * </ul>
 *
 * @see streamblocks.eclipse.cal.cal.CalPackage#getAstEntity()
 * @model
 * @generated
 */
public interface AstEntity extends EObject
{
  /**
   * Returns the value of the '<em><b>Annotations</b></em>' containment reference list.
   * The list contents are of type {@link streamblocks.eclipse.cal.cal.AstAnnotation}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Annotations</em>' containment reference list.
   * @see streamblocks.eclipse.cal.cal.CalPackage#getAstEntity_Annotations()
   * @model containment="true"
   * @generated
   */
  EList<AstAnnotation> getAnnotations();

  /**
   * Returns the value of the '<em><b>Actor</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Actor</em>' containment reference.
   * @see #setActor(AstAbstractActor)
   * @see streamblocks.eclipse.cal.cal.CalPackage#getAstEntity_Actor()
   * @model containment="true"
   * @generated
   */
  AstAbstractActor getActor();

  /**
   * Sets the value of the '{@link streamblocks.eclipse.cal.cal.AstEntity#getActor <em>Actor</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Actor</em>' containment reference.
   * @see #getActor()
   * @generated
   */
  void setActor(AstAbstractActor value);

} // AstEntity
