package br.utfpr.formularios.ui;

import br.utfpr.formularios.model.Produto;
import br.utfpr.formularios.services.ProdutoService;
import br.utfpr.formularios.ui.components.ConfirmationDialog;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Locale;

public class ProdutoForm extends FormLayout {
    private TextField nome = new TextField("Nome do produto");
    private TextArea descricao = new TextArea("Descrição do produto");
    private TextField fornecedor = new TextField("Fornecedor");
    private EmailField emailFornecedor = new EmailField("E-mail do fornecedor");
    private DatePicker dataUltimaCompra = new DatePicker("Última compra");
    private BigDecimalField preco = new BigDecimalField("Preço");
    private ComboBox<Produto.Status> status = new ComboBox<>("Status");

    private Button cadastrarButton = new Button("Cadastrar");
    private Button excluirButton = new Button("Excluir");
    private Button cancelarButton = new Button("Cancelar");

    private ConfirmationDialog confirmationDialog = new ConfirmationDialog();

    private Binder<Produto> binder = new BeanValidationBinder<>(Produto.class);

    private MainView mMainView;
    private ProdutoService mProdutoService;

    public ProdutoForm(MainView mainView, @Autowired ProdutoService produtoService) {
        this.mMainView = mainView;
        this.mProdutoService = produtoService;

        binder.bindInstanceFields(this);
        status.setItems(Produto.Status.values());

        configurePrecoField();
        configureDataUltimaCompraField();

        add(nome,
                descricao,
                fornecedor,
                emailFornecedor,
                dataUltimaCompra,
                preco,
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
            nome.focus();
        }
    }

    private void configurePrecoField() {
        preco.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        preco.setPrefixComponent(new Icon(VaadinIcon.DOLLAR));
    }

    private void configureDataUltimaCompraField() {
        dataUltimaCompra.setAutoOpen(true);
        dataUltimaCompra.setLocale(new Locale("pt", "BR"));
        dataUltimaCompra.setMin(LocalDate.now().minusMonths(1L));
        dataUltimaCompra.setMax(LocalDate.now());
    }

    private HorizontalLayout createButtonsLayout() {
        cadastrarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        excluirButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelarButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        cadastrarButton.addClickShortcut(Key.ENTER);
        cancelarButton.addClickShortcut(Key.ESCAPE);

        cadastrarButton.addClickListener(click -> save());
        excluirButton.addClickListener(click -> delete());
        cancelarButton.addClickListener(click -> mMainView.clearSelection());

        return new HorizontalLayout(cadastrarButton, excluirButton, cancelarButton);
    }

    private void save() {
        Produto produto = binder.getBean();
        mProdutoService.save(produto);
        mMainView.updateList();
        setProduto(null);
    }

    private void delete() {
        Produto produto = binder.getBean();
        confirmationDialog.setQuestion("Excluir o produto " + produto.getNome() + "?");
        confirmationDialog.open();
        confirmationDialog.addConfirmationListener(evt -> {
            this.mProdutoService.delete(produto.getId());
            this.mMainView.updateList();
        });
    }
}
