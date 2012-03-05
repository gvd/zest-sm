package com.ghvandoorn.zest.statemachine.providers;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;

import com.ghvandoorn.xtext.statemachine.dsl.State;
import com.ghvandoorn.xtext.statemachine.dsl.Transition;

public class StatemachineLabelProvider extends LabelProvider implements ILabelProvider {
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
}
