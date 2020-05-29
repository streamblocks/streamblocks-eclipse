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
package streamblocks.eclipse.cal.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

import streamblocks.eclipse.cal.cal.AstAction;
import streamblocks.eclipse.cal.cal.AstInequality;
import streamblocks.eclipse.cal.cal.AstPort;
import streamblocks.eclipse.cal.cal.AstState;
import streamblocks.eclipse.cal.cal.AstTag;
import streamblocks.eclipse.cal.cal.AstTransition;
import streamblocks.eclipse.cal.cal.AstType;
import streamblocks.eclipse.cal.cal.AstVariable;

/**
 * Provides labels for EObjects.
 * 
 * See https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#label-provider
 */
public class CalLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	public CalLabelProvider(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	public String text(AstAction action) {
		AstTag tag = action.getTag();
		if (tag == null) {
			return "(untagged)";
		} else {
			return concatenateTags(action.getTag());
		}
	}

	public String text(AstVariable var) {
		return var.getName() + " - " + doType(var.getType()); 
	}

	public String text(AstPort port) {
		return port.getName() + " - " + doType(port.getType()); 
	}
	
	public String text(AstState state) {
		return state.getName();
	}

	public String text(AstTransition transition) {
		return getText(transition.getSource()) + " --> "
				+ getText(transition.getTarget());
	}
	
	public String text(AstInequality inequality) {
		String result = concatenateTags(inequality.getTags().get(0));
		result += " > ";
		for (int i = 1; i < inequality.getTags().size(); i++) {
			result += concatenateTags(inequality.getTags().get(i));
			if (i < inequality.getTags().size() - 1)
				result += ", ";
		}
		return result;
	}
	
	private String concatenateTags(AstTag tag) {
		String id = tag.getIdentifiers().get(0);
		for (int i = 1; i < tag.getIdentifiers().size(); i++) 
			id = id + "." + tag.getIdentifiers().get(i);
		return id;
	}
	
	private String doType(AstType type) {
		if (type == null) 
			return "<undef>";
		if (type.getBuiltin() != null) {
			return type.getBuiltin();
		} else {
			return type.getName().getName();
		}
	}
	
}
