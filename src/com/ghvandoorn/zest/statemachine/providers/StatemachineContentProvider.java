package com.ghvandoorn.zest.statemachine.providers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.zest.core.viewers.IGraphEntityRelationshipContentProvider;

import com.ghvandoorn.xtext.statemachine.dsl.State;
import com.ghvandoorn.xtext.statemachine.dsl.StateMachine;
import com.ghvandoorn.xtext.statemachine.dsl.Transition;

public class StatemachineContentProvider implements IGraphEntityRelationshipContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		List<Object> objects = new ArrayList<Object>();
		if (inputElement instanceof StateMachine) {
			StateMachine sm = (StateMachine)inputElement;
			for (State state : sm.getStates()) {
				objects.add(state);
			}
		}
		return objects.toArray();
	}

	public void traverseState(State state, HashSet<Transition> traversedTransitions, HashSet<Object> states) {
		if (!states.contains(state)) {
			states.add(state);
		}
		for (Transition transition : state.getTransitions()) {
			if (!traversedTransitions.contains(transition)) {
				traversedTransitions.add(transition);
				traverseState(transition.getState(), traversedTransitions, states);
			}
		}
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getRelationships(Object source, Object dest) {
		if (source instanceof State && dest instanceof State) {
			State state1 = (State) source;
			State state2 = (State) dest;
			List<Transition> ts = new ArrayList<Transition>();
			for (Transition t : state1.getTransitions()) {
				if (t.getState().getName().equals(state2.getName())) {
					ts.add(t);
				}
			}
			return ts.toArray();
		}
		return new Object[] {};
	}
}
