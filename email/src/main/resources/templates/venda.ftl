<h1>Segue os dados da compra ${venda.id}!!!</h1>

<table>
    <tr>
        <th>Produto</th>
        <th>Quantidade</th>
        <th>Valor</th>
        <th>Total</th>
    </tr>
    <#list venda.itens as item>
        <tr>
            <td>${item.produto}</td>
            <td>${item.quantidade}</td>
            <td>${item.valor}</td>
            <td>${item.valorTotal}</td>
        </tr>
    </#list>
</table>

<hr/>

Desconto: ${venda.desconto}
Total: <strong>${venda.total}</strong>

