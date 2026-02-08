package mercury.interview.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mercury.interview.core.BootstrapNetworkService
import mercury.interview.core.DebitCard
import mercury.interview.core.Transaction
import javax.inject.Inject

@HiltViewModel
class BootstrapViewModel @Inject constructor(
    private val networkService: BootstrapNetworkService
) : ViewModel() {
    private val _cards = MutableStateFlow<List<DebitCard>>(emptyList())
    val cards: StateFlow<List<DebitCard>> = _cards

    private val _transactionByCard = MutableStateFlow<Map<String, List<Transaction>>>(emptyMap())

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val response = networkService.getData()
                _cards.value = response.debitCards
                _transactionByCard.value = response.transactions.groupBy { it.cardId }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAvailableBalance(card: DebitCard) : Double {
        val cardTransactions = _transactionByCard.value[card.id] ?: emptyList()
        return card.maxMonthlySpending + cardTransactions.sumOf { it.amount }
    }

    fun getTransactions(card: String) : List<Transaction> {
        return _transactionByCard.value[card] ?: emptyList()
    }
}