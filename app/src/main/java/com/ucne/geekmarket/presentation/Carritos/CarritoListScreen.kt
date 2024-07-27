package com.ucne.geekmarket.presentation.Carritos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ucne.geekmarket.data.local.entities.ItemEntity
import com.ucne.geekmarket.presentation.Common.formatNumber
import com.ucne.geekmarket.presentation.Productos.ProductoViewModel
import com.ucne.geekmarket.presentation.components.CenteredTextDivider

@Composable
fun CarritoListScreen(
    innerPadding: PaddingValues,
    viewModel: CarritoViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val items by viewModel.items.collectAsStateWithLifecycle()

    CarritoListScreenBody(
        uiState = uiState,
        items = items,
        innerPadding = innerPadding,
        onRemoveItem = viewModel::deleteItem,
        calcularTotal = { viewModel.calcularTotal() }
    )
}

@Composable
fun CarritoListScreenBody(
    uiState: carritoUistate,
    innerPadding: PaddingValues,
    onRemoveItem: (ItemEntity) -> Unit,
    calcularTotal: () -> Unit,
    items: List<ItemEntity>
) {

    LazyColumn(modifier = Modifier.padding(innerPadding)) {
        items(uiState.items ?: emptyList()) { item ->
            CartItemCard(
                uiState = uiState,
                item = item,
                onRemoveItem = onRemoveItem,
                calcularTotal = calcularTotal
            )
        }
        item {
            val total = uiState.total
            CenteredTextDivider(text = "Total: $${formatNumber(total)} ")
        }
    }

}

@Composable
fun CartItemCard(
    uiState: carritoUistate,
    calcularTotal: () -> Unit,
    item: ItemEntity,
    onRemoveItem: (ItemEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        val producto = uiState.productos?.find { it.productoId == item.productoId }
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = producto?.imagen,
                    contentDescription = producto?.nombre,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Row {
                        Text(
                            text = producto?.nombre ?: "",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(0.30f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$${formatNumber(producto?.precio)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "x${formatNumber(item.cantidad?.toDouble())}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                        IconButton(
                            onClick = {
                                onRemoveItem(item)
                                calcularTotal()
                            },
                            modifier = Modifier.padding(1.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove Item"
                            )
                        }
                    }
                    Text(
                        text = "Monto: ${formatNumber( item.monto)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
