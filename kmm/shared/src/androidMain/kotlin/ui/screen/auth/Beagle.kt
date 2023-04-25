package ui.screen.auth

//import com.pandulapeter.beagle.Beagle
//import com.pandulapeter.beagle.common.configuration.toText
//import com.pandulapeter.beagle.common.contracts.BeagleListItemContract
//import com.pandulapeter.beagle.modules.DividerModule
//import com.pandulapeter.beagle.modules.ItemListModule
//import com.pandulapeter.beagle.modules.TextModule
//
//internal fun createBeagleModules(
//    onItemSelected: (user: User) -> Unit,
//) = arrayOf(
//    DividerModule(),
//    TextModule(text = "Login", type = TextModule.Type.SECTION_HEADER),
//    ItemListModule(
//        items = listOf(
//            User("user@halcyonmobile.com", "12345678"),
//            User("asd@gmail.com", "123456"),
//            User("mova@gmail.com", "123456"),
//        ),
//        isExpandedInitially = true,
//        onItemSelected = { user ->
//            onItemSelected(user)
//            Beagle.hide()
//        },
//        title = "Test Staging Login Credentials"
//    )
//)
//
//internal data class User(val email: String, val password: String) : BeagleListItemContract {
//    override val id = email
//    override val title = email.substringBefore("@").toText()
//}