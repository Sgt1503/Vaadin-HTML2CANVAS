package org.vaadin.HTMLtoCANVAS;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.router.Route;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Route("")
public class AddonView extends Div {

    public AddonView() {
        Button button = new Button("Click here");
        button.addClickListener(l -> {
            CompletableFuture<String> completableFuture = HTML2CANVAS.takeScreenShot(button.getElement());
            completableFuture.thenRun(() -> {
                try {
                    Image image = new Image(completableFuture.get(), "adfs");
                    add(image);
                } catch (InterruptedException | ExecutionException ignored) {
                }
            });
        });
        add(button);
    }
}
