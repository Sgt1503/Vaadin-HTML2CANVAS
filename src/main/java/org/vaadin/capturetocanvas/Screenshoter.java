package org.vaadin.capturetocanvas;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.dom.DomListenerRegistration;
import com.vaadin.flow.dom.Element;
import org.vaadin.HTMLtoCANVAS.Util;

import java.util.concurrent.CompletableFuture;

@JavaScript("./screenshot.js")
public class Screenshoter {
    public static CompletableFuture screenshot() {
        UI ui = UI.getCurrent();
        Element e = ui.getElement();
        CompletableFuture<String> future = new CompletableFuture<>();
        e.executeJs("let element = $0;\n" +
                "function send(canvas) {\n" +
                "   blobValue = canvas.toDataURL();\n" +
                "   element.dispatchEvent(new Event('blobReady'));\n" +
                "}\n" +
                "capture().then((canvas) => send(canvas));", e);
        DomListenerRegistration blobReady = e.addEventListener("blobReady", l -> {
            Util.getJavaScriptReturn(e.getNode(), "blobValue.valueOf()").then(jsonValue -> future.complete(jsonValue.asString()));
        });
        future.thenRun(() -> blobReady.remove());
        return future;
    }
}
