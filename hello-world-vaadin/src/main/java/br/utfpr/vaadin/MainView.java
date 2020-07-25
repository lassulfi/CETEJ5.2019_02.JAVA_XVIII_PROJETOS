package br.utfpr.vaadin;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@PWA(name="Vaadin Application", shortName = "Vaadin App",
        description = "This is an example Vaddin Application", enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "text-field")
public class MainView extends VerticalLayout {
    public MainView(@Autowired GreetService service) {
        TextField textField = new TextField("Informe seu nome");
        textField.addThemeName("bordered");
        Button button = new Button("Clique",
                event -> Notification.show(service.greet(textField.getValue())));
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClickShortcut(Key.ENTER);
        addClassName("centered-content");
        add(textField, button);
    }
}
