/*
 * generated by Xtext 2.23.0
 */
package streamblocks.eclipse.cal.web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.xtext.util.Modules2;
import streamblocks.eclipse.cal.CalRuntimeModule;
import streamblocks.eclipse.cal.CalStandaloneSetup;
import streamblocks.eclipse.cal.ide.CalIdeModule;

/**
 * Initialization support for running Xtext languages in web applications.
 */
public class CalWebSetup extends CalStandaloneSetup {
	
	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new CalRuntimeModule(), new CalIdeModule(), new CalWebModule()));
	}
	
}
