package mercury.interview.core

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    @Json(name = "debitCards") val debitCards: List<DebitCard>,
    @Json(name = "transactions") val transactions: List<Transaction>
)

@JsonClass(generateAdapter = true)
data class DebitCard(
    val id: String,
    val nickname: String?,
    val status: String,
    val cardRealm: String,
    val last4Digits: String,
    val userFirstName: String,
    val userLastName: String,
    val maxMonthlySpending: Double
)

@JsonClass(generateAdapter = true)
data class Transaction(
    val amount: Double,
    val cardId: String,
    val status: String,
    val createdAt: String,
    val postedAt: String?,
    val description: String,
    val recipientUrl: String?
)