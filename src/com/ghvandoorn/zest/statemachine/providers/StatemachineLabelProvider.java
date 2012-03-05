package com.ghvandoorn.zest.statemachine.providers;

import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.zest.core.viewers.IEntityConnectionStyleProvider;
import org.eclipse.zest.core.widgets.ZestStyles;

import com.ghvandoorn.xtext.statemachine.dsl.State;
import com.ghvandoorn.xtext.statemachine.dsl.Transition;

public class StatemachineLabelProvider extends LabelProvider implements ILabelProvider, IEntityConnectionStyleProvider {
	@Override
	public String getText(Object element) {
		if (element instanceof State) {
			State state = (State)element;
			return state.getName();
		} else if (element instanceof Transition) {
			Transition transition = (Transition)element;
			return transition.getEvent().getName();
		}
		return null;
	}

	@Override
	public int getConnectionStyle(Object src, Object dest) {
		return ZestStyles.CONNECTIONS_DIRECTED;
	}

	@Override
	public Color getColor(Object src, Object dest) {
		return null;
	}

	@Override
	public Color getHighlightColor(Object src, Object dest) {
		return null;
	}

	@Override
	public int getLineWidth(Object src, Object dest) {
		return 0;
	}

	@Override
	public IFigure getTooltip(Object src, Object dest) {
		return null;
	}

	@Override
	public ConnectionRouter getRouter(Object src, Object dest) {
		return null;
	}
}
