package br.utfpr.formularios.ui;

import br.utfpr.formularios.model.Produto;
import br.utfpr.formularios.services.ProdutoService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("produtos/lista/")
public class ProdutoList extends VerticalLayout {

    private Grid<Produto> grid = new Grid<>(Produto.class);
    private TextField filterTextField = new TextField();

    private ProdutoService service;

    public ProdutoList(@Autowired ProdutoService service) {
        this.service = service;
        configureGrid();
        configureTextField();

        add(filterTextField, grid);
        updateList();
    }

    private void configureTextField() {
        filterTextField.setPlaceholder("Filtrar por nome do produto");
        filterTextField.setClearButtonVisible(true);
        filterTextField.setValueChangeMode(ValueChangeMode.LAZY);
        filterTextField.addValueChangeListener(e -> updateList());
    }

    private void updateList() {
        grid.setItems(service.findAll(filterTextField.getValue()));
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("nome", "fornecedor", "preco", "dataUltimaCompra", "status");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
