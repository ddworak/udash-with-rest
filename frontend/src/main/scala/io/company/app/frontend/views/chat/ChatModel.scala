package io.company.app.frontend.views.chat

import io.company.app.shared.model.chat.ChatMessage
import io.udash._

case class ChatModel(msgs: Seq[ChatMessage], msgInput: String, connectionsCount: Int)
object ChatModel extends HasModelPropertyCreator[ChatModel]
