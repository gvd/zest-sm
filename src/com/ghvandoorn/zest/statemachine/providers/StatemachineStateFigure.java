package com.ghvandoorn.zest.statemachine.providers;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.swt.widgets.Display;

public class StatemachineStateFigure extends Ellipse {

	public enum Type { REGULAR, FINAL, INITIAL }

	private Type mType = Type.REGULAR;
	private Ellipse mInnerCircle = null;
	private Label mLabel = null;

	public StatemachineStateFigure(String text) {
		this(text, Type.REGULAR);
	}

	public StatemachineStateFigure(String text, Type type) {
		super();
		mType = type;
		if (mType == Type.INITIAL) {
			this.setBackgroundColor(ColorConstants.darkGray);
		}
		this.setForegroundColor(ColorConstants.black);
		this.setBorder(new MarginBorder(4));
		this.setLayoutManager(new StackLayout());
		((Shape) this).setLineWidth(1);
		((Shape) this).setAntialias(1);

		mLabel = new Label(text);
		mLabel.setForegroundColor(ColorConstants.black);
		mLabel.setFont(Display.getCurrent().getActiveShell().getFont());

		if (mType == Type.FINAL || mType == Type.INITIAL) {
			mInnerCircle = new Ellipse();
			mInnerCircle.setBorder(new MarginBorder(4));
			mInnerCircle.setBackgroundColor(ColorConstants.white);
			mInnerCircle.setLayoutManager(new StackLayout());
			((Shape) mInnerCircle).setLineWidth(1);
			((Shape) mInnerCircle).setAntialias(1);
			mInnerCircle.add(mLabel);
			this.add(mInnerCircle);
		} else {
			this.add(mLabel);
		}
		int preferredWidth = getPreferredSize().width();
		int width = Math.max(40, preferredWidth);
		this.setSize(width, width);
	}
}
