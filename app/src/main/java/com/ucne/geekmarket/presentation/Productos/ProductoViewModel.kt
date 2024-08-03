package com.ucne.geekmarket.presentation.Productos


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.geekmarket.data.local.entities.ItemEntity
import com.ucne.geekmarket.data.local.entities.ProductoEntity
import com.ucne.geekmarket.data.local.entities.PromocionEntity
import com.ucne.geekmarket.data.repository.ItemRepository
import com.ucne.geekmarket.data.repository.ProductoRepository
import com.ucne.geekmarket.data.repository.PromcionRepository
import com.ucne.geekmarket.data.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductoViewModel @Inject constructor(
    private val productoRepository: ProductoRepository,
    private val itemRepository: ItemRepository,
    private val promocionRepository: PromcionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow((ProductoUistate()))
    val uiState = _uiState.asStateFlow()

    fun onAddItem(item: ItemEntity) {
        viewModelScope.launch {
            itemRepository.AddItem(item)
        }
    }

    init {
        loadProductos()
        getPromociones()
        getProductos()
    }

    private fun getProductos() {
        viewModelScope.launch {
            productoRepository.getProductosDb().collectLatest {
                result ->
                _uiState.update {
                    it.copy(
                        laptops = result.filter { it.categoria == "Laptop" },
                        accesorios = result.filter { it.categoria == "Accesorio" },
                        laptopsGaming = result.filter { it.categoria == "Laptop-Gaming" },
                        desktops = result.filter { it.categoria == "Desktop" }
                    )
                }
            }
        }
    }

    private fun loadProductos(){
        viewModelScope.launch {
            productoRepository.loadToDb().collect{
                _uiState.update {
                    it.copy(
                       errorMessage = it.errorMessage
                    )
                }
            }
        }
    }


    private fun getPromociones() {
        viewModelScope.launch {
            promocionRepository.getApiToDb()
            promocionRepository.getPromocionesDb().collectLatest { promociones ->
                _uiState.update {
                    it.copy(
                        promociones = promociones
                    )
                }
            }
        }
    }
}


data class ProductoUistate(
    var promociones: List<PromocionEntity>? = emptyList(),
    var accesorios: List<ProductoEntity> = emptyList(),
    var laptops: List<ProductoEntity> = emptyList(),
    var laptopsGaming: List<ProductoEntity> = emptyList(),
    var desktops: List<ProductoEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)