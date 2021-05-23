package tornado

import javafx.scene.Parent
import javafx.scene.layout.AnchorPane
import tornadofx.App
import tornadofx.View
import tornadofx.launch

class CryptoPriceViewerApp: App(CryptoPriceViewerView::class){}

class CryptoPriceViewerView() : View("Crypto Price Viewer"){
    override val root: AnchorPane by fxml()

}












fun main(args: Array<String>) {
    launch<CryptoPriceViewerApp>(args)
}