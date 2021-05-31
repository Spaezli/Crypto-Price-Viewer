package model

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.serialization.descriptors.SerialKind
import java.io.File
import java.net.URL


object CoinDesk {
    var coins: Set<Currency>
    var alias = mutableSetOf<String>()

    init {
        val json = URL("https://api.coindesk.com/v1/bpi/supported-currencies.json").readText()
        val coindeskMapper = jacksonObjectMapper()
        coindeskMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).createArrayNode()
        coins = coindeskMapper.readValue<Set<Currency>>(json)
        coins.forEach { it ->
            alias.add(it.currency)
        }
        File("src/main/kotlin/model/CoinDeskAlias.txt").writeText(alias.joinToString(","))
    }

    data class Currency(
        val currency: String,
        val country: String,
    )

    data class RealCurrency(val cset: Set<String> = alias.toSet())



}



fun getCoinDeskCurrencys(f: File): List<CoinDesk.Currency> {
    val json = f.readText()
    val coindeskMapper = jacksonObjectMapper()
    coindeskMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).createArrayNode()
    return coindeskMapper.readValue<List<CoinDesk.Currency>>(json)
}