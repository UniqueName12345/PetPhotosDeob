package androidx.constraintlayout.solver.widgets;

import androidx.appcompat.widget.ActivityChooserView;
import androidx.constraintlayout.solver.Cache;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class WidgetContainer extends ConstraintWidget {
    protected ArrayList<ConstraintWidget> mChildren;

    public WidgetContainer() {
        this.mChildren = new ArrayList<>();
    }

    public WidgetContainer(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.mChildren = new ArrayList<>();
    }

    public WidgetContainer(int width, int height) {
        super(width, height);
        this.mChildren = new ArrayList<>();
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void reset() {
        this.mChildren.clear();
        super.reset();
    }

    public void add(ConstraintWidget widget) {
        this.mChildren.add(widget);
        if (widget.getParent() != null) {
            WidgetContainer container = (WidgetContainer) widget.getParent();
            container.remove(widget);
        }
        widget.setParent(this);
    }

    public void add(ConstraintWidget... widgets) {
        for (ConstraintWidget constraintWidget : widgets) {
            add(constraintWidget);
        }
    }

    public void remove(ConstraintWidget widget) {
        this.mChildren.remove(widget);
        widget.setParent(null);
    }

    public ArrayList<ConstraintWidget> getChildren() {
        return this.mChildren;
    }

    public ConstraintWidgetContainer getRootConstraintContainer() {
        ConstraintWidget parent = getParent();
        ConstraintWidgetContainer container = null;
        if (this instanceof ConstraintWidgetContainer) {
            container = (ConstraintWidgetContainer) this;
        }
        while (parent != null) {
            ConstraintWidget item = parent;
            parent = item.getParent();
            if (item instanceof ConstraintWidgetContainer) {
                container = (ConstraintWidgetContainer) item;
            }
        }
        return container;
    }

    public ConstraintWidget findWidget(float x, float y) {
        ConstraintWidget found = null;
        int l = getDrawX();
        int t = getDrawY();
        int r = l + getWidth();
        int b = t + getHeight();
        if (x >= l && x <= r && y >= t && y <= b) {
            found = this;
        }
        int mChildrenSize = this.mChildren.size();
        for (int i = 0; i < mChildrenSize; i++) {
            ConstraintWidget widget = this.mChildren.get(i);
            if (widget instanceof WidgetContainer) {
                ConstraintWidget f = ((WidgetContainer) widget).findWidget(x, y);
                if (f != null) {
                    found = f;
                }
            } else {
                int l2 = widget.getDrawX();
                int t2 = widget.getDrawY();
                int r2 = l2 + widget.getWidth();
                int b2 = t2 + widget.getHeight();
                if (x >= l2 && x <= r2 && y >= t2 && y <= b2) {
                    found = widget;
                }
            }
        }
        return found;
    }

    public ArrayList<ConstraintWidget> findWidgets(int x, int y, int width, int height) {
        ArrayList<ConstraintWidget> found = new ArrayList<>();
        Rectangle area = new Rectangle();
        area.setBounds(x, y, width, height);
        int mChildrenSize = this.mChildren.size();
        for (int i = 0; i < mChildrenSize; i++) {
            ConstraintWidget widget = this.mChildren.get(i);
            Rectangle bounds = new Rectangle();
            bounds.setBounds(widget.getDrawX(), widget.getDrawY(), widget.getWidth(), widget.getHeight());
            if (area.intersects(bounds)) {
                found.add(widget);
            }
        }
        return found;
    }

    public static Rectangle getBounds(ArrayList<ConstraintWidget> widgets) {
        Rectangle bounds = new Rectangle();
        if (widgets.size() != 0) {
            int minX = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            int maxX = 0;
            int minY = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            int maxY = 0;
            int widgetsSize = widgets.size();
            for (int i = 0; i < widgetsSize; i++) {
                ConstraintWidget widget = widgets.get(i);
                if (widget.getX() < minX) {
                    minX = widget.getX();
                }
                if (widget.getY() < minY) {
                    minY = widget.getY();
                }
                if (widget.getRight() > maxX) {
                    maxX = widget.getRight();
                }
                if (widget.getBottom() > maxY) {
                    maxY = widget.getBottom();
                }
            }
            bounds.setBounds(minX, minY, maxX - minX, maxY - minY);
        }
        return bounds;
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void setOffset(int x, int y) {
        super.setOffset(x, y);
        int count = this.mChildren.size();
        for (int i = 0; i < count; i++) {
            ConstraintWidget widget = this.mChildren.get(i);
            widget.setOffset(getRootX(), getRootY());
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void updateDrawPosition() {
        super.updateDrawPosition();
        if (this.mChildren != null) {
            int count = this.mChildren.size();
            for (int i = 0; i < count; i++) {
                ConstraintWidget widget = this.mChildren.get(i);
                widget.setOffset(getDrawX(), getDrawY());
                if (!(widget instanceof ConstraintWidgetContainer)) {
                    widget.updateDrawPosition();
                }
            }
        }
    }

    public void layout() {
        updateDrawPosition();
        if (this.mChildren != null) {
            int count = this.mChildren.size();
            for (int i = 0; i < count; i++) {
                ConstraintWidget widget = this.mChildren.get(i);
                if (widget instanceof WidgetContainer) {
                    ((WidgetContainer) widget).layout();
                }
            }
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void resetSolverVariables(Cache cache) {
        super.resetSolverVariables(cache);
        int count = this.mChildren.size();
        for (int i = 0; i < count; i++) {
            ConstraintWidget widget = this.mChildren.get(i);
            widget.resetSolverVariables(cache);
        }
    }

    public void removeAllChildren() {
        this.mChildren.clear();
    }
}
