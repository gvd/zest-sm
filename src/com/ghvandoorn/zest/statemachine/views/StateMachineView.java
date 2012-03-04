package com.ghvandoorn.zest.statemachine.views;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.eclipse.zest.core.viewers.GraphViewer;

import com.ghvandoorn.xtext.statemachine.dsl.StateMachine;
import com.ghvandoorn.zest.statemachine.providers.StatemachineContentProvider;

public class StateMachineView extends ViewPart implements IPartListener, IExecutionListener {

	private GraphViewer mViewer = null;
	private XtextEditor mCurrentXtextEditor = null;
	private IXtextDocument mCurrentDocument = null;

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
		mViewer.setContentProvider(new StatemachineContentProvider());

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
				mCurrentDocument = mCurrentXtextEditor.getDocument();
				drawGraph(mCurrentDocument);
			}
		}
	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		IPartService partService = (IPartService) getSite().getService(IPartService.class);
		partService.addPartListener(this);
		ICommandService cmdService = (ICommandService) getSite().getService(ICommandService.class);
		cmdService.addExecutionListener(this);
	}

	private void drawGraph(IXtextDocument document) {
		document.readOnly(new IUnitOfWork<Boolean, XtextResource>(){
			@Override
			public Boolean exec(final XtextResource resource) throws Exception {
				EcoreUtil.resolveAll(resource); // Resolve all errors
				if (!resource.getErrors().isEmpty()) {
					return false;
				}
				//resource.
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						EObject eobject = resource.getContents().get(0);
						if (eobject != null && eobject instanceof StateMachine) {
							mViewer.setInput(eobject);
							mViewer.applyLayout();
						}
					}
				});
				return true;
			}
		});
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

	@Override
	public void notHandled(String commandId, NotHandledException exception) {
	}

	@Override
	public void postExecuteFailure(String commandId,
			ExecutionException exception) {
	}

	@Override
	public void postExecuteSuccess(String commandId, Object returnValue) {
		if (commandId.equals(IWorkbenchCommandConstants.FILE_SAVE)) {
			drawGraph(mCurrentDocument);
		}
	}

	@Override
	public void preExecute(String commandId, ExecutionEvent event) {
	}
}