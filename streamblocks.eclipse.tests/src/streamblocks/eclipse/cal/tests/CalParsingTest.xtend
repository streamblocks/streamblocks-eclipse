/*
 * generated by Xtext 2.21.0
 */
package streamblocks.eclipse.cal.tests

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith
import streamblocks.eclipse.cal.cal.CompilationUnit

@ExtendWith(InjectionExtension)
@InjectWith(CalInjectorProvider)
class CalParsingTest {
	@Inject
	ParseHelper<CompilationUnit> parseHelper
	
	@Test
	def void loadModel() {
		val result = parseHelper.parse('''
			namespace test: end
		''')
		Assertions.assertNotNull(result)
		val errors = result.eResource.errors
		Assertions.assertTrue(errors.isEmpty, '''Unexpected errors: «errors.join(", ")»''')
	}
}
