package mercury.interview.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import mercury.interview.core.DebitCard
import mercury.interview.core.Transaction
import mercury.interview.ui.vm.BootstrapViewModel
@Composable fun MainContent(vm : BootstrapViewModel = hiltViewModel(), navController: NavController){
    NavHost(
        navController = navController as androidx.navigation.NavHostController,
        startDestination = "card_list"
    ) {
        composable("card_list") {
            CardListScreen(vm, onCardClick = {
                cardId -> navController.navigate("transactions/$cardId")
            })
        }

        composable("transactions/{cardId}") { backStackEntry ->
            val cardId = backStackEntry.arguments?.getString("cardId") ?: ""
            TransactionsScreen(cardId = cardId, vm = vm, onBackClick = { navController.popBackStack() })
        }
    }
}

@Composable
fun CardListScreen(vm: BootstrapViewModel, onCardClick: (String) -> Unit) {
    val cards: List<DebitCard> by vm.cards.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Card List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            items(cards) { card ->
                CardRow(card = card, vm = vm, onClick = { onCardClick(card.id.toString()) })
                HorizontalDivider()
            }
        }
    }
}
@Composable
fun CardRow(card: DebitCard, vm: BootstrapViewModel, onClick: () -> Unit) {
    val balance = vm.getAvailableBalance(card)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${card.userFirstName} ${card.userLastName}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = card.status.uppercase(),
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "${card.nickname ?: "Card"} **** ${card.last4Digits}")

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Available: $${String.format("%.2f", balance)}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun TransactionsScreen(cardId: String, vm: BootstrapViewModel, onBackClick: () -> Unit) {
    val transactions = vm.getTransactions(cardId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transactions") },
                navigationIcon = {
                    IconButton(onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        if (transactions.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No transactions found")
            }
        } else {
            LazyColumn(contentPadding = innerPadding) {
                items(transactions) { transaction ->
                    TransactionRow(transaction)
                    HorizontalDivider()
                }
            }
        }
    }
}



@Composable
fun TransactionRow(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.description,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = transaction.createdAt.take(10),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
        Text(
            text = "$${String.format("%.2f", transaction.amount)}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = if (transaction.amount > 0) Color(0xFF006400) else Color.Black
        )
    }
}

