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

import org.eclipse.xtext.ide.editor.syntaxcoloring.HighlightingStyles;

public interface CalHighlightingStyles extends HighlightingStyles {
	
	static final String ENTITY_DECLARATION_ID = "entity_declaration";
	
	static final String ENTITY_REFERENCE_ID = "entity_reference_declaration";

	static final String ACTOR_DECLARATION_ID = "actor_declaration";

	static final String ACTION_TAG_ID = "actor_tag";
	
	static final String NETWORK_DECLARATION_ID = "network_declaration";

	static final String GLOBAL_VARIABLE_DECLARATION_ID = "global_variable_declaration";
	
	static final String NAMESPACE_VARIABLE_REFERENCE_ID = "namespace_variable";

	static final String GLOBAL_VARIABLE_REFERENCE_ID = "global_variable";

	static final String LOCAL_VARIABLE_DECLARATION_ID = "local_variable_declaration";

	static final String LOCAL_VARIABLE_REFERNCE_ID = "local_variable";
	
	static final String ARROWS_ID = "arrows";
	
	static final String PORT_ID = "port";
	
	static final String CALLABLE_ID = "callable";
	
	static final String TYPE_PARAMETER_ID = "type_parameter";
	
	static final String TYPE_ID = "type";
	
	static final String TYPE_REFERENCE_ID = "type_reference";
	
	static final String FSM_STATE_ID = "fsm_state";
}
