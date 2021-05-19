package model

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlinx.coroutines.*
import java.net.URL
import java.util.*
import kotlin.system.measureTimeMillis

//enum class Currency{USD,GBP,EUR}
enum class Currency {
    USD,MWK,GMD,MZN
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

fun apiRequests(): List<RequestData> {
    val sum: List<RequestData>
    val time = measureTimeMillis {
        runBlocking {
            val deferred: List<Deferred<RequestData>> =
                Currency.values().map { it ->
                    async(Dispatchers.IO) {
                        println(Thread.currentThread())
                        val json = URL("https://api.coindesk.com/v1/bpi/currentprice/$it.json").readText()
                        val mapper = ObjectMapper().registerKotlinModule()
                        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        mapper.readValue<RequestData>(json)
                    }
                }
            sum = deferred.awaitAll()
        }
    }
    println(time)
    return sum
}


fun main() {
//    //val coinList = getCoinDeskCurrencys(File("src/main/kotlin/model/CoinDeskCurrency.json"))
//    val test = CoinDesk.alias
//
//    val json = URL("https://api.coindesk.com/v1/bpi/currentprice/USD.json").readText()
//    //val json = File("src/main/kotlin/data/data.json").readText()
//    val mapper = ObjectMapper().registerKotlinModule()
//    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//    val t = mapper.readValue<RequestData>(json)
    //val data = apiRequests()


    val test = apiRequests()
    print("end")
}






