/*
 * generated by Xtext 2.21.0
 */
package streamblocks.eclipse.cal.parser.antlr;

import com.google.inject.Inject;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import streamblocks.eclipse.cal.parser.antlr.internal.InternalCalParser;
import streamblocks.eclipse.cal.services.CalGrammarAccess;

public class CalParser extends AbstractAntlrParser {

	@Inject
	private CalGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalCalParser createParser(XtextTokenStream stream) {
		return new InternalCalParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "CompilationUnit";
	}

	public CalGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(CalGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
