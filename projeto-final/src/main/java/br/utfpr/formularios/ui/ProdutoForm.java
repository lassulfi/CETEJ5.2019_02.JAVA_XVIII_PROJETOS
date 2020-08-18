package br.utfpr.formularios.ui;

import br.utfpr.formularios.model.Produto;
import br.utfpr.formularios.services.ProdutoService;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Locale;

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

    private MainView mainView;
    private ProdutoService produtoService;

    public ProdutoForm(MainView mainView, @Autowired ProdutoService produtoService) {
        this.mainView = mainView;
        this.produtoService = produtoService;

        binder.bindInstanceFields(this);
        status.setItems(Produto.Status.values());

        configurePrecoField();
        configureDataUltimaCompraField();

        add(nomeProdutoTextField,
            descricaoProdutoTextArea,
            fornecedorTextField,
            fornecedorEmailField,
            dataUtimaCompra,
            precoDecimalField,
            status,
            createButtonsLayout());
    }

    public void setProduto(Produto produto) {
        binder.setBean(produto);
        if(produto == null) {
            setVisible(false);
        } else {
            setVisible(true);
            if(binder.getBean().isPersisted()) {
                excluirButton.setVisible(true);
            } else {
                excluirButton.setVisible(false);
            }
            nomeProdutoTextField.focus();
        }
    }

    private void configurePrecoField() {
        precoDecimalField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        precoDecimalField.setPrefixComponent(new Icon(VaadinIcon.DOLLAR));
    }

    private void configureDataUltimaCompraField() {
        dataUtimaCompra.setAutoOpen(true);
        dataUtimaCompra.setLocale(new Locale("pt", "BR"));
        dataUtimaCompra.setMin(LocalDate.now().minusMonths(1L));
        dataUtimaCompra.setMax(LocalDate.now());
    }

    private HorizontalLayout createButtonsLayout() {
        cadastrarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        excluirButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelarButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        cadastrarButton.addClickShortcut(Key.ENTER);
        cancelarButton.addClickShortcut(Key.ESCAPE);

        cadastrarButton.addClickListener(click -> save());
        excluirButton.addClickListener(click -> fireEvent(new DeleteEvent(this, produto)));
        cancelarButton.addClickListener(click -> fireEvent(new CancelEvent(this)));

        return new HorizontalLayout(cadastrarButton, excluirButton, cancelarButton);
    }

    private void save() {
        Produto produto = binder.getBean();
        produtoService.save(produto);
        mainView.updateList();
        setProduto(null);
    }

    private void delete() {
        Produto produto = binder.getBean();

    }
}
