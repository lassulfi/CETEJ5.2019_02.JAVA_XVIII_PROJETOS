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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Route("produtos/cadastro/")
public class ProdutoForm extends FormLayout {
    private TextField nomeProdutoTextField = new TextField("Nome do produto");
    private TextArea descricaoProdutoTextArea = new TextArea("Descrição do produto");
    private TextField fornecedorTextField = new TextField("Fornecedor");
    private EmailField fornecedorEmailField = new EmailField("E-mail do fornecedor");
    private DatePicker dataUtimaCompra = new DatePicker("Última compra");
    private BigDecimalField precoDecimalField = new BigDecimalField("Preço");
    private ComboBox<Produto.Status> statusComboBox = new ComboBox<>("Status");

    private Button cadastrarButton = new Button("Cadastrar");
    private Button excluirButton = new Button("Excluir");
    private Button cancelarButton = new Button("Cancelar");


    private ProdutoService service;

    public ProdutoForm(@Autowired ProdutoService service) {
        this.service = service;

        configureComponents();

        add(nomeProdutoTextField,
            descricaoProdutoTextArea,
            fornecedorTextField,
            fornecedorEmailField,
            dataUtimaCompra,
            precoDecimalField,
            statusComboBox,
            createButtonsLayout());
    }


    private void configureComponents() {
        nomeProdutoTextField.setErrorMessage("O nome do produto deve ser informado");
        fornecedorEmailField.setErrorMessage("O e-mail do fornecedor deve ser informado");

        statusComboBox.setItems(Produto.Status.values());

        descricaoProdutoTextArea.getStyle()
                .set("maxHeight", "250px")
                .set("minHeight", "150px");

        precoDecimalField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        precoDecimalField.setPrefixComponent(new Icon(VaadinIcon.DOLLAR));

        dataUtimaCompra.setAutoOpen(true);
        dataUtimaCompra.setMin(LocalDate.now().minusMonths(1L));
        dataUtimaCompra.setMax(LocalDate.now());
    }

    private HorizontalLayout createButtonsLayout() {
        cadastrarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        excluirButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelarButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        cadastrarButton.addClickShortcut(Key.ENTER);
        cancelarButton.addClickShortcut(Key.ESCAPE);

        cadastrarButton.addClickListener(click -> {
            Produto produto = getPodutoFromForm();
            service.save(produto);
        });
        excluirButton.addClickListener(click -> {
            Produto produto = service.findByName(nomeProdutoTextField.getValue());
            service.delete(produto.getId());
        });

        return new HorizontalLayout(cadastrarButton, excluirButton, cancelarButton);
    }

    private Produto getPodutoFromForm() {
        String nome = nomeProdutoTextField.getValue();
        String descricao = descricaoProdutoTextArea.getValue();
        BigDecimal preco = precoDecimalField.getValue();
        Date dataUltimaCompra = Date.from(dataUtimaCompra.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String emailFornecedor = fornecedorEmailField.getValue();
        String fornecedor = fornecedorTextField.getValue();
        Produto.Status status = statusComboBox.getValue();

        return Produto
                .builder()
                .nome(nome)
                .descricao(descricao)
                .preco(preco)
                .dataUltimaCompra(dataUltimaCompra)
                .emailFornecedor(emailFornecedor)
                .fornecedor(fornecedor)
                .status(status)
                .build();
    }
}
