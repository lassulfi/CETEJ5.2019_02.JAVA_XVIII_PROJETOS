package br.utfpr.formularios.ui.components;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;

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

    }

    private void createContent() {

    }

    private void createFooter() {
    }
}
