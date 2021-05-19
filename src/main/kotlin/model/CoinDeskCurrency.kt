package model

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File


class CoinDesk() {
    var coins: Set<Currency>

    init {
        val json = File("src/main/kotlin/model/CoinDeskCurrency.json").readText()
        val coindeskMapper = jacksonObjectMapper()
        coindeskMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).createArrayNode()
        coins = coindeskMapper.readValue<Set<Currency>>(json)
    }

    data class Currency(
        val currency: String,
        val country: String,
    )
}


fun getCoinDeskCurrencys(f: File): List<CoinDesk.Currency> {
    val json = f.readText()
    val coindeskMapper = jacksonObjectMapper()
    coindeskMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).createArrayNode()
    return coindeskMapper.readValue<List<CoinDesk.Currency>>(json)
}