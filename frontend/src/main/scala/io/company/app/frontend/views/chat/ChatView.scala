package io.company.app.frontend.views.chat

import io.company.app.frontend.services.TranslationsService
import io.company.app.shared.css.ChatStyles
import io.company.app.shared.i18n.Translations
import io.udash._
import io.udash.bootstrap.button.{ButtonStyle, UdashButton}
import io.udash.bootstrap.form.{UdashForm, UdashInputGroup}
import io.udash.bootstrap.panel.UdashPanel
import io.udash.bootstrap.utils.UdashIcons.FontAwesome
import io.udash.css._
import io.udash.i18n._

class ChatView(model: ModelProperty[ChatModel], presenter: ChatPresenter, translationsService: TranslationsService)
  extends FinalView with CssView {

  import translationsService._

  import scalatags.JsDom.all._

  private val messagesWindow = div(
    ChatStyles.messagesWindow,
    repeat(model.subSeq(_.msgs)) { msgProperty =>
      val msg = msgProperty.get
      div(
        ChatStyles.msgContainer,
        strong(msg.author, ": "),
        span(msg.text),
        span(ChatStyles.msgDate, msg.created.toString)
      ).render
    }
  )

  // Standard Udash TextInput (we don't need Bootstrap Forms input wrapping)
  private val msgInput = TextInput(model.subProp(_.msgInput))(
    translatedAttrDynamic(Translations.Chat.inputPlaceholder, "placeholder")(_.apply())
  )

  // Button from Udash Bootstrap wrapper
  private val submitButton = UdashButton(
    buttonStyle = ButtonStyle.Primary,
    block = true, componentId = ComponentId("send")
  )(span(FontAwesome.send), tpe := "submit")

  private val msgForm = div(
    UdashForm(
      _ => {
        presenter.sendMsg()
        true // prevent default callback call
      }
    )(
      componentId = ComponentId("msg-from"),

      // disable form if user don't has write access
      UdashForm.disabled(Property(!presenter.hasWriteAccess)) {
        UdashInputGroup()(
          UdashInputGroup.input(msgInput.render),
          UdashInputGroup.buttons(submitButton.render)
        ).render
      }
    ).render
  )

  override def getTemplate: Modifier = div(
    UdashPanel(componentId = ComponentId("chat-panel"))(
      UdashPanel.heading(
        produce(model.subProp(_.connectionsCount)) { count =>
          span(translatedDynamic(Translations.Chat.connections)(_.apply(count))).render
        }
      ),
      UdashPanel.body(messagesWindow),
      UdashPanel.footer(msgForm)
    ).render
  )
}
