/*
 * ----------------------------------------------------------------------------
 *  ____  _                            ____  _            _
 * / ___|| |_ _ __ ___  __ _ _ __ ___ | __ )| | ___   ___| | _____
 * \___ \| __| '__/ _ \/ _` | '_ ` _ \|  _ \| |/ _ \ / __| |/ / __|
 *  ___) | |_| | |  __/ (_| | | | | | | |_) | | (_) | (__|   <\__ \
 * |____/ \__|_|  \___|\__,_|_| |_| |_|____/|_|\___/ \___|_|\_\___/
 * ----------------------------------------------------------------------------
 * Copyright (c) 2020, Streamgenomics sarl
 * All rights reserved.
 * ----------------------------------------------------------------------------
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the Streamgenomics sarl nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package streamblocks.eclipse.cal.ui.highlighting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ide.editor.syntaxcoloring.HighlightingStyles;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class CalHighlightingConfiguration implements IHighlightingConfiguration {

	public static final String KEYWORD_ID = HighlightingStyles.KEYWORD_ID;

	public static final String PUNCTUATION_ID = HighlightingStyles.PUNCTUATION_ID;

	public static final String COMMENT_ID = HighlightingStyles.COMMENT_ID;

	public static final String STRING_ID = HighlightingStyles.STRING_ID;

	public static final String NUMBER_ID = HighlightingStyles.NUMBER_ID;

	public static final String DEFAULT_ID = HighlightingStyles.DEFAULT_ID;

	public static final String INVALID_TOKEN_ID = HighlightingStyles.INVALID_TOKEN_ID;

	public static final String TASK_ID = HighlightingStyles.TASK_ID;

	// -- CAL

	public static final String ACTOR_DECLARATION_ID = CalHighlightingStyles.ACTOR_DECLARATION_ID;

	public static final String ACTION_TAG_ID = CalHighlightingStyles.ACTION_TAG_ID;

	public static final String ENTITY_DECLARATION_ID = CalHighlightingStyles.ENTITY_DECLARATION_ID;

	public static final String ENTITY_REFERENCE_ID = CalHighlightingStyles.ENTITY_REFERENCE_ID;

	public static final String NETWORK_DECLARATION_ID = CalHighlightingStyles.NETWORK_DECLARATION_ID;

	public static final String GLOBAL_VARIABLE_DECLARATION_ID = CalHighlightingStyles.GLOBAL_VARIABLE_DECLARATION_ID;

	public static final String GLOBAL_VARIABLE_REFERENCE_ID = CalHighlightingStyles.GLOBAL_VARIABLE_REFERENCE_ID;

	public static final String NAMESPACE_VARIABLE_REFERENCE_ID = CalHighlightingStyles.NAMESPACE_VARIABLE_REFERENCE_ID;

	public static final String LOCAL_VARIABLE_DECLARATION_ID = CalHighlightingStyles.LOCAL_VARIABLE_DECLARATION_ID;

	public static final String LOCAL_VARIABLE_REFERNCE_ID = CalHighlightingStyles.LOCAL_VARIABLE_REFERNCE_ID;

	public static final String ARROWS_ID = CalHighlightingStyles.ARROWS_ID;

	public static final String PORT_ID = CalHighlightingStyles.PORT_ID;

	public static final String TYPE_PARAMETER_ID = CalHighlightingStyles.TYPE_PARAMETER_ID;

	public static final String TYPE_ID = CalHighlightingStyles.TYPE_ID;

	public static final String TYPE_REFERENCE_ID = CalHighlightingStyles.TYPE_REFERENCE_ID;

	public static final String CALLABLE_ID = CalHighlightingStyles.CALLABLE_ID;
	
	public static final String FSM_STATE_ID = CalHighlightingStyles.FSM_STATE_ID;

	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		acceptor.acceptDefaultHighlighting(KEYWORD_ID, "Keyword", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(PUNCTUATION_ID, "Punctuation character", punctuationTextStyle());
		acceptor.acceptDefaultHighlighting(COMMENT_ID, "Comment", commentTextStyle());
		acceptor.acceptDefaultHighlighting(TASK_ID, "Task Tag", taskTextStyle());
		acceptor.acceptDefaultHighlighting(STRING_ID, "String", stringTextStyle());
		acceptor.acceptDefaultHighlighting(NUMBER_ID, "Number", numberTextStyle());
		acceptor.acceptDefaultHighlighting(DEFAULT_ID, "Default", defaultTextStyle());
		acceptor.acceptDefaultHighlighting(INVALID_TOKEN_ID, "Invalid Symbol", errorTextStyle());
		acceptor.acceptDefaultHighlighting(ACTOR_DECLARATION_ID, "Actor Declaration", actorTextStyle());
		acceptor.acceptDefaultHighlighting(NETWORK_DECLARATION_ID, "Network Declaration", networkTextStyle());
		acceptor.acceptDefaultHighlighting(ENTITY_DECLARATION_ID, "Entity Declaration", entityTextStyle());
		acceptor.acceptDefaultHighlighting(ENTITY_REFERENCE_ID, "Entity Reference", entityReferenceTextStyle());
		acceptor.acceptDefaultHighlighting(ARROWS_ID, "Arrows", arrowsTextStyle());
		acceptor.acceptDefaultHighlighting(NAMESPACE_VARIABLE_REFERENCE_ID, "Namespace Variable Refence",
				namespaceVariableReferenceTextStyle());
		acceptor.acceptDefaultHighlighting(GLOBAL_VARIABLE_REFERENCE_ID, "Actor Variable Refence",
				globalVariableReferenceTextStyle());
		acceptor.acceptDefaultHighlighting(LOCAL_VARIABLE_REFERNCE_ID, "Local Variable Refence",
				localVariableReferenceTextStyle());
		acceptor.acceptDefaultHighlighting(ACTION_TAG_ID, "Action Tag", actionTagTextStyle());
		acceptor.acceptDefaultHighlighting(PORT_ID, "Port name", portTextStyle());
		acceptor.acceptDefaultHighlighting(TYPE_PARAMETER_ID, "Type parameter", keywordItalicTextStyle());
		acceptor.acceptDefaultHighlighting(CALLABLE_ID, "Function/Procedure name", callableTextStyle());
		acceptor.acceptDefaultHighlighting(TYPE_ID, "Type name", typeTextStyle(true));
		acceptor.acceptDefaultHighlighting(TYPE_REFERENCE_ID, "Type reference name", typeTextStyle(false));
		acceptor.acceptDefaultHighlighting(FSM_STATE_ID, "FSM State name", fsmStateTextStyle());
	}

	public TextStyle defaultTextStyle() {
		TextStyle textStyle = new TextStyle();
		return textStyle;
	}

	public TextStyle errorTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		return textStyle;
	}

	public TextStyle numberTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(125, 125, 125));
		return textStyle;
	}

	public TextStyle stringTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(42, 0, 255));
		return textStyle;
	}

	public TextStyle commentTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(63, 127, 95));
		return textStyle;
	}

	public TextStyle taskTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(127, 159, 191));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	public TextStyle keywordTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(186, 107, 39));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	public TextStyle keywordItalicTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(186, 107, 39));
		// textStyle.setStyle(SWT.BOLD | SWT.ITALIC);
		return textStyle;
	}

	public TextStyle punctuationTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		return textStyle;
	}

	public TextStyle actorTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(67, 153, 209));
		return textStyle;
	}

	public TextStyle networkTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(114, 216, 153));
		return textStyle;
	}

	public TextStyle arrowsTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(185, 126, 180));
		return textStyle;
	}

	public TextStyle entityTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(230, 227, 56));
		return textStyle;
	}

	public TextStyle entityReferenceTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(176, 229, 63));
		return textStyle;
	}

	public TextStyle localVariableReferenceTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(229, 222, 123));
		return textStyle;
	}

	public TextStyle globalVariableReferenceTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(124, 198, 221));
		return textStyle;
	}

	public TextStyle namespaceVariableReferenceTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(193, 168, 252));
		return textStyle;
	}

	public TextStyle actionTagTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(136, 219, 156));
		return textStyle;
	}

	public TextStyle portTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(165, 214, 62));
		return textStyle;
	}

	public TextStyle callableTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(73, 177, 73));
		return textStyle;
	}

	public TextStyle typeTextStyle(boolean bold) {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(76, 200, 59));
		if (bold)
			textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}
	
	public TextStyle fsmStateTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(151, 117, 168));
		
		return textStyle;
	}

}
