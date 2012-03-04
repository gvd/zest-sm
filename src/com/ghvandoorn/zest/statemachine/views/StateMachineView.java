package com.ghvandoorn.zest.statemachine.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.zest.core.viewers.GraphViewer;

public class StateMachineView extends ViewPart implements IPartListener {

	private GraphViewer mViewer = null;
	private XtextEditor mCurrentXtextEditor = null;

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

	@Override
	public void partActivated(IWorkbenchPart part) {
		IEditorPart editor = part.getSite().getPage().getActiveEditor();
		if(editor != null && editor instanceof XtextEditor){
			if (editor != mCurrentXtextEditor) { // In case we activate another editor/view
				mCurrentXtextEditor = (XtextEditor)editor;
				IXtextDocument document = mCurrentXtextEditor.getDocument();
				drawGraph(document);
			}
		}
	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		IPartService service = (IPartService) getSite().getService(IPartService.class);
		service.addPartListener(this);
	}

	private void drawGraph(IXtextDocument document) {
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
	}
}