package br.utfpr.formularios.ui;

import br.utfpr.formularios.model.Produto;
import br.utfpr.formularios.services.ProdutoService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

public class ProdutoList extends VerticalLayout {

    private Grid<Produto> mGrid = new Grid<>(Produto.class);
    private MainView mMainView;
    
    private final ProdutoService mProdutoService;

    public ProdutoList(MainView mainView, @Autowired ProdutoService service) {
        this.mMainView = mainView;
        this.mProdutoService = service;

        configureGrid();

        add(mGrid);
    }

    public void updateList(String filteredText) {
        mGrid.setItems(mProdutoService.findAll(filteredText));
    }

    public Produto getSelectedItem() {
        return this.mGrid.asSingleSelect().getValue();
    }

    public void clearSelection() {
        this.mGrid.asSingleSelect().clear();
    }

    private void configureGrid() {
        mGrid.setSizeFull();
        mGrid.setColumns("nome", "fornecedor", "preco", "dataUltimaCompra", "status");
        mGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        mGrid.asSingleSelect().addValueChangeListener(evt -> mMainView.setProduto());
    }
}
