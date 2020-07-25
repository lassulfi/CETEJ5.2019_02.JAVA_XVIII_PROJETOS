package br.utfpr.vaadin;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("todo")
public class TodoView extends VerticalLayout {

    public TodoView() {
        VerticalLayout todosList = new VerticalLayout();
        VerticalLayout todosDone = new VerticalLayout();
        TextField taskField = new TextField();
        Button addButton = new Button("Adicionar");
        addButton.addClickShortcut(Key.ENTER);
        addButton.addClickListener(click -> {
            Checkbox checkbox = new Checkbox(taskField.getValue());
            todosList.add(checkbox);
            checkbox.addClickListener(check -> {
                Span obj = new Span(checkbox.getLabel());
                todosDone.add(obj);
                todosList.remove(checkbox);
                taskField.focus();
            });
            taskField.setValue("");
            taskField.focus();
        });
        add(
                new H1("Lista de Tarefas"),
                new HorizontalLayout(
                        taskField,
                        addButton
                ),
                todosList,
                new H1("Tarefas concluídas"),
                todosDone
        );
    }
}
