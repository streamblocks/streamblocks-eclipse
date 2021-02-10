package streamblocks.eclipse.cal.ui.xdf.diagram;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

public class StreamBlocksDiagramTypeProvider extends AbstractDiagramTypeProvider {
	private IToolBehaviorProvider[] toolBehaviorProviders;

	public StreamBlocksDiagramTypeProvider() {
		super();
		setFeatureProvider(new XdfDiagramFeatureProvider(this));
	}

	@Override
	public IToolBehaviorProvider[] getAvailableToolBehaviorProviders() {
		if (toolBehaviorProviders == null) {
			toolBehaviorProviders = new IToolBehaviorProvider[] { new XdfDiagramToolBehaviorProvider(this) };
		}
		return toolBehaviorProviders;
	}

	@Override
	public boolean isAutoUpdateAtStartup() {
		return true;
	}
}
