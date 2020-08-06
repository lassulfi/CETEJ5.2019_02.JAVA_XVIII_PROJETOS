package br.utfpr.formularios.ui;

import br.utfpr.formularios.model.Produto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.*;

public class ProdutoForm extends FormLayout {
    TextField nomeProdutoTextField = new TextField("Nome do produto");
    TextArea descricaoProdutoTextArea = new TextArea("Descrição do produto");
    TextField fornecedorTextField = new TextField("Fornecedor");
    EmailField fornecedorEmailField = new EmailField("E-mail do fornecedor");
    DatePicker dataUtimaCompra = new DatePicker("Última compra");
    BigDecimalField precoDecimalField = new BigDecimalField("Preço");
    ComboBox<String> status = new ComboBox<>("Status");

    Button cadastrarButton = new Button("Cadastrar");
    Button excluirButton = new Button("Excluir");
    Button cancelarButton = new Button("Cancelar");

    public ProdutoForm() {
        precoDecimalField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        precoDecimalField.setPrefixComponent(new Icon(VaadinIcon.DOLLAR));
    }
}
