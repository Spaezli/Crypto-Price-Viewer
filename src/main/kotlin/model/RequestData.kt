package model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import javafx.beans.property.SimpleStringProperty
import java.io.File
import java.net.URL
import java.util.*
import javax.json.JsonObject

//enum class Currency{USD,GBP,EUR}
enum class Currency {
    USD, EUR, GBP, CHF, NZD
}



data class RequestData(
    val time: Time,
    val bpi: EnumMap<Currency, Bpi>,
)

data class Time(
    val updatedISO: String,
)

data class Bpi(
    val code: String,
    val rate: String,
    val description: String,
)


fun main() {
    val coinList = getCoinDeskCurrencys(File("src/main/kotlin/model/CoinDeskCurrency.json"))
    val json = URL("https://api.coindesk.com/v1/bpi/currentprice/USD.json").readText()
    //val json = File("src/main/kotlin/data/data.json").readText()
    val mapper = ObjectMapper().registerKotlinModule()
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    val t = mapper.readValue<RequestData>(json)

    print("end")
}






