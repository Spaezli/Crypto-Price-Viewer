package model

import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.fasterxml.jackson.core.json.UTF8DataInputJsonParser
import com.fasterxml.jackson.databind.DeserializationConfig
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlinx.coroutines.*
import java.net.URL
import java.util.*
import javax.management.Query.value
import kotlin.system.measureTimeMillis

//enum class Currency{USD,GBP,EUR}
enum class Currency {
    USD, MWK, GMD, MZN
}

/**
 * This data-class structure represents the one from the API (json) request.
 * The Default constructor was set, to be able to loop over the various @constructor bpi values received
 */
@JacksonXmlRootElement
data class RequestData(
    val time: Time = Time(),
   // @JsonAnySetter
    //val bpi : Pair<String, Bpi> = Pair("",Bpi())
    val bpi: EnumMap<Currency, Bpi> = emptyMap<Currency, Bpi>() as EnumMap<Currency, Bpi>,
    //val bpi: Map<String, Bpi> = emptyMap<String, Bpi>(),
)

data class Time(
    val updatedISO: String = "",
)

data class Bpi(
    val code: String = "",
    val rate: String = "",
    val description: String = "",
)

fun prettyPrint(r: RequestData) {

}

/**
 * @exception SupervisorJob makes sure the parent routine doesn't get abort when one of it coroutine does. try and catch must be implemented as well.
 * @param time may be used to determine from which Threadpool the async function take use of.
 */
fun apiRequests(): List<RequestData> {
    val sum: List<RequestData>
    val time = measureTimeMillis {
        runBlocking {
            //XAU, VEF
            val deferred: List<Deferred<RequestData>> =
                Currency.values().map { it ->
                    async(Dispatchers.IO + SupervisorJob()) {
                        println(Thread.currentThread())
                        println(it)
                            val mapper = ObjectMapper().registerKotlinModule()
                            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                            mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                            val json = URL("https://api.coindesk.com/v1/bpi/currentprice/$it.json").readText()
                            mapper.readValue<RequestData>(json) as RequestData
                    }
                }
            sum = deferred.awaitAll()
        }
    }
    println(time)
    return sum
}
/*
fun prettyRequests(o: List<RequestData>) {
    println("The following list shows various currencies in relation to Bitcoin")
    println("------------------------------------------------------------------")

    o.forEach { it ->
        it.bpi.forEach { currency, bpi ->
            if (currency != "USD") {
                println(bpi.description)
                println(bpi.rate)
                println("---------------------")
            }
        }
    }

}
*/

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

    val t = CoinDesk.alias.toSet()
    val data = apiRequests()
      //prettyRequests(data)
    //SetValuedMap<>


    print("end")
}






