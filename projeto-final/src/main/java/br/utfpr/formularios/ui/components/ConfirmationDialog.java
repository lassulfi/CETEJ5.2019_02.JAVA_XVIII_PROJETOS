package br.utfpr.formularios.ui.components;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ConfirmationDialog extends Dialog {
    
    private Label title;
    private Label question;
    private Button confirm;
    
    public ConfirmationDialog() {
        createHeader();
        createContent();
        createFooter();
    }

    public ConfirmationDialog(String title, String content, ComponentEventListener listener) {
        this();
        setTitle(title);
        setQuestion(content);
        addConfirmationListener(listener);
    }

    public void setTitle(String title) {
        this.title.setTitle(title);
    }

    public void setQuestion(String question) {
        this.question.setText(question);
    }

    public void addConfirmationListener(ComponentEventListener listener) {
        confirm.addClickListener(listener);
    }

    private void createHeader() {
        this.title = new Label();

        Button closeButton = new Button();
        closeButton.setIcon(VaadinIcon.CLOSE.create());
        closeButton.addClickListener(click -> close());

        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.add(this.title, closeButton);
        headerLayout.getStyle().set("text-weight", "bold");
        headerLayout.setFlexGrow(1, this.title);
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        add(headerLayout);
    }

    private void createContent() {
        this.question = new Label();

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.add(this.question);
        add(contentLayout);
    }

    private void createFooter() {
        Button abortButton = new Button("Cancelar");
        abortButton.addClickListener(click -> close());

        this.confirm = new Button("Confirmar");
        this.confirm.addClickListener(click -> close());
        this.confirm.addThemeVariants(ButtonVariant.LUMO_ERROR);

        HorizontalLayout footerLayout = new HorizontalLayout();
        footerLayout.add(abortButton, this.confirm);
        footerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        add(footerLayout);
    }
}
