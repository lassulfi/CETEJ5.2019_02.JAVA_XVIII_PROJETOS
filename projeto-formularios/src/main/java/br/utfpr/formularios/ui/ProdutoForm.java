package br.utfpr.formularios.ui;

import br.utfpr.formularios.model.Produto;
import br.utfpr.formularios.ui.ProdutoForm.ProdutoFormEvent.CancelEvent;
import br.utfpr.formularios.ui.ProdutoForm.ProdutoFormEvent.DeleteEvent;
import br.utfpr.formularios.ui.ProdutoForm.ProdutoFormEvent.SaveEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

@Route("produtos/cadastro/")
public class ProdutoForm extends FormLayout {
    private TextField nomeProdutoTextField = new TextField("Nome do produto");
    private TextArea descricaoProdutoTextArea = new TextArea("Descrição do produto");
    private TextField fornecedorTextField = new TextField("Fornecedor");
    private EmailField fornecedorEmailField = new EmailField("E-mail do fornecedor");
    private DatePicker dataUtimaCompra = new DatePicker("Última compra");
    private BigDecimalField precoDecimalField = new BigDecimalField("Preço");
    private ComboBox<Produto.Status> status = new ComboBox<>("Status");

    private Button cadastrarButton = new Button("Cadastrar");
    private Button excluirButton = new Button("Excluir");
    private Button cancelarButton = new Button("Cancelar");

    private Binder<Produto> binder = new BeanValidationBinder<>(Produto.class);
    private Produto produto;

    public ProdutoForm() {
        binder.bindInstanceFields(this);
        status.setItems(Produto.Status.values());

        precoDecimalField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        precoDecimalField.setPrefixComponent(new Icon(VaadinIcon.DOLLAR));

        add(nomeProdutoTextField,
            descricaoProdutoTextArea,
            fornecedorTextField,
            fornecedorEmailField,
            dataUtimaCompra,
            precoDecimalField,
            status,
            createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        cadastrarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        excluirButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelarButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        cadastrarButton.addClickShortcut(Key.ENTER);
        cancelarButton.addClickShortcut(Key.ESCAPE);

        cadastrarButton.addClickListener(click -> validateAndSave());
        excluirButton.addClickListener(click -> fireEvent(new DeleteEvent(this, produto)));
        cancelarButton.addClickListener(click -> fireEvent(new CancelEvent(this)));

        return new HorizontalLayout(cadastrarButton, excluirButton, cancelarButton);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(produto);
            fireEvent(new SaveEvent(this, produto));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public abstract static class ProdutoFormEvent extends ComponentEvent<ProdutoForm> {
        private Produto mProduto;

        public ProdutoFormEvent(ProdutoForm source, Produto produto) {
            super(source, false);
            this.mProduto = produto;
        }


        public Produto getProduto() {
            return this.mProduto;
        }

        public static class SaveEvent extends ProdutoFormEvent {
            public SaveEvent(ProdutoForm source, Produto produto) {
                super(source, produto);
            }
        }

        public static class DeleteEvent extends ProdutoFormEvent {
            public DeleteEvent(ProdutoForm source, Produto produto) {
                super(source, produto);
            }
        }

        public static class CancelEvent extends ProdutoFormEvent {
            public CancelEvent(ProdutoForm source) {
                super(source, null);
            }
        }

        public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                      ComponentEventListener<T> listener) {
            return getEventBus().addListener(eventType, listener);
        }
    }
}
