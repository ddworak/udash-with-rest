package io.company.app.frontend.views.chat

import io.company.app.frontend.routing.ChatState
import io.company.app.frontend.services.rpc.NotificationsCenter
import io.company.app.frontend.services.{TranslationsService, UserContextService}
import io.company.app.shared.model.SharedExceptions
import io.udash._

class ChatViewFactory(
  userService: UserContextService,
  translationsService: TranslationsService,
  notificationsCenter: NotificationsCenter
) extends ViewFactory[ChatState.type] {
  import scala.concurrent.ExecutionContext.Implicits.global

  override def create(): (View, Presenter[ChatState.type]) = {
    val model = ModelProperty[ChatModel](ChatModel(Seq.empty, "", 0))

    val rpc = userService.secureRpc()
    if (rpc.isEmpty) throw SharedExceptions.UnauthorizedException()

    val presenter = new ChatPresenter(model, rpc.get.chat(), userService, notificationsCenter)
    val view = new ChatView(model, presenter, translationsService)

    (view, presenter)
  }
}
