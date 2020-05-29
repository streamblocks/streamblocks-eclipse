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
package streamblocks.eclipse.cal.ui.hyperlinking;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkAcceptor;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import streamblocks.eclipse.cal.cal.AstAction;
import streamblocks.eclipse.cal.cal.AstActor;
import streamblocks.eclipse.cal.cal.AstInequality;
import streamblocks.eclipse.cal.cal.AstTag;
import streamblocks.eclipse.cal.cal.AstTransition;

public class CalHyperlinkHelper extends HyperlinkHelper {
	@Inject
	private EObjectAtOffsetHelper eObjectAtOffsetHelper;

	@Override
	public IHyperlink[] createHyperlinksByOffset(XtextResource resource, int offset, boolean createMultipleHyperlinks) {

		final EObject obj = eObjectAtOffsetHelper.resolveContainedElementAt(resource, offset);
		final EObject container = obj.eContainer();

		if (obj instanceof AstTag && (container instanceof AstTransition || container instanceof AstInequality)) {
			// Ok, user ctrl+click (or similar) on a transition/priority tag,
			// try to find corresponding actions

			final List<IHyperlink> links = Lists.newArrayList();
			final IHyperlinkAcceptor acceptor = new HyperlinkAcceptor(links);

			final String currentTag = TagtoString(((AstTag) obj).getIdentifiers(), ".");
			final ICompositeNode tagNode = NodeModelUtils.getNode(obj);
			final Region tagRegion = new Region(tagNode.getOffset(), tagNode.getLength());

			final AstActor actor = EcoreUtil2.getContainerOfType(obj, AstActor.class);
			for (final AstAction action : actor.getActions()) {
				// We don't care untagged actions
				if (action.getTag() == null)
					continue;

				final String actionName = TagtoString(action.getTag().getIdentifiers(), ".");
				if (actionName.equals(currentTag) || actionName.startsWith(currentTag + '.')) {
					createHyperlinksTo(resource, tagRegion, action, acceptor);
				}
			}
			// We must not return an empty array or eclipse will throws assert
			// exceptions
			return links.isEmpty() ? null : Iterables.toArray(links, IHyperlink.class);
		}

		return super.createHyperlinksByOffset(resource, offset, createMultipleHyperlinks);
	};

	public static String TagtoString(Iterable<? extends Object> objects, String sep) {
		StringBuilder builder = new StringBuilder();
		Iterator<? extends Object> it = objects.iterator();
		if (it.hasNext()) {
			builder.append(it.next());
			while (it.hasNext()) {
				builder.append(sep);
				builder.append(it.next());
			}
		}

		return builder.toString();
	}

}
