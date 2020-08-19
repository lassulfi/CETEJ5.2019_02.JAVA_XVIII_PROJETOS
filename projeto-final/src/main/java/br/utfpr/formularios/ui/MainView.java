package br.utfpr.formularios.ui;

import br.utfpr.formularios.model.Produto;
import br.utfpr.formularios.services.ProdutoService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Sistema de Cadastro de Produtos",
        shortName = "Cadastro de Produtos",
        description = "Essa é uma aplicação para cadastro de produtos",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {
    private ProdutoForm mProdutoForm;
    private ProdutoList mProdutoList;

    private TextField filterTextField = new TextField();
    private Button newProdutoButton = new Button("Novo produto");

    private ProdutoService mProdutoService;


    public MainView(@Autowired ProdutoService produtoService) {
        this.mProdutoService = produtoService;

        this.mProdutoForm = new ProdutoForm(this, produtoService);
        this.mProdutoForm.setProduto(null);

        this.mProdutoList = new ProdutoList(this, produtoService);

        updateList();

        setSizeFull();

        configureTextField();

        newProdutoButton.addClickListener(click -> addNewProduto());

        H1 mainTitle = new H1("Sistema de cadastro de Produtos");

        HorizontalLayout mainContentLayout = new HorizontalLayout(mProdutoList, mProdutoForm);
        mainContentLayout.setSizeFull();

        HorizontalLayout filterLayout = new HorizontalLayout(filterTextField, newProdutoButton);

        add(mainTitle,
            filterLayout,
            mainContentLayout);
    }

    public void updateList() {
        this.mProdutoList.updateList(filterTextField.getValue());
    }

    public void setProduto() {
        this.mProdutoForm.setProduto(this.mProdutoList.getSelectedItem());
    }

    public void clearSelection() {
        this.mProdutoList.clearSelection();
        this.mProdutoForm.setProduto(null);
    }

    private void addNewProduto() {
        this.mProdutoList.clearSelection();
        this.mProdutoForm.setProduto(new Produto());
    }

    private void configureTextField() {
        filterTextField.setPlaceholder("Filtrar por nome do produto");
        filterTextField.setClearButtonVisible(true);
        filterTextField.setValueChangeMode(ValueChangeMode.EAGER);
        filterTextField.addValueChangeListener(e -> updateList());
    }
}
