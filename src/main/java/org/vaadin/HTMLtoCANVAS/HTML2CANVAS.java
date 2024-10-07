package org.vaadin.HTMLtoCANVAS;

import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.internal.StateNode;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.CompletableFuture;

@NpmPackage(value = "html2canvas",version = "1.4.1")
@JavaScript("./html2canvas-loader.js")
public class HTML2CANVAS extends Div {

    public static CompletableFuture takeScreenShot(Element e){
        CompletableFuture<String> future = new CompletableFuture<>();
        e.executeJs("let element = $0;\n" +
            "async function  makeScreenshot() \n" +
            "{\n" +
            "  return new Promise((resolve, reject) => {  \n" +
            "    resolve(html2canvas(element));\n" +
            "  });\n" +
            "}\n" +
            "\n" +
            "function send(canvas) {\n" +
            "   blobValue = canvas.toDataURL();\n" +
            "    element.dispatchEvent(new Event('blobReady'));\n" +
            "}\n" +
            "\n" +
            "makeScreenshot().then((canvas) =>{\n" +
            "  send(canvas);\n" +
            "});\n" +
            "\n", e);
        e.addEventListener("blobReady", l -> {
            Util.getJavaScriptReturn(e.getNode(), "blobValue.valueOf()").then(jsonValue -> future.complete(jsonValue.asString()));
        });
        return future;
    }
}
