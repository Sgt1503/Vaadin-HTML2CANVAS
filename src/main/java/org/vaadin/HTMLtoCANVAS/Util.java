package org.vaadin.HTMLtoCANVAS;

import com.vaadin.flow.component.internal.PendingJavaScriptInvocation;
import com.vaadin.flow.component.internal.UIInternals;
import com.vaadin.flow.internal.StateNode;

public class Util {
    public static PendingJavaScriptInvocation getJavaScriptReturn(StateNode node, String expression) {
        UIInternals.JavaScriptInvocation invocation = new UIInternals.JavaScriptInvocation("return " + expression);
        PendingJavaScriptInvocation pending = new PendingJavaScriptInvocation(node, invocation);
        node.runWhenAttached((ui) -> {
            ui.getInternals().getStateTree().beforeClientResponse(node, (context) -> {
                if (!pending.isCanceled()) {
                    context.getUI().getInternals().addJavaScriptInvocation(pending);
                }
            });
        });
        return pending;
    }

    public static PendingJavaScriptInvocation getJavaScriptInvoke(StateNode node, String expression) {
        UIInternals.JavaScriptInvocation invocation = new UIInternals.JavaScriptInvocation(expression);
        PendingJavaScriptInvocation pending = new PendingJavaScriptInvocation(node, invocation);
        node.runWhenAttached((ui) -> {
            ui.getInternals().getStateTree().beforeClientResponse(node, (context) -> {
                if (!pending.isCanceled()) {
                    context.getUI().getInternals().addJavaScriptInvocation(pending);
                }
            });
        });
        return pending;
    }
}
