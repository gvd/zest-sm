package com.ghvandoorn.zest.statemachine.views;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.GraphViewer;

public class StateMachineView extends ViewPart {

	private GraphViewer mViewer = null;

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.ghvandoorn.zest.statemachine.views.StateMachineView";

	/**
	 * The constructor.
	 */
	public StateMachineView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		mViewer = new GraphViewer(parent, SWT.BORDER);

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(mViewer.getControl(), "com.ghvandoorn.zest.statemachine.viewer");
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}
}