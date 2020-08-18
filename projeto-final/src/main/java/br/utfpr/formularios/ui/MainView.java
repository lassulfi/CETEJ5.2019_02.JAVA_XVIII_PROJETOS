package br.utfpr.formularios.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;

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

    public MainView() {
        H1 mainTitle = new H1("Sistema de cadastro de Produtos");
        setSizeFull();

        add(mainTitle,
            createTabs());
    }

    private Tab createTabs() {
        Tab tabs = new Tab();
        tabs.add(new RouterLink("Cadastrar/Editar Produto", ProdutoForm.class));
        tabs.add(new RouterLink("Visualizar Produtos", ProdutoList.class));
        return tabs;
    }

}
