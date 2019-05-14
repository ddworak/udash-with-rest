package io.company.app.frontend.services.rpc

import io.company.app.shared.rpc.client.MainClientRPC
import io.company.app.shared.rpc.client.chat.ChatNotificationsRPC

class RPCService(notificationsCenter: NotificationsCenter) extends MainClientRPC {
  override val chat: ChatNotificationsRPC =
    new ChatService(notificationsCenter.msgListeners, notificationsCenter.connectionsListeners)
}